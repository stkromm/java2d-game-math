package vine.math;

import vine.math.auxilliary.SAT;
import vine.math.vector.Vec2f;
import vine.math.vector.VectorUtils;

public final class Intersection
{
    private Intersection()
    {

    }

    public static boolean intersectOBBOBB(
            final Vec2f origin1,
            final Vec2f upperLeft1,
            final Vec2f lowerRight1,
            final Vec2f origin2,
            final Vec2f upperLeft2,
            final Vec2f lowerRight2,
            final HitData data)
    {
        float axisX;
        float axisY;
        for (int i = 0; i < 2; i++)
        {
            if (i == 0)
            {
                axisX = lowerRight1.getX() - origin1.getX();
                axisY = lowerRight1.getY() - origin1.getY();
            } else
            {
                axisX = upperLeft1.getX() - origin1.getX();
                axisY = upperLeft1.getY() - origin1.getY();
            }
            if (!SAT.obbOverlapsAxis(
                    origin1.getX(),
                    origin1.getY(),
                    axisX,
                    axisY,
                    origin2.getX(),
                    origin2.getY(),
                    lowerRight2.getX(),
                    lowerRight2.getY(),
                    upperLeft2.getX(),
                    upperLeft2.getY()))
            {
                return false;
            }
        }
        for (int i = 0; i < 2; i++)
        {
            if (i == 0)
            {
                axisX = lowerRight2.getX() - origin2.getX();
                axisY = lowerRight2.getY() - origin2.getY();
            } else
            {
                axisX = upperLeft2.getX() - origin2.getX();
                axisY = upperLeft2.getY() - origin2.getY();
            }
            if (!SAT.obbOverlapsAxis(
                    origin2.getX(),
                    origin2.getY(),
                    axisX,
                    axisY,
                    origin1.getX(),
                    origin1.getY(),
                    lowerRight1.getX(),
                    lowerRight1.getY(),
                    upperLeft1.getX(),
                    upperLeft1.getY()))
            {
                return false;
            }

        }
        return true;
    }

    public static boolean intersectAABBAABB(
            final Vec2f positionA,
            final Vec2f extendsA,
            final Vec2f positionB,
            final Vec2f extendsB)
    {
        return Intersection.intersectAABBAABB(
                extendsA.getX(),
                extendsA.getY(),
                positionB.getX() - positionA.getX(),
                positionB.getY() - positionA.getY(),
                extendsB.getX(),
                extendsB.getY(),
                null);
    }

    public static boolean intersectAABBAABB(
            final Vec2f positionA,
            final Vec2f extendsA,
            final Vec2f positionB,
            final Vec2f extendsB,
            final HitData data)
    {
        return Intersection.intersectAABBAABB(
                extendsA.getX(),
                extendsA.getY(),
                positionB.getX() - positionA.getX(),
                positionB.getY() - positionA.getY(),
                extendsB.getX(),
                extendsB.getY(),
                data);
    }

    public static boolean intersectAABBAABB(
            final float extAX,
            final float extAY,
            final float difX,
            final float difY,
            final float extBX,
            final float extBY,
            final HitData data)
    {
        final boolean hit = 0 <= difX + extBX && difX <= extAX && 0 <= difY + extBY && difY <= extAY;

        if (hit && data != null)
        {
            final float xOverlap = extBX / 2 + extAX / 2 - GMath.abs(difX);
            final float yOverlap = extAY / 2 + extBY / 2 - GMath.abs(difY);
            if (yOverlap < xOverlap)
            {
                if (difY < 0)
                {
                    data.setNormal(0, -1);
                }
                {
                    data.setNormal(0, 1);
                }
                data.setPenetration(difY < 0 ? -yOverlap : yOverlap);

            } else
            {
                if (difX < 0)
                {
                    data.setNormal(-1, 0);
                } else
                {
                    data.setNormal(1, 0);
                }
                data.setPenetration(xOverlap);
            }
        }
        return hit;
    }

    public static boolean intersectAABBCircle(
            final Vec2f position,
            final Vec2f extend,
            final Vec2f point,
            final float radius)
    {
        return intersectAABBCircle(
                position.getX(),
                position.getY(),
                extend.getX(),
                extend.getY(),
                point.getX(),
                point.getY(),
                radius,
                null);
    }

    public static boolean intersectAABBCircle(
            final Vec2f position,
            final Vec2f extend,
            final Vec2f point,
            final float radius,
            final HitData data)
    {
        return intersectAABBCircle(
                position.getX(),
                position.getY(),
                extend.getX(),
                extend.getY(),
                point.getX(),
                point.getY(),
                radius,
                data);
    }

    public static boolean intersectAABBCircle(
            final float posX,
            final float posY,
            final float width,
            final float height,
            final float pointX,
            final float pointY,
            final float radius,
            final HitData data)
    {
        float relPosX = pointX - posX;
        float relPosY = pointY - posY;

        if (relPosX > width)
        {
            relPosX = width;
        } else if (relPosX < 0)
        {
            relPosX = 0;
        }

        if (relPosY > height)
        {
            relPosY = height;
        } else if (relPosY < 0)
        {
            relPosY = 0;
        }

        final double distance = VectorUtils.length(pointX - posX - relPosX, pointY - posY - relPosY);

        final boolean hit = distance <= radius;

        if (hit && data != null)
        {
            data.setPenetration((float) (radius - distance));
            data.setNormal(pointX - posX - relPosX, pointY - posY - relPosY);
            data.setPoint(relPosX + width, relPosY + height);
        }

        return hit;
    }

    public static boolean intersectAABBOBB(
            final Vec2f position,
            final Vec2f extend,
            final Vec2f origin,
            final Vec2f upperLeft,
            final Vec2f lowerRight)
    {
        return intersectAABBOBB(position, extend, origin, upperLeft, lowerRight, null);
    }

    public static boolean intersectAABBOBB(
            final Vec2f position,
            final Vec2f extend,
            final Vec2f origin,
            final Vec2f upperLeft,
            final Vec2f lowerRight,
            final HitData data)
    {
        // SAT Test with OBB against AABB Axis
        final boolean satAABB = intersectAABBAABB(
                extend.getX(),
                extend.getY(),
                origin.getX() - position.getX(),
                lowerRight.getY() - position.getY(),
                lowerRight.getX() + upperLeft.getX() - origin.getX(),
                upperLeft.getY(),
                data);
        if (!satAABB)
        {
            return false;
        }

        float axisX;
        float axisY;
        for (int i = 0; i < 2; i++)
        {
            if (i == 0)
            {
                axisX = lowerRight.getX() - origin.getX();
                axisY = lowerRight.getY() - origin.getY();
            } else
            {
                axisX = upperLeft.getX() - origin.getX();
                axisY = upperLeft.getY() - origin.getY();
            }
            if (!SAT.aabbOverlapsAxis(
                    origin.getX(),
                    origin.getY(),
                    axisX,
                    axisY,
                    position.getX(),
                    position.getY(),
                    extend.getX(),
                    extend.getY()))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean intersectAABBEllipsoid(
            final Vec2f position,
            final Vec2f extend,
            final Vec2f origin,
            final float height,
            final float width,
            final HitData data)
    {
        final float scale = width / height;
        final float newHeight = extend.getY() * scale;
        return intersectAABBCircle(
                position.getX(),
                position.getY(),
                extend.getX(),
                newHeight,
                origin.getX(),
                origin.getY(),
                width,
                data);
    }

    public static boolean intersectAABBSegment(
            final Vec2f position,
            final Vec2f extend,
            final Vec2f pointA,
            final Vec2f pointB)
    {
        return intersectAABBSegment(
                position.getX(),
                position.getY(),
                extend.getX(),
                extend.getY(),
                pointA.getX(),
                pointA.getY(),
                pointB.getX(),
                pointB.getY(),
                null);
    }

    public static boolean intersectAABBSegment(
            final Vec2f position,
            final Vec2f extend,
            final Vec2f pointA,
            final Vec2f pointB,
            final HitData data)
    {
        return intersectAABBSegment(
                position.getX(),
                position.getY(),
                extend.getX(),
                extend.getY(),
                pointA.getX(),
                pointA.getY(),
                pointB.getX(),
                pointB.getY(),
                data);
    }

    public static boolean intersectAABBSegment(
            final float x,
            final float y,
            final float width,
            final float height,
            final float px1,
            final float py1,
            final float px2,
            final float py2,
            final HitData data)
    {
        final float dx = GMath.abs(px2 - px1);
        final float dy = GMath.abs(py2 - py1);
        final float minx = GMath.min(px1, px2);
        final float miny = GMath.min(py1, py2);
        return intersectAABBAABB(width, height, x - minx, y - miny, dx, dy, data)
                && SAT.aabbOverlapsAxis(px1, py1, px2 - px1, py2 - py1, x, y, width, height);
    }

    public static boolean intersectRayAABB(
            final Vec2f origin,
            final Vec2f direction,
            final Vec2f position,
            final Vec2f extend,
            final HitData data)
    {
        final float inversedDirX = 1 / direction.getX();
        final float inversedDirY = 1 / direction.getY();
        return Intersection.intersectRayAABB(
                origin.getX(),
                origin.getY(),
                inversedDirX,
                inversedDirY,
                position.getX(),
                position.getY(),
                extend.getX(),
                extend.getY(),
                data);
    }

    /**
     * Calculates the length of the ray from the origin to the intersection.
     * <p>
     * The given direction has to be normalized or the behaviour is undefined.
     * </p>
     * If there is no intersection the yield result is Float.MAX_VALUE
     */
    public static boolean intersectRayAABB(
            final float originX,
            final float originY,
            final float iDirecX,
            final float iDirecY,
            final float positionX,
            final float positionY,
            final float extendX,
            final float extendY,
            final HitData data)
    {
        final float t1 = (positionX - originX) * iDirecX;
        final float t2 = (positionX + extendX - originX) * iDirecX;
        final float t3 = (positionY - originY) * iDirecY;
        final float t4 = (positionY + extendY - originY) * iDirecY;

        final float tmin = GMath.max(GMath.min(t1, t2), GMath.min(t3, t4));
        final float tmax = GMath.min(GMath.max(t1, t2), GMath.max(t3, t4));
        return Float.MAX_VALUE != (tmax < 0 || tmin > tmax ? Float.MAX_VALUE : tmin);
    }

    /**
     * Checks, if the two given circles intersect each other.
     *
     * @see #intersectCircleCircle(float, float, float, float, float, float,
     *      HitData)
     */
    public static boolean intersectCircleCircle(
            final Vec2f centerA,
            final float radiusA,
            final Vec2f centerB,
            final float radiusB)
    {
        return intersectCircleCircle(
                centerA.getX(),
                centerA.getY(),
                centerB.getX(),
                centerB.getY(),
                radiusA,
                radiusB,
                null);
    }

    /**
     * Checks, if the two given circles intersect each other.
     *
     * @see #intersectCircleCircle(float, float, float, float, float, float,
     *      HitData)
     */
    public static boolean intersectCircleCircle(
            final Vec2f centerA,
            final float radiusA,
            final Vec2f centerB,
            final float radiusB,
            final HitData data)
    {
        return intersectCircleCircle(
                centerA.getX(),
                centerA.getY(),
                centerB.getX(),
                centerB.getY(),
                radiusA,
                radiusB,
                data);
    }

    /**
     * Checks, if the two given circles intersect each other.
     *
     * @see #intersectCircleCircle(float, float, float, float, float, float,
     *      HitData)
     */
    public static boolean intersectCircleCircle(
            final float x1,
            final float y1,
            final float x2,
            final float y2,
            final float radiusA,
            final float radiusB)
    {
        return intersectCircleCircle(x1, y1, x2, y2, radiusA, radiusB, null);
    }

    /**
     * Checks, if the two given circles intersect each other.
     *
     * It sets the data object, if given accordingly: The penetration is the
     * maximal distance, the two circles overlap. The hit- point is the nearest
     * point on the surface of the 2nd circle to the 1st circle's center. The
     * normal is the direction the 1st circle needs to move so the two circles
     * would not intersect anymore. The needed distance to do so would be the
     * negative penetration.
     *
     * @param x1
     *            x Coordinate of the center of the 1st circle.
     * @param y1
     *            y Coordinate of the center of the 1st circle.
     * @param x2
     *            x Coordinate of the center of the 2nd circle.
     * @param y2
     *            y Coordinate of the center of the 2nd circle.
     * @param radiusA
     *            radius of the 1st circle.
     * @param radiusB
     *            radius of the 2nd circle.
     * @param data
     *            Data, that will contain detailed intersection informations, if
     *            given.
     * @return True, if the two circles intersect.
     */
    public static boolean intersectCircleCircle(
            final float x1,
            final float y1,
            final float x2,
            final float y2,
            final float radiusA,
            final float radiusB,
            final HitData data)
    {
        final float radiusSum = radiusA + radiusB;
        final float difX = x2 - x1;
        final float difY = y2 - y1;
        final float dist = VectorUtils.squaredLength(difX, difY);
        final boolean hit = radiusSum * radiusSum >= dist;

        if (data != null)
        {
            data.setNormal(difX, difY);
            final float rtDist = (float) GMath.sqrt(dist);
            data.setPenetration(radiusSum - rtDist);
            data.setPoint(x1, y1);
            data.getPoint().addScaled(rtDist - radiusB, data.getNormal());
        }

        return hit;
    }

    public static boolean intersectRayRay(
            final Vec2f origin1,
            final Vec2f direction1,
            final Vec2f origin2,
            final Vec2f direction2)
    {
        return intersectRayRay(origin1, direction1, origin2, direction2, null);
    }

    public static boolean intersectRayRay(
            final Vec2f origin1,
            final Vec2f direction1,
            final Vec2f origin2,
            final Vec2f direction2,
            final HitData data)
    {
        final float dis = origin1.distance(origin2);
        final float x = origin1.getX() + direction1.getX() * dis;
        final float y = origin1.getY() + direction1.getY() * dis;
        final boolean hit = direction1.getSlope() != direction2.getSlope() && !GMath.isNearlyEqual(x, origin2.getX())
                && !GMath.isNearlyEqual(y, origin2.getY());

        if (hit && data != null)
        {
            data.setNormal(direction1.getX(), direction1.getY());

        }

        return hit;
    }

    public static boolean intersectRayCircle(
            final Vec2f origin,
            final Vec2f direction,
            final Vec2f point,
            final float radius,
            final HitData data)
    {
        final float a = direction.dot(direction);
        final float c1 = origin.getX() - point.getX();
        final float c2 = origin.getY() - point.getY();
        final float b = VectorUtils.dot(direction.getX() * 2, direction.getY() * 2, c1, c2);
        final float c = VectorUtils.dot(c1, c2, c1, c2) - radius * radius;
        final float d = b * b - 4 * a * c;
        return d >= 0;
    }

    public static boolean intersectSegmentSegment(
            final float point1X,
            final float point1Y,
            final float point2X,
            final float point2Y,
            final float point3X,
            final float point3Y,
            final float point4X,
            final float point4Y,
            final HitData data)
    {
        final float d = (point4Y - point3Y) * (point2X - point1X) - (point4X - point3X) * (point2Y - point1Y);
        if (data != null)
        {
            final float ua = ((point4X - point3X) * (point1Y - point3Y) - (point4Y - point3Y) * (point1X - point3X))
                    / d;
            data.setPoint(point1X + (point2X - point1X) * ua, point1Y + (point2Y - point1Y) * ua);
            data.setPenetration(ua);
            data.setNormal(point2X - point1X, point2Y - point1Y);
        }
        return d == 0;
    }
}
