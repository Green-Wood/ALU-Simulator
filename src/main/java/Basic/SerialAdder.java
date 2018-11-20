package Basic;

public class SerialAdder {
    private char[] s1, s2;
    private FullAdder[] adders;
    public char nextC;
    public SerialAdder(String s1, String s2){         // 输入32位二进制数
        this.s1 = s1.toCharArray();
        this.s2 = s2.toCharArray();
        adders = new FullAdder[s1.length()];
    }

    public String calculate(char c0){           // c0 == 0时加法，c0 == 1时减法
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
