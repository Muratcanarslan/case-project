package com.ets.caseproject.service.impl;

import com.ets.caseproject.core.mapping.ModelMapperService;
import com.ets.caseproject.domain.FileEntity;
import com.ets.caseproject.domain.FileType;
import com.ets.caseproject.domain.dtos.FileByteDto;
import com.ets.caseproject.domain.dtos.FileDto;
import com.ets.caseproject.domain.request.FileChangeRequest;
import com.ets.caseproject.domain.request.FileSaveRequest;
import com.ets.caseproject.repository.FileRepository;
import com.ets.caseproject.service.FileService;
import com.ets.caseproject.service.FileTypeService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        String fileName = this.generateFileName(fileSaveRequest.getFileName(), fileType.getName());
        String filePath = this.defaultPath + "\\" + fileName;
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            Files.createDirectory(Path.of(file.getParent()));
        }
        boolean fileCreateResult = file.createNewFile();
        if(fileCreateResult) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileSaveRequest.getFile());
            fileOutputStream.close();
            return this.modelMapperService.forDto().map(this.fileRepository.save(this.mapFileSaveRequestToFileEntity(fileType.getId(), filePath, fileSaveRequest.getFileName(), size)), FileDto.class);
        }
        throw new RuntimeException("Error on saving file to system!");
    }

    @Override
    public FileDto findById(Long id) {
        return this.modelMapperService.forDto().map(this.fileRepository.findById(id).orElseThrow(() -> new RuntimeException("There is no file for this id : " + id)), FileDto.class);
    }


    @Override
    public FileDto updateFile(FileChangeRequest fileChangeRequest) throws IOException {
        FileEntity fileEntity = this.getById(fileChangeRequest.getId());
        String size = this.checkFileSize(fileChangeRequest.getFile());
        FileType fileType = this.fileTypeService.findByName(fileChangeRequest.getExtension());
        String fileName = this.generateFileName(fileChangeRequest.getFileName(), fileType.getName());
        String filePath = this.defaultPath + "\\" + fileName;
        File file = new File(fileEntity.getFilePath());
        if (file.exists()) {
            file.delete();
        }
        boolean fileCreateResult =  file.createNewFile();
        if(fileCreateResult) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileChangeRequest.getFile());
            fileOutputStream.close();
            return this.modelMapperService.forDto().map(this.fileRepository.save(this.mapFileChangeRequestToFileEntity(fileEntity.getId(),fileType.getId(), filePath, fileChangeRequest.getFileName(), size)), FileDto.class);
        }
        throw new RuntimeException("Error on saving file to system!");
    }

    @Override
    public void delete(Long id) {
        FileEntity fileEntity = this.getById(id);
        File file = new File(fileEntity.getFilePath());
        if(file.exists()){
            file.delete();
        }
        this.fileRepository.deleteById(id);
    }

    @Override
    public FileEntity getById(Long id) {
        return this.fileRepository.findById(id).orElseThrow(()->new RuntimeException("There is no file for this id : "+id));
    }

    @Override
    public FileByteDto getFileContent(Long id) throws IOException {
        FileEntity fileEntity = this.getById(id);
        File file = new File(fileEntity.getFilePath());
        if(!file.exists()){
            throw new RuntimeException("File does not exists by this id: "+id);
        }
        return new FileByteDto(FileUtil.readAsByteArray(file));
    }

    @Override
    public List<FileDto> getAll() {
        return this.fileRepository.findAll().stream().map(fileEntity -> this.modelMapperService.forDto().map(fileEntity,FileDto.class)).collect(Collectors.toList());
    }

    private String checkFileSize(byte[] file) {
        double size = file.length / (1024.0*1024.0);
        if(size > Double.parseDouble(this.size)){
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

    private FileEntity mapFileChangeRequestToFileEntity(Long fileId,Long fileTypeId,String filePath,String fileName,String size){
        FileEntity file = new FileEntity();
        file.setId(fileId);
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
