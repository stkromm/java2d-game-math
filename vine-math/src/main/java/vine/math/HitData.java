package vine.math;

import vine.math.vector.MutableVec2f;

public class HitData
{
    private final MutableVec2f point  = new MutableVec2f();
    private final MutableVec2f normal = new MutableVec2f();
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
