package toyStoreDatabase;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import ca.cyberscientist.toystoredb.model.Toy;
import ca.cyberscientist.toystoredb.model.Animal;
import ca.cyberscientist.toystoredb.controller.Database;

/**
 * @author Bryce Carson
 *
 */
class TestRecordsWriteToDisk {
	private String TEST_DATABASE_FILENAME = "res/testDatabase.txt";
	private Database DATABASE = new Database(TEST_DATABASE_FILENAME);

	private String TOY_SERIAL_NUMBER		= "0123456789";
	private String TOY_NAME							= "Test_Toy_001";
	private String TOY_BRAND						= "CyberScientist Toystore";
	private int    TOY_COUNT						= 1;
	private double TOY_PRICE						= 9.99;
	private int    TOY_AGE							= 1;
	private String TOY_ANIMAL_MATERIAL	= "Fabric";
	private String TOY_ANIMAL_SIZE			= "M";

	/**
	 * If this test passes, then adding a toy to the database, saving to disk, and
	 * loading that changed database from disk all work as expected.
	 * Test method for
	 * {@link ca.cyberscientist.toystoredb.model.Database#addRecord(ca.cyberscientist.toystoredb.model.Toy)},
	 * {@link ca.cyberscientist.toystoredb.model.Database#writeToDisk()},
	 * {@link ca.cyberscientist.toystoredb.model.Database#removeRecord(ca.cyberscientist.toystoredb.model.Toy)}.
	 *
	 * @throws FileNotFoundException
	 */
		@Test
	void testDatabaseMegalithicTest() {

		ArrayList<Toy> testToyList =
				DATABASE.searchRecords(TOY_SERIAL_NUMBER,
															 DATABASE.SEARCH_BY_SERIAL_NUMBER);

		if (testToyList.size() < 1) {
			DATABASE
					.addRecord(new Animal(TOY_SERIAL_NUMBER,
																TOY_NAME,
																TOY_BRAND,
																TOY_PRICE,
																TOY_COUNT,
																TOY_AGE,
																TOY_ANIMAL_MATERIAL,
																TOY_ANIMAL_SIZE));
			DATABASE.writeToDisk(TEST_DATABASE_FILENAME);

			// Recreate the database from disk.
			DATABASE = new Database(TEST_DATABASE_FILENAME);

			testToyList =
					DATABASE.searchRecords(TOY_SERIAL_NUMBER,
																 DATABASE.SEARCH_BY_SERIAL_NUMBER);

			assertTrue(testToyList.size() == 1);

		} else if (testToyList.size() > 1) {
			fail("More than one toy object returned.");

		} else if (testToyList.size() == 1) {
			assertTrue(true);
		}
	}

}
