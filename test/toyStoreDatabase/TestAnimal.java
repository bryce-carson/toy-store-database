package toyStoreDatabase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.cyberscientist.toystoredb.model.Animal;

class TestAnimal {
	Animal testAnimal; 

	@BeforeEach
	void setUp() throws Exception {
		testAnimal = new Animal("2222222222", "Giraffe", "Hotwheels", 12.99, 10, 14, "cotton", "S");
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
	void testToString() {
		assertEquals("2222222222;Giraffe;Hotwheels;12.99;10;14;cotton;S");
	}

}
