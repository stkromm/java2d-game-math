package vine.math.geometry.shape;


import java.io.Serializable;

import vine.math.vector.Vec2f;


public class Polygon implements Shape, Serializable
{

	private static final long serialVersionUID = -4142622807950600590L;


	@Override
	public boolean contains(final float x, final float y)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(final Vec2f point)
	{
		return contains(point.getX(), point.getY());
	}

	@Override
	public float getArea()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCircumference()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
