import Basic.StringGenerator;

public class Main {
    public static void main(String[] args) {
        System.out.println(Integer.valueOf("00" + StringGenerator.repeat('1', 30), 2));
    }
}
