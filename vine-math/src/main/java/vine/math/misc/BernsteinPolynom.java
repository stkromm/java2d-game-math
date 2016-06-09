package vine.math.misc;


import vine.math.GMath;


public final class BernsteinPolynom
{

	final double	coeffi;
	final int		grad;
	final int		i;


	/**
	 * Creates a new BernsteinPolynome with the given grad and index.
	 *
	 * @param grad
	 *            The grad of the Bernstein Polynom
	 * @param i
	 *            The index of the Bernstein Polynom of the given grad
	 */
	public BernsteinPolynom(final int grad, final int i)
	{
		this.grad = grad;
		this.i = i;
		coeffi = GMath.binominalCoefficient(grad, i);
	}

	/**
	 * Evaluates the Bernstein Polynome function.
	 *
	 * @param u
	 *            The function parameter to evaluate the Bernstein Polynome.
	 * @return The function value of the Bernstein Polynome at {@code u}
	 */
	public float evaluate(final float u)
	{
		float uPowI = u;
		for (int a = grad - i; a > 0; a--)
		{
			uPowI *= u;
		}
		final float oneMinusU = 1 - u;
		float uPowNi = oneMinusU;
		for (int a = i; a > 0; a--)
		{
			uPowNi *= oneMinusU;
		}

		return (float) (coeffi * uPowI * uPowNi);
	}
}
