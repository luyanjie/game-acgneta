package com.tongbu.game.common.constant;

import java.util.HashMap;

/**
 * @author jokin
 * @date 2018/9/20 11:21
 *
 * 捏他 友盟信息：
 *  账号：401120187@qq.com
 *  密码：jie88854859
 *
 */
public class UmengConstant {
    public static class Android{
        /**
         * acgneta Android 包名
         * */
        public static final String PACK_NAME = "scholar.acgneta.com.acgneta";
        /**
         * PUSH Appkey
         * */
        public static final String APPKEY = "5b6a8106f43e480edc00017a";

        /**
         * Umeng Message Secret
         * */
        public static final String UMENG_MESSAGE_SECRET = "0ca3d0f9b7d5504791c3cb5a01c8a5f9";
        /**
         * App Master Secret
         * */
        public static final String APP_MASTER_SECRET = "8p0gawi9kjf5vnrrtcn2grsmhhbvndwe";

        public static final String PUSH_ICON = "http://im5.acgneta.com/acgneta/umeng/push/ic_notify_logo.png";
    }

    public static class IOS{
        /**
         * acgneta Android 包名
         * */
        public static final String PACK_NAME = "com.tongbu.ACGNeta";
        public static final String APPKEY = "5b7bae78a40fa30b03000086";
        public static final String UMENG_MESSAGE_SECRET = "";
        public static final String APP_MASTER_SECRET = "qndw2kn5k3ahomjjhypykjroihuttns4";
        /**
         * 声音显示，这里使用默认的
         * */
        public static final String SOUND = "default";
    }

    public static final String CUSTOMZIED_FIELD_CONTENT = "content";

    public static final String CUSTOMZIED_FIELD_TYPE_SOURCE = "typeSource";

    public static final String TITLE = "source";

    private static HashMap<String,String> umengTitle = new HashMap<>();

    private static HashMap<Integer,String> umengDescription = new HashMap<>();


    public static HashMap<String,String> getUmengTitle(){
        return umengTitle;
    }

    public static HashMap<Integer,String> getUmengDescription(){
        return umengDescription;
    }

    static {
        umengTitle.put("source1","你获得了一个优评~");
        // 动画的
        umengTitle.put("source2","有小伙伴赞了你");
        umengTitle.put("source3","有小伙伴回复了你");

        // 问答的
        /*umengTitle.put("source22","有小伙伴赞了你的评论");
        umengTitle.put("source23","有小伙伴回答了你的问题");*/

        umengDescription.put(1,"系统消息");
        umengDescription.put(2,"点赞");
        umengDescription.put(3,"新的回复");
    }
}
