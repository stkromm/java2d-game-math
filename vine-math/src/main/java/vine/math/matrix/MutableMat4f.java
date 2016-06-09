package vine.math.matrix;

public class MutableMat4f extends Mat4f
{

	/**
	 * Sets the translation of this transformation matrix.
	 */
	public void setTranslation(final float x, final float y, final float z)
	{
		elements[0 + 3 * 4] = x;
		elements[1 + 3 * 4] = y;
		elements[2 + 3 * 4] = z;
	}

	public void setTransform(final Mat3f matrix)
	{
		setTranslation(matrix.getA13(), matrix.getA23(), 0);

	}

}
