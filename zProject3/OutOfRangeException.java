
/**
 * Extends the base exception, and able to create specific error message through string parameter
 * 
 * @param message creates personalized error message
 * @author melodee
 *
 */
@SuppressWarnings("serial")
public class OutOfRangeException extends Exception{
	  public OutOfRangeException (String message){
	      super (message);
	   }
	}
