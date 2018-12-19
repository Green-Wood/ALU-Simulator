import Basic.*;

/**
 * 一种神奇的十进制数的二进制表达方法。
 * 这里我们使用了33位二进制数来表达（1位符号位和32位数据位）
 * 例如：15 -> 0 0000 0000 0000 0000 0000 0000 0001 0101       -22 -> 1 0000 0000 0000 0000 0000 0000 0010 0010
 * 还未实现这种表达方式的乘除法
 * @author greenwood
 */
public class DecimalNumber extends AbstractALU {

    @Override
    protected String toDecimal(String bin) {               // 二进制转十进制
        StringBuilder decimal = new StringBuilder();
        for (int i = 1; i < bin.length(); i += 4) {
            String binaryValue = bin.substring(i, i + 4);
            int decimalValue = 0;
            for (int j = 0; j < binaryValue.length(); j++) {
                decimalValue *= 2;
                decimalValue += binaryValue.charAt(j) - '0';
            }
            if (decimalValue != 0 || decimal.length() != 0) decimal.append(decimalValue);
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
        if (s1.charAt(0) == s2.charAt(0)) return unsignedNumberAdd(s1, s2);
        else return unsignedNumberSub(s1, s2);
    }

    @Override
    protected String sub(String s1, String s2) {                         // 将减法变为加法
        if (s2.charAt(0) == '1') s2 = '0' + s2.substring(1);
        else s2 = '1' + s2.substring(1);
        return add(s1, s2);
    }

    private String unsignedNumberAdd(String s1, String s2) {                 // 可看作是无符号数的相加，结果与s1的符号相同
        StringBuilder sb = new StringBuilder();
        sb.append(s1.charAt(0));
        char preC = '0';
        for (int i = 29; i >= 1; i -= 4) {
            adder.setOperand(s1.substring(i, i + 4), s2.substring(i, i + 4));
            String ans = adder.calculate(preC);
            preC = adder.nextC;
            if (preC == '1' || (ans.charAt(0) == '1' && (ans.charAt(1) == '1' || ans.charAt(2) == '1'))) {
                ans = adder.setOperand(ans, "0110").calculate('0');
                preC = Arithmetic.OR(adder.nextC, preC);
            }
            sb.insert(1, ans);
        }
        return sb.toString();
    }

    private String unsignedNumberSub(String s1, String s2) {              // 可看作是无符号数的相减，如果结果产生进位，证明减成功
        s2 = reverse(s2);                                        // 若没有产生进位，则需要对结果进行"取反加一"
        String ans = unsignedNumberAdd(s1, s2);
        if (adder.nextC == '0') {
            ans = reverse(ans);
        }
        return ans;
    }

    protected String reverse(String s) {                       // 对数进行"取反加一"
        StringBuilder reverse = new StringBuilder();
        for (int i = 1; i < 33; i += 4) {
            String subString = s.substring(i, i + 4);
            subString = adder.setOperand(subString, "0110").calculate('0');
            for (int j = 0; j < subString.length(); j++) {
                if (subString.charAt(j) == '0') reverse.append('1');
                else reverse.append('0');
            }
        }
        String ans = reverse.toString();
        adder.setOperand(ans, repeat('0', 32));
        ans = adder.calculate('1');                  //  加一
        ans = Arithmetic.NOT(s.charAt(0)) + ans;
        return ans;
    }


    @Override
    protected String multi(String s1, String s2) {
        return null;
    }

    @Override
    protected String[] division(String s1, String s2) {
        return null;
    }
}
