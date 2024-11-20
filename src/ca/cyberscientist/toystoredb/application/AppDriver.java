package ca.cyberscientist.toystoredb.application;

import ca.cyberscientist.toystoredb.controller.ToyStore;

/**
 * The class which initializes the program and handles any command-line arguments.
 * @author Bryce Carson
 *
 */
public class AppDriver {

	/**
	 * 
	 * @param args Command-line arguments, which are ignored.
	 */
	public static void main(String[] args) {
		new ToyStore();
	}

}
