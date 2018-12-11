import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerNumberTest {
    private ALU test = new IntegerNumber();
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
        int ran1 = (int) (Math.random() * -1000 + 500);
        int ran2 = (int) (Math.random() * -1000 + 500);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        assertEquals(String.valueOf(ran1 * ran2), test.multi());
        assertEquals(String.valueOf(7 * 3), test.setOperand("7", "3").multi());
    }

    @Test
    public void division() {
        int ran1 = (int) (Math.random() * -1000 + 500);
        int ran2 = (int) (Math.random() * -1000 + 500);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        assertEquals(String.valueOf(ran1 / ran2), test.division());
        assertEquals(String.valueOf(ran1 % ran2), test.getRemainder());
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