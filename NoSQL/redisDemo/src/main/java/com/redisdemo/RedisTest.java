package com.redisdemo;

public class RedisTest {

    // Количество пользователей для регистрации
    private static final int RPS = 20;

    public static void main(String[] args) throws InterruptedException {

        RedisStorage redis = new RedisStorage();
        redis.init();

        for (int userId = 1; userId <= RPS; userId++) {
            redis.usersRegistration(userId);
            Thread.sleep(100);
        }
        while (true) {
            redis.getAllElements();
        }
    }
}