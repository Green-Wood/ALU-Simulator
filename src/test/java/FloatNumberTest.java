import org.junit.Test;

import static org.junit.Assert.*;

public class FloatNumberTest {

    private ALU test = new FloatNumber();

    @Test
    public void toDecimal() {
        assertEquals("0.5", test.toDecimal("00111111000000000000000000000000"));
        assertEquals("-0.4375", test.toDecimal("10111110111000000000000000000000"));
        assertEquals("0", test.toDecimal("00000000000000000000000000000000"));
        assertEquals("0", test.toDecimal("10000000000000000000000000000000"));
        assertEquals("NaN", test.toDecimal("11111111110110000000000000000000"));
        assertEquals("plus infinity", test.toDecimal("01111111100000000000000000000000"));
        assertEquals(String.valueOf((float) Math.pow(2, -149)), test.toDecimal("00000000000000000000000000000001"));
    }

    @Test
    public void toBinary() {
        assertEquals("00111111000000000000000000000000", test.toBinary("0.5"));
        assertEquals("10111110111000000000000000000000", test.toBinary("-0.4375"));
        assertEquals("11000010111101100111010111000011", test.toBinary("-123.23"));
        assertEquals("00000000000000000000000000000000", test.toBinary("0"));
        assertEquals("01111111100000000000000000000000", test.toBinary("plus infinity"));
        assertEquals("00000000000000000000000000000001", test.toBinary(String.valueOf((float) Math.pow(2, -149))));
    }

    @Test
    public void add() {
        test.setOperand("0.8125", "0.625");
        assertEquals("1.4375", test.add());
        test.setOperand("1", "1");
        assertEquals(String.valueOf((float) 2), test.add());
        test.setOperand("1", "-1");
        assertEquals("0", test.add());
        test.setOperand("-20", "-100");
        assertEquals(String.valueOf((float) -120), test.add());
        float ran1 = (float) (Math.random() * -1000 + 500);
        float ran2 = (float) (Math.random() * -1000 + 500);
        System.out.println(ran1 + "    " + ran2);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        assertEquals(String.valueOf(ran1 + ran2), test.add());
    }

    @Test
    public void sub() {
        test.setOperand("0.8125", "0.625");
        assertEquals("0.1875", test.sub());
        test.setOperand("0.625", "0.8125");
        assertEquals("-0.1875", test.sub());
        float ran1 = (float) (Math.random() * -1000 + 500);
        float ran2 = (float) (Math.random() * -1000 + 500);
        System.out.println(ran1 + "    " + ran2);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        assertEquals(String.valueOf(ran1 - ran2), test.sub());
    }

    @Test
    public void multi() {
        test.setOperand("0.5", "0.4375");
        assertEquals(String.valueOf((float) 0.21875), test.multi());
        test.setOperand("2", "-0.5");
        assertEquals(String.valueOf((float) -1), test.multi());
        test.setOperand("-50", "-3");
        assertEquals(String.valueOf((float) 150), test.multi());
        test.setOperand("27", "21");
        assertEquals(String.valueOf((float) 567), test.multi());
        float ran1 = (float) (Math.random() * -100 + 50);
        float ran2 = (float) (Math.random() * -100 + 50);
        System.out.println(ran1 + "    " + ran2);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        assertEquals(String.valueOf(ran1 * ran2), test.multi());
    }

    @Test
    public void division() {
    }
}