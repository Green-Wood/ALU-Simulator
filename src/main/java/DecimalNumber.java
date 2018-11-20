import Basic.*;

/**
 *一种神奇的十进制数的二进制表达方法。
 *这里我们使用了33位二进制数来表达（1位符号位和32位数据位）
 * 例如：15 -> 0 0000 0000 0000 0000 0000 0000 0001 0101       -22 -> 1 0000 0000 0000 0000 0000 0000 0010 0010
 * 还未实现这种表达方式的乘除法
 * @author greenwood
 */
public class DecimalNumber extends ALU {

    private char preC;          // 检测是否产生进位

    public DecimalNumber(String n1, String n2) {
        super(n1, n2);
    }

    @Override
    protected String toDecimal(String bin) {               // 二进制转十进制
        StringBuilder decimal = new StringBuilder();
        for (int i = 1; i < 33; i += 4) {
            String num = bin.substring(i, i + 4);
            int ans = 0;
            for (int j = 0; j < num.length(); j++) {
                ans *= 2;
                ans += num.charAt(j) - '0';
            }
            if (ans != 0 || decimal.length() != 0) decimal.append(ans);
        }
        if (decimal.length() == 0) return "0";
        if (bin.charAt(0) == '1') decimal.insert(0, '-');
        return decimal.toString();
    }

    @Override
    protected String toBinary(String n) {               // 十进制转二进制
        StringBuilder sb = new StringBuilder();
        if (n.charAt(0) == '-') {
            sb.append(1);
            n = n.substring(1);
        } else {
            sb.append(0);
        }
        for (int i = 0; i < 32 - 4 * n.length(); i++) {
            sb.append('0');
        }
        for (int i = 0; i < n.length(); i++) {
            StringBuilder num = new StringBuilder(Integer.toBinaryString(n.charAt(i) - '0'));
            while (num.length() < 4) {
                num.insert(0, "0");
            }
            sb.append(num);
        }
        return sb.toString();
    }

    @Override
    protected String add(String s1, String s2) {                  // 符号相同则相加，否则相减
        if (s1.charAt(0) == s2.charAt(0)) return innerAdd(s1, s2);
        else return innerSub(s1, s2);
    }

    private String innerAdd(String s1, String s2) {                 // 可看作是无符号数的相加，结果与s1的符号相同
        StringBuilder sb = new StringBuilder();
        sb.append(s1.charAt(0));
        preC = '0';
        for (int i = 29; i >= 1; i -= 4) {
            SerialAdder adder = new SerialAdder(s1.substring(i, i + 4), s2.substring(i, i + 4));
            String ans = adder.calculate(preC);
            preC = adder.nextC;
            if (preC == '1' || (ans.charAt(0) == '1' && (ans.charAt(1) == '1' || ans.charAt(2) == '1'))) {
                adder = new SerialAdder(ans, "0110");
                ans = adder.calculate('0');
                preC = Arithmetic.OR(adder.nextC, preC);
            }
            sb.insert(1, ans);
        }
        return sb.toString();
    }

    @Override
    protected String sub(String s1, String s2) {                         // 符号相同则相减，否则相加
        if (s1.charAt(0) == s2.charAt(0)) return innerSub(s1, s2);
        else return innerAdd(s1, s2);
    }

    private String innerSub(String s1, String s2) {              // 可看作是无符号数的相减，如果结果产生进位，证明减成功
        s2 = reverse(s2);                                        // 若没有产生进位，则需要对结果进行"取反加一"
        String ans = innerAdd(s1, s2);
        if (preC == '0') {
            ans = reverse(ans);
        }
        return ans;
    }

    private String reverse(String s) {                       // 对数进行"取反加一"
        StringBuilder reverse = new StringBuilder();
        for (int i = 1; i < 33; i += 4) {
            String subString = s.substring(i, i + 4);
            subString = new SerialAdder(subString, "0110").calculate('0');        // 加6后取反
            for (int j = 0; j < subString.length(); j++) {
                if (subString.charAt(j) == '0') reverse.append('1');
                else reverse.append('0');
            }
        }
        String ans = reverse.toString();
        ans = new SerialAdder(ans, "00000000000000000000000000000000").calculate('1');     // 加一
        if (s.charAt(0) == '0') ans = "1" + ans;
        else ans = "0" + ans;
        return ans;
    }


    @Override
    protected String multi(String s1, String s2) {
        return null;
    }

    @Override
    protected void division(String s1, String s2) { }
}
