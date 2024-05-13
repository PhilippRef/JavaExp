import java.util.concurrent.atomic.AtomicLong;

public class Account implements Comparable<Account>{
    private final AtomicLong money;
    private final String accNumber;

    public Account(AtomicLong money, String accNumber) {
        this.money = money;
        this.accNumber = accNumber;
    }

    public long getMoney() {
        return money.get();
    }

    public String getAccNumber() {
        return accNumber;
    }

    public long incrementMoney(long money) {
        return this.money.addAndGet(money);
    }

    public long decrementMoney(long money) {
        return this.money.addAndGet(-money);
    }

    @Override
    public String toString() {
        return accNumber;
    }

    @Override
    public int compareTo(Account o) {
        return this.getAccNumber().compareTo(o.getAccNumber());
    }
}

