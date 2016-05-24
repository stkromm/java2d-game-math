package vector.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import vine.math.VineMath;
import vine.math.vector.Vec2Util;

public class Vectors2Test
{
    @Test
    public void testDotProduct()
    {
        assertTrue(Vec2Util.dot(0.0f, 1.0f, 1.0f, 0.0f) == 0.0f);
        assertTrue(Vec2Util.dot(-1, 1, -1, -1) == 0.f);
        assertTrue(Math.abs(Vec2Util.dot(0.4f, 1.2f, 0.2f, 0.15f) - 13 / 50f) <= VineMath.EPSILON);
    }

    @Test
    public void testCrossProduct()
    {
        assertTrue(Vec2Util.pseudoCross(1.0f, 1.0f, 1.0f, 1.0f) == 0);
        assertTrue(Vec2Util.pseudoCross(-1.0f, 1.0f, 1.0f, 1.0f) == -2);
        assertTrue(Vec2Util.pseudoCross(1.0f, 1.0f, -1.0f, 1.0f) == 2);
    }

    @Test
    public void testAngleCalculation()
    {
        assertTrue(VineMath.isNearlyZero(Vec2Util.getAngle(1, 1, 0, 1) - VineMath.HALF_PIF * 0.5f));
        System.out.println(Vec2Util.getAngle(0, 1, 1, 1));
        assertTrue(VineMath.isNearlyZero(Vec2Util.getAngle(0, 1, 1, 1) + VineMath.HALF_PIF * 0.5f));

        assertTrue(VineMath.isNearlyEqual(Vec2Util.getAngle(0, 1, 0, -1), VineMath.PIF));
        assertTrue(VineMath.isNearlyEqual(Vec2Util.getAngle(1, 0, 0, 1), VineMath.HALF_PIF));
        assertTrue(VineMath.isNearlyEqual(Vec2Util.getAngle(1, 0, -1, 0), VineMath.PIF));
        assertTrue(VineMath.isNearlyEqual(Vec2Util.getAngle(-1, 0, 1, 0), VineMath.PIF));
    }

    @Test
    public void testLengthCalculation()
    {
        assertTrue(Vec2Util.length(0, 0) == 0);
        assertTrue(Vec2Util.length(1, 0) == 1);
        assertTrue(Vec2Util.length(1, 1) == Math.sqrt(2));
        assertTrue(Vec2Util.squaredLength(1.f, 1) == 2);
        assertTrue(Vec2Util.length(0, -1) == 1);
    }

    @Test
    public void testSlope()
    {
        assertTrue(Vec2Util.getSlope(0, 8) == Float.MAX_VALUE);
        assertTrue(Vec2Util.getSlope(1, 8) == 8);
    }
}
