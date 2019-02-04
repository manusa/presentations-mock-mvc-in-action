package com.marcnuri.demo.mockmvcinaction.web.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-04.
 */
@Controller
@RequestMapping("/files")
public class FileResource {

    private final FileService fileService;

    @Autowired
    public FileResource(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<Void> insertFile(MultipartHttpServletRequest request) throws IOException {
        final MultipartFile multipartFile = request.getFile(request.getFileNames().next());
        fileService.saveFile(multipartFile.getName(), multipartFile.getInputStream());
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> ioException(IOException ioException) {
        return ResponseEntity.badRequest()
                .header("Warning", "199 FileResource \"IOException\"")
                .body(ioException.getMessage());
    }

}
