package com.marcnuri.demo.mockmvcinaction.web;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.marcnuri.demo.mockmvcinaction.web.beer.Beer;
import com.marcnuri.demo.mockmvcinaction.web.beer.BeerService;
import com.marcnuri.demo.mockmvcinaction.web.beer.BeerType;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-01.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FullIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private BeerService mockBeerService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @After
    public void tearDown() {
        mockMvc = null;
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void forwardToIndex_validUrlAndHeaders_shouldReturnOkAndRedirectToContent() throws Exception {
        // Given
        // Real application context

        // When
        final ResultActions result = mockMvc.perform(get("/")
                .accept(MimeTypeUtils.TEXT_HTML_VALUE));

        // Then
        result.andExpect(status().isOk()).andExpect(forwardedUrl("/index.html"));

        final ResultActions redirectResult = mockMvc.perform(
                get(result.andReturn().getResponse().getForwardedUrl())
                    .accept(MimeTypeUtils.TEXT_HTML_VALUE));

        redirectResult.andExpect(content().string(containsString("MockMVC in Action!")));
    }

    @Test
    public void getBeersAsJson() throws Exception {
        // Given
        final Beer beer = new Beer();
        beer.setId("Integration test");
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
