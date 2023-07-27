package com.ets.caseproject.service.impl;

import com.ets.caseproject.core.mapping.ModelMapperService;
import com.ets.caseproject.domain.FileEntity;
import com.ets.caseproject.domain.FileType;
import com.ets.caseproject.domain.dtos.FileDto;
import com.ets.caseproject.domain.request.FileSaveRequest;
import com.ets.caseproject.repository.FileRepository;
import com.ets.caseproject.service.FileService;
import com.ets.caseproject.service.FileTypeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final ModelMapperService modelMapperService;
    private final FileRepository fileRepository;

    private final FileTypeService fileTypeService;

    @Value("${file.default.path}")
    private String defaultPath;

    @Value("${file.size}")
    private String size;

    public FileServiceImpl(ModelMapperService modelMapperService, FileRepository fileRepository, FileTypeService fileTypeService) {
        this.modelMapperService = modelMapperService;
        this.fileRepository = fileRepository;
        this.fileTypeService = fileTypeService;
    }


    @Override
    public FileDto saveFile(FileSaveRequest fileSaveRequest) throws IOException {
        FileType fileType = this.fileTypeService.findByName(fileSaveRequest.getExtension());
        String size = this.checkFileSize(fileSaveRequest.getFile());
        String fileName = this.generateFileName(fileSaveRequest.getFileName(),fileType.getName());
        String filePath = this.defaultPath+ "\\" + fileName;
        File file = new File(filePath);
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(fileSaveRequest.getFile());
        fileOutputStream.close();
        return this.modelMapperService.forDto().map(this.fileRepository.save(this.mapFileSaveRequestToFileEntity(fileType.getId(),filePath,fileName,size)),FileDto.class);
    }

    private String checkFileSize(byte[] file) {
        double size = file.length / (1024.0*1024.0);
        if(size > 5.0){
            throw new RuntimeException("File Size can be maximum 5 MB!");
        }
        return String.valueOf(size);
    }

    private FileEntity mapFileSaveRequestToFileEntity(Long fileTypeId,String filePath,String fileName,String size){
        FileEntity file = new FileEntity();
        file.setFilePath(filePath);
        file.setFileType(new FileType(fileTypeId,null));
        file.setFileName(fileName);
        file.setFileSize(size);
        return file;
    }

    private String generateFileName(String fileName,String fileType){
        return fileName+ UUID.randomUUID().toString() +"."+fileType;
    }
}
