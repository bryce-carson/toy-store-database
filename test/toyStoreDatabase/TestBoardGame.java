package toyStoreDatabase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.cyberscientist.toystoredb.model.BoardGame;

class TestBoardGame {
	BoardGame testBoardGame;
	BoardGame testArray;
	
	String[] array = {"1111111111", "Turtle", "Mattel", "12.99", "10", "14", "2-5", "Koddy, Khosro" };

	@BeforeEach
	void setUp() throws Exception {
		testBoardGame = new BoardGame("7777777777", "Monopoly", "Pixar", 16.99, 10, 14, "2-5", "Koddy");
		testArray = new BoardGame(array);
	}

	@Test
	void testGetDesigner() {
		assertEquals("Koddy", testBoardGame.getDesigner());
	}
	
	@Test
	void testGetMaxNumPlayers() {
		assertEquals(5, testBoardGame.getMaxNumPlayers());
	}
	
	@Test
	void testGetMinNumPlayers() {
		assertEquals(2, testBoardGame.getMinNumPlayers());
	}
	
	@Test
	void testToString() {
		assertEquals("7777777777;Monopoly;Pixar;16.99;10;14;Koddy;2;5", testBoardGame.toString());
	}

	@Test
	void testToFormat() {
		assertEquals("7777777777;Monopoly;Pixar;16.99;10;14;2-5;Koddy", testBoardGame.format());
	}
}
