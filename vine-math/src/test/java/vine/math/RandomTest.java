package vine.math;

import org.junit.Test;

public class RandomTest
{
    @Test
    public void testPow()
    {
        final long time = System.nanoTime();
        final float b = GMath.fastAsin(0.9f);
        final long timeA = System.nanoTime() - time;
        System.out.println(timeA / 1000000f + " " + b);
    }
}
