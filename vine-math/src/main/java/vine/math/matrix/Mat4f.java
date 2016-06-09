package vine.math.matrix;


import vine.math.GMath;
import vine.math.vector.Vec3f;


public class Mat4f
{

	private static final int	SIZE	= 4 * 4;
	protected final float[]		elements;


	public Mat4f()
	{
		elements = new float[SIZE];
	}

	/**
	 * Creates copy of the given matrix.
	 */
	public Mat4f(final Mat4f matrix)
	{
		if (matrix == null)
		{
			throw new IllegalArgumentException("Tried to copy construct a Mat4f from null");
		}
		elements = matrix.elements.clone();
	}

	public float[] getElements()
	{
		return elements;
	}

	/**
	 * Creates a identity Matrix.
	 *
	 * @return The identity Matrix4f
	 */
	public static Mat4f identity()
	{
		final Mat4f result = new Mat4f();
		for (int i = 0; i < SIZE; i++)
		{
			result.elements[i] = 0.0f;
		}
		result.elements[0 + 0 * 4] = 1.0f;
		result.elements[1 + 1 * 4] = 1.0f;
		result.elements[2 + 2 * 4] = 1.0f;
		result.elements[3 + 3 * 4] = 1.0f;

		return result;
	}

	/**
	 * Creates a orthographic projection matrix.
	 */
	public static Mat4f orthographic(
			final float left,
			final float right,
			final float bottom,
			final float top,
			final float near,
			final float far)
	{
		final Mat4f result = identity();

		result.elements[0 + 0 * 4] = 2.0f / (right - left);

		result.elements[1 + 1 * 4] = 2.0f / (top - bottom);

		result.elements[2 + 2 * 4] = 2.0f / (near - far);

		result.elements[0 + 3 * 4] = (left + right) / (left - right);
		result.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
		result.elements[2 + 3 * 4] = (far + near) / (far - near);

		return result;
	}

	/**
	 * Adds the given vector as translation to this transformation matrix.
	 */
	public static Mat4f translate(final Vec3f vector)
	{
		final Mat4f result = identity();
		result.elements[0 + 3 * 4] = vector.getX();
		result.elements[1 + 3 * 4] = vector.getY();
		result.elements[2 + 3 * 4] = vector.getZ();
		return result;
	}


	/**
	 * Rotates the rotation of this transformation matrix by the given angle.
	 */
	public static Mat4f rotate(final float angle)
	{
		final Mat4f result = identity();
		final float r = GMath.toRadians(angle);
		final float cos = GMath.cos(r);
		final float sin = GMath.sin(r);

		result.elements[0 + 0 * 4] = cos;
		result.elements[1 + 0 * 4] = sin;
		result.elements[0 + 1 * 4] = -sin;
		result.elements[1 + 1 * 4] = cos;

		return result;
	}

	/**
	 * Calculates the matrix product of this * matrix and returns it in a new created matrix.
	 */
	public Mat4f multiply(final Mat4f matrix)
	{
		final Mat4f result = new Mat4f();
		for (int y = 0; y < 4; y++)
		{
			for (int x = 0; x < 4; x++)
			{
				result.elements[x + y * 4] = elements[x] * matrix.elements[y * 4];
				result.elements[x + y * 4] += elements[x + 4] * matrix.elements[1 + y * 4];
				result.elements[x + y * 4] += elements[x + 2 * 4] * matrix.elements[2 + y * 4];
				result.elements[x + y * 4] += elements[x + 3 * 4] * matrix.elements[3 + y * 4];
			}
		}
		return result;
	}
}