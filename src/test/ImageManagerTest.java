/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import photo_renamer.ImageManager;
import photo_renamer.Photo;

/**
 * @author Tiansheng
 * @author Jeevaa
 *
 */
public class ImageManagerTest {

	/** The ImageManager being used during the test. */
	private ImageManager manager;

	/** The image folder path that is being used in the test. */
	private String PARENTPATH = "src//test//ImageTest";
	
	/** The Photo instance that is being used in the test. */
	private Photo img1;
	
	/** The Photo instance that is being used in the test. */
	private Photo img2;


	/**
	 * Get the instance of the ImageManager and reset the ImageManager.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		manager = ImageManager.getInstance();
		manager.reset();
	}

	/**
	 * Reset and save the ImageManager.
	 *  
	 * @throws java.lang.Exception
	 * 
	 */
	@After
	public void tearDown() throws Exception {
		manager.reset();
		manager.saveToFile();
	}

	/**
	 * Test an empty library with no tags or photos.
	 * 
	 */
	@Test
	public void testEmptyLibrary() {
		HashMap<String, HashSet<Photo>> expected = new HashMap<String, HashSet<Photo>>();
		HashMap<String, HashSet<Photo>> actual = manager.getAllTags();
		assertEquals("test empty library failed", expected, actual);
	}

	/**
	 * Test library with no tags and contains some photos.
	 * 
	 */
	@Test
	public void testNoTagLibrary() {
		File parentFile = new File(PARENTPATH);
		img1 = new Photo("photo1.jpg", parentFile);
		manager.addPhoto(img1);
		img2 = new Photo("photo2.jpg", parentFile);
		manager.addPhoto(img2);
		HashMap<String, HashSet<Photo>> actual = manager.getAllTags();
		HashMap<String, HashSet<Photo>> expected = new HashMap<String, HashSet<Photo>>();
		assertEquals("test no tag library failed", expected, actual);
	}

	/**
	 * Test library with tags and contains some photos.
	 * 
	 */
	@Test
	public void testHaveTagLibrary() {
		File parentFile = new File(PARENTPATH);
		img1 = new Photo("photo1.jpg", parentFile);
		img2 = new Photo("photo2.jpg", parentFile);
		try {
			img1.addTag("Peter");
			img2.addTag("Peter");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Actual
		HashMap<String, HashSet<Photo>> actual = manager.getAllTags();

		// Expected
		HashMap<String, HashSet<Photo>> expected = new HashMap<String, HashSet<Photo>>();
		HashSet<Photo> set = new HashSet<>();
		set.add(img1);
		set.add(img2);
		expected.put("Peter", set);
		assertEquals("test library with tags failed", expected, actual);

		// Revert the changes
		try {
			img1.removeAllTags();
			img2.removeAllTags();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Test library after adding one tag.
	 * 
	 */
	@Test
	public void testAddOneTag() {
		File parentFile = new File(PARENTPATH);
		img1 = new Photo("photo1.jpg", parentFile);
		img2 = new Photo("photo2.jpg", parentFile);
		try {
			img1.addTag("Peter");
			img2.addTag("Peter");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Actual
		HashMap<String, HashSet<Photo>> actual = manager.getAllTags();

		// Expected
		HashMap<String, HashSet<Photo>> expected = new HashMap<String, HashSet<Photo>>();
		HashSet<Photo> set = new HashSet<>();
		set.add(img1);
		set.add(img2);
		expected.put("Peter", set);
		assertEquals(expected, actual);
		
		try {
			img1.addTag("Jane");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		actual = manager.getAllTags();
		HashSet<Photo> newSet = new HashSet<>();
		newSet.add(img1);
		expected.put("Jane", newSet);
		assertEquals("test library after adding one tag failed", expected, actual);
		
		// Revert the changes
				try {
					img1.removeAllTags();
					img2.removeAllTags();
				} catch (IOException e) {
					e.printStackTrace();
				}
	}
	
	/**
	 * Test library after adding multiple tags.
	 * 
	 */
	@Test
	public void testAddMultiTag() {
		File parentFile = new File(PARENTPATH);
		img1 = new Photo("photo1.jpg", parentFile);
		img2 = new Photo("photo2.jpg", parentFile);
		try {
			img1.addTag("Peter");
			img2.addTag("Peter");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Actual
		HashMap<String, HashSet<Photo>> actual = manager.getAllTags();

		// Expected
		HashMap<String, HashSet<Photo>> expected = new HashMap<String, HashSet<Photo>>();
		HashSet<Photo> set = new HashSet<>();
		set.add(img1);
		set.add(img2);
		expected.put("Peter", set);
		assertEquals(expected, actual);
		
		try {
			img1.addTag("Jane");
			img2.addTag("Jake");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		actual = manager.getAllTags();
		HashSet<Photo> janeSet = new HashSet<>();
		janeSet.add(img1);
		HashSet<Photo> jakeSet = new HashSet<>();
		jakeSet.add(img2);
		expected.put("Jane", janeSet);
		expected.put("Jake", jakeSet);
		assertEquals("test library after adding multiple tag failed", expected, actual);
		
		// Revert the changes
				try {
					img1.removeAllTags();
					img2.removeAllTags();
				} catch (IOException e) {
					e.printStackTrace();
				}
	}

	/**
	 * Test library after removing one tag.
	 * 
	 */
	@Test
	public void testRemoveOneTag() {
		File parentFile = new File(PARENTPATH);
		img1 = new Photo("photo1.jpg", parentFile);
		img2 = new Photo("photo2.jpg", parentFile);

		// Add the tags
		try {
			img1.addTag("Peter");
			img1.addTag("John");
			img2.addTag("Peter");

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Remove the "Peter" tag from img1 only
		try {
			img1.removeTag("Peter");
		} catch (IOException e) {
			e.printStackTrace();
		}

		HashMap<String, HashSet<Photo>> expected = new HashMap<String, HashSet<Photo>>();
		HashSet<Photo> setPeter = new HashSet<>();
		setPeter.add(img2);
		expected.put("Peter", setPeter);

		HashSet<Photo> setJohn = new HashSet<>();
		setJohn.add(img1);
		expected.put("John", setJohn);

		HashMap<String, HashSet<Photo>> actual = manager.getAllTags();

		assertEquals("test library after removing one tag failed", expected, actual);

		// Revert the changes
		try {
			img1.removeAllTags();
			img2.removeAllTags();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Test library after removing all tags.
	 * 
	 */
	@Test
	public void testRemoveAllTag() {
		File parentFile = new File(PARENTPATH);
		img1 = new Photo("photo1.jpg", parentFile);
		img2 = new Photo("photo2.jpg", parentFile);

		// Add the tags
		try {
			img1.addTag("Peter");
			img2.addTag("Peter");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Remove the tags
		try {
			img1.removeAllTags();
			img2.removeAllTags();
		} catch (IOException e) {
			e.printStackTrace();
		}

		HashMap<String, HashSet<Photo>> expected = new HashMap<String, HashSet<Photo>>();
		HashMap<String, HashSet<Photo>> actual = manager.getAllTags();

		assertEquals("test library after removing all tags failed", expected, actual);
	}

}
