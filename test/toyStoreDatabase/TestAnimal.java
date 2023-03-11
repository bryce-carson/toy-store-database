package toyStoreDatabase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.cyberscientist.toystoredb.model.Animal;

class TestAnimal {
	Animal testAnimal; 
	Animal testDefault;
	Animal testArray;
	
	String[] array = {"1111111111", "Turtle", "Mattel", "12.99", "10", "14", "plastic", "M" };
	
	@BeforeEach
	void setUp() throws Exception {
		testAnimal = new Animal("2222222222", "Giraffe", "Hotwheels", 12.99, 10, 14, "cotton", "S");
		testDefault = new Animal();
		testArray = new Animal(array);
	}

	@Test
	void testGetSize() {
		assertEquals("S", testAnimal.getSize());
	}
	
	@Test
	void testGetMaterial() {
		assertEquals("cotton", testAnimal.getMaterial());
	}
	
	@Test
	void testToStringAndFormat() {
		assertEquals("2222222222;Giraffe;Hotwheels;12.99;10;14;cotton;S", testAnimal.toString());
		assertEquals("2222222222;Giraffe;Hotwheels;12.99;10;14;cotton;S", testAnimal.format());
	}
	
	@Test 
	void testConstructors() {
		assertNotNull(testDefault);
		assertNotNull(testArray);
	}

}
