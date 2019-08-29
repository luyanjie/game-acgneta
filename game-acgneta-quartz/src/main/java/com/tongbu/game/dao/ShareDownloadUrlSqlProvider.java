package com.tongbu.game.dao;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * @author jokin
 * @date 2018/11/21 16:29
 */
public class ShareDownloadUrlSqlProvider {

    private static final String DOWNLOAD = "tbShareDownloadUrl";

    private static final String DOWNLOAD_GAME = "tbShareDownloadUrl_Game";

    public String list()
    {
        String sql = new SQL(){{
            SELECT("id,sku,version,url,real_sku as realSku,regisndesc");
            FROM(DOWNLOAD_GAME);
            WHERE("sku=#{sku} and active=1 and regisndesc=#{regisndesc}");
            ORDER_BY("id desc");
        }}.toString();
        return sql;
    }

    /**
     * 执行更新
     * */
    public String execute(Map<String, Object> parameter) {
        int active = (int) parameter.getOrDefault("active", 0);
        String sql = String.format("update tbShareDownloadUrl_Game set active=1 where regisndesc=#{teamName} and inserttime>=#{startTime} and %s",
                active == 0 ? " active=-10" : "(active=-10 or active=9)");
        return sql;
    }
}
