package Basic;

public class FullAdder {
    public char s, nextC;

    FullAdder(char x, char y, char preC){
        this.s = Arithmetic.XOR(Arithmetic.XOR(x, y), preC);
        char xy = Arithmetic.AND(x, y);
        char xc = Arithmetic.AND(x, preC);
        char yc = Arithmetic.AND(y, preC);
        this.nextC = Arithmetic.OR(Arithmetic.OR(xy, xc), yc);
    }
}
