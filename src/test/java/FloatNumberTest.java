import org.junit.Test;

import static org.junit.Assert.*;

public class FloatNumberTest {

    private AbstractALU test = new FloatNumber();

    @Test
    public void toDecimal() {
        assertEquals("0.5", test.toDecimal("00111111000000000000000000000000"));
        assertEquals("-0.4375", test.toDecimal("10111110111000000000000000000000"));
        assertEquals("0", test.toDecimal("00000000000000000000000000000000"));
        assertEquals("0", test.toDecimal("10000000000000000000000000000000"));
        assertEquals("NaN", test.toDecimal("11111111110110000000000000000000"));
        assertEquals("plus infinity", test.toDecimal("01111111100000000000000000000000"));
        assertEquals(String.valueOf((float) Math.pow(2, -149)), test.toDecimal("00000000000000000000000000000001"));
        String testNum = String.valueOf((float) 1e-44);
        System.out.println(testNum);
        assertEquals(testNum, test.toDecimal(test.toBinary(testNum)));
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
//        assertEquals("1.4375", test.setOperand("0.8125", "0.625").add());
//        assertEquals(String.valueOf((float) 2), test.setOperand("1", "1").add());
//        assertEquals("0", test.setOperand("1", "-1").add());
//        assertEquals(String.valueOf((float) -120), test.setOperand("-20", "-100").add());
//        assertEquals(String.valueOf((float) -60), test.setOperand("20", "-80").add());

        //    denormalization test
        assertEquals(String.valueOf((float) 1.6e-43 + (float) -2e-43),
                test.setOperand(String.valueOf((float) 1.6e-43), String.valueOf((float) -2e-43)).add());
        float ran1 = (float) (Math.random() * -1000 + 500);
        float ran2 = (float) (Math.random() * -1000 + 500);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        float expect = ran1 + ran2;
        float actual = Float.valueOf(test.add());
        System.out.printf("ADD:\nRan1: %f   Ran2: %f\nExpect: %f   Actual: %f\n", ran1, ran2, expect, actual);
        assertTrue(Math.abs(expect - actual) < 0.0001);
    }

    @Test
    public void sub() {
        assertEquals("0.1875", test.setOperand("0.8125", "0.625").sub());
        assertEquals("-0.1875", test.setOperand("0.625", "0.8125").sub());

        //    denormalization test
        assertEquals(String.valueOf((float) -3.4e-43 - (float) 2.1e-43),
                test.setOperand(String.valueOf((float) -3.4e-43), String.valueOf((float) 2.1e-43)).sub());
        float ran1 = (float) (Math.random() * -1000 + 500);
        float ran2 = (float) (Math.random() * -1000 + 500);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        float expect = ran1 - ran2;
        float actual = Float.valueOf(test.sub());
        System.out.printf("SUB:\nRan1: %f   Ran2: %f\nExpect: %f   Actual: %f\n", ran1, ran2, expect, actual);
        assertTrue(Math.abs(expect - actual) < 0.0001);
    }

    @Test
    public void multi() {
        assertEquals(String.valueOf((float) 0.21875), test.setOperand("0.5", "0.4375").multi());
        assertEquals(String.valueOf((float) -1), test.setOperand("2", "-0.5").multi());
        assertEquals(String.valueOf((float) 150), test.setOperand("-50", "-3").multi());
        assertEquals(String.valueOf((float) 567), test.setOperand("27", "21").multi());

        //    denormalized test
        assertEquals(String.valueOf((float) 2e-43 * 2), test.setOperand(String.valueOf((float) 2e-43), "2").multi());
        float ran1 = (float) (Math.random() * -100 + 50);
        float ran2 = (float) (Math.random() * -100 + 50);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        float expect = ran1 * ran2;
        float actual = Float.valueOf(test.multi());
        System.out.printf("MULTI:\nRan1: %f   Ran2: %f\nExpect: %f   Actual: %f\n", ran1, ran2, expect, actual);
        assertTrue(Math.abs(expect - actual) < 0.0001);
    }

    @Test
    public void division() {
//        assertEquals(String.valueOf((float) 2e-43 / 2),
//                test.setOperand(String.valueOf((float) 2e-43), "2").division()[0]);
        float ran1 = (float) (Math.random() * -100 + 50);
        float ran2 = (float) (Math.random() * -100 + 50);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        float expect = ran1 / ran2;
        float actual = Float.valueOf(test.division()[0]);
        System.out.printf("DIVISION:\nRan1: %f   Ran2: %f\nExpect: %f   Actual: %f\n", ran1, ran2, expect, actual);
        assertTrue(Math.abs(expect - actual) < 0.0001);
    }
}