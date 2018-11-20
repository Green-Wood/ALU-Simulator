package Basic;

/**
 *  全加器的实现
 * @author greenwood
 */
public class FullAdder {
    public char s;             // 这一个全加器运算的结果
    public char nextC;         // 这一个全加器的进位

    /**
     *
     * @param x  一个加数 (1 or 0)
     * @param y  另一个加数 (1 or 0)
     * @param preC  前面一个全加器的进位 (1 or 0)
     */
    FullAdder(char x, char y, char preC){
        this.s = Arithmetic.XOR(Arithmetic.XOR(x, y), preC);
        char xy = Arithmetic.AND(x, y);
        char xc = Arithmetic.AND(x, preC);
        char yc = Arithmetic.AND(y, preC);
        this.nextC = Arithmetic.OR(Arithmetic.OR(xy, xc), yc);
    }
}
