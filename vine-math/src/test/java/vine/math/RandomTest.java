package vine.math;

import org.junit.Test;

public class RandomTest
{
    @Test
    public void testPow()
    {
        double x;
        long time = System.currentTimeMillis();
        x = 0;
        for (int i = 0; i < 1000; i++)
        {
            x += Math.pow(0.88, i);
        }
        System.out.println(System.currentTimeMillis() - time + " , " + x);
        time = System.currentTimeMillis();
        x = 0;
        for (int i = 0; i < 1000; i++)
        {
            x += VineMath.pow(0.88, i);
        }
        System.out.println(System.currentTimeMillis() - time + " , " + x);
    }
}
