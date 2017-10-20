package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

/**
 * The ExitButton.
 * 
 * @author Tiansheng
 * @author Jeevaa
 *
 */
public class ExitButton extends JButton implements ActionListener {

	/**
	 * Create a new ExitButton.
	 * 
	 */
	// prevent default constructors
	private ExitButton() {
	};

	/**
	 * Create the ExitButton with the label.
	 * 
	 * @param label
	 *            the label for this ExitButton
	 */
	ExitButton(String label) {
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
		System.exit(0);
	}

}
