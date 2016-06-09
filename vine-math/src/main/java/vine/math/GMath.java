package vine.math;


import vine.math.auxilliary.FactorialLookup;
import vine.math.auxilliary.Icecore;
import vine.math.auxilliary.LookupSinCos;
import vine.math.auxilliary.Xorshift128Plus;


/**
 * A math function collection, which replaces java.math.Math for most part and extends with
 * functions useful for graphics and game applications. All Method favor speed before
 * special-case-handling and accuracy.
 * <p>
 * Function, that are prefixed with "fast" may have a really bad accuracy and can only fit a limited
 * number of use-cases. But for the case, that accuracy is no concern for your problem, use these
 * function, because the are multiple times faster than the regular methods.
 * </p>
 * <p>
 * Equivalents of Math functions may have a lower accuracy, too. But these accuracy errors should be
 * negligible due to floating-point errors and the speed-up.
 * </p>
 *
 * @author Steffen Kromm, first created on 25.01.2016
 *
 */
public final class GMath
{

	/**
	 * Random number generator.
	 */
	private static final Xorshift128Plus	RANDOM_GENERATOR	= new Xorshift128Plus();
	/**
	 * Magic number for value of the lsb of int values.
	 */
	private static final int				ONE_BIT				= 1;
	/**
	 * Magic number to round floats.
	 */
	public static final float				FLOAT_ROUND_SHIFT	= 0.5f;
	/**
	 * Magic number to shift floats, so they can be correct rounded.
	 */
	public static final int					FLOAT_ROUND_INT		= GMath.pow(2, 14);
	/**
	 * Wikipedia:
	 * "The number PI is a mathematical constant, the ratio of a circle's circumference to its diameter."
	 */
	public static final float				PIF					= 3.14159265358979323846f;
	public static final double				PI					= 3.14159265358979323846;
	public static final float				TWO_PIF				= PIF * 2;
	public static final float				HALF_PIF			= PIF * 0.5f;
	/**
	 * Standard floating point error of 1e-6.
	 */
	public static final float				EPSILON				= 0.000001f;


	private GMath()
	{
		// Utility class
	}

	/**
	 * Calculates the binomial coefficient n over k. Only values between 0 and 20 are permitted.
	 *
	 * @param n
	 *            upper value
	 * @param k
	 *            lower value
	 * @return The binomial coefficient of the given values.
	 */
	public static double binominalCoefficient(final int n, final int k)
	{
		if (k < n / 2)
		{
			return factorial(n) * factorial(n - k) * (1 / (double) factorial(k));
		}
		else
		{
			return factorial(n) * factorial(n - (n - k)) * (1 / (double) factorial(n - k));
		}
	}

	/**
	 * Calculates the factorial of a given value n.
	 *
	 * @throws IllegalArgumentException
	 *             Thrown, if the value {@code n} exceeds the interval [0,20].
	 */
	public static long factorial(final int n) throws IllegalArgumentException
	{
		if (n < 0 || n > 20)
		{
			throw new IllegalArgumentException(n + " is no valid value to calculate a factorial.");
		}
		return FactorialLookup.factorial(n);
	}

	/**
	 * Calculates the squareroot of the given value.
	 */
	public static float sqrt(final double value)
	{
		return (float) StrictMath.sqrt(value);
	}

	/**
	 * Calculates the square root of the given value. This method is about two times faster than the
	 * regular sqrt method, but it is only a very rough approximation. The calculated value has a
	 * error of 0.5 - 1.5 % but a error up to 10% is possible.
	 */
	public static float fastSqrt(final float x)
	{
		if (isNearlyZero(x))
		{
			return 0;
		}
		else if (isNearlyEqual(x, 1))
		{
			return 1;
		}
		return Float.intBitsToFloat(532483686 + (Float.floatToRawIntBits(x) >> 1));
	}

	/**
	 * Calculates the value of a^b.
	 */
	public static float pow(final float a, final float b)
	{
		return (float) StrictMath.pow(a, b);
	}

	/**
	 * Calculates the value of a^b.
	 */
	public static int pow(final int a, final int b)
	{
		int base = a;
		int power = b;
		int result = 1;
		for (; power != 0; power >>= 1)
		{
			if ((power & ONE_BIT) == ONE_BIT)
			{
				result *= base;
			}
			base *= base;
		}
		return result;
	}

	/**
	 * https://github.com/jeffhain/jafama/blob/master/src/main/java/net/jafama/ FastMath.java
	 * 1e-13ish accuracy or better on whole double range.
	 *
	 * @param value
	 *            A double value.
	 * @param power
	 *            A power.
	 * @return value^power.
	 */
	public static float fastPow(final float value, final float power)
	{
		if (power == 0.0)
		{
			return 1.0f;
		}
		else if (power == 1.0)
		{
			return value;
		}
		else
		{
			return exp(power * ln(value));
		}
	}

	/**
	 * Evaluates the exponential function at {@code value}.
	 *
	 * @param value
	 *            The function parameter
	 * @return The function value at {@code value}
	 */
	public static float exp(final float value)
	{
		return (float) Math.exp(value);
	}

	/**
	 * Calculates exp faster than the standard version at the expense of accuracy.
	 *
	 * @param val
	 *            The function parameter
	 * @return The function value at {@code value}
	 */
	public static float fastExp(final float val)
	{
		final long exp = (long) (1512775L * val + 1072632447L);
		return Float.intBitsToFloat((int) (exp << 32));
	}

	public static float ln(final float value)
	{
		return (float) Math.log(value);
	}

	/**
	 * Accuracy about 1e-4.
	 */
	public static float log(final float a, final float value)
	{
		return fastLn(value) / ln(a);
	}

	public static float log(final float value)
	{
		return (float) Math.log10(value);
	}

	public static float fastLn(final float x)
	{
		return 6 * (x - 1) / (x + 1 + 4 * GMath.sqrt(x));
	}

	/**
	 * Returns the absolute value of the given value.
	 */
	public static int abs(final int value)
	{
		return Math.abs(value);
	}

	/**
	 * Returns the absolute value of the given value.
	 */
	public static float abs(final float value)
	{
		return Math.abs(value);
	}

	/**
	 * Calculates the minimum of both values.
	 *
	 * @param x
	 *            The 1st value
	 * @param y
	 *            The 2nd value
	 * @return The minium of the given values.
	 */
	public static float min(final float x, final float y)
	{
		return x <= y ? x : y;
	}

	public static float min(final float a, final float b, final float c)
	{
		return a < b ? a < c ? a : c : b < c ? b : c;
	}

	public static float min(final float[] values)
	{
		if (values.length == 0)
		{
			return 0;
		}
		if (values.length == 1)
		{
			return values[0];
		}
		float min = values[0];
		for (int i = values.length - 2; i >= 0; i--)
		{
			min = min(min, values[i]);
		}
		return min;
	}

	/**
	 * Calculates the minimum of both values.
	 *
	 * @param x
	 *            The 1st value
	 * @param y
	 *            The 2nd value
	 * @return The minimum of the given values.
	 */
	public static int min(final int x, final int y)
	{
		return x <= y ? x : y;
	}

	/**
	 * Calculates the minimum of the three given values.
	 *
	 * @param a
	 *            1st value
	 * @param b
	 *            2nd value
	 * @param c
	 *            2rd value
	 * @return The minimum of the given values.
	 */
	public static int min(final int a, final int b, final int c)
	{
		return a < b ? //
				a < c ? a : c : //
				b < c ? b : c;
	}

	/**
	 * Calculates the minimum of the array of given {@code int} values.
	 *
	 * @param values
	 *            The values of which the minimal value gets calculated
	 * @return The minimum of the input set of values.
	 */
	public static int min(final int[] values)
	{
		if (values.length == 0)
		{
			return 0;
		}
		if (values.length == 1)
		{
			return values[0];
		}
		int min = values[0];
		for (int i = values.length - 2; i >= 0; i--)
		{
			min = min(min, values[i]);
		}
		return min;
	}

	/**
	 * Calculates the maximum of both values.
	 *
	 * @param x
	 *            The 1st value
	 * @param y
	 *            The 2nd value
	 * @return The maximum of the given values.
	 */
	public static int max(final int x, final int y)
	{
		return x > y ? x : y;
	}

	/**
	 * Calculates the maximum of both values.
	 *
	 * @param x
	 *            The 1st value
	 * @param y
	 *            The 2nd value
	 * @return The maximum of the given values.
	 */
	public static float max(final float x, final float y)
	{
		return x > y ? x : y;
	}

	public static float max(final float a, final float b, final float c)
	{
		return a > b ? //
				a > c ? a : c : //
				b > c ? b : c;
	}

	public static float max(final float[] values)
	{
		if (values == null || values.length == 0)
		{
			return 0;
		}
		if (values.length == 1)
		{
			return values[0];
		}
		float max = values[0];
		for (int i = values.length - 2; i >= 0; i--)
		{
			max = max(max, values[i]);
		}
		return max;
	}

	public static int max(final int a, final int b, final int c)
	{
		return a > b ? //
				a > c ? a : c : //
				b > c ? b : c;
	}

	public static int max(final int[] values)
	{
		if (values == null || values.length == 0)
		{
			return 0;
		}
		if (values.length == 1)
		{
			return values[0];
		}
		int max = values[0];
		for (int i = values.length - 2; i >= 0; i--)
		{
			max = max(max, values[i]);
		}
		return max;
	}

	/**
	 * Checks, if the given value if even.
	 *
	 * @param value
	 *            The checked value
	 * @return True, if the given value is even
	 */
	public static boolean isEven(final int value)
	{
		return (value & 1) == 0;
	}

	/**
	 * Checks, if the given value is odd.
	 *
	 * @param value
	 *            The checked value
	 * @return True, if the given value is odd.
	 */
	public static boolean isOdd(final int value)
	{
		return (value & 1) == 1;
	}

	/**
	 * Checks, if the given value is a power of 2.
	 *
	 * @param value
	 *            Value to be checked.
	 * @return True, if the value is a power of two.
	 */
	public static boolean isPowerOfTwo(final int value)
	{
		return (value & value - 1) == 0;
	}

	/**
	 * Calculates the value of the next bigger power of 2.
	 *
	 * @param value
	 *            The value for which the next bigger power of 2 is searched.
	 * @return The smallest power of 2, n, for which n > value applies.
	 */
	public static int nextPowerOfTwo(final int value)
	{
		return value == 0 ? 1 : Integer.highestOneBit(value) * 2;
	}

	/**
	 * Clamps the given value to the interval [min,max].
	 *
	 * @param value
	 *            The value that should be clamped
	 * @param min
	 *            The lower interval border
	 * @param max
	 *            The upper interval border
	 * @return the clamped value
	 */
	public static float clamp(final float value, final float min, final float max)
	{
		if (value <= min)
		{
			return min;
		}
		if (value >= max)
		{
			return max;
		}
		return value;
	}

	/**
	 * Clamps the given value to the interval [min,max].
	 *
	 * @param value
	 *            The value that should be clamped
	 * @param min
	 *            The lower interval border
	 * @param max
	 *            The upper interval border
	 * @return the clamped value
	 */
	public static int clamp(final int value, final int min, final int max)
	{
		if (value <= min)
		{
			return min;
		}
		else if (value >= max)
		{
			return max;
		}
		else
		{
			return value;
		}
	}

	/**
	 * Clamps a negative value to zero.
	 *
	 * @param value
	 *            The value that should be clamped
	 * @return The clamped value
	 */
	public static float clampPositive(final float value)
	{
		if (value <= 0)
		{
			return 0;
		}
		return value;
	}

	/**
	 * Clamps a negative value to zero.
	 *
	 * @param value
	 *            The value that should be clamped
	 * @return The clamped value
	 */
	public static int clampPositive(final int value)
	{
		if (value <= 0)
		{
			return 0;
		}
		return value;
	}

	/**
	 * Repeats the given value in the interval {@code [min,max-1]}
	 *
	 * @param value
	 *            The repeated value
	 * @param min
	 *            The lower border of the repeat interval
	 * @param max
	 *            The supremum of the repeat interval
	 * @return A repeated value in the given interval.
	 */
	public static int repeat(final int value, final int min, final int max)
	{
		if (value > max)
		{
			return min + value % (max - min);
		}
		else if (value <= min)
		{
			return max - (min - value);
		}
		else
		{
			return value;
		}
	}

	/**
	 * Repeats the given value in the interval {@code [min,max-1]}
	 *
	 * @param value
	 *            The repeated value
	 * @param min
	 *            The lower border of the repeat interval
	 * @param max
	 *            The supremum of the repeat interval
	 * @return A repeated value in the given interval.
	 */
	public static float repeat(final float value, final float min, final float max)
	{
		if (value > max)
		{
			return min + value % (max - min);
		}
		else if (value <= min)
		{
			return max - (min - value);
		}
		else
		{
			return value;
		}
	}

	/**
	 * Checks if the given {@code value} lies in the interval [ {@code min},{@code max}].
	 *
	 * @param value
	 *            Value, that is checked for containment in the given interval.
	 * @param min
	 *            Lower border of the interval
	 * @param max
	 *            Upper border of the interval
	 * @return True, if {@code value} lies in the given interval.
	 */
	public static boolean isBetween(final double value, final double min, final double max)
	{
		assert min < max : "Called isBetween with false interval. min < max";
		return value >= min && value <= max;
	}

	public static boolean randomBoolean()
	{
		return random() > 0.5f;
	}

	/**
	 * Returns an random integer in the interval [0,max)
	 *
	 * @param max
	 *            The supremum of the interval of possible returned values.
	 * @return The random value in the interval
	 */
	public static int randomInteger(final int max)
	{
		return GMath.round(randomFloat(max));
	}

	public static int randomInteger(final int min, final int max)
	{
		assert min <= max : "Interval for random float is in wrong order. min > max";
		return (int) (RANDOM_GENERATOR.nextFloat() * (max - min)) + min;
	}

	/**
	 * Calculates a random float in the given interval [{@code min},{@code max} ).
	 * <p>
	 * Result may be undefined if {@code min > max}.
	 * </p>
	 */
	public static float randomFloat(final float min, final float max)
	{
		assert min <= max : "Interval for random float is in wrong order. min > max";
		return RANDOM_GENERATOR.nextFloat() * (max - min) + min;
	}

	/**
	 * Returns an random {@code float} in the interval {@code [0,max) } or {@code (max,0]} if max is
	 * negative.
	 *
	 * @param max
	 *            The supremum/infimum of the interval of possible returned values.
	 * @return The random value in the interval
	 */
	public static float randomFloat(final float max)
	{
		return RANDOM_GENERATOR.nextFloat() * max;
	}

	/**
	 * Calculates a new random {@code float} in the interval {@code [0,1)}.
	 *
	 * @return A new random {@code float} in the interval {@code [0,1)}
	 */
	public static float random()
	{
		return RANDOM_GENERATOR.nextFloat();
	}

	/**
	 * Calculates the cosin of the given radian value.
	 *
	 * @param rad
	 *            Radian input value for cos.
	 * @return The cos value for the given radian.
	 */
	public static float cos(final float rad)
	{
		return LookupSinCos.cos(rad);
	}

	/**
	 * Calculates the sin of the given radian value.
	 *
	 * @param rad
	 *            Radian input value for the sin function.
	 * @return Value of sin for the given radian value {@code rad}
	 */
	public static float sin(final float rad)
	{
		return LookupSinCos.sin(rad);
	}

	/**
	 * Calculates tan of the given value.
	 */
	public static float tan(final float value)
	{
		return sin(value) / cos(value);
	}

	/**
	 * http://http.developer.nvidia.com/Cg/cosh.html
	 */
	public static float cosh(final float value)
	{
		return 0.5f * (exp(value) + exp(-value));
	}

	/**
	 * Calculates sinh of the given value.
	 */
	public static float sinh(final float value)
	{
		return 0.5f * (exp(value) - exp(-value));
	}

	/**
	 * Calculates tanh of the given value.
	 */
	public static float tanh(final float value)
	{
		final float exp2x = exp(2 * value);
		return (exp2x - 1) / (exp2x + 1);
	}

	/**
	 * Calculates acos of the given value.
	 */
	public static float acos(final float value)
	{
		return (float) Math.acos(value);
	}

	/**
	 * Calculates acos of the given value. http://http.developer.nvidia.com/Cg/acos.html
	 */
	public static float fastAcos(final float value)
	{
		final float x = abs(value);
		float ret = -0.0187293f;
		ret = ret * x;
		ret = ret + 0.0742610f;
		ret = ret * x;
		ret = ret - 0.2121144f;
		ret = ret * x;
		ret = ret + HALF_PIF;
		ret = ret * sqrt(1.0 - x);
		ret = value < 0 ? ret - 2 * ret : ret;
		return value < 0 ? PIF * 2 + ret : ret;
	}

	// Alternative Implementation:
	// http://http.developer.nvidia.com/Cg/asin.html
	// BUT: Benchmarking suggests that Math.asin is still faster.
	/**
	 * Calculates the asin of the given value.<br>
	 */
	public static float asin(final float value)
	{
		return (float) Math.asin(value);
	}

	/**
	 * Calculates atan of the given value.
	 */
	public static float atan(final float value)
	{
		return atan2(value, 1);
	}

	/**
	 * Returns the angle theta from the conversion of rectangular coordinates (x, y) to polar
	 * coordinates (r, theta). This method computes the phase theta by computing an arc tangent of
	 * y/x in the range of -pi to pi.
	 *
	 * @param y
	 *            The y coordinate
	 * @param x
	 *            The x coordinate
	 * @return The polar coordinate (positive angle is counter-clockwise rotation).
	 */
	public static float atan2(final float y, final float x)
	{
		return Icecore.atan2(y, x);
	}

	/**
	 * Converts the given value from radian to angle unit.
	 *
	 * @param radians
	 *            The converted angle.
	 * @return The value of the given angle in degrees.
	 */
	public static float toDegress(final float radians)
	{
		return radians * 180.0f * (1 / PIF);
	}

	/**
	 * Converts the given value from angle to radian unit.
	 *
	 * @param angle
	 *            The converted angle.
	 * @return The value of the given angle in radians.
	 */
	public static float toRadians(final float angle)
	{
		return angle * (1 / 180.f) * PIF;
	}

	/**
	 * Unwinds the given radians and returns a new radian value in the interval {@code [-2Pi,2Pi]}.
	 *
	 * @param angle
	 *            the angle in radians, that should be unwinded.
	 * @return the unwinded angle (value lays in {@code [-2Pi,2Pi]}).
	 */
	public static float unwindRadians(final float angle)
	{
		float d = angle;
		while (d > PIF)
		{
			d -= TWO_PIF;
		}
		while (d < -PIF)
		{
			d += TWO_PIF;
		}
		return d;
	}

	/**
	 * Unwinds the given degrees and returns new degree value in the interval {@code [-180,180]}.
	 */
	public static float unwindDegrees(final float angle)
	{
		float d = angle;
		while (d > 180.f)
		{
			d -= 360.f;
		}
		while (d < -180.f)
		{
			d += 360.f;
		}
		return d;
	}

	/**
	 * Checks, if the given value is nearly equal to zero.
	 *
	 * @param value
	 *            The value, that is compared with zero.
	 * @return True, if the given value is near zero with a maximum difference of {@link #EPSILON}.
	 * @see {@link #isNearlyZero(float, float)} with {@link #EPSILON}
	 */
	public static boolean isNearlyZero(final double value)
	{
		return isNearlyZero(value, EPSILON);
	}

	/**
	 * Checks, if the given value is nearly equal to zero.
	 *
	 * @param value
	 *            The value, that is compared with zero.
	 * @param epsilon
	 *            The maximum difference of the value can have and still count as zero.
	 * @return True, if the given value is near zero with a maximum difference of {@link epsilon}.
	 */
	public static boolean isNearlyZero(final double value, final double epsilon)
	{
		return value <= epsilon && -value <= epsilon;
	}

	/**
	 * Checks if the given {@code float} numbers are nearly equal.
	 *
	 * @param val1
	 *            The 1st value to compare
	 * @param val2
	 *            The 2nd value to compare
	 * @see {@link #isNearlyEqual(float, float, float)} with {@link #EPSILON}
	 */
	public static boolean isNearlyEqual(final double val1, final double val2)
	{
		return isNearlyEqual(val1, val2, EPSILON);
	}

	/**
	 * Checks, if the given {@code float} numbers are nearly equal.
	 *
	 * @param val1
	 *            The 1st value to compare.
	 * @param val2
	 *            The 2nd value to compare.
	 * @param epsilon
	 *            The epsilon that limits the tolerated deviation of both numbers.
	 * @return True, if the deviation of both numbers is within [0, {@code epsilon}].
	 */
	public static boolean isNearlyEqual(final double val1, final double val2, final double epsilon)
	{
		return val1 > val2 ? val1 - val2 <= epsilon : val2 - val1 <= epsilon;
	}

	/**
	 * Algorithm taken from libgdx MathUtils class.
	 * <p>
	 * Rounds the given {@code float} value to the nearest {@code integer} value.
	 * </p>
	 * <p>
	 * This method will only properly round floats from -(2^14) to (Float.MAX_VALUE - 2^14).
	 * </p>
	 *
	 * @param value
	 *            The value, that should be rounded
	 * @return The rounded value
	 */
	public static int round(final float value)
	{
		return (int) (value + FLOAT_ROUND_INT + FLOAT_ROUND_SHIFT) - FLOAT_ROUND_INT;
	}

	/**
	 * Rounds a positive {@code float} to the nearest {@code integer} value. The result in not
	 * defined for negative values.
	 *
	 * @param value
	 *            The value, that should be rounded
	 * @return The rounded value
	 */
	public static int roundPositive(final float value)
	{
		assert value > -FLOAT_ROUND_SHIFT - EPSILON : "Tried to roundPositive negative value "
				+ value;
		return (int) (value + FLOAT_ROUND_SHIFT);
	}

	public static int snapToGrid(final float value, final int gridSize)
	{
		if (gridSize == 0 || gridSize == 1)
		{
			return round(value);
		}
		return round((value + 0.5f * gridSize) / gridSize) * gridSize;
	}
}
