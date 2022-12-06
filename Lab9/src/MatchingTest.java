import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MatchingTest {

	@Test
	void testFindKMP() {
		Matching m = new Matching();

		assertEquals(6, m.findKMP("ABCABA", "CABA"));
	}

	@Test
	void testFindBoyerMoore() {
		Matching m = new Matching();

		assertEquals(4, m.findBoyerMoore("abcbccacac".toCharArray(),"ac".toCharArray()));
	}

	@Test
	void testFindBrute() {
		Matching m = new Matching();

		assertEquals(6, m.findBrute("ABCABAB", "AB"));
	}

	@Test
	void testFindRabinKarp() {
		Matching m = new Matching();

		assertEquals(6, m.findRabinKarp("ABCABAB", "AB"));
	}

}
