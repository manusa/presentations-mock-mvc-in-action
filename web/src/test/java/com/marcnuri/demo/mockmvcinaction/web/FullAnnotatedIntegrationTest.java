/*
 * FullAnnotatedIntegrationTest.java
 *
 * Created on 2019-02-19, 12:50
 */
package com.marcnuri.demo.mockmvcinaction.web;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.marcnuri.demo.mockmvcinaction.web.beer.Beer;
import com.marcnuri.demo.mockmvcinaction.web.beer.BeerResource;
import com.marcnuri.demo.mockmvcinaction.web.beer.BeerService;
import com.marcnuri.demo.mockmvcinaction.web.beer.BeerType;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-19.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {BeerResource.class})
public class FullAnnotatedIntegrationTest {

  @MockBean
  private BeerService mockBeerService;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void getBeersAsJson() throws Exception {
    // Given
    final Beer beer = new Beer();
    beer.setId("Annotation Integration test");
    beer.setName("Integral");
    beer.setType(BeerType.IPA);
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
    result.andExpect(jsonPath("$[0].name", equalTo("Integral")));
    result.andExpect(jsonPath("$[0].lastModified", equalTo("2015-10-21T07:28:00")));
  }

}
