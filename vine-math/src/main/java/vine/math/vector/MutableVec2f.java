package vine.math.vector;


import vine.math.GMath;
import vine.math.Interpolation;
import vine.math.geometry.Transformable;


/*
 * Method Chaining:
 * It's ambiguous if the returned vector is the same
 * instance or a new instance.
 * I think the convenience of method chaining is in this case not worth this danger of not
 * knowing when new objects are created.
 */
/**
 * Mutable version of Vec2f.
 *
 * @see Vec2f
 * @author Steffen Kromm, first created on 04.05.2016
 *
 */
public class MutableVec2f extends Vec2f implements Transformable
{

	private static final long serialVersionUID = 7194729065343295239L;


	/**
	 * Creates a zero vector.
	 */
	public MutableVec2f()
	{
		super();
	}

	/**
	 * Creates a new Vector2f object, that represents the mathematical vector 2d with the given
	 * float element.
	 *
	 * @param x
	 *            The x value of the vector
	 * @param y
	 *            The y value of the vector
	 */
	public MutableVec2f(final float x, final float y)
	{
		super(x, y);
	}

	/**
	 * Creates a copy of the given vector.
	 *
	 * @param vector
	 *            The vector which is copied by the newly created.
	 */
	public MutableVec2f(final Vec2f vector)
	{
		super(vector);
	}

	/**
	 * @param x
	 *            x value of the vector.
	 */
	public final void setX(final float x)
	{
		this.x = x;
		invalidate();
	}

	/**
	 * @param y
	 *            y value of the vector.
	 */
	public final void setY(final float y)
	{
		this.y = y;
		invalidate();
	}

	/**
	 * Sets the x and y value of this vector equal to the given.
	 *
	 * @param vec
	 *            The vector with which the values of this vector are setted.
	 */
	public final void set(final Vec2f vec)
	{
		x = vec.x;
		y = vec.y;
		invalidate();
	}

	/**
	 *
	 * @param x
	 *            The new x Coordinate of this vector.
	 * @param y
	 *            The new y Coordinate of this vector.
	 */
	public final void set(final float x, final float y)
	{
		this.x = x;
		this.y = y;
		invalidate();
	}

	@Override
	public final void translate(final float x, final float y)
	{
		add(x, y);
	}

	/**
	 * Adds the given values to the corresponding elements of this Vector2f.
	 *
	 * @param x
	 *            The x value added to this x value
	 * @param y
	 *            The y value added to this y value
	 */
	public final void add(final float x, final float y)
	{
		this.x += x;
		this.y += y;
		invalidate();
	}

	/**
	 * Adds the elements of the given vector the elements of this Vector2f.
	 *
	 * @param vector
	 *            The vector, which elements are added to the elements of this vector
	 */
	public final void add(final Vec2f vector) throws NullPointerException
	{
		add(vector.x, vector.y);
	}

	/**
	 * Adds the given vector multiplied by the given factor.
	 *
	 * @param scale
	 *            The factor with which the added vector is scaled.
	 * @param vector
	 *            The added vector.
	 */
	public final void addScaled(final float scale, final Vec2f vector) throws NullPointerException
	{
		add(vector.x * scale, vector.y * scale);
	}

	/**
	 * Subtracts the elements of the given vector from the elements of this Vector2f.
	 *
	 * @param vector
	 *            The vector which is subtracted from this.
	 */
	public final void sub(final Vec2f vector) throws NullPointerException
	{
		sub(vector.x, vector.y);
	}

	/**
	 * Substracts the each element given vector from this vector.
	 *
	 * @param x
	 *            The x element of the subtracted vector
	 * @param y
	 *            The y element of the subtracted vector
	 */
	public final void sub(final float x, final float y)
	{
		this.x -= x;
		this.y -= y;
		invalidate();
	}

	/**
	 * Rotates this vector by the given degree angle. (positive value means counterclockwise)
	 *
	 * @param degrees
	 *            The angle of rotation.
	 */
	public final void rotateDegrees(final float degrees)
	{
		rotate(GMath.toRadians(degrees));
	}

	@Override
	public final void rotate(final float radians)
	{
		final float cos = GMath.cos(radians);
		final float sin = GMath.sin(radians);

		final float newX = x * cos - y * sin;
		final float newY = x * sin + y * cos;
		x = newX;
		y = newY;
	}

	/**
	 * Rotates this vector by 180 degree.
	 */
	public final void rotate180()
	{
		uniformScale(-1.f);
	}

	/**
	 * Rotates this vector by 90 degrees.
	 *
	 * @param clockwise
	 *            If true, the rotation is clockwise, false counterclockwise.
	 */
	public final void rotate90(final boolean clockwise)
	{
		final float tmpX = x;
		if (clockwise)
		{
			x = y;
			y = -tmpX;
		}
		else
		{
			x = -y;
			y = tmpX;
		}
	}

	@Override
	public final void scale(final float x, final float y)
	{
		this.x *= x;
		this.y *= y;
		invalidate();
	}

	/**
	 * Scales this vector with the given vector. Each element of this vector is scaled by the factor
	 * given by the corresponding element of the given vector.
	 *
	 * @param scaleVector
	 *            The vector with scale factors.
	 */
	public final void scale(final Vec2f scaleVector) throws NullPointerException
	{
		x *= scaleVector.x;
		y *= scaleVector.y;
		invalidate();
	}

	@Override
	public final void uniformScale(final float factor)
	{
		scale(factor, factor);
		cachedLength *= factor;
	}

	/**
	 * Normalizes this vector.
	 */
	public final void normalize()
	{
		if (isNearlyZero())
		{
			return;
		}
		final double inversedLength = 1.f / length();
		x *= inversedLength;
		y *= inversedLength;
		cachedLength = 1;
	}

	public final void lerp(final Vec2f vector, final float alpha) throws NullPointerException
	{
		x = Interpolation.lerp(x, vector.x, alpha);
		y = Interpolation.lerp(y, vector.y, alpha);
	}
}
