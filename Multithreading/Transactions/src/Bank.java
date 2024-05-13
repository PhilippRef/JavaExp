import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Bank {

    private ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private boolean isBlocked = false;
    private int count;
    private Account fromAccount;
    private Account toAccount;


    public ConcurrentHashMap<String, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ConcurrentHashMap<String, Account> accounts) {
        this.accounts = accounts;
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount) {

        fromAccount = accounts.get(fromAccountNum);
        toAccount = accounts.get(toAccountNum);

        Account lowSync;
        Account highSync;

        int fromHash = System.identityHashCode(fromAccount);
        int toHash = System.identityHashCode(toAccount);
        if (fromHash < toHash) {
            lowSync = fromAccount;
            highSync = toAccount;
        } else if (fromHash > toHash) {
            lowSync = toAccount;
            highSync = fromAccount;
        } else {
            return;
        }
        
        synchronized (lowSync) {
            synchronized (highSync) {
                System.out.println("Попытка перевода денежных средств с аккаунта: " + fromAccountNum
                        + " на аккаунт: " + toAccountNum + " в размере: " + amount + " руб.");
                if (amount < 0) {
                    System.out.println("Введите сумму больше 0");
                    return;
                }
                if (fromAccount.getMoney() < amount) {
                    System.out.println("На счете " + fromAccountNum + " недостаточно средств для перевода");
                    return;
                }
                if (amount > 50000) {
                    try {
                        if (isFraud(fromAccountNum, toAccountNum, amount)) {
                            System.out.println("Подозрительная операция. Cчета " + fromAccountNum
                                    + " " + toAccountNum + " заблокированы!");
                            isBlocked = true;
                            return;
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                fromAccount.decrementMoney(amount);
                System.out.println("Сумма в размере: " + amount + " списана у аккаунта: " +
                        fromAccountNum);
                toAccount.incrementMoney(amount);
                System.out.println("Сумма в размере: " + amount + " зачислена аккаунту: " +
                        toAccountNum);
            }
        }
    }

    public long getBalance(String accountNum) {
        System.out.println("Баланс лицевого счета: " + accountNum + " - " +
                accounts.get(accountNum).getMoney() + " руб.");
        return accounts.get(accountNum).getMoney();
    }

    public long getSumAllAccounts() {
        long sum = 0;
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            sum += entry.getValue().getMoney();
        }
        System.out.println("Баланс банка: " + sum + " руб.");
        return sum;
    }
}
