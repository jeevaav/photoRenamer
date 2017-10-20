package photo_renamer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * An ImageFinder finds all the images under a directory.
 * 
 * @author TianSheng
 * @author Jeevaa
 *
 */
public class ImageFinder {

	/** The list of valid image extensions ImageFinder considers. */
	private static ArrayList<String> VALID_IMAGE_EXTENSION = new ArrayList<String>(
			Arrays.asList("tiff", "bmp", "gif", "jpg", "png", "JPG", "jpeg"));

	/**
	 * Add an image extension to VALID_IMAGE_EXTENSIONS.
	 * 
	 * @param extension
	 *            extension to be added
	 */
	public static void addExtensions(String extension) {
		VALID_IMAGE_EXTENSION.add(extension);
	}

	/**
	 * Add valid Images to the ImageManager's library.
	 * 
	 * @param file
	 *            the directory to be explored
	 * @param setOfParents
	 *            stores the set of parent path for each Image
	 */
	private void addToLibrary(File file, LinkedHashSet<String> setOfParents) {

		// Base case, when file is a file
		if (file.isFile()) {
			String extension = getExtension(file);
			// Check if file is an image file
			if (VALID_IMAGE_EXTENSION.contains(extension)) {
				// Create temporary Image instance
				Photo newImage = new Photo(file.getName(), file.getParentFile());
				ImageManager.getInstance().addPhoto(newImage);
			}
		}
		// When file is a folder
		else {
			String path = file.getAbsolutePath();
			setOfParents.add(path);
			for (File newFile : file.listFiles()) {
				addToLibrary(newFile, setOfParents);
			}
		}
	}

	/**
	 * Get a set of Images under a directory and put all the Images in the
	 * ImageManager's library.
	 * 
	 * @param file
	 *            the directory to be explored
	 */
	public LinkedHashSet<Photo> getImages(File file) {
		LinkedHashSet<String> parents = new LinkedHashSet<String>();
		addToLibrary(file, parents);
		return listImages(parents);
	}

	/**
	 * Return a set of Images from the ImageManager's library based on the
	 * parent paths.
	 * 
	 * @param parents
	 *            set of parent paths that user is interested
	 * @param local
	 *            the ImageManager that stores Images
	 */
	private LinkedHashSet<Photo> listImages(LinkedHashSet<String> parents) {
		LinkedHashSet<Photo> setOfImages = new LinkedHashSet<Photo>();
		for (String parent : parents) {
			if (ImageManager.getInstance().getLibrary().containsKey(parent)) {
				setOfImages.addAll(ImageManager.getInstance().getLibrary().get(parent));
			}
		}
		return setOfImages;
	}

	/**
	 * Return the extension of a file.
	 * 
	 * @param file
	 *            the file that user is interested
	 * @param return
	 *            the extension of the file
	 * 
	 */
	private String getExtension(File file) {
		String extension = "";
		int i = file.getName().lastIndexOf('.');
		if (i > 0) {
			extension = file.getName().substring(i + 1);
		}
		return extension;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		/*
		 * ImageFinder dir = new ImageFinder(); String path =
		 * "C:\\Users\\Jeevaa\\Desktop\\Test"; File file = new File(path);
		 * ImageManager manager = new ImageManager(); LinkedHashSet<Image>
		 * images = dir.getImages(file, manager);
		 * System.out.println(manager.getLibrary()); System.out.println(images);
		 * 
		 * String parentPath = "C:\\Users\\Jeevaa\\Desktop\\Test"; File parent =
		 * new File(parentPath); Image p1 = new Image("test2.bmp", parent);
		 */
	}
}