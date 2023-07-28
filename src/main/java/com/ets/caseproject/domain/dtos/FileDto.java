package com.ets.caseproject.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileDto {

    private Long id;
    private String fileName;
    private String path;
    private String size;
}
