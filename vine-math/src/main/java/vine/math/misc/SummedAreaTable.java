package vine.math.misc;

/**
 *
 * @author Steffen Kromm, first created on 29.04.2016
 *
 */
public final class SummedAreaTable
{
    private SummedAreaTable()
    {
        // Utility
    }

    /**
     * Converts a 2d value array to a SummedAreaTable.
     *
     * @param values
     *            Value array that should be converted to a SummedAreaTable.
     * @param width
     *            The width of the given 2d array.
     */
    public static void convertFromSummedAreaTable(final float[] values, final int width)
    {
        final float[] result = values;
        final int height = values.length / width;
        for (int i = width * height - 1; i >= 1; i--)
        {
            final int x = i % width;
            if (x > 0)
            {
                result[i] -= result[i - 1];
            }
        }
        for (int j = width * height - 1; j >= 1; j--)
        {
            final int y = j / width;
            if (y > 0)
            {
                result[j] -= result[j - width];
            }
        }
    }

    /**
     * Converts a SummedAreaTable to the 2d value array, it was original created
     * from.
     *
     * @param values
     *            SummedAreaTable that should be converted to a value array.
     * @param width
     *            The width of the given 2d array.
     */
    public static void convertToSummedAreaTable(final float[] values, final int width)
    {
        final float[] result = values;
        final int height = values.length / width;
        for (int i = 1; i < width * height; i++)
        {
            final int x = i % width;
            if (x > 0)
            {
                result[i] += result[i - 1];
            }
        }
        for (int j = 1; j < width * height; j++)
        {
            final int y = j / width;
            if (y > 0)
            {
                result[j] += result[j - width];
            }
        }
    }
}
