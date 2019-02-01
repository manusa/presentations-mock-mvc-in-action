package com.marcnuri.demo.mockmvcinaction.web.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by marc on 2019-02-01.
 */
public class SpaResourceTest {

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new SpaResource())
                .build();
    }

    @After
    public void tearDown() {
        mockMvc = null;
    }

    @Test
    public void forwardToIndex_validUrlAndHeaders_shouldReturnOk() throws Exception {
        // Given

        // When
        final ResultActions result = mockMvc.perform(get("/")
                        .accept(MimeTypeUtils.TEXT_HTML_VALUE));

        // Then
        result.andExpect(status().isOk()).andExpect(forwardedUrl("/index.html"));
    }

}
