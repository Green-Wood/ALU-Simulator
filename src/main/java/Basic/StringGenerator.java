package Basic;

import java.util.Arrays;

public class StringGenerator {
    // TODO  优化所有的生成重复字符串
    public static String repeat(char c, int times) {
        char[] arr = new char[times];
        Arrays.fill(arr, c);
        return String.valueOf(arr);
    }

    // TODO  取反
    public static String getReverse(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++){         // s2 取反
            if (s.charAt(i) == '0') sb.append('1');
            else sb.append('0');
        }
        return sb.toString();
    }
}
