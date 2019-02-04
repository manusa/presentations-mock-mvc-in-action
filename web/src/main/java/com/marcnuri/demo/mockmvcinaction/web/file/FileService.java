package com.marcnuri.demo.mockmvcinaction.web.file;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-04.
 */
@Service
public class FileService {

    private static final String TEMP_DIR_PROPERTY = "java.io.tmpdir";

    void saveFile(String fileName, InputStream fileInputStream) throws IOException {
        final String tempDir = System.getProperty(TEMP_DIR_PROPERTY);
        final File tempDirFile = Paths.get(tempDir).toFile();
        final File savedFile = new File(tempDirFile, fileName);
        Files.copy(fileInputStream, savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
