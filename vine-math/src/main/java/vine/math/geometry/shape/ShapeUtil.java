package vine.math.geometry.shape;


import vine.math.GMath;
import vine.math.vector.Vec2f;
import vine.math.vector.VectorUtils;


public final class ShapeUtil
{

	private ShapeUtil()
	{
		// Utility class
	}

	/**
	 * Creates a Aabb that contains each of the given points.
	 *
	 * @param points
	 *            Points for which an Aabb is calculated
	 * @return An Aabb, that contains each of the given points.
	 */
	public static MutableAabb createAabb(final Vec2f[] points)
	{
		final MutableAabb aabb = new MutableAabb(Float.MAX_VALUE, Float.MAX_VALUE, -Float.MAX_VALUE,
				-Float.MAX_VALUE);
		for (final Vec2f point : points)
		{
			addPointToAabb(aabb, point);
		}
		return aabb;
	}

	private static void addPointToAabb(final MutableAabb aabb, final Vec2f point)
	{
		aabb.setWidth(GMath.max(aabb.getWidth(), point.getX() - aabb.getX()));
		aabb.setHeight(GMath.max(aabb.getHeight(), point.getY() - aabb.getY()));
	}

	/**
	 * Checks, if the circle defined by the center (centerX, centerY) and the given radius contains
	 * the given point (x,y).
	 *
	 * @param centerX
	 *            The x coordinate of the center of the circle.
	 * @param centerY
	 *            The y coordinate of the center of the circle.
	 * @param radius
	 *            The radius of the circle.
	 * @param x
	 *            The x coordinates of the checked point.
	 * @param y
	 *            The y coordinates of the checked point.
	 * @return True, if the point is contained in the circle (or lays on the border of the circle).
	 */
	public static boolean circleContainsPoint(
			final float centerX,
			final float centerY,
			final float radius,
			final float x,
			final float y)
	{
		final float xDiff = x - centerX;
		final float yDiff = y - centerY;
		if (xDiff + yDiff == 0)
		{
			return true;
		}
		return VectorUtils.dot(xDiff, yDiff, xDiff, yDiff) <= radius * radius;
	}

	public static float circleArea(final float radius)
	{
		return GMath.PIF * radius * radius;
	}

	public static float circleCircumference(final float radius)
	{
		return GMath.TWO_PIF * radius;
	}

	/**
	 * Calculates the circumference of the ellipsoid.
	 *
	 * @param width
	 *            The width of the ellipsoid (maximum center border distance)
	 * @param height
	 *            The height of the ellipsoid (miniumal center border distance)
	 * @return The circumference of the ellipsoid.
	 */
	public static float ellipsoidCircumference(final float width, final float height)
	{
		final float accuracy = width / height;
		if (accuracy > 3 || accuracy < 0.333f)
		{
			// Ramanujan approximation
			final float h = (width - height) * (width - height)
					/ ((width + height) * (width + height));
			return GMath.PIF * (width + height) * (1 + 3 * h / (10 + GMath.sqrt(4 - 3 * h)));
		}
		else
		{
			// 5% approximation
			final double sqrt = GMath.sqrt((width * width + height * height) * 0.5);
			return (float) (GMath.TWO_PIF * sqrt);
		}
	}

	public static float ellipsoidArea(final float width, final float height)
	{
		return GMath.PIF * width * height;
	}

	public static boolean ellipsoidContainsPoint(
			final float centerX,
			final float centerY,
			final float width,
			final float height,
			final float x,
			final float y)
	{
		final double diff = VectorUtils.squaredLength(centerX - width / height * x, centerY - y);
		return diff <= width * width;
	}

	/**
	 * Calculates the area of the triangle defined by the three given points.
	 *
	 * @param x1
	 *            x Coordinate of the 1st point of the triangle
	 * @param y1
	 *            y Coordinate of the 1st point of the triangle
	 * @param x2
	 *            x Coordinate of the 2nd point of the triangle
	 * @param y2
	 *            y Coordinate of the 2nd point of the triangle
	 * @param x3
	 *            x Coordinate of the 3rd point of the triangle
	 * @param y3
	 *            y Coordinate of the 3rd point of the triangle
	 * @return The area of the defined triangle.
	 */
	public static float triangleArea(
			final float x1,
			final float y1,
			final float x2,
			final float y2,
			final float x3,
			final float y3)
	{
		final double a = VectorUtils.length(x1, y1);
		final double b = VectorUtils.length(x2, y2);
		final double c = VectorUtils.length(x3, y3);
		final double s = a + b + c;
		return GMath.sqrt(s * (s - a) * (s - b) * (s - c));
	}

	public static float triangleCircumference(
			final float x1,
			final float y1,
			final float x2,
			final float y2,
			final float x3,
			final float y3)
	{
		return VectorUtils.length(x1, y1) + VectorUtils.length(x2, y2) + VectorUtils.length(x3, y3);
	}

	public static float triangleContainsPoint(
			final float x1,
			final float y1,
			final float x2,
			final float y2,
			final float x3,
			final float y3,
			final float pointX,
			final float pointY)
	{
		return 0;
	}

	public static float rectangleArea(final float width, final float height)
	{
		return width * height;
	}

	/**
	 * Calculates the perimeter of the rectangle with the given width and height.
	 *
	 * @param width
	 *            Width of the rectangle (distance from lower left to lower right corner).
	 * @param height
	 *            Height of the rectangle (distance from the lower left to the upper left corner).
	 * @return The perimeter of the rectangle in the unit of the given width and height.
	 */
	public static float rectanglePerimeter(final float width, final float height)
	{
		return width * 2 + height * 2;
	}

	/**
	 * Checks, if the by x,y, width and height defined AABB contains the given point (pointX,
	 * pointY)
	 *
	 * @param x
	 *            Lower left corner x coordinate
	 * @param y
	 *            Lower left corner y coordinate
	 * @param w
	 *            width of the aabb
	 * @param h
	 *            height of the aabb
	 * @param pointX
	 *            x Coordinate of the checked point
	 * @param pointY
	 *            y Coordinate of the checked point
	 * @return True, if the point is located inside the rectangle shape (Also true is the point lays
	 *         on the border of the rectangle).
	 */
	public static boolean aabbContainsPoint(
			final float x,
			final float y,
			final float w,
			final float h,
			final float pointX,
			final float pointY)
	{
		return pointX >= x && pointX <= x + w && pointY >= y && pointY <= y + h;
	}

	/**
	 * Sames as polygonArea(polygon, polygon.length)
	 */
	public static float polygonArea(final float[] polygon)
	{
		return polygonArea(polygon, polygon.length);
	}

	/**
	 * http://alienryderflex.com/polygon_area/
	 * <p>
	 * if the parameters don't match the requirements or an error in the caluclation happens the
	 * error code -1 is returned.
	 * </p>
	 *
	 * @param polygon
	 *            2 values of the array will be used as 1 vertice with the x coordinate followed by
	 *            the y coordinate. The length of the polygon has to be just and greater equal 6.
	 * @param length
	 *            Marks the end of points in the given array that will be used to calculate the
	 *            array. Therefore the value of length has to be in the interval [6,polygon.length].
	 *            Also length % 2 has to be zero.
	 */
	public static float polygonArea(final float[] polygon, final int length)
	{
		if (!isValidPolygon(polygon) || length > polygon.length || length < 6
				|| GMath.isOdd(length))
		{
			return -1;
		}
		float area = polygon[length - 2] * polygon[1] - polygon[length - 1] * polygon[0];
		for (int i = length - 1; i >= 3; i -= 2)
		{
			area += polygon[i] * polygon[i - 3] - polygon[i - 1] * polygon[i - 2];
		}
		return GMath.abs(area * 0.5f);
	}

	/**
	 * Checks, if the given array of floats can be interpreted as a polygon of 2d vertices.
	 *
	 * @param polygon
	 *            The array, that is checked.
	 * @return True, if the array can be interpreted as an 2D polygon.
	 */
	public static boolean isValidPolygon(final float[] polygon)
	{
		return polygon != null && polygon.length >= 6 && !GMath.isOdd(polygon.length);
	}

	/**
	 * Same as polygonPerimeter(polygon, polygon.length)
	 */
	public static float polygonCircumference(final float[] polygon)
	{
		return polygonCircumference(polygon, polygon.length);
	}

	/**
	 * if the parameters don't match the requirements or an error in the calculation happens the
	 * error code -1 is returned.
	 *
	 * @param polygon
	 *            2 values of the array will be used as 1 vertex with the x coordinate followed by
	 *            the y coordinate. The length of the polygon has to be just and greater equal 6.
	 * @param length
	 *            Marks the end of points in the given array that will be used to calculate the
	 *            perimeter. Therefore the value of length has to be in the interval
	 *            [6,polygon.length]. Also length % 2 has to be an even number.
	 */
	public static float polygonCircumference(final float[] polygon, final int length)
	{
		if (!isValidPolygon(polygon))
		{
			return -1;
		}
		float perimeter = 0;
		for (int i = length - 3; i >= 0; i -= 2)
		{
			final float x = polygon[i + 1] - polygon[i - 1];
			final float y = polygon[i] - polygon[i + 2];
			perimeter += VectorUtils.length(x, y);
		}
		return perimeter;
	}
}
