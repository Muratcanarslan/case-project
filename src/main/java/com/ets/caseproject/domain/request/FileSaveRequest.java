package com.ets.caseproject.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileSaveRequest {
    private String fileName;
    private String extension;
    private byte[] file;
}
