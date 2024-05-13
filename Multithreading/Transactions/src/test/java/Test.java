import junit.framework.TestCase;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Test extends TestCase {
    Bank bank = new Bank();
    ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();
    String accName1;
    String accName2;
    String accNumber1;
    String accNumber2;
    AtomicLong accName1Money;
    AtomicLong accName2Money;


    @Override
    protected void setUp() throws Exception {
        accName1 = "Acc1";
        accName2 = "Acc2";

        accNumber1 = "USER_1";
        accNumber2 = "USER_2";

        accName1Money = new AtomicLong(15000);
        accName2Money = new AtomicLong(10000);


        accounts.put(accName1, new Account(accName1Money, accNumber1));
        accounts.put(accName2, new Account(accName2Money, accNumber2));

        bank.setAccounts(accounts);
    }

    public void testTransferNotEnoughMoney() {
        AtomicLong actual = accName1Money;
        bank.transfer(accName1, accName2, 60000);
        AtomicLong expected = accName1Money;
        assertEquals(expected, actual);
    }

    public void testTransferUser1ToUser2() {
        AtomicLong actual = accName1Money;
        bank.transfer(accName1, accName2, 7000);
        int expected = 8000;
        assertEquals(expected, actual.get());
    }

    public void testTransferUser2toUser1() {
        AtomicLong actual = accName2Money;
        bank.transfer(accName2, accName1, 8000);
        int expected = 2000;
        assertEquals(expected, actual.get());

    }

    public void testTransferNegative() {
        AtomicLong actual = accName1Money;
        bank.transfer(accName1, accName2, -7000);
        int expected = 15000;
        assertEquals(expected, actual.get());
    }

    public void testGetBalance() {
        long actual = bank.getBalance(accName1);
        AtomicLong expected = accName1Money;
        assertEquals(expected.get(), actual);
    }

    public void testGetSumAllAccounts() {
        long actual = bank.getSumAllAccounts();
        long expected = 25000;
        assertEquals(expected, actual);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}

