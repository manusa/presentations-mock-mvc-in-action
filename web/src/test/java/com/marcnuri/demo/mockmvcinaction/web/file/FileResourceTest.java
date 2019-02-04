package com.marcnuri.demo.mockmvcinaction.web.file;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-04.
 */
public class FileResourceTest {

    private FileService mockFileService;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockFileService = Mockito.mock(FileService.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new FileResource(mockFileService))
                .build();
    }

    @After
    public void tearDown() {
        mockMvc = null;
        mockFileService = null;
    }

    @Test
    public void insertFile_validMultipart_shouldReturnNoContent() throws Exception {
        // Given
        doNothing().when(mockFileService).saveFile(Mockito.eq("x-files.xml"), Mockito.any());

        // When
        final ResultActions result = mockMvc.perform(
                multipart("/files")
                        .file("x-files.xml", "<file></file>".getBytes())
        );

        // Then
        result.andExpect(status().isNoContent());
    }


    @Test
    public void insertFile_throwsException_shouldReturnBadRequest() throws Exception {
        // Given
        doThrow(new IOException("Not so exceptional"))
                .when(mockFileService).saveFile(Mockito.eq("exceptional.file"), Mockito.any());

        // When
        final ResultActions result = mockMvc.perform(
                multipart("/files")
                        .file("exceptional.file", "<file></file>".getBytes())
        );

        // Then
        result.andExpect(status().isBadRequest());
        result.andExpect(header().exists("Warning"));
        result.andExpect(content().string("Not so exceptional"));
    }
}
