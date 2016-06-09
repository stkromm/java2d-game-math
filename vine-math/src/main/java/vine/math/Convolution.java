package vine.math;

public final class Convolution
{

	public static final float[]	SOBEL					= null;
	public static final float[]	SHARPEN					= null;
	public static final float[]	EMBOSSING_RIGHT_UP		= null;
	public static final float[]	EMBOSSING_RIGHT_DOWN	= null;
	public static final float[]	LOWPASS					= null;
	public static final float[]	HIGHPASS				= null;


	private Convolution()
	{
		// Utiltiy class
	}

	/**
	 * Calculates the convolution for the given parameters.
	 *
	 * @param values
	 *            The source value matrix.
	 * @param valuesWidth
	 *            The width of the source matrix.
	 * @param filter
	 *            The filter matrix.
	 * @param filterWidth
	 *            The width of the filter matrix.
	 * @param dest
	 *            The array to store the result within
	 */
	public static void convolute(
			final float[] values,
			final int valuesWidth,
			final float[] filter,
			final int filterWidth,
			final float[] dest)
	{
		final int valuesHeight = values.length / valuesWidth;
		final int filterHeight = filter.length / filterWidth;

		for (int a = valuesWidth - 1; a >= 0; a--)
		{
			for (int b = valuesHeight - 1; b >= 0; b--)
			{
				float tmp = 0;
				for (int i = filterWidth - 1; i >= 0; i--)
				{
					for (int j = filterHeight - 1; j >= 0; j--)
					{
						final int x = GMath.clamp(a - filterWidth / 2 + i, 0, valuesWidth);
						final int y = GMath.clamp(b - filterHeight / 2 + j, 0, valuesHeight);
						tmp += values[a + valuesWidth * b] * filter[x + y * filterWidth];
					}
				}
				dest[a + valuesWidth * b] = tmp;
			}
		}

	}
}
