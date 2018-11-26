package Basic;

/**
 * 顺序进位加法器的实现
 */
public class SerialAdder {                 // 单件模式，全局只有一个顺序加法器adder
    private char[] s1, s2;
    private FullAdder[] adders;
    public char nextC;
    public static SerialAdder serialAdder;

    public static SerialAdder getSerialAdder() {
        if (serialAdder == null) serialAdder = new SerialAdder();
        return serialAdder;
    }

    private SerialAdder(){}

    public void setOperand(String n1, String n2) {
        s1 = n1.toCharArray();
        s2 = n2.toCharArray();
        adders = new FullAdder[s1.length];
    }

    /**
     *
     * @param c0 当这个参数为1时，就相当于计算s1 + s2 + 1的结果（常用于计算减法）
     * @return 返回两个二进制数相加的结果。
     */
    public String calculate(char c0){
        char[] ans = new char[s1.length];
        char preC = c0;
        for (int i = ans.length-1; i >= 0; i--){
            adders[i] = new FullAdder(s1[i], s2[i], preC);
            ans[i] = adders[i].s;
            preC = adders[i].nextC;
        }
        nextC = preC;
        return String.valueOf(ans);
    }
}
