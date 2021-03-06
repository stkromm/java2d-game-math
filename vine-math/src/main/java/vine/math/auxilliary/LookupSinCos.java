package vine.math.auxilliary;


import vine.math.GMath;


public final class LookupSinCos
{

	private static final int		SIN_BITS;
	private static final int		SIN_MASK;
	private static final int		SIN_COUNT;
	private static final float		RAD_FULL;
	private static final float		RAD_TO_INDEX;
	private static final float		DEG_FULL;
	private static final float		DEG_TO_INDEX;
	private static final float[]	SIN_VALUES;

	static
	{
		SIN_BITS = 12;
		SIN_MASK = ~(-1 << SIN_BITS);
		SIN_COUNT = SIN_MASK + 1;

		RAD_FULL = GMath.PIF * 2.0f;
		DEG_FULL = 360.0f;
		RAD_TO_INDEX = SIN_COUNT / RAD_FULL;
		DEG_TO_INDEX = SIN_COUNT / DEG_FULL;

		SIN_VALUES = new float[SIN_COUNT];

		for (int i = 0; i < SIN_COUNT; i++)
		{
			SIN_VALUES[i] = (float) Math.sin((i + 0.5f) / SIN_COUNT * RAD_FULL);
		}

		// Four cardinal directions (credits: Nate)
		for (int i = 0; i < 360; i += 90)
		{
			SIN_VALUES[i * Float.floatToIntBits(DEG_TO_INDEX) & SIN_MASK] = (float) Math
					.sin(i * Math.PI / 180.0);
		}
	}


	private LookupSinCos()
	{
		// Utility class
	}

	// Lookup table for cos / sin :
	// http://www.java-gaming.org/topics/fast-math-sin-cos-lookup-tables/24191/view.html
	public static float sin(final float rad)
	{
		final int index = Math.round(rad * RAD_TO_INDEX);
		return SIN_VALUES[index & SIN_MASK];
	}

	public static float cos(final float rad)
	{
		final int index = Math.round((rad + GMath.HALF_PIF) * RAD_TO_INDEX);
		return SIN_VALUES[index & SIN_MASK];
	}
}
