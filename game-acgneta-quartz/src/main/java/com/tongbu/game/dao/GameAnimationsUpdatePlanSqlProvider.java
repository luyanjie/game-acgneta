package com.tongbu.game.dao;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class GameAnimationsUpdatePlanSqlProvider {

    private static final String FIELDS = "id,animationId,platformCode,url,nextUpdateDate,intervalDay,lastEpisode,lastUpdateTime,urlApiParamter";

    private static final String TABLES = "gameAnimationsUpdatePlan";

    public String updatePlanWeek(Map<String, Object> parameter) {
        SQL sql = new SQL() {{
            SELECT(FIELDS);
            FROM(TABLES);
            WHERE("NextUpdateDate<@today and LastUpdateTime<NextUpdateDate and status=1");
        }};
        return sql.toString();
    }

    public String updatePlanToday(Map<String, Object> parameter) {
        SQL sql = new SQL() {{
            SELECT(FIELDS);
            FROM(TABLES);
            WHERE("NextUpdateDate=@today and LastUpdateTime<@today and status=1");
        }};
        return sql.toString();
    }
}
