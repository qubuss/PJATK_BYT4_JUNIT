package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals("SEK100", Integer.valueOf(10000), SEK100.getAmount());
		assertEquals("EUR10", Integer.valueOf(1000), EUR10.getAmount());
		assertEquals("SEK200", Integer.valueOf(20000), SEK200.getAmount());
		assertEquals("EUR20", Integer.valueOf(2000), EUR20.getAmount());
		assertEquals("SEK0", Integer.valueOf(0), SEK0.getAmount());
		assertEquals("EUR0", Integer.valueOf(0), EUR0.getAmount());
		assertEquals("SEKn100", Integer.valueOf(-10000), SEKn100.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertEquals("SEK100", SEK, SEK100.getCurrency());
		assertEquals("EUR10", EUR, EUR10.getCurrency());
		assertEquals("SEK200", SEK, SEK200.getCurrency());
		assertEquals("EUR20", EUR, EUR20.getCurrency());
		assertEquals("SEK0", SEK, SEK0.getCurrency());
		assertEquals("EUR0", EUR, EUR0.getCurrency());
		assertEquals("SEKn100", SEK, SEKn100.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("SEK100", "Money{amount=100.00, currency=" + "SEK" + '}', SEK100.toString());
		assertEquals("EUR10", "Money{amount=10.00, currency=" + "EUR" + '}', EUR10.toString());
		assertEquals("SEK200", "Money{amount=200.00, currency=" + "SEK" + '}', SEK200.toString());
		assertEquals("EUR20", "Money{amount=20.00, currency=" + "EUR" + '}', EUR20.toString());
		assertEquals("SEK0", "Money{amount=0.00, currency=" + "SEK" + '}', SEK0.toString());
		assertEquals("EUR0", "Money{amount=0.00, currency=" + "EUR" + '}', EUR0.toString());
		assertEquals("SEKn100", "Money{amount=-100.00, currency=" + "SEK" + '}', SEKn100.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals("SEK100", Integer.valueOf(1500), SEK100.universalValue());
		assertEquals("EUR10", Integer.valueOf(1500), EUR10.universalValue());
		assertEquals("SEK200", Integer.valueOf(3000), SEK200.universalValue());
		assertEquals("EUR20", Integer.valueOf(3000), EUR20.universalValue());
		assertEquals("SEK0", Integer.valueOf(0), SEK0.universalValue());
		assertEquals("EUR0", Integer.valueOf(0), EUR0.universalValue());
		assertEquals("SEKn100", Integer.valueOf(-1500), SEKn100.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		assertEquals("SEK100", true, SEK100.equals(SEK100));
		assertEquals("EUR10", true, EUR10.equals(EUR10));
		assertEquals("SEK200", false, SEK200.equals(EUR0));
		assertEquals("EUR20", false, EUR20.equals(SEK100));
		assertEquals("EUR10_v2", true, EUR10.equals(EUR10));
		assertEquals("SEK200_v2", false, SEK200.equals(EUR0));

	}

	@Test
	public void testAdd() {
		Money m1 = new Money(30000, SEK);
		Money m2 = new Money(20000, SEK);
		Money m3 = new Money(3000, EUR);
		Money m4 = new Money(1000, EUR);


		assertEquals("SEK100", m1, SEK100.add(EUR20));
		assertEquals("EUR10", m2, SEK100.add(EUR10));
		assertEquals("SEK200", m3, EUR10.add(EUR20));
		assertEquals("EUR20", m4, EUR0.add(SEK100));
	}

	@Test
	public void testSub() {
		Money m1 = new Money(-10000, SEK);
		Money m2 = new Money(0, SEK);
		Money m3 = new Money(-1000, EUR);
		Money m4 = new Money(-1000, EUR);


		assertEquals("SEK100", m1, SEK100.sub(EUR20));
		assertEquals("EUR10", m2, SEK100.sub(EUR10));
		assertEquals("SEK200", m3, EUR10.sub(EUR20));
		assertEquals("EUR20", m4, EUR0.sub(SEK100));
	}

	@Test
	public void testIsZero() {
		assertEquals("SEK100", false, SEK100.isZero());
		assertEquals("EUR10", false, EUR10.isZero());
		assertEquals("SEK200", true, SEK0.isZero());
		assertEquals("EUR20", true, SEK0.isZero());
	}

	@Test
	public void testNegate() {
		Money m1 = new Money(-10000, SEK);
		Money m2 = new Money(-1000, EUR);
		Money m3 = new Money(0, SEK);
		Money m4 = new Money(0, EUR);

		assertEquals("SEK100", m1, SEK100.negate());
		assertEquals("EUR10", m2, EUR10.negate());
		assertEquals("SEK200", m3, SEK0.negate());
		assertEquals("EUR20", m4, EUR0.negate());
	}

	@Test
	public void testCompareTo() {
		assertEquals("SEK100", -1, SEK100.compareTo(EUR20));
		assertEquals("EUR10", 0, SEK100.compareTo(EUR10));
		assertEquals("SEK200", -1, EUR10.compareTo(EUR20));
		assertEquals("EUR20", 1, SEK100.compareTo(SEK0));
	}
}
