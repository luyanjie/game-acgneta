package com.tongbu.game.dao;

import com.tongbu.game.service.dynamics.IUserDynamicsService;

import java.util.List;
import java.util.Map;

public class AccountSqlProvider {

    public String updateScore(Map<String, Object> parameter)
    {
        StringBuilder sb = new StringBuilder();
        List<String> list = (List<String>) parameter.get("list");
        list.forEach(x->{
            String[] str = x.split(IUserDynamicsService.CACHE_SCORE_LIST_SPLIT);
            if(str.length == 2){
                sb.append("update tbMember_Game2Element_Info set ")
                        .append(" Score=Score+").append(str[1])
                        .append(" where id=").append(str[0])
                        .append(" ;");
            }
        });
        System.out.println(sb.toString());
        return sb.toString();
    }
}
