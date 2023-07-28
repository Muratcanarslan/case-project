package com.ets.caseproject.service;

import com.ets.caseproject.domain.FileEntity;
import com.ets.caseproject.domain.dtos.FileByteDto;
import com.ets.caseproject.domain.dtos.FileDto;
import com.ets.caseproject.domain.request.FileChangeRequest;
import com.ets.caseproject.domain.request.FileSaveRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileService {
    FileDto saveFile(FileSaveRequest fileSaveRequest) throws IOException;
    FileDto findById(Long id);

    FileDto updateFile(FileChangeRequest fileChangeRequest) throws IOException;

    void delete(Long id);

    FileEntity getById(Long id);

    FileByteDto getFileContent(Long id) throws IOException;
    List<FileDto> getAll();
}
