package photo_renamer;

import java.awt.Component;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//The Control uses Observer design pattern.
//The observer object is the all the buttons in the Control. 
//The observable object is the the actions that are performed when the buttons are clicked.

/**
 * The Control manages adding, removing and restoring tags for one Photo instance.
 * 
 * @author Jeevaa
 * @author Tiansheng
 *
 */
public class Control extends JPanel implements ActionListener {

	/** The serial ID for this Control. */
	private static final long serialVersionUID = 1L;

	/** The list of tags that is associated with this Control's Photo. */
	private JComboBox<String> tags;

	/** The record that is associated with this Control's Photo. */
	private JComboBox<String> record;

	/** The list of all tags from the master tags. */
	private JComboBox<String> masterTags;

	/** The button to add a new tag to this Control's Photo. */
	private JButton addButton;

	/** The button to remove a tag from this Control's Photo. */
	private JButton removeButton;

	/** The button to restore to old name for Control's Photo. */
	private JButton restoreButton;

	/** The button to add a new tag from the master tag list. */
	private JButton addFromMaster;

	/** The text area to input new tag to be added. */
	private JTextField tagToAdd;

	/** The Photo that is associated with this Control. */
	private Photo photo;

	/** The list of master tags. */
	private static String[] masterTagArray;

	/** The area that all the Control will be added. */
	private JPanel imageArea;

	/**
	 * Create a new Control with the given Photo and the imageArea. Set the
	 * control with appropriate layout and gaps. Initialize all the JButtons,
	 * JComboBoxes and JTextArea. Set the ToolTipText for each components and
	 * add all the component to Control.
	 * 
	 * @param photo
	 *            the Photo associated with this Control
	 * @param imageArea
	 *            the JPanel that contains this Control
	 */
	public Control(Photo photo, JPanel imageArea) {

		// Set layout and gap for the Control
		GridLayout layout = new GridLayout(0, 2);
		layout.setHgap(5);
		layout.setVgap(0);
		setLayout(layout);

		this.imageArea = imageArea;
		this.photo = photo;

		// Change HashSet of tags to String array
		String[] tagArray = photo.getTags().toArray(new String[photo.getTags().size()]);

		// Build drop down box for tags
		this.tags = new JComboBox<String>(tagArray);

		// Change HashSet of master tags to String array
		masterTagArray = ImageManager.getAllTags().keySet()
				.toArray(new String[ImageManager.getAllTags().keySet().size()]);

		// Build drop down box for master tags
		this.masterTags = new JComboBox<String>(masterTagArray);

		// Initialize all the buttons and text area
		this.addFromMaster = new JButton("Add from master tags");
		this.addButton = new AddButton("Add Tag");
		this.removeButton = new RemoveButton("Remove Tag");
		this.restoreButton = new RestoreButton("Restore to Old Name");
		this.record = new JComboBox<String>(photo.getRecordArray());
		this.tagToAdd = new JTextField();

		// Set the ToolTipText for all the JButton, JTextArea and JComboBox
		this.addButton.setToolTipText("Click to add tag");
		this.tagToAdd.setToolTipText("Type the tag you want to add here");
		this.removeButton.setToolTipText("Click to remove tag");
		this.restoreButton.setToolTipText("Click to restore to old name");
		this.tags.setToolTipText("List of tags");
		this.record.setToolTipText("The record of the Photo names");
		this.masterTags.setToolTipText("Master list of tags");
		this.addFromMaster.setToolTipText("Add from master list of tags");

		// add all the components to the Control
		this.add(masterTags);
		this.add(addFromMaster);
		this.add(tagToAdd);
		this.add(addButton);
		this.add(tags);
		this.add(removeButton);
		this.add(record);
		this.add(restoreButton);

		// add ActionListener to all the buttons, text area and combo boxes.
		this.addButton.addActionListener(this);
		this.tagToAdd.addActionListener(this);
		this.removeButton.addActionListener(this);
		this.restoreButton.addActionListener(this);
		this.tags.addActionListener(this);
		this.record.addActionListener(this);
		this.addFromMaster.addActionListener(this);
		this.masterTags.addActionListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		Object c = (Object) e.getSource();
		if (c.equals(this.addButton)) {
			String tag = this.tagToAdd.getText();
			try {
				this.photo.addTag(tag);

				// update photo's tags list
				if (((DefaultComboBoxModel<String>) tags.getModel()).getIndexOf(tag) == -1) {
					tags.addItem(tag);
				} else {
					JOptionPane.showMessageDialog(imageArea, "Tag already exists!");
				}

				// update the masterTags
				masterTagArray = ImageManager.getAllTags().keySet()
						.toArray(new String[ImageManager.getAllTags().keySet().size()]);

				// update all the Control's master tag list in the imageArea
				updateMaster();

				// update the record of the Photo
				String lastItem = this.photo.getRecordArray()[this.photo.getRecordArray().length - 1];
				this.record.addItem(lastItem);

				// clear the JTextArea
				this.tagToAdd.setText("");

			} catch (IOException e1) {
		

			}

		}
		if (c.equals(this.removeButton)) {
			String tag = (String) this.tags.getSelectedItem();
			try {
				this.photo.removeTag(tag);

				// update tags list and record
				this.tags.removeItem(tag);

				// update the masterTags
				masterTagArray = ImageManager.getAllTags().keySet()
						.toArray(new String[ImageManager.getAllTags().keySet().size()]);

				// update all the Control's master tag list in the imageArea
				updateMaster();

				// update the record
				String lastItem = this.photo.getRecordArray()[this.photo.getRecordArray().length - 1];
				this.record.addItem(lastItem);

				// refresh the GUI
				this.revalidate();
				this.repaint();

			} catch (IOException e1) {

			}

		}
		if (c.equals(this.restoreButton)) {
			String tag = (String) this.record.getSelectedItem();
			TimeStamp time = TimeStamp.getTimeStamp(tag);
			try {
				this.photo.renameToOldName(time);

				// update tags list
				this.tags.removeAllItems();
				for (String t : this.photo.getTags()) {
					this.tags.addItem(t);
				}

				// update the record
				String lastItem = this.photo.getRecordArray()[this.photo.getRecordArray().length - 1];
				this.record.addItem(lastItem);

				// refresh the GUI
				this.revalidate();
				this.repaint();

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		if (c.equals(this.addFromMaster)) {
			String tag = (String) this.masterTags.getSelectedItem();
			System.out.println(tag);
			try {
				this.photo.addTag(tag);
				// update tags list
				if (((DefaultComboBoxModel<String>) tags.getModel()).getIndexOf(tag) == -1) {
					tags.addItem(tag);
				}

				// update the record
				String lastItem = this.photo.getRecordArray()[this.photo.getRecordArray().length - 1];
				this.record.addItem(lastItem);
				this.revalidate();
				this.repaint();
			} catch (IOException e1) {

			}
		}

	}

	// helper function
	private void updateMaster() {
		Component[] components = this.imageArea.getComponents();
		for (Component comp : components) {
			Component[] subComponents = ((ImagePanel) comp).getComponents();
			for (Component subComp : subComponents) {
				if (subComp.getClass().equals(this.getClass())) {
					((Control) subComp).masterTags.setModel(new JComboBox<String>(masterTagArray).getModel());
					subComp.revalidate();
					subComp.repaint();
				}
			}
		}
	}

	/**
	 * Return the Photo instance that is associated with this Control instance.
	 * 
	 * @return Photo instance that is associated with this Control
	 */
	public Photo getPhoto() {
		return photo;
	}

}
