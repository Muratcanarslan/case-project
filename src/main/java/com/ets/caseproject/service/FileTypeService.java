package com.ets.caseproject.service;

import com.ets.caseproject.domain.FileType;
import com.ets.caseproject.domain.dtos.FileTypeDto;
import com.ets.caseproject.domain.request.FileTypeRequest;

public interface FileTypeService {
    FileTypeDto save(FileTypeRequest fileTypeRequest);
    FileTypeDto findById(Long id);
    FileType findByName(String name);
}
