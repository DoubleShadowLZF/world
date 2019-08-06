package org.string;

/**
 * 字符串替换
 */
public class Replacal {
    /**
     * 将一串字符串中的空格替换成“%20”
     *
     * @return
     */
    public static String replace(String text) {
        char[] cs = text.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cs.length; i++) {
            if(cs[i] == ' '){
                sb.append("%20");
            }else{
                sb.append(cs[i]);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String replace = Replacal.replace(" text 123");
        System.out.println(replace);
    }
}
