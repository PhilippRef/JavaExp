import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    private static final String USER_ONE = "User_1";
    private static final String USER_TWO = "User_2";
    private static final String ACC_ONE = "Acc_1";
    private static final String ACC_TWO = "Acc_2";
    private static final Random random = new Random();
    private static final int MAX_INIT = 100000;
    private static final int MAX_TRANSFER = 100000;
    private static final int MIN_INIT = 0;

    public static void main(String[] args) {

        Bank bank = new Bank();
        ConcurrentHashMap<String, Account> accountMap = new ConcurrentHashMap<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            accountMap.put(ACC_ONE, new Account(new AtomicLong(random.nextInt
                    (MAX_INIT - MIN_INIT) + MIN_INIT), USER_ONE));
            accountMap.put(ACC_TWO, new Account(new AtomicLong(random.nextInt
                    (MAX_INIT - MIN_INIT) + MIN_INIT), USER_TWO));
            bank.setAccounts(accountMap);
            bank.getBalance(ACC_ONE);
            bank.getBalance(ACC_TWO);
            bank.getSumAllAccounts();
        }
        for (int y = 0; y < 10000; y++) {
            threads.add(new Thread(() -> {
                bank.transfer(ACC_ONE, ACC_TWO, random.nextInt
                        (MAX_TRANSFER - MIN_INIT) + MIN_INIT);
                bank.getBalance(ACC_ONE);
                bank.getBalance(ACC_TWO);
                bank.getSumAllAccounts();
                bank.transfer(ACC_TWO, ACC_ONE, random.nextInt
                        (MAX_TRANSFER - MIN_INIT) + MIN_INIT);
                bank.getBalance(ACC_ONE);
                bank.getBalance(ACC_TWO);
                bank.getSumAllAccounts();
                System.out.println("------------");
            }));
        }
        threads.forEach(s -> s.start());
    }
}


