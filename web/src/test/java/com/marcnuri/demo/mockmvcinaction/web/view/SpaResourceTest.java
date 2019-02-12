package com.marcnuri.demo.mockmvcinaction.web.view;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-01.
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
        result.andExpect(status().isOk());
        result.andExpect(forwardedUrl("/index.html"));
    }

    @Test
    public void forwardToIndex_validUrlHierarchyAndHeaders_shouldReturnOk() throws Exception {
        // Given

        // When
        final ResultActions result = mockMvc.perform(get("/this/is/a/front-end/route")
                .accept(MimeTypeUtils.TEXT_HTML_VALUE));

        // Then
        result.andExpect(status().isOk());
        result.andExpect(forwardedUrl("/index.html"));
    }

    @Test
    public void forwardToIndex_validUrlAndInvalidHeaders_shouldReturnNotAcceptable() throws Exception {
        // Given

        // When
        final ResultActions result = mockMvc.perform(get("/")
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        // Then
        result.andExpect(status().isNotAcceptable());
    }
}
