package toyStoreDatabase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.cyberscientist.toystoredb.model.Puzzle;

class TestPuzzle {
	Puzzle testPuzzle;
	Puzzle testArray;
	
	String[] array = {"5555555555", "Turtle", "Mattel", "12.99", "10", "14", "M" };

	@BeforeEach
	void setUp() throws Exception {
		testPuzzle = new Puzzle("4444444444", "Jigsaw", "SAW", 13.99, 10, 14, "M");
		testArray = new Puzzle(array);
	}

	@Test
	void testGetPuzzleType() {
		assertEquals("M", testPuzzle.getPuzzleType());
	}
	
	@Test 
	void testToString() {
		assertEquals("4444444444;Jigsaw;SAW;13.99;10;14;M", testPuzzle.toString());
	}
	
	@Test
	void testFormat() {
		assertEquals("4444444444;Jigsaw;SAW;13.99;10;14;M", testPuzzle.format());
	}

}
