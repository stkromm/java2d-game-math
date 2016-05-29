package vine.math;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import vine.math.vector.MutableVec2f;

public class IntersectionTests
{

    @Test
    public void testCircleCircle()
    {
        final HitData hit = new HitData();
        final MutableVec2f center1 = new MutableVec2f(1, 1);
        final float radius1 = 1.f;
        final MutableVec2f center2 = new MutableVec2f(2, 2);
        final float radius2 = 1.5f;
        assertTrue(Intersection.intersectCircleCircle(center1, radius1, center2, radius2, hit));

    }

    @Test
    public void testAABBSegment()
    {
        final MutableVec2f origin1 = new MutableVec2f(1, 1);
        final MutableVec2f extend1 = new MutableVec2f(1, 1);
        final MutableVec2f point1 = new MutableVec2f(3, 1);
        final MutableVec2f point2 = new MutableVec2f(0, 2);
        assertTrue(Intersection.intersectAABBSegment(origin1, extend1, point1, point2));
    }

    @Test
    public void testAABBCircle()
    {
        final HitData hit = new HitData();
        final MutableVec2f origin1 = new MutableVec2f(1, 1);
        final MutableVec2f extend1 = new MutableVec2f(1, 1);
        final MutableVec2f center = new MutableVec2f(3, 0.999f);
        final float radius = 1.0001f;
        assertTrue(Intersection.intersectAABBCircle(origin1, extend1, center, radius, hit));
        System.out.println(hit);
    }

    @Test
    public void testAABBAABB()
    {
        final MutableVec2f origin1 = new MutableVec2f(1, 1);
        final MutableVec2f extend1 = new MutableVec2f(2, 2);
        final MutableVec2f origin2 = new MutableVec2f(0, 0);
        final MutableVec2f extend2 = new MutableVec2f(4, 4);
        assertTrue(Intersection.intersectAABBAABB(origin1, extend1, origin2, extend2));
        assertTrue(Intersection.intersectAABBAABB(origin2, extend2, origin1, extend1));
    }

    @Test
    public void testAABBOBB()
    {
        final MutableVec2f origin = new MutableVec2f(0, 4.0f);
        final MutableVec2f lowerRight = new MutableVec2f(4.0f, 0);
        final MutableVec2f upperLeft = new MutableVec2f(1, 5.0f);
        final MutableVec2f position = new MutableVec2f(0, 0);
        final MutableVec2f extend = new MutableVec2f(2, 2);
        assertTrue(Intersection.intersectAABBOBB(position, extend, origin, upperLeft, lowerRight));
        origin.set(0, 4.0001f);
        lowerRight.set(4.0001f, 0);
        upperLeft.set(1, 5.0001f);
        position.set(0, 0);
        extend.set(2, 2);
        assertTrue(!Intersection.intersectAABBOBB(position, extend, origin, upperLeft, lowerRight));
        origin.set(0, 1);
        lowerRight.set(0, 1);
        upperLeft.set(3, 3);
        position.set(1, 1);
        extend.set(1, 1);
        assertTrue(Intersection.intersectAABBOBB(position, extend, origin, upperLeft, lowerRight));
    }
}
