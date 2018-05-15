import java.util.Random;

/**
 * Die.java
 *
 * Represents one die (singular of dice) with faces showing values
 * between 1 and 6.
 *
 * @author Java Foundations
 * @author CS121 Instructors (modified a few things from book)
 */
public class Die
{
	private int numSides;  // face value
	private int faceValue;  // current value showing on the die
	private Random rand;

	/**
	 * Constructor: Sets the initial face value of this die.
	 */
	public Die()
	{
		this.numSides = 6;
		rand = new Random();
		faceValue = 1;
	}
	
	public Die(int numSides) 
	{
		this.numSides = numSides;
		rand = new Random();
		faceValue = 1;
		
	}

	/**
	 * Computes a new face value for this die.
	 */
	public void roll()
	{
		faceValue = rand.nextInt(numSides) + 1;
	}

	/**
	 * Face value mutator. The face value is not modified if the
	 * specified value is not valid.
	 *
	 * @param value The new face value. Must be between 1 and max face
	 * value.
	 */
	public void setFaceValue (int value)
	{
		if (value > 0 && value <= numSides) {
			faceValue = value;
		}
	}

	/**
	 * Face value accessor.
	 * @return The current face value.
	 */
	public int getFaceValue()
	{
		return faceValue;
	}

	/**
	 * Returns a string representation of this die.
	 */
	public String toString()
	{
		String result = "Die [faceValue = " + faceValue + "]";
		return result;
	}
}
