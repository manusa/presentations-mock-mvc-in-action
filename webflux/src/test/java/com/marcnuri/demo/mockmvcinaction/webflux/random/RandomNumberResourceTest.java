/*
 * RandomNumberResourceTest.java
 *
 * Created on 2019-02-19, 17:06
 */
package com.marcnuri.demo.mockmvcinaction.webflux.random;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-19.
 */
public class RandomNumberResourceTest {

  private RandomNumberService mockRandomNumberService;
  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockRandomNumberService = Mockito.mock(RandomNumberService.class);

    mockMvc = MockMvcBuilders
        .standaloneSetup(new RandomNumberResource(mockRandomNumberService))
        .build();
  }

  @After
  public void tearDown() {
    mockMvc = null;
  }

  @Test
  public void getRandomNumbers_shouldReturnOk() throws Exception {
    // Given
    doReturn(
        Flux.interval(Duration.ofMillis(100))
            .map(i ->1337D)
            .take(2)
    ).when(mockRandomNumberService).getRandomNumbers();

    // When
    final ResultActions result = mockMvc.perform(get("/random")
        .accept("text/event-stream"));

    // Then
    result.andDo(MvcResult::getAsyncResult);
    result.andExpect(status().isOk());
    result.andExpect(content().string(containsString("1337.0\n")));
  }
}
