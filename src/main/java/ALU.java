/**
 * 整个ALU simulator的抽象类
 *
 * 通过继承这个类并完成抽象方法，来实现不同的加减乘除模拟器
 * 内部运算均使用二进制实现，只有在返回给用户时，才将数据转化为十进制
 * @author greenwood
 */
public abstract class ALU {
    String n1, n2;

    String remainder;
    String quotient;

    /**
     * @param n1, n2 载入需要进行运算的两个十进制数
     */
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

    /**
     * 调用division方法进行除法后，需要使用getQuotient和getRemainder方法来获取 商和余数
     */
    public void division() {
        division(n1, n2);
    }

    public String getRemainder() {
        return toDecimal(remainder);
    }

    public String getQuotient() {
        return toDecimal(quotient);
    }

    protected abstract String toDecimal(String bin);         // 各个具体子类需要自己实现十进制与二进制互相转化的方法
    protected abstract String toBinary(String n);

    protected abstract String add(String s1, String s2);
    protected abstract String sub(String s1, String s2);
    protected abstract String multi(String s1, String s2);
    protected abstract void division(String s1, String s2);
}
