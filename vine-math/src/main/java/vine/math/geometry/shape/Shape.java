package vine.math.geometry.shape;


import vine.math.vector.Vec2f;


public interface Shape
{

	/**
	 * @param x
	 *            x Coordinate that defines the point, that is checked for containment in the shape.
	 * @param y
	 *            y Coordinate that defines the point, that is checked for containment in the shape.
	 * @return True, if the given point defined by x and y is contained in the shape.
	 */
	boolean contains(float x, float y);

	/**
	 * Checks if a point, given in object space coordinates of this shape, is contained in this
	 * shape.
	 *
	 * @param point
	 *            Point, that is checked for containment in the shape.
	 * @return True, if the given point defined by a Vec2f is contained in the shape.
	 */
	boolean contains(final Vec2f point);

	/**
	 * * Checks if a point, given in object space coordinates of this shape, is contained in this
	 * shape.
	 *
	 * @return Calculates the area of the shape and returns it. The area is in unit that was used to
	 *         define the points and geometry of the shape.
	 */
	float getArea();

	/**
	 * @return Calculates the perimeter of the shape and returns it. The perimeter is in the unit
	 *         that was used to define the points and geometry of the shape.
	 */
	float getCircumference();
}
