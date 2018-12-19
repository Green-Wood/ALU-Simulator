import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerNumberTest {
    private AbstractALU test = new IntegerNumber();
    @Test
    public void add() {
        int ran1 = (int) (Math.random() * -1000 + 500);
        int ran2 = (int) (Math.random() * -1000 + 500);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        assertEquals(String.valueOf(ran1 + ran2), test.add());
    }

    @Test
    public void sub() {
        int ran1 = (int) (Math.random() * -1000 + 500);
        int ran2 = (int) (Math.random() * -1000 + 500);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        assertEquals(String.valueOf(ran1 - ran2), test.sub());
    }

    @Test
    public void multi() {
        for (int i = 0; i < 100; i++) {
            int ran1 = (int) (Math.random() * -1000 + 500);
            int ran2 = (int) (Math.random() * -1000 + 500);
            test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
            assertEquals(String.valueOf(ran1 * ran2), test.multi());
            assertEquals(String.valueOf(7 * 3), test.setOperand("7", "3").multi());
        }
    }

    @Test
    public void division() {
        test.setOperand("-2", "1");
        assertArrayEquals(new String[]{"-2", "0"}, test.division());
        test.setOperand("-4", "-2");
        assertArrayEquals(new String[]{"2", "0"}, test.division());
        test.setOperand("9", "-9");
        assertArrayEquals(new String[]{"-1", "0"}, test.division());
        for (int i = 0; i < 100; i++) {
            int ran1 = (int) (Math.random() * -100000 + 50000);
            int ran2 = (int) (Math.random() * -100000 + 50000);
            String[] expect = new String[]{String.valueOf(ran1 / ran2), String.valueOf(ran1 % ran2)};
            test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
            System.out.println("ran1: " + ran1 + " ran2: " + ran2);
            assertArrayEquals(expect, test.division());
        }
    }

    @Test
    public void handyToBinary() {
        IntegerNumber integer = new IntegerNumber();
        assertEquals(integer.toBinary("12"), integer.handyToBinary("12"));
        assertEquals(integer.toBinary("-122"), integer.handyToBinary("-122"));
        int ran = (int) (Math.random() * -1000 + 500);
        assertEquals(integer.toBinary(String.valueOf(ran)), integer.handyToBinary(String.valueOf(ran)));
        System.out.println(ran);
    }
}