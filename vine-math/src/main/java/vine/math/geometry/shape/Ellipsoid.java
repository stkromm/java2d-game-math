package vine.math.geometry.shape;


import java.io.Serializable;

import vine.math.vector.Vec2f;
import vine.math.vector.VectorUtils;


public class Ellipsoid implements Shape, Serializable
{

	private static final long	serialVersionUID	= -9151844306521905347L;
	/**
	 * Factor, by which the height-radius differs from the radius (width of the ellipsoid).
	 */
	protected float				scale;
	protected float				x;
	protected float				y;
	protected float				rotation;
	protected float				radius;


	/**
	 * Creates a new Ellipsoid.
	 *
	 * @param x
	 *            The x Coordinate of the center of the ellipsoid
	 * @param y
	 *            The y Coordinate of the center of the ellipsoid
	 * @param width
	 *            The maximal center border distance of the ellipsoid
	 * @param height
	 *            The minimal center border distance of the ellipsoid
	 */
	public Ellipsoid(final float x, final float y, final float width, final float height)
	{
		scale = height / width;
		this.x = x;
		this.y = y;
		radius = width;
	}

	public float getHeight()
	{
		return radius * scale;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public float getWidth()
	{
		return radius;
	}

	@Override
	public boolean contains(final Vec2f point)
	{
		return contains(point.getX(), point.getY());
	}

	@Override
	public boolean contains(final float pointX, final float pointY)
	{
		final double length = VectorUtils.length(x - pointX, y - pointY * scale);
		return length <= radius;
	}

	@Override
	public float getArea()
	{
		return ShapeUtil.ellipsoidArea(radius, radius * scale);
	}

	@Override
	public float getCircumference()
	{
		return ShapeUtil.ellipsoidCircumference(radius, radius * scale);
	}
}
