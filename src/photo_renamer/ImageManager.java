package photo_renamer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

// ImageManager is a singleton design pattern.
// Being a singleton, it only constructs one instance of ImageManager.
// The instance can be accessed through calling getInstance method

/**
 * ImageManager manages all the Images.
 * 
 * @author Jeevaa
 * @author TianSheng
 *
 */
public class ImageManager implements Serializable {

	/** The serial ID this ImageManager. */
	private static final long serialVersionUID = 7580610948330537397L;

	/** The list of all tags in this ImageManager. */
	private static HashMap<String, HashSet<Photo>> allTags;

	/** The library of this ImageManager. */
	private static HashMap<String, HashSet<Photo>> library;

	/** The data file path of this ImageManager. */
	private String DATAPATH = System.getProperty("user.dir") + "//" + "data.ser";

	/** The only instance for ImageManager. */
	// creating a static instance for singleton
	private static ImageManager instance;

	static {
		try {
			instance = new ImageManager();
		} catch (ClassNotFoundException e) {

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new ImageManager with allTags map and library map. It populate
	 * the library with data from the DATAPATH or create new data file.
	 * 
	 * @throws ClassNotFoundException
	 */
	private ImageManager() throws IOException, ClassNotFoundException {

		allTags = new HashMap<String, HashSet<Photo>>();
		library = new HashMap<String, HashSet<Photo>>();

		File file = new File(DATAPATH);
		if (file.exists()) {
			readFromFile(DATAPATH);
		} else {
			file.createNewFile();
		}

	}

	/**
	 * Return allTags.
	 * 
	 * @return allTags HashMap
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	public static ImageManager getInstance() {
		return instance;
	}

	public static HashMap<String, HashSet<Photo>> getAllTags() {
		return allTags;
	}

	/**
	 * Populates the ImageManager with the data from the file at DATAPATH.
	 * 
	 * @throws FileNotFoundException
	 *             if DATAPATH is not a valid path
	 * @throws ClassNotFoundException
	 *             if a class is not a valid in the data file
	 */
	private void readFromFile(String filePath) throws IOException, ClassNotFoundException {
		InputStream file = new FileInputStream(filePath);
		InputStream buffer = new BufferedInputStream(file);
		ObjectInput input = new ObjectInputStream(buffer);

		library = (HashMap<String, HashSet<Photo>>) input.readObject();
		allTags = (HashMap<String, HashSet<Photo>>) input.readObject();
	}

	/**
	 * Writes the ImageManager to file at DATAPATH.
	 * 
	 * @throws IOException
	 *             if the input is invalid
	 */
	public void saveToFile() throws IOException {

		OutputStream file = new FileOutputStream(DATAPATH);
		OutputStream buffer = new BufferedOutputStream(file);
		ObjectOutput output = new ObjectOutputStream(buffer);

		output.writeObject(library);
		output.writeObject(allTags);
		output.close();
	}

	/**
	 * Return a string representation of this ImageManager.
	 * 
	 * @return a string representation of this ImageManger
	 * 
	 */
	@Override
	public String toString() {
		String result = "--ImageManager--" + "\n";
		for (String path : library.keySet()) {
			result += "Path: " + path + "\n";
			for (Photo img : library.get(path)) {
				result += "--" + img + "\n";
			}
			result += "\n";
		}
		return result.trim();

	}

	/**
	 * Add a Photo to the library and also updates the allTags HashMap.
	 * 
	 * @param photo
	 *            Image to be added
	 * 
	 */
	public void addPhoto(Photo photo) {
		String parentPath = photo.getParentFile().getAbsolutePath();
		// Check if folder containing image already exist in library
		if (library.containsKey(parentPath)) {
			if (!library.get(parentPath).contains(photo)) {
				// If image does not already exist, add to existing key
				library.get(parentPath).add(photo);
			}
			// When the folder containing image was not added before
		} else {
			HashSet<Photo> newSet = new HashSet<Photo>();
			newSet.add(photo);
			library.put(parentPath, newSet);
		}
	}

	/**
	 * Returns the library of this ImageManager.
	 * 
	 * @return the library of this ImageManager
	 */
	public static Map<String, HashSet<Photo>> getLibrary() {
		return library;
	}

	/**
	 * Return a string representation of allTags map of this ImageManager.
	 * 
	 * @return a string representation of allTags of this ImageManger
	 * 
	 */
	public String displayAllTags() {
		String result = "";
		for (String tag : allTags.keySet()) {
			result += tag + "\n";
			for (Photo i : allTags.get(tag)) {
				result += "--" + i + "\n";
			}
		}
		return result.trim();
	}

	/**
	 * Reset all tags from master tags list. Reset the entire library to empty.
	 * 
	 */
	public void reset() {
		this.library.clear();
		this.allTags.clear();
	}

	public static void main(String[] args) throws IOException {

		/*
		 * String parentPath = "/h/u7/c5/03/velayut2/Desktop/Test"; File parent
		 * = new File(parentPath); String fileName = "data.ser"; ImageManager
		 * lib = new ImageManager(parentPath + "/" + fileName); ImageFinder dir
		 * = new ImageFinder(); dir.getImages(parent, lib);
		 * lib.saveToFile(parentPath + "/" + fileName);
		 * 
		 * String parentPathNew = "/h/u7/c5/03/velayut2/Desktop/Test1"; File
		 * parentNew = new File(parentPathNew); ImageManager lib2 = new
		 * ImageManager(parentPath + "/" + fileName); ImageFinder dir2 = new
		 * ImageFinder(); dir2.getImages(parentNew, lib2);
		 * lib2.saveToFile(parentPath + "/" + fileName);
		 * 
		 * System.out.println(lib2);
		 * 
		 * for (String s : lib2.getLibrary().keySet()) { for (Image i :
		 * lib2.getLibrary().get(s)) { System.out.println(i); } }
		 */

	}
}
