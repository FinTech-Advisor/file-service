package org.advisor.file.services;

import lombok.RequiredArgsConstructor;
import org.advisor.file.constants.FileStatus;
import org.advisor.file.entities.FileInfo;
import org.advisor.file.repositories.FileInfoRepository;
import org.advisor.global.Exceptions.UnAuthorizedException;
import org.advisor.global.libs.Utils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class FileDeleteService {
    private final FileInfoService infoService;
    private final FileInfoRepository infoRepository;
    private Utils utils;

    public FileInfo delete(Long seq) {
        FileInfo item = infoService.get(seq);
        String filePath = item.getFilePath();
        String createdBy = item.getCreatedBy();

        // isadmin 필요

        infoRepository.delete(item);
        infoRepository.flush();

        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            file.delete();
        }

        return item;
    }

    public List<FileInfo> deletes(String gid, String location) {
        List<FileInfo> items = infoService.getList(gid, location, FileStatus.ALL);
        items.forEach(i -> delete(i.getSeq()));

        return items;
    }

    public List<FileInfo> deletes(String gid) {
        return deletes(gid, null);
    }
}
