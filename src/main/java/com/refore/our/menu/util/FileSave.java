package com.refore.our.menu.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileSave {

    @Value("${file.dir}")
    private String path;


    public String imgSave(MultipartFile imageFile) {
        // 파일이 저장될 경로 설정
        File directory = new File(path);
        // 디렉토리가 존재하지 않으면 생성
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String uuid = UUID.randomUUID().toString();
        String originalFilename = imageFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String serverFileName = uuid + fileExtension;
        File serverFile = new File(directory.getAbsolutePath() + File.separator + serverFileName);

        try {
            imageFile.transferTo(serverFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return serverFileName;
    }
}
