public abstract class ALU {
    String n1, n2;

    String re;        // remainder
    String quo;       // quotient

    public ALU(String n1, String n2) {
        this.n1 = toBinary(n1);
        this.n2 = toBinary(n2);
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

    public void division() {
        division(n1, n2);
    }

    public String getRemainder() {
        return toDecimal(re);
    }

    public String getQuotient() {
        return toDecimal(quo);
    }

    protected abstract String toDecimal(String bin);
    protected abstract String toBinary(String n);

    protected abstract String add(String s1, String s2);
    protected abstract String sub(String s1, String s2);
    protected abstract String multi(String s1, String s2);
    protected abstract void division(String s1, String s2);
}
