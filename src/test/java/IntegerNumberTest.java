import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerNumberTest {

    @Test
    public void add() {
        int ran1 = (int) (Math.random() * -1000 + 500);
        int ran2 = (int) (Math.random() * -1000 + 500);
        assertEquals(String.valueOf(ran1 + ran2), new IntegerNumber(String.valueOf(ran1), String.valueOf(ran2)).add());
    }

    @Test
    public void sub() {
        int ran1 = (int) (Math.random() * -1000 + 500);
        int ran2 = (int) (Math.random() * -1000 + 500);
        assertEquals(String.valueOf(ran1 - ran2), new IntegerNumber(String.valueOf(ran1), String.valueOf(ran2)).sub());
    }

    @Test
    public void multi() {
        int ran1 = (int) (Math.random() * -1000 + 500);
        int ran2 = (int) (Math.random() * -1000 + 500);
        assertEquals(String.valueOf(ran1 * ran2), new IntegerNumber(String.valueOf(ran1), String.valueOf(ran2)).multi());
    }

    @Test
    public void division() {
        int ran1 = (int) (Math.random() * -1000 + 500);
        int ran2 = (int) (Math.random() * -1000 + 500);
        ALU integer = new IntegerNumber(String.valueOf(ran1), String.valueOf(ran2));
        integer.division();
        assertEquals(String.valueOf(ran1 / ran2), integer.getQuotient());
        assertEquals(String.valueOf(ran1 % ran2), integer.getRemainder());
    }
}