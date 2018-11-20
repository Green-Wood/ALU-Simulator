package Basic;

public class Arithmetic {
    public static char AND(char c1, char c2){
        if (c1 == '1' && c2 == '1') return '1';
        else return '0';
    }

    public static char OR(char c1, char c2){
        if (c1 == '1' || c2 == '1') return '1';
        else return '0';
    }

    public static char XOR(char c1, char c2){
        if (c1 != c2) return '1';
        else return '0';
    }
}
