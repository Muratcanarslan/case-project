package com.ets.caseproject.controller;

import com.ets.caseproject.domain.dtos.FileDto;
import com.ets.caseproject.domain.request.FileSaveRequest;
import com.ets.caseproject.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*")
public class FileController {

    private final FileService fileService;


    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping("/upload")
    public ResponseEntity<FileDto> uploadFile(@RequestBody FileSaveRequest fileSaveRequest) throws IOException {
        return ResponseEntity.ok().body(this.fileService.saveFile(fileSaveRequest));
    }



}
