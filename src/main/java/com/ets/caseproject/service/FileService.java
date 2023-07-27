package com.ets.caseproject.service;

import com.ets.caseproject.domain.dtos.FileDto;
import com.ets.caseproject.domain.request.FileSaveRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    FileDto saveFile(FileSaveRequest fileSaveRequest) throws IOException;
}
