package org.example;

public class Friend implements Comparable<Friend> {
    private final String name;

    public Friend(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void throwBallTo(Friend catcher) {
        System.out.format("%s: %s кинул мне мяч!%n", catcher.getName(), this.name);
        synchronized (compareTo(catcher) > 0 ? catcher : this) { //если cather > 0, блокируем по catcher
            //в ином случае блокируем по объекту this
            catcher.throwBallTo(this);
        }
    }

    @Override
    public int compareTo(Friend o) {
        return this.getName().compareTo(o.getName());
    }
}
