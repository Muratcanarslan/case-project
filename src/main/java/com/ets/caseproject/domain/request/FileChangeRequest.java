package com.ets.caseproject.domain.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileChangeRequest {
    private Long id;
    private String fileName;
    private String extension;
    private byte[] file;
}
