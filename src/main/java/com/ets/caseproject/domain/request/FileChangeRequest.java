package com.ets.caseproject.domain.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileChangeRequest {
    @NotNull
    private Long id;
    @NotNull
    private String fileName;
    @NotNull
    private String extension;
    @NotNull
    private byte[] file;
}
