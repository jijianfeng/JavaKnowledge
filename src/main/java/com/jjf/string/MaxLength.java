package com.jjf.string;

/**
 * 寻找最长不重复字符
 *
 * @author IronMan 2020/07/24
 */
public class MaxLength {
    public static void main(String[] args) {
        System.out.println(maxString("12345678"));
    }

    public static String maxString(String ss) {
        String maxString = "";
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < ss.length(); i++) {
            char s = ss.charAt(i);
            // 不包含
            if (temp.indexOf(String.valueOf(s)) != -1) {
                if (maxString.length() < temp.length()) {
                    maxString = temp.toString();
                }
                temp = new StringBuilder();
            }
            temp.append(ss.charAt(i));
            if (i == ss.length() - 1 && temp.length() > maxString.length()) {
                return temp.toString();
            }
        }
        return maxString;
    }
}
