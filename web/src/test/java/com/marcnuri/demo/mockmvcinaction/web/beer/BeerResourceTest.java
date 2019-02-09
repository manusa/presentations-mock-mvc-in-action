/*
 * BeerResourceTest.java
 *
 * Created on 2019-02-09, 8:37
 */
package com.marcnuri.demo.mockmvcinaction.web.beer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-09.
 */
public class BeerResourceTest {

  private BeerService mockBeerService;
  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockBeerService = Mockito.mock(BeerService.class);
    mockMvc = MockMvcBuilders
        .standaloneSetup(new BeerResource(mockBeerService))
        .build();
  }

  @After
  public void tearDown() {
    mockMvc = null;
    mockBeerService = null;
  }

  @Test
  public void getBeersAsJson() throws Exception {
    // Given
    final Beer beer = new Beer();
    beer.setName("La Östia");
    beer.setType(BeerType.KOLSCH);
    doReturn(Collections.singletonList(beer)).when(mockBeerService).getBeers();

    // When
    final ResultActions result = mockMvc.perform(
        get("/beers").accept(MediaType.APPLICATION_JSON)
    );

    // Then
    result.andExpect(status().isOk());
    result.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    result.andExpect(jsonPath("$").isArray());
    result.andExpect(jsonPath("$[0].name", equalTo("La Östia")));
  }
}
