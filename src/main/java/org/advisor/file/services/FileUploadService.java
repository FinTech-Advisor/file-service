package org.advisor.file.services;

import lombok.RequiredArgsConstructor;
import org.advisor.file.FileProperties;
import org.advisor.file.controllers.RequestUpload;
import org.advisor.file.entities.FileInfo;
import org.advisor.file.repositories.FileInfoRepository;
import org.advisor.global.Exceptions.BadRequestException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Lazy
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileUploadService {
    private final FileProperties properties;
    private final FileInfoRepository fileInfoRepository;
    private final FileInfoService infoService;

    public List<FileInfo> upload(RequestUpload form) {
        String gid = form.getGid();
        gid = StringUtils.hasText(gid) ? gid : UUID.randomUUID().toString();

        String location = form.getLocation();
        MultipartFile[] files = form.getFiles();
        String rootPath = properties.getPath();
        String contentType = form.getContentType();


        List<FileInfo> uploadedItems = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                throw new BadRequestException();
            }

            String fileName = file.getOriginalFilename();
            String extension = "";
            if (fileName != null && fileName.lastIndexOf(".") != -1) {
                extension = fileName.substring(fileName.lastIndexOf("."));
            }

            FileInfo item = new FileInfo();
            item.setGid(gid);
            item.setLocation(location);
            item.setFileName(fileName);
            item.setContentType(contentType);


            fileInfoRepository.saveAndFlush(item);

            long seq = item.getSeq();
            String uploadFileName = seq + extension;
            File dir = new File(rootPath);

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdirs();
            }

            File _file = new File(dir, uploadFileName);
            try {
                file.transferTo(_file);
                infoService.addInfo(item);
                uploadedItems.add(item);

            } catch (IOException e) {
                fileInfoRepository.delete(item);
                fileInfoRepository.flush();
                return null;
            }
        }
        return uploadedItems;
    }
}