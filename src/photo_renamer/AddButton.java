package photo_renamer;

import javax.swing.JButton;

/**
 * The AddButton.
 * 
 * @author Tiansheng
 * @author Jeevaa
 *
 */
public class AddButton extends JButton {

	/**
	 * Create a new AddButton.
	 * 
	 */
	// prevent default constructors
	private AddButton() {
	};

	/**
	 * Create the AddButton with the label.
	 * 
	 * @param label
	 *            the label for this AddButton
	 */
	AddButton(String label) {
		super(label);
	}

}
