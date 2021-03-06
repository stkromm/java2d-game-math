package vector.test;


import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import vine.math.GMath;
import vine.math.vector.MutableVec2f;
import vine.math.vector.Vec2f;


public class Vector2fTest
{

	@Test
	public void testAdd()
	{
		final MutableVec2f vector = new MutableVec2f(1, 2);
		vector.add(new Vec2f(2, 2));
		assertTrue(vector.equals(new Vec2f(3, 4)));
		vector.add(-5.f, 1.f);
		assertTrue(vector.equals(new Vec2f(-2, 5)));
		assertTrue(vector.equals(new Vec2f(-2, 5)));
		assertTrue(!vector.equals(null));
		assertTrue(!vector.equals(new Vec2f(-1, 5)));
		assertTrue(!vector.equals(new Vec2f(-1, 6)));
		assertTrue(!vector.equals(new Vec2f(-2, 6)));
		assertTrue(!vector.equals(new Vec2f(-2, 6)));
		vector.sub(vector);
		assertTrue(vector.nearlyEquals(0, 0));
		vector.translate(-2, 6);
		assertTrue(vector.equals(new Vec2f(-2, 6)));
		assertTrue(vector.equals(new Vec2f(-2, 6)));
		vector.addScaled(0, new Vec2f(5, 5));
		assertTrue(vector.equals(new Vec2f(-2, 6)));
	}

	@Test
	public void testNormalize()
	{
		final MutableVec2f vector = new MutableVec2f(3, 0);
		assertTrue(!vector.isNormalized());
		vector.normalize();
		assertTrue(Math.abs(Math.abs(vector.getX()) - 1.f) <= GMath.EPSILON);
		final Vec2f vecc = new Vec2f(vector);
		assertTrue(vecc.isNormalized());
		assertTrue(Math.abs(vector.getY()) <= GMath.EPSILON);
		assertTrue(Math.abs(Math.abs(vector.length()) - 1) <= GMath.EPSILON);
		final MutableVec2f nullVector = new MutableVec2f(0, 0);
		nullVector.normalize();
		assertTrue(nullVector.length() == 0);
		final Random rn = new Random();
		for (int i = 0; i <= 100000; i++)
		{
			final MutableVec2f vec = new MutableVec2f(
					rn.nextFloat() * System.currentTimeMillis() - 12345, System.nanoTime());
			vec.normalize();
			assertTrue(Math.abs(vec.length() - 1) <= GMath.EPSILON);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreationFail()
	{
		final Vec2f v = new Vec2f(null);
		System.out.println(v);
	}

	@Test
	public void testPropertyAccess()
	{
		assertTrue(new Vec2f().length() == 0);
		final MutableVec2f vector = new MutableVec2f(0, 0);
		vector.setX(1);
		assertTrue(vector.getX() == 1);
		vector.setY(1);
		assertTrue(vector.getY() == 1);
		vector.set(5, 6);
		assertTrue(vector.getX() == 5);
		assertTrue(vector.getY() == 6);
		final Vec2f vec = new Vec2f(vector);
		assertTrue(vec.getX() == 5);
		assertTrue(vec.getY() == 6);
		vector.set(0, 0);
		vector.set(vec);
		assertTrue(vector.getX() == 5);
		assertTrue(vector.getY() == 6);
	}

	@Test
	public void testDotProduct()
	{
		final Vec2f vector = new Vec2f(1, 0);
		assertTrue(vector.dot(vector) == 1.f);
		assertTrue(vector.dot(0, 1) == 0.f);
	}

	@Test
	public void testEquality()
	{
		final Vec2f vec = new Vec2f(4, 5);
		final Vec2f vec2 = new Vec2f(4, 5 + GMath.EPSILON);
		assertTrue(!vec.nearlyEquals(null));
		assertTrue(vec2.nearlyEquals(4, 5));
		assertTrue(!vec2.nearlyEquals(4.5f, 5));
		assertTrue(!vec2.nearlyEquals(4, 5.4f));
		assertTrue(vec.nearlyEquals(vec2));
		assertTrue(!vec.equals(vec2));
		assertTrue(!vec.nearlyEquals(5, 4));
		assertTrue(vec.equals(vec));
		assertTrue(!vec.equals(null));
		assertTrue(!vec.equals(""));
	}

	@Test
	public void testHashCode()
	{
		final Vec2f vec = new Vec2f(4, 5);
		final Vec2f vec2 = new Vec2f(4, 5);
		assertTrue(vec.hashCode() == vec2.hashCode());
		assertTrue(vec.hashCode() != new Vec2f().hashCode());
	}

	@Test
	public void testStringRepresentation()
	{
		final Vec2f vec = new Vec2f();
		assertTrue(vec.toString().equals(new Vec2f().toString()));
	}

	@Test
	public void testScaling()
	{
		MutableVec2f vector = new MutableVec2f(1, 2);
		vector.uniformScale(0);
		assertTrue(vector.equals(new Vec2f(0, 0)));
		vector = new MutableVec2f();
		vector.set(4, 6);

		vector.scale(1.f / vector.length(), 1.f / vector.length());
		assertTrue(Math.abs(vector.length() - 1) <= GMath.EPSILON);


		final MutableVec2f vec = new MutableVec2f(vector);
		vec.scale(new Vec2f());
		assertTrue(GMath.isNearlyZero(vec.length()));
	}

	@Test
	public void testAngleBetween()
	{
		Vec2f vector = new Vec2f(1.f, 0.f);
		Vec2f vector2 = new Vec2f(1.0f, 1.f);
		assertTrue(Math.abs(vector.getAngle(vector2) - GMath.PIF / 4) <= GMath.EPSILON);
		vector2 = new Vec2f(0, 0);
		assertTrue(vector.getAngle(vector2) == 0);
		vector = new Vec2f(0, 0);
		assertTrue(vector2.getAngle(vector) == 0);
		assertTrue(new Vec2f(0.5f, -1.5f).getAngle(new Vec2f(-1, 3)) == GMath.PIF);
	}

	@Test
	public void testRotation()
	{
		final Vec2f vector = new Vec2f(2, 1);
		MutableVec2f vec = new MutableVec2f(vector);
		vec.rotate180();
		vec.rotate180();
		assertTrue(vector.nearlyEquals(vec));
		vec = new MutableVec2f(1, 1);
		vec.rotate90(false);
		assertTrue(vec.nearlyEquals(-1, 1));
		vec.rotate90(true);
		vec.rotateDegrees(-90);
		final Vec2f v2 = new Vec2f(1, -1);
		assertTrue(v2.nearlyEquals(vec));
	}

	@Test
	public void testSlope()
	{
		assertTrue(new Vec2f(1, 1).getSlope() == 1);
	}

	@Test
	public void testDistance()
	{
		final Vec2f vector = new Vec2f(0.f, 3.f);
		assertTrue(vector.distance(0, 3) == 0);
		assertTrue(vector.distance(new Vec2f(0, 0)) == 3);
		assertTrue(vector.squaredDistance(new Vec2f(0, 0)) == 9);
	}

	@Test
	public void testLength()
	{
		final Vec2f vector = new Vec2f(0.f, 3.f);
		assertTrue(!vector.isNearlyZero());
		assertTrue(!new Vec2f(2, 0).isNearlyZero());
		assertTrue(vector.length() == 3.f);
		assertTrue(vector.squaredLength() == 9.f);
		final Vec2f vec = new Vec2f(0.f, 0.f);
		assertTrue(vec.length() == 0);
		assertTrue(vec.squaredLength() == 0);
	}
}
