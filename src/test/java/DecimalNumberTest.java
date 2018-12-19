import static org.junit.Assert.*;

public class DecimalNumberTest {

    private AbstractALU test = new DecimalNumber();

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
        assertEquals("99", test.setOperand("98", "1").add());
        assertEquals("0", test.setOperand("-100", "100").add());
        assertEquals("-20", test.setOperand("-7", "-13").add());
        assertEquals("-16", test.setOperand("32", "-48").add());
        int ran1 = (int) (Math.random() * -1000 + 500);
        int ran2 = (int) (Math.random() * -1000 + 500);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        assertEquals(String.valueOf(ran1 + ran2), test.add());
    }

    @org.junit.Test
    public void sub() {
        assertEquals("27", test.setOperand("48", "21").sub());
        assertEquals("-200", test.setOperand("-100", "100").sub());
        assertEquals("6", test.setOperand("-7", "-13").sub());
        assertEquals("80", test.setOperand("32", "-48").sub());
        int ran1 = (int) (Math.random() * -1000 + 500);
        int ran2 = (int) (Math.random() * -1000 + 500);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        assertEquals(String.valueOf(ran1 - ran2), test.sub());
    }
}