package Representations;

public interface Representation {
    public String toBinary();
    public String toDecimal();
    public Representation setValueByDecimal(String num);
    public Representation setValueByBinary(String num);
}
