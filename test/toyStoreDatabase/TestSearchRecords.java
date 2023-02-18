/**
 * 
 */
package toyStoreDatabase;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import ca.cyberscientist.toystoredb.model.Database;

/**
 * @author Bryce Carson
 *
 */
class TestSearchRecords {

	/**Ensure that a search for toys of type Figure returns thirty-eight results when using the provided, unedited toys.txt file.
	 * Test method for {@link ca.cyberscientist.toystoredb.model.Database#searchRecords(char)}.
	 * @throws FileNotFoundException 
	 */
	@Test
	void testSearchRecordsByTypeFor38Figures() throws FileNotFoundException {
		Database records = new Database();
		assertTrue(records.searchRecords('f').size() == 38);
	}
	
	/**Ensure that only one result is returned when searching for the name "Amish doll".
	 * Test method for {@link ca.cyberscientist.toystoredb.model.Database#searchRecords(java.lang.String, char)}
	 * @throws FileNotFoundException 
	 */
	@Test
	void testSearchRecordsByNameForOneName() throws FileNotFoundException {
		Database records = new Database();
		assertTrue(records.searchRecords("Amish doll", 'n').size() == 1);
	}
	
	/**Ensure that two results are returned when searching with the query "Ball".
	 * Test method for {@link ca.cyberscientist.toystoredb.model.Database#searchRecords(java.lang.String, char)}.
	 * @throws FileNotFoundException 
	 */
	@Test
	void testSearchRecordsByNameForTwoNames() throws FileNotFoundException {
		Database records = new Database();
		assertTrue(records.searchRecords("Ball", 'n').size() == 2);
	}
	
	/**Ensure that only one result is returned when searching by a serial number.
	 * Test method for {@link ca.cyberscientist.toystoredb.model.Database#searchRecords(java.lang.String, char)}
	 * @throws FileNotFoundException 
	 */
	@Test
	void testSearchRecordsBySerialNumber() throws FileNotFoundException {
		Database records = new Database();
		assertTrue(records.searchRecords("3131436838", 's').size() == 1);
	}

}
