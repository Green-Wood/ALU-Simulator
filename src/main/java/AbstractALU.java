import Basic.SerialAdder;

import java.util.Arrays;

/**
 * 整个ALU simulator的抽象类
 *
 * 通过继承这个类并完成抽象方法，来实现不同的加减乘除模拟器
 * 内部运算均使用二进制实现，只有在返回给用户时，才将数据转化为十进制
 * @author greenwood
 */
public abstract class AbstractALU {
    String n1, n2;

    SerialAdder adder;

    public AbstractALU() {
        adder = SerialAdder.getSerialAdder();
    }

    /**
     * @param n1, n2 载入需要进行运算的两个十进制数
     */
    public AbstractALU setOperand(String n1, String n2) {
        this.n1 = toBinary(n1);
        this.n2 = toBinary(n2);
        return this;
    }

    public String add() {
        return toDecimal(add(n1, n2));
    }

    public String sub() {
        return toDecimal(sub(n1, n2));
    }

    public String multi() {
        return  toDecimal(multi(n1, n2));
    }

    public String[] division() {                //  Integer的默认实现
        String[] bin = division(n1, n2);
        return new String[]{toDecimal(bin[0]), toDecimal(bin[1])};
    }    // 返回商，余数需要使用getRemainder得到

    String repeat(char c, int times) {
        char[] arr = new char[times];
        Arrays.fill(arr, c);
        return String.valueOf(arr);
    }

    String rightShift(String s, int n, char addChar) {
        return repeat(addChar, n) + s.substring(0, s.length() - n);
    }

    StringBuilder rightShift(StringBuilder s, int n, char addChar) {
        s.delete(s.length() - n, s.length());
        return s.insert(0, repeat(addChar, n));
    }

    String leftShift(String s, int n, char addChar) {
        s = s.substring(n);
        return s + repeat(addChar, n);
    }

    StringBuilder leftShift(StringBuilder s, int n, char addQ) {
        s.delete(0, n);
        return s.append(repeat(addQ, n));
    }

    boolean isAllZero(String bin) {
        for (int i = 0; i < bin.length(); i++) {
            if (bin.charAt(i) != '0') return false;
        }
        return true;
    }

    boolean isAllOne(String bin) {
        for (int i = 0; i < bin.length(); i++) {
            if (bin.charAt(i) != '1') return false;
        }
        return true;
    }

    protected String reverse(String s) {                 // Float 和 Integer的默认reverse实现
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') sb.append('0');
            else sb.append('1');
        }
        return sb.toString();
    }

    protected abstract String toDecimal(String bin);         // 各个具体子类需要自己实现十进制与二进制互相转化的方法
    protected abstract String toBinary(String n);

    // 以下方法都是给予两个二进制数，返回计算结果的二进制表示
    protected abstract String add(String s1, String s2);
    protected abstract String sub(String s1, String s2);
    protected abstract String multi(String s1, String s2);
    protected abstract String[] division(String s1, String s2);
}
