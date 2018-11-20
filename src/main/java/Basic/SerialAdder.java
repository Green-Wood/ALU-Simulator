package Basic;

/**
 * 顺序进位加法器的实现
 */
public class SerialAdder {
    private char[] s1, s2;
    private FullAdder[] adders;
    public char nextC;

    /**
     * @param s1, s2 输入两个二进制数，结果通过calculate方法返回，产生的进位存储在nextC中
     */
    public SerialAdder(String s1, String s2){         // 输入32位二进制数
        this.s1 = s1.toCharArray();
        this.s2 = s2.toCharArray();
        adders = new FullAdder[s1.length()];
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
