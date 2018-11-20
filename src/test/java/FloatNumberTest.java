import org.junit.Test;

import static org.junit.Assert.*;

public class FloatNumberTest {

    private ALU floatPoint = new FloatNumber("0", "0");

    @Test
    public void toDecimal() {
        assertEquals("0.5", floatPoint.toDecimal("00111111000000000000000000000000"));
        assertEquals("-0.4375", floatPoint.toDecimal("10111110111000000000000000000000"));
        assertEquals("0", floatPoint.toDecimal("00000000000000000000000000000000"));
        assertEquals("0", floatPoint.toDecimal("10000000000000000000000000000000"));
        assertEquals("NaN", floatPoint.toDecimal("11111111110110000000000000000000"));
        assertEquals("plus infinity", floatPoint.toDecimal("01111111100000000000000000000000"));
        assertEquals(String.valueOf(Math.pow(2, -149)), floatPoint.toDecimal("00000000000000000000000000000001"));
    }

    @Test
    public void toBinary() {
    }

    @Test
    public void add() {
    }

    @Test
    public void sub() {
    }

    @Test
    public void multi() {
    }

    @Test
    public void division() {
    }
}