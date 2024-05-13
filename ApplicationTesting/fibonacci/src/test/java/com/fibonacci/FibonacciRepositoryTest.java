package com.fibonacci;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FibonacciRepositoryTest extends PostgresTestContainerInitializer {

    @Autowired
    private FibonacciRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    private FibonacciNumber fibonacciNumber;

    @BeforeAll
    public static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    public static void afterAll() {
        postgres.stop();
    }

    @AfterEach
    public void clearDB() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Test save and find Fibonacci number from DB")
    public void findFibonacciNumberFromDB() {
        fillingDB();

        List<FibonacciNumber> actual = jdbcTemplate.query(
                "SELECT * FROM fibonacci_number WHERE index = 3",
                (rs, rowNum) -> new FibonacciNumber(rs.getInt("index"),
                        rs.getInt("value"))
        );

        assertEquals(fibonacciNumber.getValue(), actual.get(0).getValue());
    }

    @Test
    @DisplayName("Test put same Fibonacci numbers to DB")
    public void putTheSameFibonacciNumbersToDB() {
        int expectedSize = 1;

        fillingDB();
        fillingDB();

        List<FibonacciNumber> actual = jdbcTemplate.query(
                "SELECT * FROM fibonacci_number WHERE index = 3",
                (rs, rowNum) -> new FibonacciNumber(rs.getInt("index"),
                        rs.getInt("value"))
        );

        assertEquals(fibonacciNumber.getValue(), actual.get(0).getValue());
        assertEquals(fibonacciNumber.getIndex(), actual.get(0).getIndex());
        assertEquals(expectedSize, actual.size());
    }

    private void fillingDB() {
        fibonacciNumber = new FibonacciNumber(3, 2);
        repository.save(fibonacciNumber);
        entityManager.flush();
        entityManager.detach(fibonacciNumber);
    }
}
