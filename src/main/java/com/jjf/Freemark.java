package com.jjf;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jijianfeng on 2017/6/27.
 */
public class Freemark {
    public static void main(String[] args){
//        StringBuilder code = getCode("inst_account", InstAccountEntity.class);
//        System.out.println(code);
    }

    public static StringBuilder getCode(String tableName, Class classz){
        String pre = "<?xml version='1.0' encoding='UTF-8'?>\n<dataset>\n    ";
        String after = "\n    />\n</dataset>";
        Field[] fields = classz.getDeclaredFields();
        StringBuilder sb = new StringBuilder(pre);
        sb.append("<"+tableName+"  ");
        for (Field field : fields) {
            if(field.getName().equals("serialVersionUID")){
                continue;
            }
            sb.append(camel2Underline(field.getName()));
            int value = new Random().nextInt(10);
            sb.append("=\""+value+"\"  ");
        }
        sb.append(after);
        return sb;
    }

    /**
     * 下划线转驼峰法
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 驼峰法转下划线
     *
     * @param str 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
