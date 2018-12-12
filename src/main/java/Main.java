import Basic.StringGenerator;

public class Main {
    public static void main(String[] args) {
        IntegerNumber test = new IntegerNumber();
        System.out.println(test.setOperand("-2", "1").division());
        System.out.println(test.getRemainder());
    }
}
