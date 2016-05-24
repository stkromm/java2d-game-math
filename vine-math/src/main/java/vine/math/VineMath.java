package vine.math;

import vine.math.auxilliary.FactorialLookup;
import vine.math.auxilliary.Icecore;
import vine.math.auxilliary.LookupSinCos;
import vine.math.auxilliary.Xorshift128Plus;

public final class VineMath
{
    private static final Xorshift128Plus RANDOM            = new Xorshift128Plus();
    private static final int             ONE_BIT           = 1;
    public static final float            FLOAT_ROUND_SHIFT = 0.5f;
    public static final int              FLOAT_ROUND_INT   = VineMath.pow(2, 14);
    public static final float            PIF               = 3.14159265358979323846f;
    public static final double           PI                = 3.14159265358979323846;
    public static final float            TWO_PIF           = PIF * 2;
    public static final float            HALF_PIF          = PIF * 0.5f;
    /**
     * Standard floating point error of 1e-6.
     */
    public static final float            EPSILON           = 0.000001f;

    private VineMath()
    {
        // Utility class
    }

    /**
     * Unwinds the given radians and returns a new radian value in the interval
     * {@code [-2Pi,2Pi]}
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
     * Unwinds the given degrees and returns new degree value in the interval
     * {@code [-180,180]}
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
    public static float pow(final double a, final double b)
    {
        return (float) StrictMath.pow(a, b);
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
        } else
        {
            return exp(power * log(value));
        }
    }

    /**
     *
     * @param value
     * @return
     */
    public static float exp(final double value)
    {
        return (float) Math.exp(value);
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
    public static float log(final double value)
    {
        return (float) Math.log(value);
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
        return x >= y ? x : y;
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
        return x >= y ? x : y;
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
    public static int min(final int x, final int y)
    {
        return x <= y ? x : y;
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
     * Clamps a negative value to zero
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
        } else if (value <= min)
        {
            return max - (min - value);
        } else
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
        } else if (value <= min)
        {
            return max - (min - value);
        } else
        {
            return value;
        }
    }

    /**
     * Checks if the given {@code value} lies in the interval [
     * {@code min,@code max].
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

    /**
     * Returns an random integer in the interval [0,max)
     *
     * @param max
     *            The supremum of the interval of possible returned values.
     * @return The random value in the interval
     */
    public static int randomInteger(final int max)
    {
        return VineMath.round(randomFloat(max));
    }

    /**
     * Calculates a random float in the given interval [{@code min},{@code max}
     * ). Result may be undefined if {@code min > max}.
     *
     * @param min
     * @param max
     * @return
     */
    public static float randomFloat(final float min, final float max)
    {
        assert min <= max : "Interval for random float is in wrong order. min > max";
        return RANDOM.nextFloat() * (max - min) + min;
    }

    /**
     * Returns an random {@code float} in the interval {@code [0,max) }
     *
     * @param max
     *            The supremum of the interval of possible returned values.
     * @return The random value in the interval
     */
    public static float randomFloat(final float max)
    {
        return RANDOM.nextFloat() * max;
    }

    /**
     * Calculates a new random {@code float} in the interval {@code [0,1)}
     *
     * @return A new random {@code float} in the interval {@code [0,1)}
     */
    public static float random()
    {
        return RANDOM.nextFloat();
    }

    /**
     * Linear interpolates between the values {@code a} and {@code b} with the
     * given lerp value {@code alpha}, which has to be in the interval [0,1].
     *
     * @param a
     *            The 1st value
     * @param b
     *            The 2nd value
     * @param alpha
     *            Value used to linear interpolate between {@code a} and
     *            {@code b}. A value of 0 will result in returning the value
     *            {@code a}, a value of 1 will result in returning the value
     *            {@code b}.
     * @return The linear interpolation of {@code a} and {@code b} evaluated
     *         with {@code alpha}
     */
    public static float lerp(final float a, final float b, final float alpha)
    {
        return a * (1 - alpha) + b * alpha;
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

    /**
     * Checks, if the given value is nearly equal to zero.
     *
     * @param value
     *            The value, that is compared with zero.
     * @return True, if the given value is near zero with a maximum difference
     *         of {@link #EPSILON}.
     * @see {@link #isNearlyZero(float, float)} with {@link #EPSILON}
     */
    public static boolean isNearlyZero(final float value)
    {
        return isNearlyZero(value, EPSILON);
    }

    /**
     * Checks, if the given value is nearly equal to zero.
     *
     * @param value
     *            The value, that is compared with zero.
     * @param epsilon
     *            The maximum difference of the value can have and still count
     *            as zero.
     * @return True, if the given value is near zero with a maximum difference
     *         of {@link epsilon}.
     */
    public static boolean isNearlyZero(final float value, final float epsilon)
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
    public static boolean isNearlyEqual(final float val1, final float val2)
    {
        return isNearlyEqual(val1, val2, EPSILON);
    }

    /**
     * Checks if the given {@code float} numbers are nearly equal.
     *
     * @param val1
     *            The 1st value to compare
     * @param val2
     *            The 2nd value to compare
     * @param epsilon
     *            The epsilon that limits the tolerated difference of both
     *            numbers
     * @return True, if the deviation of both numbers is within [0,
     *         {@code epsilon}].
     */
    public static boolean isNearlyEqual(final float val1, final float val2, final float epsilon)
    {
        return val1 > val2 ? val1 - val2 <= epsilon : val2 - val1 <= epsilon;
    }

    /**
     * <p>
     * From libgdx MathUtils.
     * </p>
     * Returns the closest integer to the specified float. This method will only
     * properly round floats from -(2^14) to (Float.MAX_VALUE - 2^14).
     *
     * @param value
     *            The value, that should be rounded
     * @return A value, that is the rounded input value
     */
    static public int round(final float value)
    {
        return (int) (value + FLOAT_ROUND_INT + FLOAT_ROUND_SHIFT) - FLOAT_ROUND_INT;
    }

    /**
     * Rounds a positive {@code float} to the nearest {@code integer} value. The
     * result in not defined for negative values.
     *
     * @param value
     *            The value, that should be rounded
     * @return A value, that is the rounded input value
     */
    public static int roundPositive(final float value)
    {
        assert value > -FLOAT_ROUND_SHIFT - EPSILON : "Tried to round positive negative value " + value;
        return (int) (value + FLOAT_ROUND_SHIFT);
    }
}
