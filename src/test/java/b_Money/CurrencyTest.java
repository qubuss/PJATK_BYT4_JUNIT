package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CurrencyTest {

    Currency SEK, DKK, NOK, EUR;

    @Before
    public void setUp() throws Exception {
        /* Setup currencies with exchange rates */
        SEK = new Currency("SEK", 0.15);
        DKK = new Currency("DKK", 0.20);
        EUR = new Currency("EUR", 1.5);
    }

    @Test
    public void testGetName() {
        assertEquals("SEK", "SEK", SEK.getName());
        assertEquals("DKK", "DKK", DKK.getName());
        assertEquals("EUR", "EUR", EUR.getName());
    }

    @Test
    public void testGetRate() {
        assertEquals("SEK", Double.valueOf(0.15), SEK.getRate());
        assertEquals("DKK", Double.valueOf(0.20), DKK.getRate());
        assertEquals("EUR", Double.valueOf(1.5), EUR.getRate());
    }

    @Test
    public void testSetRate() {
        Double newRateSEK = 1.0;
        Double newRateDKK = 2.0;
        Double newRateEUR = 3.0;

        SEK.setRate(newRateSEK);
        DKK.setRate(newRateDKK);
        EUR.setRate(newRateEUR);

        assertEquals("SEK", Double.valueOf(1.0), SEK.getRate());
        assertEquals("DKK", Double.valueOf(2.0), DKK.getRate());
        assertEquals("EUR", Double.valueOf(3.0), EUR.getRate());
    }

    @Test
    public void testGlobalValue() {
        assertEquals("SEK", Integer.valueOf(15), SEK.universalValue(100));
        assertEquals("DKK", Integer.valueOf(20), DKK.universalValue(100));
        assertEquals("EUR", Integer.valueOf(150), EUR.universalValue(100));
    }

    @Test
    public void testValueInThisCurrency() {
        assertEquals("SEK", Integer.valueOf(6), SEK.valueInThisCurrency(1, EUR));
        assertEquals("DKK", Integer.valueOf(75), DKK.valueInThisCurrency(100, SEK));
        assertEquals("EUR", Integer.valueOf(13), EUR.valueInThisCurrency(100, DKK));
    }


}
