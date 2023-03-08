/**
 * 
 */
package toyStoreDatabase;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ca.cyberscientist.toystoredb.model.Animal;
import ca.cyberscientist.toystoredb.controller.Database;
import ca.cyberscientist.toystoredb.model.Toy;

/**
 * @author Bryce Carson
 *
 */
class TestRecordsWriteToDisk {
	
	private Database records;
	
	/**Initialize the database before any of the tests run.
	 * @throws FileNotFoundException
	 * 
	 */
	@BeforeAll
	void initializeDatabase() throws FileNotFoundException {
		this.records = new Database();
	}

	/**FIXME: this test needs to be busted up into a bunch of smaller test. This is ridiculous!
	 * If this test passes, then adding a toy to the database, saving to disk, and loading that changed database from disk all work as expected.
	 * Test method for {@link ca.cyberscientist.toystoredb.model.Database#addRecord(ca.cyberscientist.toystoredb.model.Toy)}.
	 * @throws FileNotFoundException 
	 */
	@Test
	void testAddRecordAndSave() throws FileNotFoundException {
		
		ArrayList<Toy> testToyList = this.records.searchRecords("2000000001", 's');
		
		if(testToyList.size() < 1) {
			this.records.addRecord(new Animal("2000000001", "testAnimal", "cyberScientistToys", 9.99, 1, 1, "Fabric", "M"));
			this.records.writeToDisk();
			
			// Recreate the database from disk.
			this.records = new Database();
			
			testToyList = this.records.searchRecords("2000000001", 's');
			assertTrue(testToyList.size() == 1);
		} else if(testToyList.size() > 1) {
			fail("More than one toy object returned.");
		} else if(testToyList.size() == 1) {
			assertTrue(true);
		}
		
		System.out.println(testToyList.get(0).format());
	}

}
