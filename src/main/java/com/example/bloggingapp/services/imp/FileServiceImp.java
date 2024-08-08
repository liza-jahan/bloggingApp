package com.example.bloggingapp.services.imp;

import com.example.bloggingapp.services.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileServiceImp implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //file name
        String name = file.getOriginalFilename();

        //random name generate file
        String randomID = UUID.randomUUID().toString();
        String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));

        //Full path
        String filePath = path + File.separator + fileName;

        //create folder . if not create
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
        //file copy
        Files.copy(file.getInputStream(), Paths.get(fileName));

        return name;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }
}
