package geometry.shape.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import vine.math.geometry.shape.Aabb;
import vine.math.vector.Vec2f;

public class AabbTest
{
    @Test
    public void testContainment()
    {
        final Aabb aabb = new Aabb(0, 0, 1, 1);
        assertTrue(aabb.contains(new Vec2f(1, 1)));
        assertTrue(!aabb.contains(new Vec2f(-1, 1)));
        assertTrue(!aabb.contains(-1, -1));
        assertTrue(aabb.contains(1, 0));
    }

    @Test
    public void testArea()
    {
        final Aabb aabb = new Aabb(0, 0, 2, 1);
        assertTrue(aabb.getArea() == 2);
    }

    @Test
    public void testCircumference()
    {
        final Aabb aabb = new Aabb(0, 0, 2, 1);
        assertTrue(aabb.getCircumference() == 6);
    }

    @Test
    public void testAccessors()
    {
        final Aabb aabb = new Aabb(0, 0, 2, 1);
        assertTrue(aabb.getX() == 0);
        assertTrue(aabb.getY() == 0);
        assertTrue(aabb.getWidth() == 2);
        assertTrue(aabb.getHeight() == 1);
    }

    /**
     * Null as origin vector should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreationFromNullOrigin()
    {
        Aabb aabb = new Aabb(new Vec2f(0, 0), null);
        aabb = new Aabb(null, new Vec2f(0, 0));
        aabb.equals(null);
    }

    @Test
    public void testCreationWithNegativExtends()
    {
        final Aabb aabb = new Aabb(new Vec2f(0, 0), new Vec2f(-1, -1));
        assertTrue(aabb.getHeight() == 1);
        assertTrue(aabb.getWidth() == 1);
    }

    @Test
    public void testEquals()
    {
        final Aabb aabb = new Aabb(1, 1, 4, 4);
        assertTrue(!aabb.equals(null));
        assertTrue(aabb.equals(new Aabb(1, 1, 4, 4)));
        assertTrue(!aabb.equals(new Aabb(1, 1, 4, 3)));
        assertTrue(!aabb.equals(new Aabb(1, 1, 3, 4)));
        assertTrue(!aabb.equals(new Aabb(1, 2, 4, 3)));
        assertTrue(!aabb.equals(new Aabb(2, 1, 4, 4)));
        assertTrue(!aabb.equals(new Aabb(2, -1, -4, 3)));
    }

    @Test
    public void testHashcode()
    {
        final Aabb aabb = new Aabb(new Vec2f(0, 0), new Vec2f(-1, -1));
        assertTrue(new Aabb(aabb).hashCode() == aabb.hashCode());
    }

    @Test
    public void testStringRepresentation()
    {
        final Aabb aabb = new Aabb(new Vec2f(0, 0), new Vec2f(-1, -1));
        assertTrue(new Aabb(aabb).toString().equals(aabb.toString()));
    }
}
