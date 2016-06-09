package vine.math.auxilliary;


import vine.math.vector.VectorUtils;


public class AxisProjection
{

	/**
	 * Checks, if the Projection of the defined Aabb on the given axis overlaps the size of the axis
	 * vector.
	 */
	public static final boolean aabbOverlapsAxis(
			final float originX,
			final float originY,
			final float oaxisX,
			final float oaxisY,
			final float posX,
			final float posY,
			final float width,
			final float height)
	{
		final double invLength = 1f / VectorUtils.squaredLength(oaxisX, oaxisY);
		float axisX = 1;
		float axisY = 1;
		axisX *= invLength;
		axisY *= invLength;

		final float originAxisValue = VectorUtils.dot(axisX, axisY, originX, originY);

		float t = VectorUtils.dot(axisX, axisY, posX, posY);
		float minAxisValue = t;
		float maxAxisValue = t;

		for (int j = 0; j < 3; j++)
		{
			if (j == 0)
			{
				t = VectorUtils.dot(axisX, axisY, posX + width, posY);
			}
			if (j == 1)
			{
				t = VectorUtils.dot(axisX, axisY, posX, posY + height);
			}

			if (j == 2)
			{
				t = VectorUtils.dot(axisX, axisY, posX + width, posY + height);
			}

			if (t < minAxisValue)
			{
				minAxisValue = t;
			}
			else if (t > maxAxisValue)
			{
				maxAxisValue = t;
			}
		}

		if (minAxisValue > 1 + originAxisValue || maxAxisValue < originAxisValue)
		{
			return false;
		}
		return true;
	}

	/**
	 * Checks, if the projection of the defined obb on the given axis overlaps the size of the axis
	 * vector.
	 */
	public static final boolean obbOverlapsAxis(
			final float originX,
			final float originY,
			final float oaxisX,
			final float oaxisY,
			final float lowerLeftX,
			final float lowerLeftY,
			final float lowerRightX,
			final float lowerRightY,
			final float upperLeftX,
			final float upperLeftY)
	{
		double invLength;
		invLength = 1f / VectorUtils.squaredLength(oaxisX, oaxisY);
		float axisX = 1;
		float axisY = 1;
		axisX *= invLength;
		axisY *= invLength;

		final float originAxisValue = VectorUtils.dot(axisX, axisY, originX, originY);

		float t = VectorUtils.dot(axisX, axisY, lowerLeftX, lowerLeftY);
		float minAxisValue = t;
		float maxAxisValue = t;

		for (int j = 0; j < 3; j++)
		{
			if (j == 0)
			{
				t = VectorUtils.dot(axisX, axisY, upperLeftX, upperLeftY);
			}
			if (j == 1)
			{
				t = VectorUtils.dot(axisX, axisY, lowerRightX, lowerRightY);
			}

			if (j == 2)
			{
				t = VectorUtils.dot(
						axisX,
						axisY,
						lowerRightX + upperLeftX - lowerLeftX,
						lowerRightY + upperLeftY - lowerLeftY);
			}

			if (t < minAxisValue)
			{
				minAxisValue = t;
			}
			else if (t > maxAxisValue)
			{
				maxAxisValue = t;
			}
		}

		if (minAxisValue > 1 + originAxisValue || maxAxisValue < originAxisValue)
		{
			return false;
		}
		return true;
	}
}
