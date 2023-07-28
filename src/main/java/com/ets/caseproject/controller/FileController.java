package com.ets.caseproject.controller;

import com.ets.caseproject.domain.dtos.FileByteDto;
import com.ets.caseproject.domain.dtos.FileDto;
import com.ets.caseproject.domain.request.FileChangeRequest;
import com.ets.caseproject.domain.request.FileSaveRequest;
import com.ets.caseproject.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*")
public class FileController {

    private final FileService fileService;



    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping("/upload")
    public ResponseEntity<FileDto> saveFile(@RequestBody FileSaveRequest fileSaveRequest) throws IOException {
        return ResponseEntity.ok().body(this.fileService.saveFile(fileSaveRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDto> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.fileService.findById(id));
    }

    @PutMapping
    public ResponseEntity<FileDto> update(@RequestBody FileChangeRequest fileChangeRequest) throws IOException {
        return ResponseEntity.ok().body(this.fileService.updateFile(fileChangeRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        this.fileService.delete(id);
        return ResponseEntity.ok().body("Successfuly deleted!");
    }

    @GetMapping("/getBytes/{id}")
    public ResponseEntity<FileByteDto> getBytes(@PathVariable Long id) throws IOException {
        return ResponseEntity.ok().body(this.fileService.getFileContent(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FileDto>> getAll(){
        return ResponseEntity.ok().body(this.fileService.getAll());
    }

}
