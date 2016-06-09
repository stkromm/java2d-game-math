package vine.math;

public final class Interpolation
{

	/**
	 * Linear interpolates between the values {@code a} and {@code b} with the given lerp value
	 * {@code alpha}, which must be in the interval [0,1].
	 *
	 * @param a
	 *            The 1st value
	 * @param b
	 *            The 2nd value
	 * @param alpha
	 *            Value used to interpolate between {@code a} and {@code b}. A value of 0 will
	 *            result in returning the value {@code a}, a value of 1 will result in returning the
	 *            value {@code b}.
	 * @return The linear interpolation of {@code a} and {@code b} evaluated with {@code alpha}
	 */
	public static float lerp(final float a, final float b, final float alpha)
	{
		return a * (1 - alpha) + b * alpha;
	}

	/**
	 * Interpolates between a and b by alpha with cubic function defined by a, b and t1, t2.
	 */
	public static float cubic(
			final float a,
			final float b,
			final float t1,
			final float t2,
			final float alpha)
	{
		final float sqrdAlpha = alpha * alpha;
		final float a0 = t2 - t1 - a + b;
		final float a1 = a - b - a0;
		final float a2 = t1 - a;
		final float a3 = b;

		return a0 * alpha * sqrdAlpha + a1 * sqrdAlpha + a2 * alpha + a3;
	}

	/**
	 * http://paulbourke.net/miscellaneous/interpolation/.
	 */
	public static float hermite(
			final float a,
			final float b,
			final float t1,
			final float t2,
			final float alpha,
			final float tension,
			final float bias)
	{
		final float alphaPow2 = alpha * alpha;
		final float alphaPow3 = alphaPow2 * alpha;
		float m0 = (b - a) * (1 + bias) * (1 - tension) / 2;
		m0 += (t1 - b) * (1 - bias) * (1 - tension) / 2;
		float m1 = (t1 - b) * (1 + bias) * (1 - tension) / 2;
		m1 += (t2 - t1) * (1 - bias) * (1 - tension) / 2;
		final float a0 = 2 * alphaPow3 - 3 * alphaPow2 + 1;
		final float a1 = alphaPow3 - 2 * alphaPow2 + alpha;
		final float a2 = alphaPow3 - alphaPow2;
		final float a3 = -2 * alphaPow3 + 3 * alphaPow2;
		return a0 * b + a1 * m0 + a2 * m1 + a3 * t1;
	}

	/**
	 * https://en.wikipedia.org/wiki/Smoothstep
	 * <p>
	 * Interpolates between the values {@code a} and {@code b} with a circular out function and a
	 * circular in function merged at alpha = 0.5 with the given lerp value {@code alpha}, which has
	 * to be in the interval [0,1].
	 * </p>
	 *
	 * @param a
	 *            The 1st value
	 * @param b
	 *            The 2nd value
	 * @param alpha
	 *            Value used to interpolate between {@code a} and {@code b}.
	 * @return The circular inout interpolation of {@code a} and {@code b} evaluated with
	 *         {@code alpha}
	 */
	public static float smoothStep(final float a, final float b, final float alpha)
	{
		final float newAlpha = GMath.clamp((alpha - a) / (b / a), 0, 1);
		return newAlpha * newAlpha * (3 - 2 * newAlpha);
	}

	/**
	 * Interpolates between the values {@code a} and {@code b} with a circular out function and a
	 * circular in function merged at alpha = 0.5 with the given lerp value {@code alpha}, which has
	 * to be in the interval [0,1].
	 *
	 * @param a
	 *            The 1st value
	 * @param b
	 *            The 2nd value
	 * @param alpha
	 *            Value used to interpolate between {@code a} and {@code b}.
	 * @return The circular inout interpolation of {@code a} and {@code b} evaluated with
	 *         {@code alpha}
	 */
	public static float easeOut(final float a, final float b, final float exp, final float alpha)
	{
		final float newAlpha = 1.f - GMath.fastPow(1.f - alpha, exp);
		return lerp(a, b, newAlpha);
	}

	/**
	 * Interpolates between the values {@code a} and {@code b} with a circular out function and a
	 * circular in function merged at alpha = 0.5 with the given lerp value {@code alpha}, which has
	 * to be in the interval [0,1].
	 *
	 * @param a
	 *            The 1st value
	 * @param b
	 *            The 2nd value
	 * @param alpha
	 *            Value used to interpolate between {@code a} and {@code b}.
	 * @return The circular inout interpolation of {@code a} and {@code b} evaluated with
	 *         {@code alpha}
	 */
	public static float easeIn(final float a, final float b, final float exp, final float alpha)
	{
		final float newAlpha = GMath.fastPow(alpha, exp);
		return lerp(a, b, newAlpha);
	}

	/**
	 * Interpolates between the values {@code a} and {@code b} with a circular out function and a
	 * circular in function merged at alpha = 0.5 with the given lerp value {@code alpha}, which has
	 * to be in the interval [0,1].
	 *
	 * @param a
	 *            The 1st value
	 * @param b
	 *            The 2nd value
	 * @param alpha
	 *            Value used to interpolate between {@code a} and {@code b}.
	 * @return The circular inout interpolation of {@code a} and {@code b} evaluated with
	 *         {@code alpha}
	 */
	public static float easeInOut(final float a, final float b, final float exp, final float alpha)
	{
		final float newAlpha = alpha < 0.5f ? 0.5f * GMath.fastPow(2.f * alpha, exp)
				: 1.f - 0.5f * GMath.fastPow(2.f * (1.f - alpha), exp);
		return lerp(a, b, newAlpha);
	}

	/**
	 * Interpolates between the values {@code a} and {@code b} with a circular out function and a
	 * circular in function merged at alpha = 0.5 with the given lerp value {@code alpha}, which has
	 * to be in the interval [0,1].
	 *
	 * @param a
	 *            The 1st value
	 * @param b
	 *            The 2nd value
	 * @param alpha
	 *            Value used to interpolate between {@code a} and {@code b}.
	 * @return The circular inout interpolation of {@code a} and {@code b} evaluated with
	 *         {@code alpha}
	 */
	public static float expoInOut(final float a, final float b, final float exp, final float alpha)
	{
		return alpha < 0.5f ? expoIn(a, b, alpha * 2.f) * 0.5f
				: expoOut(a, b, alpha * 2.f - 1.f) * 0.5f + 0.5f;
	}

	/**
	 * Interpolates between the values {@code a} and {@code b} with a circular out function and a
	 * circular in function merged at alpha = 0.5 with the given lerp value {@code alpha}, which has
	 * to be in the interval [0,1].
	 *
	 * @param a
	 *            The 1st value
	 * @param b
	 *            The 2nd value
	 * @param alpha
	 *            Value used to interpolate between {@code a} and {@code b}.
	 * @return The circular inout interpolation of {@code a} and {@code b} evaluated with
	 *         {@code alpha}
	 */
	public static float expoOut(final float a, final float b, final float alpha)
	{
		final float newAlpha = alpha == 1.f ? 1.f : -GMath.fastPow(2.f, -10.f * alpha) + 1.f;
		return lerp(a, b, newAlpha);
	}

	/**
	 * Interpolates between the values {@code a} and {@code b} with a circular out function and a
	 * circular in function merged at alpha = 0.5 with the given lerp value {@code alpha}, which has
	 * to be in the interval [0,1].
	 *
	 * @param a
	 *            The 1st value
	 * @param b
	 *            The 2nd value
	 * @param alpha
	 *            Value used to interpolate between {@code a} and {@code b}.
	 * @return The circular inout interpolation of {@code a} and {@code b} evaluated with
	 *         {@code alpha}
	 */
	public static float expoIn(final float a, final float b, final float alpha)
	{
		final float newAlpha = alpha == 0.f ? 0.f : GMath.fastPow(2.f, 10.f * (alpha - 1.f));
		return lerp(a, b, newAlpha);
	}

	/**
	 * Interpolates between the values {@code a} and {@code b} with a circular out function and a
	 * circular in function merged at alpha = 0.5 with the given lerp value {@code alpha}, which has
	 * to be in the interval [0,1].
	 *
	 * @param a
	 *            The 1st value
	 * @param b
	 *            The 2nd value
	 * @param alpha
	 *            Value used to interpolate between {@code a} and {@code b}.
	 * @return The circular inout interpolation of {@code a} and {@code b} evaluated with
	 *         {@code alpha}
	 */
	public static float circularOut(final float a, final float b, final float alpha)
	{
		final float oneMinusAlpha = alpha - 1.f;
		final double newAlpha = GMath.fastSqrt(1.f - oneMinusAlpha * oneMinusAlpha);
		return lerp(a, b, (float) newAlpha);
	}

	/**
	 * Interpolates between the values {@code a} and {@code b} with a circular out function and a
	 * circular in function merged at alpha = 0.5 with the given lerp value {@code alpha}, which has
	 * to be in the interval [0,1].
	 *
	 * @param a
	 *            The 1st value
	 * @param b
	 *            The 2nd value
	 * @param alpha
	 *            Value used to interpolate between {@code a} and {@code b}.
	 * @return The circular inout interpolation of {@code a} and {@code b} evaluated with
	 *         {@code alpha}
	 */
	public static float circularIn(final float a, final float b, final float alpha)
	{
		final double newAlpha = -1.f * (GMath.fastSqrt(1.f - alpha * alpha) - 1.f);
		return lerp(a, b, (float) newAlpha);
	}

	/**
	 * Interpolates between the values {@code a} and {@code b} with a circular out function and a
	 * circular in function merged at alpha = 0.5 with the given lerp value {@code alpha}, which has
	 * to be in the interval [0,1].
	 *
	 * @param a
	 *            The 1st value
	 * @param b
	 *            The 2nd value
	 * @param alpha
	 *            Value used to interpolate between {@code a} and {@code b}.
	 * @return The circular inout interpolation of {@code a} and {@code b} evaluated with
	 *         {@code alpha}
	 */
	public static float circularInOut(
			final float a,
			final float b,
			final float exp,
			final float alpha)
	{
		return alpha < 0.5f ? circularIn(a, b, alpha * 2.f) * 0.5f
				: circularOut(a, b, alpha * 2.f - 1.f) * 0.5f + 0.5f;
	}

	/**
	 * Interpolates between the values {@code a} and {@code b} with a sin out function and a sin in
	 * function merged at alpha = 0.5 with the given lerp value {@code alpha}, which has to be in
	 * the interval [0,1].
	 *
	 * @param a
	 *            The 1st value
	 * @param b
	 *            The 2nd value
	 * @param alpha
	 *            Value used to interpolate between {@code a} and {@code b}.
	 * @return The sin out interpolation of {@code a} and {@code b} evaluated with {@code alpha}
	 */
	public static float sinInOut(final float a, final float b, final float exp, final float alpha)
	{
		return alpha < 0.5f ? sinIn(a, b, alpha * 2.f) * 0.5f
				: sinOut(a, b, alpha * 2.f - 1.f) * 0.5f + 0.5f;
	}

	/**
	 * Interpolates between the values {@code a} and {@code b} with a sin in function with the given
	 * lerp value {@code alpha}, which has to be in the interval [0,1].
	 *
	 * @param a
	 *            The 1st value
	 * @param b
	 *            The 2nd value
	 * @param alpha
	 *            Value used to interpolate between {@code a} and {@code b}.
	 * @return The sin in interpolation of {@code a} and {@code b} evaluated with {@code alpha}
	 */
	public static float sinIn(final float a, final float b, final float alpha)
	{
		return lerp(a, b, 1.f - GMath.cos(alpha * GMath.HALF_PIF));
	}

	/**
	 * Interpolates between the values {@code a} and {@code b} with a sin out function with the
	 * given lerp value {@code alpha}, which has to be in the interval [0,1].
	 *
	 * @param a
	 *            The 1st value
	 * @param b
	 *            The 2nd value
	 * @param alpha
	 *            Value used to interpolate between {@code a} and {@code b}.
	 * @return The sin out interpolation of {@code a} and {@code b} evaluated with {@code alpha}
	 */
	public static float sinOut(final float a, final float b, final float alpha)
	{
		return lerp(a, b, GMath.sin(alpha * GMath.HALF_PIF));
	}

}
