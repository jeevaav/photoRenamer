package photo_renamer;

import java.awt.Dimension;

import javax.swing.JButton;

/**
 * The RemoveButton.
 * 
 * @author Tiansheng
 * @author Jeevaa
 *
 */
public class RemoveButton extends JButton {

	/**
	 * Create a new RemoveButton.
	 * 
	 */
	// prevent default constructors
	private RemoveButton() {
	};

	/**
	 * Create the RemoveButton with the label.
	 * 
	 * @param label
	 *            the label for this RemoveButton
	 */
	RemoveButton(String label) {
		super(label);

	}

}
