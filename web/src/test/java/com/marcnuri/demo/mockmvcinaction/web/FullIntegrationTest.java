package com.marcnuri.demo.mockmvcinaction.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by marc on 2019-02-01.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FullIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

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
    public void forwardToIndex_validUrlAndHeaders_shouldReturnOkAndRedirectToContent() throws Exception {
        // Given

        // When
        final ResultActions result = mockMvc.perform(get("/")
                .accept(MimeTypeUtils.TEXT_HTML_VALUE));

        // Then
        result.andExpect(status().isOk()).andExpect(forwardedUrl("/index.html"));

        final ResultActions redirectResult = mockMvc.perform(
                get(result.andReturn().getResponse().getForwardedUrl()));

        redirectResult.andExpect(content().string(containsString("MockMVC in Action!")));
    }
}
