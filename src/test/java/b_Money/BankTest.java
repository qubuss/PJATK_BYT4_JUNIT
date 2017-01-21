package b_Money;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BankTest {
    Currency SEK, DKK;
    Bank SweBank, Nordea, DanskeBank;

    @Before
    public void setUp() throws Exception {
        DKK = new Currency("DKK", 0.20);
        SEK = new Currency("SEK", 0.15);
        SweBank = new Bank("SweBank", SEK);
        Nordea = new Bank("Nordea", SEK);
        DanskeBank = new Bank("DanskeBank", DKK);
        SweBank.openAccount("Ulrika");
        SweBank.openAccount("Bob");
        Nordea.openAccount("Bob");
        DanskeBank.openAccount("Gertrud");
    }

    @Test
    public void testGetName() {
        assertEquals("DKK", "DKK", DKK.getName());
        assertEquals("SEK", "SEK", SEK.getName());
        assertEquals("SweBank", "SweBank", SweBank.getName());
        assertEquals("Nordea", "Nordea", Nordea.getName());
        assertEquals("DanskeBank", "DanskeBank", DanskeBank.getName());

    }

    @Test
    public void testGetCurrency() {
        assertEquals("SweBank SEK", SEK, SweBank.getCurrency());
        assertEquals("Nordea SEK", SEK, Nordea.getCurrency());
        assertEquals("DanskeBank DKK", DKK, DanskeBank.getCurrency());
    }

    @Rule
    public ExpectedException thrown1 = ExpectedException.none();


    @Test
    public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
        //     try {
        // Try to open an account
        SweBank.openAccount("Test");
        // Try to deposit - If account is opened, we can do a deposit without getting exception
        SweBank.deposit("Test", new Money(100, SEK));
        //   } catch (AccountExistsException e) {

        //   }

    }

    @Test
    public void testDeposit() throws AccountDoesNotExistException {

        try {


            // Try to deposit to an account of a bank and check that we get the desired balance
            Nordea.deposit("Bob", new Money(100, SEK));
            assertEquals(100, Nordea.getBalance("Bob").intValue());

//            // Try to deposit money of a currency other than the banks currency
//            Integer testDepositMultipleCurrencies = new Money(100, SEK).add(new Money(100, DKK)).getAmount();
//            Nordea.deposit("Bob", new Money(100, DKK));
//            assertEquals(testDepositMultipleCurrencies.intValue(), Nordea.getBalance("Bob").intValue());
        } catch (AccountDoesNotExistException e) {
            fail("Nie działa");
        }
    }

    @Test
    public void testWithdraw() throws AccountDoesNotExistException {
        // Set up a balance of +100
        SweBank.deposit("Bob", new Money(100, SEK));

        // The expected value is 100 SEK - 100 DKK
        Integer testDepositMultipleCurrencies = new Money(100, SEK).sub(new Money(100, DKK)).getAmount();

        // Try to withdraw DKK from SEK bank. Check if the value is the expected
        SweBank.withdraw("Bob", new Money(100, DKK));
        assertEquals(testDepositMultipleCurrencies.intValue(), SweBank.getBalance("Bob").intValue());
    }

    @Test
    public void testGetBalance() throws AccountDoesNotExistException {
        // Deposit 100 SEK to bob
        Nordea.deposit("Bob", new Money(100, SEK));

        // We should have 100 as balance (SEK)
        assertEquals(100, Nordea.getBalance("Bob").intValue());
    }

    @Test
    public void testTransfer() throws AccountDoesNotExistException {
        // Get the current balances of two accounts
        int preTransferAmountBob = SweBank.getBalance("Bob");
        int preTransferAmountUlrika = SweBank.getBalance("Ulrika");

        System.out.println(preTransferAmountBob + " preTransferAmountBob");
        System.out.println(preTransferAmountUlrika + " preTransferAmountUlrika");
        // Transfer some money
        SweBank.transfer("Bob", "Ulrika", new Money(100, SEK));

        //TODO na dwóch bankach

        System.out.println(SweBank.getBalance("Bob") + " Bob");
        System.out.println(SweBank.getBalance("Ulrika") + " Ulrika");

        // Check that money has been withdrawn
        assertEquals(preTransferAmountBob - 100, SweBank.getBalance("Bob").intValue());
        // Check that deposit
        assertEquals(preTransferAmountUlrika + 100, SweBank.getBalance("Ulrika").intValue());

        int preTransferAmountBobSWE = SweBank.getBalance("Bob");
        int preTransferAmountBobNordea = Nordea.getBalance("Bob");

        System.out.println(preTransferAmountBobSWE + " preTransferAmountBobSWE");
        System.out.println(preTransferAmountBobNordea + " preTransferAmountBobNordea");

        SweBank.transfer("Bob", Nordea, "Bob", new Money(100, SEK));

        System.out.println(SweBank.getBalance("Bob") + " Bob");
        System.out.println(Nordea.getBalance("Bob") + " Bob Nordea");

        // Check that money has been withdrawn
        assertEquals(preTransferAmountBobSWE - 100, SweBank.getBalance("Bob").intValue());
        // Check that deposit
        assertEquals(preTransferAmountBobNordea + 100, Nordea.getBalance("Bob").intValue());
    }

    @Test
    public void testTimedPayment() throws AccountDoesNotExistException {
        // Get the current balances of two accounts
        int preTransferAmountBob = SweBank.getBalance("Bob");
        int preTransferAmountBobNordea = Nordea.getBalance("Bob");

        // Add a timed payment and tick
        SweBank.addTimedPayment("Bob", "Test", 1, 1, new Money(100, SEK), Nordea, "Bob");
        SweBank.tick();

        // Should be same amounts
        assertEquals(preTransferAmountBob, SweBank.getBalance("Bob").intValue());
        assertEquals(preTransferAmountBobNordea, Nordea.getBalance("Bob").intValue());

        SweBank.tick();

        // 100 withdrawn
        assertEquals(preTransferAmountBob - 100, SweBank.getBalance("Bob").intValue());
        // 100 deposit
        assertEquals(preTransferAmountBobNordea + 100, Nordea.getBalance("Bob").intValue());

        SweBank.tick();
        SweBank.tick();

        // 200 withdrawn
        assertEquals(preTransferAmountBob - 200, SweBank.getBalance("Bob").intValue());
        // 200 deposit
        assertEquals(preTransferAmountBobNordea + 200, Nordea.getBalance("Bob").intValue());

        // Remove the payment
        SweBank.removeTimedPayment("Bob", "Test");

        SweBank.tick();
        SweBank.tick();
        SweBank.tick();
        SweBank.tick();

        // Check that the amounts are the same as last time, so we know the payment has been removed
        assertEquals(preTransferAmountBob - 200, SweBank.getBalance("Bob").intValue());
        assertEquals(preTransferAmountBobNordea + 200, Nordea.getBalance("Bob").intValue());


        //TODO inne przypadki
    }

    @Test
    public void testTimedPayment2() throws AccountDoesNotExistException {
        // Get the current balances of two accounts
        int preTransferAmountBob = SweBank.getBalance("Bob");
        int preTransferAmountBobNordea = Nordea.getBalance("Bob");

        // Add a timed payment and tick
        SweBank.addTimedPayment("Bob", "Test", 0, 0, new Money(100, SEK), Nordea, "Bob");
        SweBank.tick();

        // Should be same amounts
        assertEquals(preTransferAmountBob-100, SweBank.getBalance("Bob").intValue());
        assertEquals(preTransferAmountBobNordea+100, Nordea.getBalance("Bob").intValue());

        SweBank.tick();

        // 100 withdrawn
        assertEquals(preTransferAmountBob - 200, SweBank.getBalance("Bob").intValue());
        // 100 deposit
        assertEquals(preTransferAmountBobNordea + 200, Nordea.getBalance("Bob").intValue());

        SweBank.tick();
        assertEquals(preTransferAmountBob - 300, SweBank.getBalance("Bob").intValue());
        // 100 deposit
        assertEquals(preTransferAmountBobNordea + 300, Nordea.getBalance("Bob").intValue());
        SweBank.tick();

        // 200 withdrawn
        assertEquals(preTransferAmountBob - 400, SweBank.getBalance("Bob").intValue());
        // 200 deposit
        assertEquals(preTransferAmountBobNordea + 400, Nordea.getBalance("Bob").intValue());

        // Remove the payment
        SweBank.removeTimedPayment("Bob", "Test");

        SweBank.tick();
        SweBank.tick();
        SweBank.tick();
        SweBank.tick();

        // Check that the amounts are the same as last time, so we know the payment has been removed
        assertEquals(preTransferAmountBob - 400, SweBank.getBalance("Bob").intValue());
        assertEquals(preTransferAmountBobNordea + 400, Nordea.getBalance("Bob").intValue());


        //TODO inne przypadki
    }

    @Test
    public void testTimedPayment3() throws AccountDoesNotExistException {
        // Get the current balances of two accounts
        int preTransferAmountBob = SweBank.getBalance("Bob");
        int preTransferAmountBobNordea = Nordea.getBalance("Bob");

        // Add a timed payment and tick
        SweBank.addTimedPayment("Bob", "Test", 1, 4, new Money(100, SEK), Nordea, "Bob");
        SweBank.tick();


        assertEquals(preTransferAmountBob, SweBank.getBalance("Bob").intValue());
        assertEquals(preTransferAmountBobNordea, Nordea.getBalance("Bob").intValue());

        SweBank.tick();


        assertEquals(preTransferAmountBob, SweBank.getBalance("Bob").intValue());

        assertEquals(preTransferAmountBobNordea, Nordea.getBalance("Bob").intValue());

        SweBank.tick();
        SweBank.tick();

        SweBank.tick();

        assertEquals(preTransferAmountBob - 100, SweBank.getBalance("Bob").intValue());

        assertEquals(preTransferAmountBobNordea + 100, Nordea.getBalance("Bob").intValue());

        SweBank.tick();

        // Remove the payment
        SweBank.removeTimedPayment("Bob", "Test");

        SweBank.tick();
        SweBank.tick();
        SweBank.tick();
        SweBank.tick();


        // Check that the amounts are the same as last time, so we know the payment has been removed
        assertEquals(preTransferAmountBob - 100, SweBank.getBalance("Bob").intValue());
        assertEquals(preTransferAmountBobNordea + 100, Nordea.getBalance("Bob").intValue());


    }
}
