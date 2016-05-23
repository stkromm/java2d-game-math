package vine.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import vine.math.vector.Vec2f;

public final class ConvexHull
{
    private final static Comparator<Vec2f> VERTEX_COMPARATOR = new Comparator<Vec2f>()
    {
        @Override
        public int compare(final Vec2f o1, final Vec2f o2)
        {
            if (o1.getX() > o2.getX())
            {
                return -1;
            } else if (o1.getX() == o2.getX())
            {
                if (o1.getY() >= o2.getY())
                {
                    return -1;
                } else
                {
                    return 1;
                }
            } else
            {
                return -1;
            }
        }

    };

    private ConvexHull()
    {
        // Utility class
    }

    public static Vec2f[] calculateConvexHull(final Vec2f[] vertices)
    {
        Arrays.sort(vertices, VERTEX_COMPARATOR);

        final List<Vec2f> upperList = new ArrayList<Vec2f>();
        final List<Vec2f> lowerList = new ArrayList<Vec2f>();

        // Calculate the upper list
        upperList.add(vertices[0]);
        upperList.add(vertices[1]);
        for (int i = 3; i <= vertices.length - 1; i++)
        {
            upperList.add(vertices[i]);
            while (upperList.size() > 2 && isClockwise(
                    upperList.get(upperList.size() - 1),
                    upperList.get(upperList.size() - 2),
                    upperList.get(upperList.size() - 3)))
            {
                upperList.remove(upperList.size() - 2);
            }
        }

        // Calculate the lower list
        lowerList.add(vertices[vertices.length - 1]);
        lowerList.add(vertices[vertices.length - 2]);
        for (int i = vertices.length - 3; i >= 0; i--)
        {
            lowerList.add(vertices[i]);
            while (lowerList.size() > 2 && isClockwise(
                    lowerList.get(lowerList.size() - 1),
                    lowerList.get(lowerList.size() - 2),
                    lowerList.get(lowerList.size() - 3)))
            {
                lowerList.remove(lowerList.size() - 2);
            }
        }
        // Remove the first and last vertice from the lower list, because they
        // are contained in the upperList.
        lowerList.remove(0);
        lowerList.remove(lowerList.size() - 1);

        // Merge lower and upper list
        lowerList.addAll(upperList);
        return (Vec2f[]) lowerList.toArray();
    }

    public static boolean isClockwise(final Vec2f vertice1, final Vec2f vertice2, final Vec2f vertice3)
    {
        final float x1 = vertice2.getX() - vertice1.getX();
        final float y1 = vertice2.getY() + vertice1.getY();
        final float x2 = vertice3.getX() - vertice2.getX();
        final float y2 = vertice3.getY() + vertice2.getY();
        return x1 * y1 + x2 * y2 >= 0;
    }
}
