package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * PhotoRenamer GUI.
 * 
 * @author Tiansheng
 * @author Jeevaa
 *
 */
public class PhotoRenamer {

	/**
	 * Build the window for the PhotoRenamer GUI.
	 * 
	 * @return JFrame of this PhotoRenamer's GUI
	 */
	public static JFrame buildWindow() {
		JFrame applicationFrame = new JFrame("PhotoRenamer");
		applicationFrame.setLayout(new BorderLayout());
		applicationFrame.setSize(500, 500);

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// Create an Image Area to display all the images
		JPanel imageArea = new JPanel();

		// Create a scroll pane
		JScrollPane scrollPane = new JScrollPane(imageArea);

		// Exit button
		ExitButton exitButton = new ExitButton("Exit");

		// Save Button
		SaveButton saveButton = new SaveButton("Save");

		// Exit and Save Button Container
		JPanel exitSavePane = new JPanel();
		exitSavePane.setLayout(new GridLayout(0, 2));
		exitSavePane.add(saveButton);
		exitSavePane.add(exitButton);

		// Application name
		JTextField appName = new JTextField();
		appName.setText("Welcome to PhotoRenamer");
		appName.setEditable(false);
		appName.setHorizontalAlignment(JTextField.CENTER);

		// The directory choosing button.
		JButton openButton = new JButton("Choose Directory");
		openButton.setVerticalTextPosition(AbstractButton.CENTER);
		openButton.setHorizontalTextPosition(AbstractButton.LEADING);
		openButton.setMnemonic(KeyEvent.VK_D);
		openButton.setActionCommand("disable");
		ActionListener buttonListener = new ImageExplorer(applicationFrame, imageArea, fileChooser);
		openButton.addActionListener(buttonListener);

		// appName and directory choosing button container
		JPanel appDirectoryChooser = new JPanel();
		appDirectoryChooser.setLayout(new GridLayout(0, 1));
		appDirectoryChooser.add(appName);
		appDirectoryChooser.add(openButton);

		// Adding containers to the Frame
		applicationFrame.add(appDirectoryChooser, BorderLayout.NORTH);
		applicationFrame.add(exitSavePane, BorderLayout.SOUTH);
		applicationFrame.add(scrollPane, BorderLayout.CENTER);
		applicationFrame.setDefaultCloseOperation(applicationFrame.EXIT_ON_CLOSE);

		// applicationFrame.pack();

		return applicationFrame;
	}

	public static void main(String[] args) {
		PhotoRenamer.buildWindow().setVisible(true);
	}

}
