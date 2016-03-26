package raj.function;

import java.io.File;
import java.io.FileFilter;
//Named methods as values Eg:isDirectory is the named method and it is passed as the value
public class Function1 {

	public static void main(String[] args) {
		System.out.println("**** Old *****");
		File[] files = oldWay();
		printFileNames(files);
		System.out.println("**** New *****");
		files = newWay();
		printFileNames(files);
	}

	private static File[] newWay() {
		//FileFilter is now a @FunctionalInterface
		//Note that the isDirectory is NOT isDirectory()
		File[] files = new File(".").listFiles(File::isDirectory);
		return files;
	}

	/**
	 * @return
	 */
	private static File[] oldWay() {
		File[] files = new File(".").listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				return file.isDirectory();
			}
		});
		return files;
	}
	/**
	 * @param files
	 */
	private static void printFileNames(File[] files) {
		for(File f : files) {
			System.out.println(f.getName());
		}
	}
}
