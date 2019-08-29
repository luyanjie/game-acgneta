package com.tongbu.game.common;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskUtil {

    public static String replaceHtmlTag(String htmlContent){
        String result = htmlContent;
        if(!StringUtils.isNoneEmpty(htmlContent)){
            return htmlContent;
        }

        result = replaceByRegex(result,"<!--(.|[\r\n])*?-->");
        result = replaceByRegex(result,"<p>更多相关资讯请关注.*?</p>");
        result = replaceByRegex(result,"<h2 class=\"post_h_quote\">(.|[\r\n])*?</h2>");
        result = replaceByRegex(result,"class=\".*?\"");
        result = replaceByRegex(result,"style=\".*?\"");
        result = replaceByRegex(result,"id=\".*?\"");
        result = replaceByRegex(result,"align=\".*?\"");
        result = replaceByRegex(result,"<noscript>(.|[\r\n])*?</noscript>");
        result = replaceByRegex(result,"<a (.|[\r\n])*?>");
        result = result.replace("</a>","");
        return result;
    }

    /**
     * 替换Html相关的一些标签
     * @param content
     * @param pattern
     * @return
     */
    private static String replaceByRegex(String content, String pattern){
        String result = content;
        try{
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(content);
            while(m.find()){
                result = result.replace(m.group(),"");
            }
//            if(m.find()){
//                int count = m.groupCount();
//                for (int i = 0; i < m.groupCount(); i++) {
//                    result = result.replace(m.group(i),"");
//                }
//
//            }
        }catch(Exception e){

        }
        return result;
    }


}
