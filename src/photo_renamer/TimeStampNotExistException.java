package photo_renamer;
/**
 * TimeStampNotExistException occurs when there is no such TimeStamp occurred .
 * 
 * @author Jeevaa
 * @author TianSheng
 */
public class TimeStampNotExistException extends Exception {

	/**
	 * Create a new TimeStampNotExistException with a message to be passed on
	 * when the exception is thrown.
	 * 
	 * @param message
	 *            error message
	 */
	public TimeStampNotExistException(String message) {
		super(message);
	}

}
