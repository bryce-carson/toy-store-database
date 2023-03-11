package toyStoreDatabase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.cyberscientist.toystoredb.model.Figure;

class TestFigure {
	Figure testFigure;
	Figure testArray;
	
	String[] array = {"1111111111", "Turtle", "Mattel", "12.99", "10", "14", "A" };
	

	@BeforeEach
	void setUp() throws Exception {
		testFigure = new Figure("0000000000", "Gundam", "Fighter", 20.01, 10, 14, "A");
		testArray = new Figure(array);
	}

	@Test
	void testGetClassification() {
		assertEquals("A", testFigure.getClassification());
	}
	
	@Test
	void testToString() {
		assertEquals("0000000000;Gundam;Fighter;20.01;10;14;A", testFigure.toString());
	}
	
	@Test
	void testFormat() {
		assertEquals("0000000000;Gundam;Fighter;20.01;10;14;A", testFigure.format());
	}

}
