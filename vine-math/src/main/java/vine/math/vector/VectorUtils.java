package vine.math.vector;

import vine.math.GMath;

/**
 * Utility class for operations on 2 dimensional vectors. All methods take the
 * vectors each element and do not operate with Vec2f, Vec2d, Vec2i.
 *
 * <h3>Units</h3>
 * <p>
 * The unit for angle is degree. If a method returns radians or takes a
 * parameter as radians it will be notified in the corresponding docs. Positions
 * are always in the unit of the parameters and will return the same unit as the
 * given parameters.
 * </p>
 *
 * @author Steffen Kromm, first created on 04.05.2016
 */
public final class VectorUtils
{
    private VectorUtils()
    {
        // Utility class
    }

    /**
     * Calculates the squared length of the given vector.
     *
     * @param x
     *            The x coordinate of the vector
     * @param y
     *            The y coordinate of the vector
     * @return The squared length of the each element given 2d vector.
     */
    public static double squaredLength(final double x, final double y, final double z)
    {
        return dot(x, y, z, x, y, z);
    }

    /**
     * Calculates the squared length of the given vector.
     *
     * @param x
     *            The x coordinate of the vector
     * @param y
     *            The y coordinate of the vector
     * @return The squared length of the each element given 2d vector.
     */
    public static float squaredLength(final float x, final float y, final float z)
    {
        return dot(x, y, z, x, y, z);
    }

    /**
     * Calculates the length of the given vector.
     *
     * @param x
     *            The x coordinate of the vector
     * @param y
     *            The y coordinate of the vector
     * @return The length of the elementwise given 2d vector.
     */
    public static double length(final double x, final double y, final double z)
    {
        return GMath.sqrt(dot(x, y, z, x, y, z));
    }

    /**
     * Calculates the dot product of the 2 each element given 3d vectors.
     *
     * @param x1
     *            x Coordinate of the 1st vector
     * @param y1
     *            y Coordinate of the 1st vector
     * @param z1
     *            z Coordinate of the 1st vector
     * @param x2
     *            x Coordinate of the 2nd vector
     * @param y2
     *            y Coordinate of the 2nd vector
     * @param z2
     *            z Coordinate of the 2nd vector
     * @return The dot product of the given vectors.
     */
    public static double dot(
            final double x1,
            final double y1,
            final double z1,
            final double x2,
            final double y2,
            final double z2)
    {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    /**
     * Calculates the dot product of the 2 each element given 3d vectors.
     *
     * @param x1
     *            x Coordinate of the 1st vector
     * @param y1
     *            y Coordinate of the 1st vector
     * @param z1
     *            z Coordinate of the 1st vector
     * @param x2
     *            x Coordinate of the 2nd vector
     * @param y2
     *            y Coordinate of the 2nd vector
     * @param z2
     *            z Coordinate of the 2nd vector
     * @return The dot product of the given vectors.
     */
    public static float dot(
            final float x1,
            final float y1,
            final float z1,
            final float x2,
            final float y2,
            final float z2)
    {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    /**
     * Calculates the length of the given vector.
     *
     * @param x
     *            The x coordinate of the vector
     * @param y
     *            The y coordinate of the vector
     * @return The length of the elementwise given 2d vector.
     */
    public static double length(final float x, final float y)
    {
        return GMath.sqrt(dot(x, y, x, y));
    }

    /**
     * Calculates the squared length of the given vector.
     *
     * @param x
     *            The x coordinate of the vector
     * @param y
     *            The y coordinate of the vector
     * @return The squared length of the elementwise given 2d vector.
     */
    public static float squaredLength(final float x, final float y)
    {
        return dot(x, y, x, y);
    }

    /**
     * Calculates the dot product of the elementwise given 2 2d vectors.
     *
     * @param x1
     *            The x coordinate of the 1st vector
     * @param y1
     *            The y coordinate of the 1st vector
     * @param x2
     *            The x coordinate of the 2nd vector
     * @param y2
     *            The y coordinate of the 2nd vector
     * @return The value of the 2d dot product of the given vectors
     */
    public static float dot(final float x1, final float y1, final float x2, final float y2)
    {
        return x2 * x1 + y2 * y1;
    }

    /**
     * <h3>Naming</h3> The method is called pseudo because there is no
     * mathematical cross product function defined for 2d space. In fact, this
     * method calculates the z value of the 3d cross product with the given
     * vectors.
     * <p>
     * Calculates the cross product of the each element given 2 2d vectors. The
     * 2d cross product is equal to the magnitude of the resulting vector of the
     * 3d cross product of the 2 given vectors (such a vector is parallel to the
     * z-Axis). The returned vector is equal to the area of the parallelogram
     * defined by the given vectors.
     * </p>
     *
     * @param x1
     *            The x coordinate of the 1st vector
     * @param y1
     *            The y coordinate of the 1st vector
     * @param x2
     *            The x coordinate of the 2nd vector
     * @param y2
     *            The y coordinate of the 2nd vector
     * @return The value of the 2d cross product of the given vectors
     */
    public static float pseudoCross(final float x1, final float y1, final float x2, final float y2)
    {
        return x1 * y2 - x2 * y1;
    }

    /**
     * Return the angle between the 2 wach element defined vector 2ds in
     * radians. The value defines the direction of rotation from vector1 to
     * vector2 and points always to a angle between [0,PI].
     *
     * @param x1
     *            x Coordinate of the first vector
     * @param y1
     *            y Coordinate of the first vector
     * @param x2
     *            x Coordinate of the second vector
     * @param y2
     *            y Coordinate of the second vector
     * @return The smaller angle between the two vectors in radians (-PI,PI]
     *         defined as clockwise rotation of vector1 to vector2.
     */
    public static float getAngle(final float x1, final float y1, final float x2, final float y2)
    {
        if (GMath.isNearlyZero(x1) && GMath.isNearlyZero(y1)
                || GMath.isNearlyZero(x2) && GMath.isNearlyZero(y2))
        {
            return 0.f;
        }
        final float dot = dot(x1, y1, x2, y2);

        // Check if the vectors are perpendicular
        if (GMath.isNearlyZero(dot))
        {
            return GMath.HALF_PIF;
        }
        final float pseudoCross = pseudoCross(x1, y1, x2, y2);

        // Check if the vectors are collinear directions
        if (GMath.isNearlyZero(pseudoCross))
        {
            // Opposite direction
            if (dot < -GMath.EPSILON)
            {
                return GMath.PIF;
            }
            // Because we know the vectors are collinear, but not opposite
            // directions, they must have the same direction.
            else
            {
                return 0.f;
            }
        }
        float angle = GMath.atan2(pseudoCross, dot);
        if (angle > GMath.PIF)
        {
            angle -= GMath.TWO_PIF;
        } else if (angle < -GMath.PIF)
        {
            angle += GMath.TWO_PIF;
        }
        return angle;
    }

    public static boolean isCollinear(final float x1, final float y1, final float x2, final float y2)
    {
        return GMath.isNearlyZero(pseudoCross(x1, y1, x2, y2));
    }

    public static boolean isSameDirection(final float x1, final float y1, final float x2, final float y2)
    {
        return isCollinear(x1, y1, x2, y2) && dot(x1, y1, x2, y2) > GMath.EPSILON;
    }

    public static boolean isOppositeDirection(final float x1, final float y1, final float x2, final float y2)
    {
        return isCollinear(x1, y1, x2, y2) && dot(x1, y1, x2, y2) < -GMath.EPSILON;
    }

    /**
     * Calculates the slope of the given vector interpreted as a direction in 2d
     * space.
     *
     * @param direction
     *            The direction whichs slope is calculated
     * @return The slope of the given direction
     */
    public static float getSlope(final float x, final float y)
    {
        if (GMath.isNearlyZero(x))
        {
            return Float.MAX_VALUE;
        } else
        {
            return y / x;
        }
    }

}
