package com.jjf.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jjf_lenovo on 2017/5/31.
 */

/**
 * 正则表达式提取字符
 */
public class RegexMatches {
    public static void main( String args[] ){

        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(\\D*)(\\d+)(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find( )) {
            for(int i=0;i<=m.groupCount();i++){
                System.out.println("Found value: " + m.group(i) );
            }
        } else {
            System.out.println("NO MATCH");
        }
    }
}
