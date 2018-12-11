import Basic.StringGenerator;

public class Main {
    public static void main(String[] args) {
        float a = Float.parseFloat("1");
        int intBits = Float.floatToIntBits(a);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.insert(0, intBits & 1);
            intBits = intBits >> 1;
        }
        System.out.println(sb);
    }
}
