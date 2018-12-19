import Basic.StringGenerator;

/**
 * 由位运算实现加减乘除
 * 整数的加减乘除（常用的32位二进制表达，负数使用补码实现）
 * 如：15 -> 0000 0000 0000 0000 0000 0000 0000 1111        -2 -> 1111 1111 1111 1111 1111 1111 1111 1110
 * @author greenwood
 */
public class IntegerNumber extends AbstractALU {

    @Override
    protected String add(String s1, String s2){     // 两个二进制数相加，返回一个二进制数
        adder.setOperand(s1, s2);
        return adder.calculate('0');
    }

    @Override
    protected String sub(String s1, String s2){          // 两个二进制数相减，返回一个二进制数
        s2 = StringGenerator.getReverse(s2);
        adder.setOperand(s1, s2);
        return adder.calculate('1');
    }

    @Override
    protected String multi(String s1, String s2){       // 使用Booth算法来计算带符号二进制乘法
        String x = s1;
        String _x = sub(StringGenerator.repeat('0', 32), s1);
        String y = s2;
        StringBuilder sb = new StringBuilder(StringGenerator.repeat('0', 32) + y);      // 模拟寄存器
        y = y + "0";                                   // y后添加零，使得我们可以计算y0
        for (int i = y.length()-1; i > 0; i--){
            int delta = y.charAt(i) - y.charAt(i-1);       // 决定使用x或-x或0
            String half = sb.substring(0, 32);          // 取前半部分
            String newHalf;
            if (delta == -1){
                newHalf = add(_x, half);
            } else if (delta == 1){
                newHalf = add(x, half);
            } else {
                newHalf = half;
            }
            sb.replace(0, 32, newHalf);        // 使用新的前半部分来替换旧的
            char sign = sb.charAt(0);                       // 根据符号来右移
            sb.deleteCharAt(sb.length()-1);                 // 右移
            sb.insert(0, sign);
        }
        String bin = sb.substring(sb.length()-32);      // 从倒数第二位开始
        return bin;
    }

    @Override
    protected String[] division(String s1, String s2){        // 将除法的余数保存在remainder中
        char diSign = s2.charAt(0);
        char reSign = s1.charAt(0);                    // remainder sign
        StringBuilder sb;
        if (reSign == '0') sb = new StringBuilder(StringGenerator.repeat('0', 32) + s1);  // 根据符号添加
        else sb = new StringBuilder(StringGenerator.repeat('1', 32) + s1);
        char addQ = '1';
        for (int i = 0; i < 33; i++){
            reSign = sb.charAt(0);
            String half = sb.substring(0, 32);
            String newHalf;
            if (reSign == diSign){                          // 如果remainder符号与divisor相同则相减，否则相加
                newHalf = sub(half, s2);
            } else {
                newHalf = add(half, s2);
            }
            reSign = newHalf.charAt(0);
            sb.replace(0, 32, newHalf);               // 替换新的左半边
            if (reSign == diSign){                  // 如果新产生的remainder的符号与divisor相同，商左移加一，否则左移加零
                addQ = '1';
            } else {
                addQ = '0';
            }
            if (i < 32){                    // 前32次需要左移，最后一次需要经过后续判断
                sb.deleteCharAt(0);
                sb.append(addQ);
            }
        }
        String remainder = sb.substring(0, 32);
        String quotient = sb.substring(32, sb.length());
        quotient = quotient.substring(1) + addQ;                    // left shift quotient
        if (quotient.charAt(0) == '1') {                             // if quotient is negative, add 1
            quotient = add(StringGenerator.repeat('0', 31) + "1" , quotient);
        }
        // 对余数的结果进行修正
        if (reSign != s1.charAt(0)) {
            if (s1.charAt(0) == diSign) {                           // 如果被除数为正，余数为负
                remainder = add(remainder, s2);
            }
            else  {                                                     // 如果被除数为负，余数为正
                remainder = sub(remainder, s2);
            }
        }

        if (add(remainder, s2).equals(StringGenerator.repeat('0', 32))) {
            remainder = add(remainder, s2);
            quotient = sub(quotient, StringGenerator.repeat('0', 31) + "1");
        } else if (sub(remainder, s2).equals(StringGenerator.repeat('0', 32))) {
            remainder = sub(remainder, s2);
            quotient = add(quotient, StringGenerator.repeat('0', 31) + "1");
        }
        return new String[]{quotient, remainder};
    }

    @Override
    protected String toBinary(String n) {         // 将整数转为32位二进制数
        int num = Integer.parseInt(n);
        String origin =  Integer.toBinaryString(num);
        StringBuilder sb = new StringBuilder(origin);
        while (sb.length() != 32) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    String handyToBinary(String n) {           // 手动实现
        int num = Integer.parseInt(n);
        boolean isNeg = num < 0;
        if (isNeg) num = -num;
        StringBuilder sb = new StringBuilder();
        while (num != 0) {
            sb.insert(0, num % 2);
            num /= 2;
        }
        while (sb.length() != 32) {
            sb.insert(0, '0');
        }
        String ans = sb.toString();
        if (isNeg) {
            ans = StringGenerator.getReverse(ans);
            ans = adder.setOperand(ans, StringGenerator.repeat('0', 32)).calculate('1');
        }
        return ans;
    }

    @Override
    protected String toDecimal(String bin) {          // 将32位二进制转为十进制
        char[] b = bin.toCharArray();
        long n = -1 * (b[0] - '0') * (long) Math.pow(2, 31);        // pow(2, 31)导致int值溢出，因此使用long
        for (int i = 1; i < 32; i++){
            n += (b[i] - '0') * (int) Math.pow(2, 31 - i);
        }
        return String.valueOf(n);
    }
}
