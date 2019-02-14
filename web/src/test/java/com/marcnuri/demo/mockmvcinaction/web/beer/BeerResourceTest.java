/*
 * BeerResourceTest.java
 *
 * Created on 2019-02-09, 8:37
 */
package com.marcnuri.demo.mockmvcinaction.web.beer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import com.marcnuri.demo.mockmvcinaction.web.exception.BusinessExceptionControllerAdvice;
import java.time.LocalDateTime;
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
//        .setMessageConverters(
//            new MappingJackson2HttpMessageConverter(Jackson2ObjectMapperBuilder.json()
//                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//                .modules(new JavaTimeModule()).build()),
//            new StringHttpMessageConverter())
        .setControllerAdvice(new BusinessExceptionControllerAdvice())
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
    beer.setId("I'm Invisible");
    beer.setName("La Östia");
    beer.setType(BeerType.KOLSCH);
    beer.setLastModified(LocalDateTime.of(2015, 10, 21, 7, 28));
    doReturn(Collections.singletonList(beer)).when(mockBeerService).getBeers();

    // When
    final ResultActions result = mockMvc.perform(
        get("/beers").accept(MediaType.APPLICATION_JSON)
    );

    // Then
    result.andExpect(status().isOk());
    result.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    result.andExpect(jsonPath("$").isArray());
    result.andExpect(jsonPath("$[0].id").doesNotExist());
    result.andExpect(jsonPath("$[0].name", equalTo("La Östia")));
  }

  @Test
  public void getBeersAsXML() throws Exception {
    // Given
    final Beer beer = new Beer();
    beer.setId("I'm Invisible");
    beer.setName("La Östia");
    beer.setType(BeerType.KOLSCH);
    doReturn(Collections.singletonList(beer)).when(mockBeerService).getBeers();

    // When
    final ResultActions result = mockMvc.perform(
        get("/beers").accept(MediaType.APPLICATION_XML)
    );

    // Then
    result.andExpect(status().isOk());
    result.andExpect(content().contentType(MediaType.valueOf("application/xml;charset=UTF-8")));
    result.andExpect(xpath("/List/item[1]/name").string(equalTo("La Östia")));
  }

  @Test
  public void insertBeer_validBeer_shouldReturnCreated() throws Exception {
    // Given
    doAnswer(a -> {
      final Beer b = a.getArgument(0);
      b.setId("1337");
      return b;
    }).when(mockBeerService).insertBeer(Mockito.any(Beer.class));

    // When
    final ResultActions result = mockMvc.perform(
        post("/beers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Östia\",\"externalId\":\"OST.1\",\"type\":\"KOLSCH\"}")
            .accept(MediaType.APPLICATION_JSON)
    );

    // Then
    result.andExpect(status().isCreated());
    result.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    result.andExpect(jsonPath("$.id").doesNotExist());
    result.andExpect(jsonPath("$.name", equalTo("Östia")));
    result.andExpect(jsonPath("$.externalId", equalTo("OST.1")));
    result.andExpect(jsonPath("$.type", equalTo("KOLSCH")));
  }

  @Test
  public void insertBeer_invalidBeer_shouldReturnBadRequest() throws Exception {
    // Given

    // When
    final ResultActions result = mockMvc.perform(
        post("/beers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Östia\",\"externalId\":\"OST.1\"}")
            .accept(MediaType.APPLICATION_JSON)
    );

    // Then
    result.andExpect(status().isBadRequest());
  }
}
