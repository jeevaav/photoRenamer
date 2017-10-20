package photo_renamer;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * The ImagePanel contains the Photo and the Control.
 * 
 * @author Tiansheng
 * @author Jeevaa
 */
public class ImagePanel extends JPanel {

	/** The serial ID for this ImagePanel. */
	private static final long serialVersionUID = 1L;

	/** The JLabel image for this ImagePanel. */
	public JLabel image;

	/** The Control associated with this ImagePanel. */
	public Control control;

	/** The EnlargeButton associated with this ImagePanel. */
	public EnlargeButton enlargeButton;

	/**
	 * Create a new ImagePanel with a JLabel image and a Control. Set the layout
	 * for this ImagePanel and also add all components to it.
	 * 
	 * @param image
	 *            JLabel that has this image
	 * @param control
	 *            the Control associated with this ImagePanel
	 */
	public ImagePanel(JLabel image, Control control) {
		// set layout and border
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		setBorder(blackline);

		// create new EnlargeButton
		enlargeButton = new EnlargeButton("View Actual Size", control);

		this.control = control;
		this.image = image;
		this.image.setToolTipText(control.getPhoto().getFullName());

		// add all the components to this ImagePanel
		this.add(image);
		this.add(enlargeButton);
		this.add(control);
	}
}
