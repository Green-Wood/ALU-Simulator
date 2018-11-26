import static org.junit.Assert.*;

public class DecimalNumberTest {

    private ALU test = new DecimalNumber();

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
        test.setOperand("98", "1");
        assertEquals("99", test.add());
        test.setOperand("-100", "100");
        assertEquals("0", test.add());
        test.setOperand("-7", "-13");
        assertEquals("-20", test.add());
        test.setOperand("32", "-48");
        assertEquals("-16", test.add());
        int ran1 = (int) (Math.random() * -1000 + 500);
        int ran2 = (int) (Math.random() * -1000 + 500);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        assertEquals(String.valueOf(ran1 + ran2), test.add());
    }

    @org.junit.Test
    public void sub() {
        test.setOperand("48", "21");
        assertEquals("27", test.sub());
        test.setOperand("-100", "100");
        assertEquals("-200", test.sub());
        test.setOperand("-7", "-13");
        assertEquals("6", test.sub());
        test.setOperand("32", "-48");
        assertEquals("80", test.sub());
        int ran1 = (int) (Math.random() * -1000 + 500);
        int ran2 = (int) (Math.random() * -1000 + 500);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        assertEquals(String.valueOf(ran1 - ran2), test.sub());
    }
}