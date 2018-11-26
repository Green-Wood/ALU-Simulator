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
    }

    @Test
    public void division() {
        int ran1 = (int) (Math.random() * -1000 + 500);
        int ran2 = (int) (Math.random() * -1000 + 500);
        test.setOperand(String.valueOf(ran1), String.valueOf(ran2));
        test.division();
        assertEquals(String.valueOf(ran1 / ran2), test.getQuotient());
        assertEquals(String.valueOf(ran1 % ran2), test.getRemainder());
    }
}