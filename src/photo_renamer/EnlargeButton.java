package photo_renamer;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 * The EnlargeButton.
 * 
 * @author Tiansheng
 * @author Jeevaa
 *
 */
public class EnlargeButton extends JButton implements MouseListener {

	
	/** The serial ID for this EnlargeButton. */
	private static final long serialVersionUID = 1L;
	
	/** The Control that is associated with this EnLargerButton.  */
	private Control control;

	/**
	 * Create a new EnlargeButton.
	 * 
	 */
	// prevent default constructors
	private EnlargeButton() {
	};

	/**
	 * Create the EnlargeButton with the label.
	 * 
	 * @param label
	 *            the label for this EnlargeButton
	 */
	EnlargeButton(String label, Control control) {
		super(label); 
		this.control = control;
		this.addMouseListener(this);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
			if (e.getSource() == this) {
				JFrame jf = new JFrame();
				jf.setLayout(new BorderLayout());
				BufferedImage img = null;
				String imagePath = this.control.getPhoto().getParentFile().getAbsolutePath() + "//"
						+ this.control.getPhoto().getFullName();

				try {
					img = ImageIO.read(new File(imagePath));
				} catch (IOException exc) {
				}
				ImageIcon image = new ImageIcon(img);
				JLabel imageLabel = new JLabel(null, image, JLabel.CENTER);
				JScrollPane scrollPane = new JScrollPane(imageLabel);
				jf.add(scrollPane, BorderLayout.CENTER);
				jf.pack();
				jf.setVisible(true);
				jf.setDefaultCloseOperation(jf.DISPOSE_ON_CLOSE);
			}
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// this method is not used

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// this method is not used

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		// this method is not used

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// this method is not used
		
	}
	}