package com.fibonacci;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FibonacciControllerTest extends PostgresTestContainerInitializer {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FibonacciRepository repository = Mockito.mock(FibonacciRepository.class);

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
    @DisplayName("Test index more one")
    public void testIndexMoreOne() throws Exception {
        int index = 3;

        mockMvc.perform(get("/fibonacci/{index}", index))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @DisplayName("Test index less one")
    public void testIndexLessOne() throws Exception {
        int index = 0;

        mockMvc.perform(get("/fibonacci/{index}", index))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andDo(print());
    }
}
