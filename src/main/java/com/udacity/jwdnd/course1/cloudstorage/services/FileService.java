package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public Integer uploadFile(MultipartFile multipartFile, Integer userId) throws IOException {

        File file = new File(
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getSize(),
                userId,
                multipartFile.getBytes()
        );

        return this.fileMapper.uploadFile(file);
    }

    public List<File> getFiles(Integer userId) {
        return this.fileMapper.getFiles(userId);
    }

    public Boolean isFilenameAvailable(String filename, Integer userId){
        return this.fileMapper.listFilesWithSameFilenameAndUserId(filename, userId).size() == 0;
    }

    public File getFileById(Integer fileId) {
        return this.fileMapper.getFileById(fileId);
    }

    public void deleteFile(Integer fileId, Integer userId) {
        this.fileMapper.delete(fileId, userId);
    }
}
