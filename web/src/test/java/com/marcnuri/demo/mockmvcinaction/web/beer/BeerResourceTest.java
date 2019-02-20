/*
 * BeerResourceTest.java
 *
 * Created on 2019-02-09, 8:37
 */
package com.marcnuri.demo.mockmvcinaction.web.beer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import com.marcnuri.demo.mockmvcinaction.web.exception.BusinessExceptionControllerAdvice;
import com.marcnuri.demo.mockmvcinaction.web.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
            .content("{\"name\":\"Östia\",\"externalId\":\"OST-1\",\"type\":\"KOLSCH\"}")
            .accept(MediaType.APPLICATION_JSON)
    );

    // Then
    result.andExpect(status().isCreated());
    result.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    result.andExpect(jsonPath("$.id").doesNotExist());
    result.andExpect(jsonPath("$.name", equalTo("Östia")));
    result.andExpect(jsonPath("$.externalId", equalTo("OST-1")));
    result.andExpect(jsonPath("$.type", equalTo("KOLSCH")));
  }

  @Test
  public void insertBeer_invalidBeer_shouldReturnBadRequest() throws Exception {
    // Given

    // When
    final ResultActions result = mockMvc.perform(
        post("/beers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Östia\",\"externalId\":\"OST-1\"}")
            .accept(MediaType.APPLICATION_JSON)
    );

    // Then
    result.andExpect(status().isBadRequest());
  }

  @Test
  public void getBeer_existingExternalId_shouldReturnOk() throws Exception {
    // Given
    final Beer beer = new Beer();
    beer.setId("I'm Invisible");
    beer.setName("Snow");
    beer.setType(BeerType.LAGER);
    doReturn(beer).when(mockBeerService).getBeer(Mockito.eq("SNW-01"));

    // When
    final ResultActions result = mockMvc.perform(
        get("/beers/SNW-01")
            .accept(MediaType.APPLICATION_JSON)
    );

    // Then
    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.id").doesNotExist());
    result.andExpect(jsonPath("$.name", equalTo("Snow")));
    result.andExpect(jsonPath("$.type", equalTo("LAGER")));
  }

  @Test
  public void getBeer_nonExistingExternalId_shouldReturnNotFound() throws Exception {
    // Given
    doThrow(new NotFoundException("Beer not found")).when(mockBeerService).getBeer(Mockito.eq("SNW-01"));

    // When
    final ResultActions result = mockMvc.perform(
        get("/beers/SNW-01")
            .accept(MediaType.APPLICATION_JSON)
    );

    // Then
    result.andExpect(status().isNotFound());
    result.andExpect(content().string("Beer not found"));
  }

  @Test
  // Corner Case - Use regex or configuration
  @Ignore
  public void getBeer_cornerCaseExternalId_shouldReturnOk() throws Exception {
    // Given
    final Beer beer = new Beer();
    beer.setId("I'm Invisible");
    beer.setName("Amstel");
    beer.setType(BeerType.LAGER);
    doReturn(beer).when(mockBeerService).getBeer(Mockito.eq("AMST.1.01"));

    // When
    final ResultActions result = mockMvc.perform(
        get("/beers/AMST.1.01")
            .accept(MediaType.APPLICATION_JSON)
    );

    // Then
    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.id").doesNotExist());
    result.andExpect(jsonPath("$.name", equalTo("Amstel")));
    result.andExpect(jsonPath("$.type", equalTo("LAGER")));
  }

  @Test
  public void updateBeer_validBeer_shouldReturnOk() throws Exception {
    // Given
    doAnswer(a -> a.getArgument(1))
        .when(mockBeerService).updateBeer(Mockito.eq("OST-1"), Mockito.any(Beer.class));

    // When
    final ResultActions result = mockMvc.perform(
        put("/beers/OST-1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Östia\",\"externalId\":\"OST-1\",\"type\":\"KOLSCH\"}")
            .accept(MediaType.APPLICATION_JSON)
    );

    // Then
    result.andExpect(status().isOk());
    result.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    result.andExpect(jsonPath("$.id").doesNotExist());
    result.andExpect(jsonPath("$.name", equalTo("Östia")));
    result.andExpect(jsonPath("$.externalId", equalTo("OST-1")));
    result.andExpect(jsonPath("$.type", equalTo("KOLSCH")));
  }

  @Test
  public void deleteBeer_validBeer_shouldReturnNoContent() throws Exception {
    // When
    final ResultActions result = mockMvc.perform(
        delete("/beers/OST-1")
    );

    // Then
    result.andExpect(status().isNoContent());
    verify(mockBeerService, times(1))
        .removeBeer(Mockito.eq("OST-1"));
  }

  @Test
  public void updateBeerAsXml_validBeer_shouldReturnOk() throws Exception {
    // Given
    doAnswer(a -> a.getArgument(1))
        .when(mockBeerService).updateBeer(Mockito.eq("OST-1"), Mockito.any(Beer.class));

    // When
    final ResultActions result = mockMvc.perform(
        put("/beers/OST-1")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Special-Header", "XML Babel")
            .content("{\"name\":\"Östia\",\"externalId\":\"OST-1\",\"type\":\"KOLSCH\"}")
            .accept(MediaType.APPLICATION_XML_VALUE)
    );

    // Then
    result.andExpect(status().isOk());
    result.andExpect(content().contentType(MediaType.valueOf("application/xml;charset=UTF-8")));
    result.andExpect(xpath("/Beer/name").string(equalTo("Östia")));
    result.andExpect(xpath("/Beer/externalId").string(equalTo("OST-1")));
    result.andExpect(xpath("/Beer/type").string(equalTo("KOLSCH")));
  }
}
