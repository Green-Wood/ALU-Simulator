package Basic;

/**
 * 整个加减乘除计算器的基础部分
 * 与、或、异或的实现
 */
public class Arithmetic {
    public static char AND(char c1, char c2) {
        if (c1 == '1' && c2 == '1') return '1';
        else return '0';
    }

    public static char OR(char c1, char c2) {
        if (c1 == '1' || c2 == '1') return '1';
        else return '0';
    }

    public static char XOR(char c1, char c2) {
        if (c1 != c2) return '1';
        else return '0';
    }

    public static char NOT(char c) {   // TODO  优化
        if (c == '1') return '0';
        else return '1';
    }
}
