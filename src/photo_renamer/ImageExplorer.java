package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ImageExplorer implements ActionListener {

	/** The JFrame that is associated with this ImageExplorer. */
	private JFrame appFrame;

	/** The JFileChooser that is associated with this ImageExplorer. */
	private JFileChooser fileChooser;

	/** The JPanel that is associated with this ImageExplorer. */
	private JPanel imageArea;

	/** The photoLabels that is associated with this ImageExplorer. */
	private LinkedHashMap<JLabel, Photo> photoLabels;

	/**
	 * Create a new ImageExplorer with a JFrame, a JPanel and a JFileChooser.
	 * Set the layout and the gap of the JPanel.
	 * 
	 * @param appFrame
	 *            JFrame that is associated with this ImageExplorer
	 * @param imageArea
	 *            JPanel that is associated with this ImageExplorer
	 * @param fileChooser
	 *            JFileChooser that is associated with this ImageExplorer
	 */
	public ImageExplorer(JFrame appFrame, JPanel imageArea, JFileChooser fileChooser) {
		this.appFrame = appFrame;
		this.fileChooser = fileChooser;
		this.imageArea = imageArea;

		// set the layout and gap for imageArea
		GridLayout layout = new GridLayout(0, 2);
		layout.setHgap(10);
		layout.setVgap(10);
		this.imageArea.setLayout(layout);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		int returnVal = fileChooser.showOpenDialog(appFrame.getContentPane());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file.exists()) {

				ImageFinder dir = new ImageFinder();
				HashSet<Photo> images = dir.getImages(file);

				this.photoLabels = new LinkedHashMap<JLabel, Photo>();

				for (Photo i : images) {
					JLabel imageLabel = getImageLabel(i.getParentFile().getAbsolutePath() + "//" + i.getFullName());
					photoLabels.put(imageLabel, i);
				}

				this.imageArea.removeAll();

				for (JLabel photoLabel : photoLabels.keySet()) {
					Control control = new Control(photoLabels.get(photoLabel), this.imageArea);
					ImagePanel imagePanel = new ImagePanel(photoLabel, control);
					this.imageArea.add(imagePanel);
				}

				this.imageArea.repaint();
				this.imageArea.revalidate();
			}
		}

	}

	/**
	 * Return a JLabel that contains the image in the imagePath with a set
	 * dimension.
	 * 
	 * @param imagePath
	 *            String that contains the path to the desired file
	 * @return JLabel with the image in the imagePath with a set dimension
	 */
	// This is an output method
	public static JLabel getImageLabel(String imagePath) {
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
		}

		Image imgNew = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(imgNew);
		JLabel imageLabel = new JLabel(null, image, JLabel.CENTER);
		return imageLabel;
	}

}
