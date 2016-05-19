package vine.math;

import org.junit.Test;

public class RandomTest
{
    @Test
    public void testPow()
    {
        double x;
        long a, b;
        long time = System.nanoTime();
        x = 0;
        for (int i = 0; i < 1; i++)
        {
            x += VineMath.pow(5.0f, 2.0f);
        }
        a = System.nanoTime() - time;
        time = System.nanoTime();
        System.out.println(x);
        x = 0;
        for (int i = 0; i < 1; i++)
        {
            x += VineMath.fastPow(5.0f, 2.0f);
        }
        System.out.println(x);
        System.out.println(Math.pow(5, 2.0));
        b = System.nanoTime() - time;
        System.out.println(a + " , " + b);
    }
}
