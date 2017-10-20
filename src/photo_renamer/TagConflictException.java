package photo_renamer;
/**
 * TagConflictException occurs when there is a conflict when adding or removing
 * tags.
 * 
 * @author Jeevaa
 * @author TianSheng
 *
 */
public class TagConflictException extends Exception {

	/**
	 * Create a new TagConflictException with a message to be passed on when the
	 * exception is thrown.
	 * 
	 * @param message
	 *            error message
	 */
	public TagConflictException(String message) {
		super(message);
	}

}
