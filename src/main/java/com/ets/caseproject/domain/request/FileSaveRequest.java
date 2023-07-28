package com.ets.caseproject.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileSaveRequest {
    @NotNull
    private String fileName;
    @NotNull
    private String extension;
    @NotNull
    private byte[] file;
}
