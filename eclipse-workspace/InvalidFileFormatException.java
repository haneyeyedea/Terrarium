import java.io.IOException;

/**
 * Extends IOException exception, and able to create specific error message through string parameter
 * 
 * @param message creates personalized error message
 * @author melodee
 *
 */
@SuppressWarnings("serial")
public class InvalidFileFormatException extends IOException {

		public InvalidFileFormatException( String message ) {
			super( message );
		}
	}

