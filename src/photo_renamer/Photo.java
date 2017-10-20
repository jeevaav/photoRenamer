package photo_renamer;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

// The Photo uses Observer design pattern.
// The observer object is the record instance of Photo constantly getting updates when an action occurs. 
// The observable object is the current tags of the Photo instance.

/**
 * A representation of a Photo.
 * 
 * @author Tiansheng
 * @author Jeevaa
 */
public class Photo implements Serializable {

	/** The serial ID for this Photo. */
	private static final long serialVersionUID = -7091257193495432340L;

	/** The parent file of this Photo. */
	private File parentFile;

	/** The initial name of the Photo. */
	private String photoName;

	/** The name that is created with the tags. */
	private String tagName;

	/** The record for any changes occurred in the name of this Photo. */
	private LinkedHashMap<TimeStamp, HashSet<String>> record; // LinkedHashMap
																// to preserve
																// insertion
																// order

	/** The set of current tags associated with this Photo. */
	private HashSet<String> currentTags;

	/** '@' symbol. */
	public static String ATSYMBOL = "@";

	/**
	 * Creates a new Photo with the given photo name and parent file, an empty
	 * string for the tag name, an empty set of current tags, a map for the
	 * record of changes. It also adds the record that this Image is created at
	 * this particular time.
	 * 
	 * @param photoName
	 *            the original name of this Photo
	 * @param parentFile
	 *            the parent file of this Photo
	 * 
	 */
	public Photo(String photoName, File parentFile) {
		this.photoName = photoName;
		this.parentFile = parentFile;
		this.tagName = "";
		currentTags = new HashSet<String>();
		record = new LinkedHashMap<TimeStamp, HashSet<String>>();
		addRecord();
	}

	/**
	 * Returns the set of current tags of this Photo.
	 * 
	 * @return the current tags of this Photo
	 */
	public HashSet<String> getTags() {
		return this.currentTags;
	}

	/**
	 * Returns the full name of this Photo.
	 * 
	 * @return the full name of this Photo
	 */
	public String getFullName() {
		return this.tagName + this.photoName;
	}

	/**
	 * Returns the HashCode of this Photo.
	 * 
	 * @return the HashCode of this Photo
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parentFile == null) ? 0 : parentFile.hashCode());
		return result;
	}

	/**
	 * Returns true if this Photo is equals to other Photo.
	 * 
	 * @return whether this Photo equals other Photo
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Photo other = (Photo) obj;

		// Create full name for self
		String fullName = this.parentFile.getAbsolutePath();
		fullName += this.tagName;
		fullName += this.photoName;

		// Create full name for other
		String otherFull = other.parentFile.getAbsolutePath();
		otherFull += other.tagName;
		otherFull += other.photoName;

		if (!otherFull.equals(fullName)) {
			return false;
		}

		return true;
	}

	/**
	 * Returns the string representation of this Photo.
	 * 
	 * @return the string representation of this Photo
	 */
	@Override
	public String toString() {
		return "Image name: " + tagName + photoName + " -- Location: " + parentFile.getAbsolutePath();
	}

	/**
	 * Returns the file associated with this Photo in the system.
	 * 
	 * @return the file associated with this Photo
	 */
	public File toFile() {
		File file = new File(this.parentFile, tagName + photoName);
		return file;
	}

	/**
	 * Remove a tag from the Photo. It will raise exception when there no such
	 * tag exist.
	 * 
	 * @param tagToRemove
	 *            The tag to be removed
	 * @throws IOexception
	 *             if input is invalid
	 * @throws TagConflictException
	 *             if tag does not exist
	 */
	public void removeTag(String tagToRemove) throws IOException {

		HashSet<String> tempTags = (HashSet<String>) this.currentTags.clone();
		tempTags.remove(tagToRemove);
		if (tempTags.size() != this.currentTags.size()) {
			if (rename(tempTags)) {
				this.currentTags.remove(tagToRemove);
				addRecord();

				// Modify allTags
				removeFromAllTags(tagToRemove, this);
			}

		} else {
			try {
				throw new TagConflictException("Tag does not exist!");
			} catch (TagConflictException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Add a new tag to the Photo. It will raise exception when there is a
	 * similar tag exist.
	 * 
	 * @param tagToAdd
	 *            The tag that is to be added
	 * @throws IOexception
	 *             if input is invalid
	 * @throws TagConflictException
	 *             if tag already exist
	 */
	public void addTag(String tagToAdd) throws IOException {

		HashSet<String> tempTags = (HashSet<String>) this.currentTags.clone();
		tempTags.add(tagToAdd);
		if (tempTags.size() != this.currentTags.size()) {
			if (rename(tempTags)) {
				this.currentTags.add(tagToAdd);
				addRecord();

				// Modify allTags
				addToAllTags(tagToAdd, this);
			}
		}

		else {
			try {
				throw new TagConflictException("Tag already exist!");
			} catch (TagConflictException e) {
				System.out.println(e.getMessage());

			}
		}

	}

	/**
	 * Rename the Photo based on the current tags. Return true if the renaming
	 * is success.
	 * 
	 * @param tempTags
	 *            temporary tags that is associated with this Photo
	 * @throws IOexception
	 *             if input is invalid
	 * @return true if renaming is success
	 */
	private boolean rename(HashSet<String> tempTags) throws IOException {

		String newTagName = "";

		for (String tag : tempTags) {
			newTagName += ATSYMBOL + tag;
		}

		File file = new File(this.parentFile, this.tagName + this.photoName);
		File newFile = new File(this.parentFile, newTagName + this.photoName);
		if (newFile.exists())
			throw new java.io.IOException("File already exists");

		boolean success = file.renameTo(newFile);

		if (success) {
			this.tagName = newTagName;
		}

		return success;

	}

	/**
	 * Rename the Photo to older name based on the time given. Also, edits the
	 * ImageManager's allTags map according to current list of tags.
	 * 
	 * @param time
	 *            TimeStamp that you want to go back to
	 * @throws IOexception
	 *             if input is invalid
	 *
	 */
	public void renameToOldName(TimeStamp time) throws IOException {
		if (record.containsKey(time)) {
			HashSet<String> original = (HashSet<String>) this.currentTags.clone();
			HashSet<String> set = (HashSet<String>) record.get(time).clone();
			HashSet<String> newSet = (HashSet<String>) set.clone();
			if (!(set.equals(this.currentTags))) {
				rename(set);
				this.currentTags = set;
				addRecord();
			} else {
				addRecord();
			}

			// Modify allTags
			HashSet<String> originalCopy = (HashSet<String>) original;
			original.removeAll(newSet);
			newSet.removeAll(originalCopy);
			if (!(original.isEmpty())) {
				for (String tag : original) {
					removeFromAllTags(tag, this);
				}
			}
			if (!(newSet.isEmpty())) {
				for (String tag : newSet) {
					addToAllTags(tag, this);
				}
			}

		} else {
			try {
				throw new TimeStampNotExistException("TimesStamp does not exist!");
			} catch (TimeStampNotExistException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	/**
	 * Return the parent file of this Photo.
	 * 
	 * @return the parent file of this Photo
	 */
	public File getParentFile() {
		return this.parentFile;
	}

	/**
	 * Display the record of this Photo. It shows all the changes made to the
	 * name and the time the changes were made.
	 * 
	 * @return record of this Photo
	 */
	public String displayRecord() {
		String result = "";
		for (TimeStamp time : this.record.keySet()) {
			result += time + ": ";
			HashSet<String> currSet = record.get(time);
			for (String tag : currSet) {
				result += ATSYMBOL + tag;
			}
			result += this.photoName + "\n";
		}

		return result.trim();
	}

	/**
	 * Add a record to this Photo if it was renamed based on the particular
	 * time.
	 * 
	 */
	private void addRecord() {
		ZonedDateTime timeNow = ZonedDateTime.now();
		// record the current time
		TimeStamp currTime = new TimeStamp(timeNow.getYear(), timeNow.getMonthValue(), timeNow.getDayOfMonth(),
				timeNow.getHour(), timeNow.getMinute(), timeNow.getSecond());
		// adding a record corresponds to the current time
		HashSet<String> oldTags = (HashSet<String>) this.currentTags.clone();
		record.put(currTime, oldTags);

	}

	/**
	 * Add tag to the ImageManager's allTags map if a new tag is created for any
	 * Photo.
	 * 
	 * @param tag
	 *            tag that is created
	 * @param photo
	 *            the photo that is associated with the tag
	 */
	private static void addToAllTags(String tag, Photo photo) {
		if (ImageManager.getAllTags().containsKey(tag)) {
			ImageManager.getAllTags().get(tag).add(photo);
		} else {
			HashSet<Photo> newSet = new HashSet<Photo>();
			newSet.add(photo);

			ImageManager.getAllTags().put(tag, newSet);
		}
	}

	/**
	 * Remove tag or photo from the ImageManager's allTags map if a tag is
	 * removed from any Photo.
	 * 
	 * @param tag
	 *            tag that is removed
	 * @param photo
	 *            the photo that is associated with the tag
	 */
	private static void removeFromAllTags(String tag, Photo photo) {
		ImageManager.getAllTags().get(tag).remove(photo);
		if (ImageManager.getAllTags().get(tag).isEmpty()) {
			ImageManager.getAllTags().remove(tag);
		}
	}

	/**
	 * Removes all the current tags from this Photo instance.
	 * 
	 * @throws IOException
	 *             if input is invalid
	 */
	public void removeAllTags() throws IOException {
		HashSet<String> tempTag = new HashSet<String>();
		if (rename(tempTag)) {
			for (String tag : this.currentTags) {
				removeFromAllTags(tag, this);
			}
			currentTags.clear();
		}
	}

	public String[] getRecordArray() {

		LinkedHashSet<String> set = new LinkedHashSet<String>();

		for (TimeStamp time : this.record.keySet()) {
			String result = "";
			result += time + ": ";
			HashSet<String> currSet = record.get(time);
			for (String tag : currSet) {
				result += ATSYMBOL + tag;
			}
			result += this.photoName;
			set.add(result);
		}

		return set.toArray(new String[set.size()]);
	}

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException,
			TagConflictException, TimeStampNotExistException {

		System.out.println(System.getProperty("user.dir"));
		/*
		 * File parentFile = new File("C:\\Users\\Jeevaa\\Desktop\\Test"); Photo
		 * img1 = new Photo("test2.bmp", parentFile); TimeUnit.SECONDS.sleep(1);
		 * img1.addTag("jeevaa"); TimeUnit.SECONDS.sleep(1);
		 * img1.addTag("peter"); for(String s: img1.getRecordArray()) {
		 * System.out.println(s); }
		 * 
		 */

		/*
		 * File parent = new File("C:\\Users\\Jeevaa\\Desktop\\Test"); Image
		 * img1 = new Image("@Janeimage1.bmp", parent);
		 * TimeUnit.SECONDS.sleep(1); img1.addTag("peter");
		 * TimeUnit.SECONDS.sleep(1); img1.addTag("kate");
		 * TimeUnit.SECONDS.sleep(1); img1.addTag("marry");
		 * TimeUnit.SECONDS.sleep(1); img1.addTag("Phil");
		 * TimeUnit.SECONDS.sleep(1); System.out.println(img1.tagName);
		 * TimeUnit.SECONDS.sleep(1); img1.removeTag("peter"); ZonedDateTime zdt
		 * = ZonedDateTime.now(); TimeStamp time = new TimeStamp(zdt.getYear(),
		 * zdt.getMonthValue(), zdt.getDayOfMonth(), zdt.getHour(),
		 * zdt.getMinute(), zdt.getSecond()); TimeUnit.SECONDS.sleep(1);
		 * img1.removeTag("kate"); TimeUnit.SECONDS.sleep(1);
		 * img1.removeTag("marry"); TimeUnit.SECONDS.sleep(1);
		 * img1.removeTag("Phil"); TimeUnit.SECONDS.sleep(1);
		 * System.out.println(img1.tagName); img1.renameToOldName(time);
		 * System.out.println(img1.displayRecord());
		 * 
		 * 
		 * HashSet<Integer> set = new HashSet<Integer>(); set.add(3);
		 * set.add(6);
		 * 
		 * HashSet<Integer> copy = (HashSet<Integer>) set.clone(); copy.add(7);
		 * set.add(8); System.out.println(copy); System.out.println(set);
		 * copy.removeAll(set); System.out.println(copy);
		 * System.out.println(set);
		 */
	}
}
