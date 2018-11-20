import static org.junit.Assert.*;

public class DecimalNumberTest {

    private ALU test = new DecimalNumber("0", "0");

    @org.junit.Test
    public void toDecimal() {
        assertEquals("102", test.toDecimal("000000000000000000000000100000010"));
        assertEquals("0", test.toDecimal("000000000000000000000000000000000"));
    }

    @org.junit.Test
    public void toBinary() {
        assertEquals("000000000000000000000000000000000", test.toBinary("0"));
        assertEquals("000000000000000000000000100000010", test.toBinary("102"));
    }

    @org.junit.Test
    public void add() {
        assertEquals("99", new DecimalNumber("98", "1").add());
        assertEquals("0", new DecimalNumber("-100", "100").add());
        assertEquals("-20", new DecimalNumber("-7", "-13").add());
        assertEquals("-16", new DecimalNumber("32", "-48").add());
        int ran1 = (int) (Math.random() * -1000 + 500);
        int ran2 = (int) (Math.random() * -1000 + 500);
        assertEquals(String.valueOf(ran1 + ran2), new DecimalNumber(String.valueOf(ran1), String.valueOf(ran2)).add());
    }

    @org.junit.Test
    public void sub() {
        assertEquals("27", new DecimalNumber("48", "21").sub());
        assertEquals("-200", new DecimalNumber("-100", "100").sub());
        assertEquals("6", new DecimalNumber("-7", "-13").sub());
        assertEquals("80", new DecimalNumber("32", "-48").sub());
        int ran1 = (int) (Math.random() * -1000 + 500);
        int ran2 = (int) (Math.random() * -1000 + 500);
        assertEquals(String.valueOf(ran1 - ran2), new DecimalNumber(String.valueOf(ran1), String.valueOf(ran2)).sub());
    }
}