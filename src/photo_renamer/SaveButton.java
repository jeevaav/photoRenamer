package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

/**
 * The SaveButton.
 * 
 * @author Tiansheng
 * @author Jeevaa
 *
 */
public class SaveButton extends JButton implements ActionListener {

	/**
	 * Create a new SaveButton.
	 * 
	 */
	// prevent default constructors
	private SaveButton() {
	};

	/**
	 * Create the SaveButton with the label.
	 * 
	 * @param label
	 *            the label for this ExitButton
	 */
	SaveButton(String label) {
		super(label); 
		this.addActionListener(this);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			ImageManager.getInstance().saveToFile();
		} catch (IOException e1) {
		}
	}

}
