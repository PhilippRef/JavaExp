package com.redisdemo;

import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.Date;
import java.util.Random;

import static java.lang.System.out;

public class RedisStorage {

    // Объект для работы с Redis
    private RedissonClient redisson;

    // Объект для работы с ключами
    private RKeys rKeys;

    // Объект для работы с Sorted Set'ом
    private RScoredSortedSet<String> onlineUsers;

    private final static String KEY = "QUEUE_USERS";

    private int count = 1;

    private double getTs() {
        return new Date().getTime() / 1000;
    }

    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            out.println("Не удалось подключиться к Redis");
            out.println(Exc.getMessage());
        }
        rKeys = redisson.getKeys();
        onlineUsers = redisson.getScoredSortedSet(KEY);
        rKeys.delete(KEY);
    }

    void usersRegistration(int user_id) {
        //ZADD ONLINE_USERS
        onlineUsers.add(getTs(), String.valueOf(user_id));
    }

    void getAllElements() throws InterruptedException {
        Random random = new Random();
        if (count % 10 == 0) {
            String randomUser = String.valueOf(random.nextInt(onlineUsers.size()) + 1);
            out.println("Пользователь: " + randomUser + " оплатил платную услугу");
            out.println("На главной странице показываем пользователя: " + randomUser);
            onlineUsers.remove(randomUser);
            usersRegistration(Integer.parseInt(randomUser));
            Thread.sleep(1000);
        }
        String currentUser = onlineUsers.first();
        String log = String.format
                ("На главной странице показываем пользователя: "
                        + currentUser);
        out.println(log);
        onlineUsers.remove(currentUser);
        usersRegistration(Integer.parseInt(currentUser));
        count++;
    }
}