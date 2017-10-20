package photo_renamer;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * PhotoRenamerCommandLine is an application to rename an image file based on set of tags.
 * You can run this application on the console with properly giving valid
 * inputs. This is purely for checking the back-end code for PhotoRenamer
 * application.
 * 
 * @author Jeevaa
 * @author TianSheng
 *
 */
public class PhotoRenamerCommandLine {
	public static void main(String[] args) throws IOException, TagConflictException, ClassNotFoundException {
		System.out.println("-- Welcome to PhotoRenamer -- \n");
		Scanner newFile = new Scanner(System.in);

		System.out.println("Type the path that you want discover: ");
		String parentPath = newFile.next();
		File parent = new File(parentPath);

		String fileName = "dataFile.ser";
		ImageFinder dir = new ImageFinder();
		HashSet<Photo> photos = dir.getImages(parent);
		ImageManager.getInstance().saveToFile();

		System.out.println("All the images from the directory above are as follows: \n");
		for (Photo i : photos) {
			System.out.println(i);

		}

		System.out.println(
				"\n You can view tags for each images. \n You can add or remove tags or go back to old names. \n");
		System.out.println("Set of all available tags as follows: ");
		System.out.println(ImageManager.getInstance().getAllTags().keySet() + "\n");

		for (Photo i : photos) {
			System.out.println(i);
			System.out.println("Image's record as follows: ");
			System.out.println(i.displayRecord());
			System.out.println("Current tags: " + i.getTags());
			System.out.println(
					"Type 'A' if you want to add tag or 'R' if you want to remove or 'O' if you want to rename to old name: ");
			Scanner addOrRemove = new Scanner(System.in);
			String addRemove = addOrRemove.next();

			if (addRemove.equals("A")) {
				Scanner scanner = new Scanner(System.in);
				String s1 = "";
				while (!(s1.equals("Done"))) {
					System.out.println("Type tag to be added or 'Done' if done: ");
					s1 = scanner.next();
					if (!(s1.equals("Done"))) {
						i.addTag(s1);
					}

				}
				System.out.println("\n");

			} else if (addRemove.equals("R")) {
				Scanner scanner = new Scanner(System.in);
				String s1 = "";
				while (!(s1.equals("Done"))) {
					System.out.println("Type tag to be removed or 'Done' if done: ");
					s1 = scanner.next();
					if (!(s1.equals("Done"))) {
						i.removeTag(s1);
					}
				}
				System.out.println("\n");
			} else if (addRemove.equals("O")) {
				Scanner scanner = new Scanner(System.in);
				String s1 = "";
				while (!(s1.equals("Done"))) {
					System.out.println("Copy paste one of the time you want to go back or 'Done' if done: ");
					s1 = scanner.next();

					if (!(s1.equals("Done"))) {
						TimeStamp time = TimeStamp.getTimeStamp(s1);
						i.renameToOldName(time);
					}
				}

				System.out.println("\n");
			} else {
				System.out.println("You choose to do nothing with this image. \n");
			}

		}

		ImageManager.getInstance().saveToFile();
		System.out.println(ImageManager.getInstance());
	}
}
