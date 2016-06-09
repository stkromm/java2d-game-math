package vine.math.auxilliary;

/**
 * Written in 2016 by David Blackman and Sebastiano Vigna (vigna@acm.org).
 * http://xoroshiro.di.unimi.it/
 * <p>
 * "This is the successor to xorshift128+. It is the fastest full-period generator passing BigCrush
 * without systematic failures, but due to the relatively short period it is acceptable only for
 * applications with a mild amount of parallelism; otherwise, use a xorshift1024* generator.
 * </p>
 * <p>
 * Beside passing BigCrush, this generator passes the PractRand test suite up to (and included)
 * 16TB, with the exception of binary rank tests, which fail due to the lowest bit being an LFSR;
 * all other bits pass all tests. We suggest to use a sign test to extract a random Boolean value.
 * </p>
 * <p>
 * Note that the generator uses a simulated rotate operation, which most C compilers will turn into
 * a single instruction. In Java, you can use Long.rotateLeft(). In languages that do not make
 * low-level rotation instructions accessible xorshift128+ could be faster.
 * </p>
 * The state must be seeded so that it is not everywhere zero. If you have a 64-bit seed, we suggest
 * to seed a splitmix64 generator and use its output to fill
 * s."(http://xoroshiro.di.unimi.it/xoroshiro128plus.c)
 *
 * @author David Blackman and Sebastiano Vigna, Java-Port by Steffen Kromm, first created on
 *         18.05.2016
 *
 */
public class Xorshift128Plus
{

	private static final long	JUMP_VALUES[]	=
	{ 0xbeac0467eba5facbL, 0xd86b048b86aa9922L };
	/**
	 * The generation seed.
	 */
	private final long			s[]				= new long[2];
	/**
	 * Normalization constant for float. Defined in Libgdx.
	 */
	private static final float	NORM_FLOAT		= (float) (1.0f / Math.pow(2, 24));


	/**
	 * Creates a new random number generator with the standard seed.
	 */
	public Xorshift128Plus()
	{
		s[0] = 763461436;
		s[1] = 821624629;
	}

	/**
	 * Creates a new Generator with the given seed.
	 *
	 * @param lowerSeed
	 *            the lower half of the 128 bit seed
	 * @param upperSeed
	 *            the upper half of the 128 bit seed
	 */
	public Xorshift128Plus(final long lowerSeed, final long upperSeed)
	{
		s[0] = lowerSeed;
		s[1] = upperSeed;
	}

	/**
	 * Calculates the next random number in the series of the random numbers generated by the given
	 * seeds.
	 *
	 * @return A random float in the interval [0,1)
	 */
	public float nextFloat()
	{
		return (next() >>> 40) * NORM_FLOAT;
	}

	/**
	 * Calculates the next random long in the series of random numbers generated by the given seed.
	 *
	 * @return A random long value
	 */
	public long next()
	{
		final long s0 = s[0];
		long s1 = s[1];
		final long result = s0 + s1;

		s1 ^= s0;
		s[0] = Long.rotateLeft(s0, 55) ^ s1 ^ s1 << 14; // a, b
		s[1] = Long.rotateLeft(s1, 36); // c

		return result;
	}

	/**
	 * This is the jump function for the generator. It is equivalent to 2^64 calls to next(); it can
	 * be used to generate 2^64 non-overlapping subsequences for parallel computations.
	 */
	public void jump()
	{
		long s0 = 0;
		long s1 = 0;
		for (int i = 0; i < 2 * 64; i++)
		{
			for (int b = 0; b < 64; b++)
			{
				if (JUMP_VALUES[i] == 1L << b)
				{
					s0 ^= s[0];
					s1 ^= s[1];
				}
				next();
			}
		}
		s[0] = s0;
		s[1] = s1;
	}
}
