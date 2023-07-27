package com.ets.caseproject.service.impl;

import com.ets.caseproject.core.mapping.ModelMapperService;
import com.ets.caseproject.domain.FileType;
import com.ets.caseproject.domain.dtos.FileTypeDto;
import com.ets.caseproject.domain.request.FileTypeRequest;
import com.ets.caseproject.repository.FileTypeRepository;
import com.ets.caseproject.service.FileTypeService;
import org.springframework.stereotype.Service;

@Service
public class FileTypeServiceImpl implements FileTypeService {

    private final ModelMapperService modelMapperService;
    private final FileTypeRepository fileTypeRepository;

    public FileTypeServiceImpl(ModelMapperService modelMapperService, FileTypeRepository fileTypeRepository) {
        this.modelMapperService = modelMapperService;
        this.fileTypeRepository = fileTypeRepository;
    }

    @Override
    public FileTypeDto save(FileTypeRequest fileTypeRequest) {
        return this.modelMapperService.forDto().map(this.fileTypeRepository.save(this.modelMapperService.forRequest().map(fileTypeRequest,FileType.class)),FileTypeDto.class);
    }

    @Override
    public FileTypeDto findById(Long id) {
        return this.modelMapperService.forDto().map(this.fileTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("there is no file type for this id : "+id)),FileTypeDto.class);
    }

    @Override
    public FileType findByName(String name) {
        return this.fileTypeRepository.findByName(name).orElseThrow(() -> new RuntimeException("there is no file type for this name : "+ name));
    }
}
