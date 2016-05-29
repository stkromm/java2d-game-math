package vector.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import vine.math.GMath;
import vine.math.vector.VectorUtils;

public class Vectors2Test
{
    @Test
    public void testDotProduct()
    {
        assertTrue(VectorUtils.dot(0.0f, 1.0f, 1.0f, 0.0f) == 0.0f);
        assertTrue(VectorUtils.dot(-1, 1, -1, -1) == 0.f);
        assertTrue(Math.abs(VectorUtils.dot(0.4f, 1.2f, 0.2f, 0.15f) - 13 / 50f) <= GMath.EPSILON);
    }

    @Test
    public void testCrossProduct()
    {
        assertTrue(VectorUtils.pseudoCross(1.0f, 1.0f, 1.0f, 1.0f) == 0);
        assertTrue(VectorUtils.pseudoCross(-1.0f, 1.0f, 1.0f, 1.0f) == -2);
        assertTrue(VectorUtils.pseudoCross(1.0f, 1.0f, -1.0f, 1.0f) == 2);
    }

    @Test
    public void testAngleCalculation()
    {
        assertTrue(GMath.isNearlyZero(VectorUtils.getAngle(1, 1, 0, 1) - GMath.HALF_PIF * 0.5f));
        System.out.println(VectorUtils.getAngle(0, 1, 1, 1));
        assertTrue(GMath.isNearlyZero(VectorUtils.getAngle(0, 1, 1, 1) + GMath.HALF_PIF * 0.5f));

        assertTrue(GMath.isNearlyEqual(VectorUtils.getAngle(0, 1, 0, -1), GMath.PIF));
        assertTrue(GMath.isNearlyEqual(VectorUtils.getAngle(1, 0, 0, 1), GMath.HALF_PIF));
        assertTrue(GMath.isNearlyEqual(VectorUtils.getAngle(1, 0, -1, 0), GMath.PIF));
        assertTrue(GMath.isNearlyEqual(VectorUtils.getAngle(-1, 0, 1, 0), GMath.PIF));
    }

    @Test
    public void testLengthCalculation()
    {
        assertTrue(VectorUtils.length(0, 0) == 0);
        assertTrue(VectorUtils.length(1, 0) == 1);
        assertTrue(GMath.isNearlyEqual(VectorUtils.length(1, 1), Math.sqrt(2)));
        assertTrue(VectorUtils.squaredLength(1.f, 1) == 2);
        assertTrue(VectorUtils.length(0, -1) == 1);
    }

    @Test
    public void testSlope()
    {
        assertTrue(VectorUtils.getSlope(0, 8) == Float.MAX_VALUE);
        assertTrue(VectorUtils.getSlope(1, 8) == 8);
    }
}
