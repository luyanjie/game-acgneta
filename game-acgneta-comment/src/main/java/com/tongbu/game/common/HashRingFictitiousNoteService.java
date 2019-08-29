package com.tongbu.game.common;

import java.util.*;

/**
 * 一致性hash算法：借助虚拟节点，实现分布平衡的hash算法
 */
public class HashRingFictitiousNoteService {
    /**
     * 待添加入Hash环的服务器列表
     */
    private static String[] servers = {"UserDynamics_1", "UserDynamics_2", "UserDynamics_3", "UserDynamics_4", "UserDynamics_5",
            "UserDynamics_6", "UserDynamics_7", "UserDynamics_8", "UserDynamics_9", "UserDynamics_a"};

    /**
     * 真实结点列表,考虑到服务器上线、下线的场景，即添加、删除的场景会比较频繁，这里使用LinkedList会更好
     */
    private static List<String> realNodes = new LinkedList<>();
    /**
     * 虚拟节点，key表示虚拟节点的hash值，value表示虚拟节点的名称
     */
    private static SortedMap<Integer, String> virtualNodes =
            new TreeMap<>();

    /**
     * 虚拟节点的数目，这里写死，为了演示需要，一个真实结点对应5个虚拟节点
     */
    private static final int VIRTUAL_NODES = 5;

    private static final String SPLIT = "&&";

    static {
        // 先把原始的服务器添加到真实结点列表中
        Collections.addAll(realNodes, servers);
        // 再添加虚拟节点，遍历LinkedList使用foreach循环效率会比较高
        for (String str : realNodes) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                String virtualNodeName = str + SPLIT + i;
                int hash = getHash(virtualNodeName);
                virtualNodes.put(hash, virtualNodeName);
            }
        }
    }

    /**
     * 使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
     */
    public static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        return hash < 0 ? Math.abs(hash) : hash;
    }

    /**
     * 得到应当路由到的结点
     */
    public static String getServer(String node) {
        // 得到带路由的结点的Hash值
        int hash = getHash(node);
        // 得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);
        String virtualNode;
        // 第一个Key就是顺时针过去离node最近的那个结点
        // 没有比他大的就取整个集合里面的第一个
        if (subMap.size() == 0) {
            virtualNode = virtualNodes.get(virtualNodes.firstKey());
        } else {
            // 有比他大的取比他大的第一个
            virtualNode = subMap.get(subMap.firstKey());
        }
        // 返回对应的虚拟节点名称，这里字符串稍微截取一下
        return virtualNode.substring(0, virtualNode.indexOf(SPLIT));
    }

    /**
     * 得到应当路由到的结点
     */
    public static String getServer(int num){
        return getServer(String.valueOf(num));
    }
}
