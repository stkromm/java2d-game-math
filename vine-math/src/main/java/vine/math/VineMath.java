package vine.math;

import vine.math.auxilliary.FactorialLookup;
import vine.math.auxilliary.Icecore;
import vine.math.auxilliary.LookupSinCos;
import vine.math.auxilliary.Xorshift128Plus;

public final class VineMath
{
    private static final Xorshift128Plus RANDOM   = new Xorshift128Plus();
    private static final int             ONE_BIT  = 1;

    public static final float            PIF      = 3.14159265358979323846f;
    public static final double           PI       = 3.14159265358979323846;
    public static final float            TWO_PIF  = PIF * 2;
    public static final float            HALF_PIF = 3.14159265358979323846f * 0.5f;
    public static final float            EPSILON  = 0.0001f;

    private VineMath()
    {
        // Utility class
    }

    /**
     * Calculates the binomial coefficient n over k. Only values between 0 and
     * 20 are permitted.
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
            return factorial(n) * factorial(n - k) / (double) factorial(k);
        } else
        {
            return factorial(n) * factorial(n - (n - k)) / (double) factorial(n - k);
        }
    }

    /**
     *
     * @param n
     * @return
     */
    public static long factorial(final int n)
    {
        if (n < 0 || n > 20)
        {
            throw new IllegalArgumentException(n + " is no valid value to calculate a factorial.");
        }
        return FactorialLookup.factorial(n);
    }

    /**
     *
     * @param value
     * @return
     */
    public static double sqrt(final double value)
    {
        return StrictMath.sqrt(value);
    }

    /**
     * Calculates the square root of the given value. This method is about two
     * times faster than the regular sqrt method, but it is only a very rough
     * approximation. The calculated value has a error of 0.5 - 1.5 % but a
     * error up to 10% is possible.
     *
     * @param x
     * @return
     */
    public static float fastSqrt(final float x)
    {
        return Float.intBitsToFloat(532483686 + (Float.floatToRawIntBits(x) >> 1));
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static double pow(final double a, final double b)
    {
        return StrictMath.pow(a, b);
    }

    /**
     *
     * @param a
     * @param b
     * @return
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
     *
     * @param value
     * @return
     */
    public static double exp(final double value)
    {
        return Math.exp(value);
    }

    /**
     * Calculates exp faster than the standard version at the expense of
     * accuracy.
     *
     * @param val
     *            The argument for exp
     * @return The function value of exp for val
     */
    public static float fastExp(final float val)
    {
        final long tmp = (long) (1512775 * val + 1072632447);
        return Float.intBitsToFloat((int) (tmp << 32));
    }

    /**
     *
     * @param value
     * @return
     */
    public static double log(final double value)
    {
        return Math.log(value);
    }

    /**
     *
     * @param x
     * @return
     */
    public static float fastLog(final float x)
    {
        return 6 * (x - 1) / (x + 1 + 4 * (float) VineMath.sqrt(x));
    }

    /**
     *
     * @param value
     * @return
     */
    public static int abs(final int value)
    {
        return Math.abs(value);
    }

    /**
     *
     * @param value
     * @return
     */
    public static float abs(final float value)
    {
        return Math.abs(value);
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public static float max(final float x, final float y)
    {
        return x >= y ? x : y;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public static float min(final float x, final float y)
    {
        return x <= y ? x : y;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public static int max(final int x, final int y)
    {
        return x >= y ? x : y;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public static int min(final int x, final int y)
    {
        return x <= y ? x : y;
    }

    /**
     *
     * @param value
     * @return
     */
    public static boolean isEven(final int value)
    {
        return value % 2 == 0;
    }

    /**
     *
     * @param value
     * @return
     */
    public static boolean isOdd(final int value)
    {
        return value % 2 == 1;
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
        // LibGdx impl
        return value != 0 && (value & value - 1) == 0;
    }

    /**
     * Clamps the given value to the interval [min,max]
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
     *
     * @param value
     * @param min
     * @param max
     * @return
     */
    public static int clamp(final int value, final int min, final int max)
    {
        if (value <= min)
        {
            return min;
        } else if (value >= max)
        {
            return max;
        } else
        {
            return value;
        }
    }

    /**
     * Clamps a negative value to zero
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
     *
     * @param value
     * @return
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
     *
     * @param value
     * @param min
     * @param max
     * @return
     */
    public static int repeat(final int value, final int min, final int max)
    {
        if (value > max)
        {
            return min + value % (max - min);
        } else if (value <= min)
        {
            return max - (min - value);
        } else
        {
            return value;
        }
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
        return StrictMath.round(randomFloat(max));
    }

    /**
     * Returns an random float in the interval [0,max)
     *
     * @param max
     *            The supremum of the interval of possible returned values.
     * @return The random value in the interval
     */
    public static float randomFloat(final int max)
    {
        return RANDOM.nextFloat() * max;
    }

    // Trigonometric functions
    /**
     *
     * @param rad
     * @return
     */
    public static float sin(final float rad)
    {
        return LookupSinCos.sin(rad);
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
     * Returns the angle theta from the conversion of rectangular coordinates
     * (x, y) to polar coordinates (r, theta). This method computes the phase
     * theta by computing an arc tangent of y/x in the range of -pi to pi.
     *
     * @param y
     *            The y coordinate
     * @param x
     *            The x coordinate
     * @return The polar coordinate (positive angle is counter-clockwise
     *         rotation).
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

    private static double TWO_POW_52 = Math.pow(2, 52);

    /**
     * https://github.com/jeffhain/jafama/blob/master/src/main/java/net/jafama/
     * FastMath.java 1e-13ish accuracy or better on whole double range.
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
        } else if (power == 1.0)
        {
            return value;
        }
        if (value <= 0.0)
        {
            // powerInfo: 0 if not integer, 1 if even integer, -1 if odd integer
            int powerInfo;
            if (Math.abs(power) >= TWO_POW_52 * 2)
            {
                // The binary digit just before comma is outside mantissa,
                // thus it is always 0: power is an even integer.
                powerInfo = 1;
            } else
            {
                // If power's magnitude permits, we cast into int instead of
                // into long,
                // as it is faster.
                if (Math.abs(power) <= Integer.MAX_VALUE)
                {
                    final int powerAsInt = (int) power;
                    if (power == powerAsInt)
                    {
                        powerInfo = (powerAsInt & 1) == 0 ? 1 : -1;
                    } else
                    { // power is not an integer (and not NaN, due to test
                      // against Integer.MAX_VALUE)
                        powerInfo = 0;
                    }
                } else
                {
                    final long powerAsLong = (long) power;
                    if (power == powerAsLong)
                    {
                        powerInfo = (powerAsLong & 1) == 0 ? 1 : -1;
                    } else
                    { // power is not an integer, or is NaN
                        if (power != power)
                        {
                            return Float.NaN;
                        }
                        powerInfo = 0;
                    }
                }
            }

            if (value == 0.0)
            {
                if (power < 0.0)
                {
                    return powerInfo < 0 ? 1 / value : Float.POSITIVE_INFINITY;
                } else
                { // power > 0.0 (0 and NaN cases already treated)
                    return powerInfo < 0 ? value : 0.0f;
                }
            } else
            { // value < 0.0
                if (value == Double.NEGATIVE_INFINITY)
                {
                    if (powerInfo < 0)
                    { // power odd integer
                        return power < 0.0 ? -0.0f : Float.NEGATIVE_INFINITY;
                    } else
                    { // power even integer, or not an integer
                        return power < 0.0 ? 0.0f : Float.POSITIVE_INFINITY;
                    }
                } else
                {
                    return powerInfo == 0 ? Float.NaN : powerInfo * (float) VineMath.exp(power * VineMath.log(-value));
                }
            }
        } else
        { // value > 0.0, or value is NaN
            return (float) exp(power * log(value));
        }
    }

    public static boolean isZero(final float value)
    {
        return -value < VineMath.EPSILON && value < VineMath.EPSILON;
    }

    public static boolean equalByEps(final float val1, final float val2)
    {
        return VineMath.abs(val1 - val2) < EPSILON;
    }
}
