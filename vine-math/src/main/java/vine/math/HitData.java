package vine.math;

import vine.math.vector.MutableVec2f;

/**
 * POJO for details of an intersection.
 *
 * @author Steffen Kromm, first created on 29.05.2016
 *
 */
public class HitData
{
    /**
     * Contact-Point of the intersection.
     */
    private final MutableVec2f point  = new MutableVec2f();
    /**
     * Normal of the intersection.
     */
    private final MutableVec2f normal = new MutableVec2f();
    /**
     * The maximum distance the two objects of the intersection are overlapping.
     */
    private float              penetration;

    public MutableVec2f getPoint()
    {
        return point;
    }

    public MutableVec2f getNormal()
    {
        return normal;
    }

    public float getPenetration()
    {
        return penetration;
    }

    public void setPenetration(final float v)
    {
        penetration = v;
    }

    /**
     * Sets the normal with the given vector and normalizes it.
     *
     * @param x
     *            x Coordinate of the vector.
     * @param y
     *            y Coordinate of the vector.
     */
    public void setNormal(final float x, final float y)
    {
        normal.set(x, y);
        normal.normalize();
    }

    public void setPoint(final float x, final float y)
    {
        point.set(x, y);
    }

    @Override
    public String toString()
    {
        return "HitData Penetration:" + penetration + " Normal:" + normal + " Point:" + point;
    }
}
