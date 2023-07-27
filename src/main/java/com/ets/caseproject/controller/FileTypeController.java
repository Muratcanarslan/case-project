package com.ets.caseproject.controller;

import com.ets.caseproject.domain.dtos.FileTypeDto;
import com.ets.caseproject.domain.request.FileTypeRequest;
import com.ets.caseproject.service.FileTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file-type")
@CrossOrigin(origins = "*")
public class FileTypeController {

    private final FileTypeService fileTypeService;

    public FileTypeController(FileTypeService fileTypeService) {
        this.fileTypeService = fileTypeService;
    }

    @PostMapping
    public ResponseEntity<FileTypeDto> save(@RequestBody FileTypeRequest fileTypeRequest){
        return ResponseEntity.ok().body(this.fileTypeService.save(fileTypeRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<FileTypeDto> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.fileTypeService.findById(id));
    }
}
