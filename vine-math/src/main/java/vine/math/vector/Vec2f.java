package vine.math.vector;

import static vine.math.GMath.sqrt;

import java.io.Serializable;

import vine.math.GMath;

/**
 * Represents a mathematical immutable vector 2d.
 *
 * You may use MutableVec2f or derive your own vector to create a mutable
 * version of Vec2f.
 *
 * @author Steffen
 *
 */
public class Vec2f implements Serializable
{
    private static final long serialVersionUID   = -48013626869712862L;

    private static final byte INVALIDATED_LENGTH = -1;
    /**
     * Vector that represents the x axis;
     */
    public static final Vec2f X_AXIS             = new Vec2f(1, 0);
    /**
     * Vector that represents the y axis;
     */
    public static final Vec2f Y_AXIS             = new Vec2f(0, 1);
    /**
     * Vector that represents the x axis in negative direction;
     */
    public static final Vec2f NEGATIV_X_AXIS     = new Vec2f(-1, 0);
    /**
     * Vector that represents the y axis in negative direction;
     */
    public static final Vec2f NEGATIVE_Y_AXIS    = new Vec2f(0, -1);
    /**
     * Zero vector.
     */
    public static final Vec2f ZERO               = new Vec2f(0, 0);
    /**
     * x Value of the vector.
     */
    protected float           x;
    /**
     * y Value of the vector.
     */
    protected float           y;

    protected float           cachedLength       = INVALIDATED_LENGTH;

    /**
     * Creates a new vector of zero length.
     */
    public Vec2f()
    {
        // Empty constructor if you don't need to initialize the vector by
        // construction.
        cachedLength = 0;
    }

    /**
     * Creates a new Vector2f object, that represents the mathematical vector 2d
     * with the given elements.
     *
     * @param x
     *            The x value of the vector
     * @param y
     *            The y value of the vector
     */
    public Vec2f(final float x, final float y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a copy of the given vector.
     *
     * @param vector
     *            the vector added to this vector.
     */
    public Vec2f(final Vec2f vector)
    {
        if (vector == null)
        {
            throw new IllegalArgumentException("Tried to copy construct a Vec2f from null");
        }
        x = vector.x;
        y = vector.y;
        if (vector.cachedLength != INVALIDATED_LENGTH)
        {
            cachedLength = vector.cachedLength;
        }
    }

    /**
     * Invalidates cached calculations and recalculates them, if they are
     * requested. (length of the vector).
     *
     */
    protected final void invalidate()
    {
        cachedLength = INVALIDATED_LENGTH;
    }

    /**
     * Getter.
     *
     * @return the float value of the x element.
     */
    public final float getX()
    {
        return x;
    }

    /**
     * Getter.
     *
     * @return the float value of the y element.
     */
    public final float getY()
    {
        return y;
    }

    /**
     * Returns a perpendicular Vector2f for this vector. Returns the 0-Vector if
     * there is no perpendicular vector (simply because this vector has length
     * zero).
     *
     * @return The new created perpendicular vector.
     */
    public final Vec2f getPerpendicular()
    {
        return new Vec2f(-y, x);
    }

    /**
     * Calculates the dot product with 2 vectors.
     *
     * @param vector
     *            The vector, that is used to calculate a dot product with this
     *            vector.
     *
     * @return The dot product of this vector and the given.
     *
     */
    public final float dot(final Vec2f vector)
    {
        return vector == null ? 0 : VectorUtils.dot(x, y, vector.getX(), vector.getY());
    }

    public final float dot(final float x, final float y)
    {
        return VectorUtils.dot(this.x, this.y, x, y);
    }

    /**
     * Calculates the length of this Vector2f.
     *
     * @return the length of this Vector2f.
     */
    public final float length()
    {
        if (cachedLength == INVALIDATED_LENGTH)
        {
            cachedLength = (float) VectorUtils.length(x, y);
        }
        return cachedLength;
    }

    /**
     * Calculates the squared length of this Vector2f.
     *
     * @return the squared length of this Vector2f.
     */
    public final float squaredLength()
    {
        return VectorUtils.squaredLength(x, y);
    }

    /**
     * Returns the squared distance from the point defined by this Vec2f to the
     * point defined by the given Vec2f.
     *
     * @param vector
     *            Point to which the squared distance is calculated
     * @return The squared distance to the given point
     */
    public final float squaredDistance(final Vec2f vector)
    {
        return vector == null ? Float.MAX_VALUE : squaredDistance(vector.x, vector.y);
    }

    /**
     * Returns the squared distance from the point defined by the each element
     * given vector and this vector.
     *
     * @x x Coordinate of the point to which the squared distance is calculated
     * @y y Coordinate of the point to which the squared distance is calculated
     * @return The squared distance to the given point
     */
    public final float squaredDistance(final float x, final float y)
    {
        return VectorUtils.squaredLength(this.x - x, this.y - y);
    }

    /**
     * Returns the distance from the point defined by this Vec2f to the point
     * defined by the given Vec2f.
     *
     * @param vector
     *            Point to which the distance is calculated
     * @return The distance to the given point or Float.MAX_VALUE if distance
     *         could not be calculated.
     */
    public final float distance(final Vec2f vector)
    {
        return vector == null ? Float.MAX_VALUE : distance(vector.x, vector.y);
    }

    /**
     * Returns the distance from the point defined by the each element given
     * vector and this vector.
     *
     * @x x Coordinate of the point to which the distance is calculated
     * @y y Coordinate of the point to which the distance is calculated
     * @return The distance to the given point
     */
    public final float distance(final float x, final float y)
    {
        return (float) sqrt(squaredDistance(x, y));
    }

    /**
     * Calculates the inner angle between this and the given vector.
     *
     * @param vector
     *            The vector that angle between this vector is calculated
     * @return The angle between this and the given vector
     */
    public final float getAngle(final Vec2f vector)
    {
        if (vector == null)
        {
            return 0;
        } else
        {
            return getAngle(vector.x, vector.y);
        }
    }

    public final float getAngle(final float x, final float y)
    {
        return VectorUtils.getAngle(this.x, this.y, x, y);
    }

    /**
     * Checks, if this vector is normalized.
     *
     * @return True, if the vector is normalized, that is, length == 1 +- 1e-7
     */
    public final boolean isNormalized()
    {
        return GMath.isNearlyEqual(squaredLength() - 1, GMath.EPSILON * 0.1f);
    }

    /**
     * Checks, if this vector is of length zero.
     *
     * @return True, if the length of this vector is zero.
     */
    public final boolean isNearlyZero()
    {
        return GMath.isNearlyZero(x) && GMath.isNearlyZero(y);
    }

    /**
     * Calculates the slope of this vector, if interpreted as a direction in
     * space.
     *
     * @return The slope of the direction defined by this vector.
     */
    public final float getSlope()
    {
        return VectorUtils.getSlope(x, y);
    }

    /**
     * Returns true, if the given vector is equal to this vector (with error
     * tolerance).
     *
     * @param vector
     *            The vector, that is checked if it is equal to this vector
     * @return True, if the vectors are equal with respect to numerical
     *         inaccuracies.
     *
     * @see #equalByEps(float, float)
     */
    public final boolean nearlyEquals(final Vec2f vector)
    {
        if (vector == null)
        {
            return false;
        } else
        {
            return nearlyEquals(vector.getX(), vector.getY());
        }
    }

    /**
     * Returns true, if the given vector is equal to this vector (with error
     * tolerance).
     *
     * @param x
     *            x Coordinate of the compared vector.
     * @param y
     *            y Coordinate of the compared vector.
     * @return True, if the vectors are equal with respect to numerical
     *         inaccuracies.
     * @see #equalByEps(Vec2f)
     */
    public final boolean nearlyEquals(final float x, final float y)
    {
        return GMath.isNearlyEqual(x, this.x) && GMath.isNearlyEqual(y, this.y);
    }

    @Override
    public final boolean equals(final Object object)
    {
        if (!(object instanceof Vec2f))
        {
            return false;
        }
        if (object == this)
        {
            return true;
        }
        final Vec2f vector = (Vec2f) object;
        return vector.x == x && vector.y == y;
    }

    @Override
    public int hashCode()
    {
        int result = 2;
        result = 5 * result + Float.floatToRawIntBits(x);
        result = 7 * result + Float.floatToRawIntBits(y);
        return result;
    }

    @Override
    public String toString()
    {
        return "Vec2f(" + x + "," + y + ")";
    }
}