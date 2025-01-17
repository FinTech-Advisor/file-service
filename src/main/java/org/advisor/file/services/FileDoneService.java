package org.advisor.file.services;

import lombok.RequiredArgsConstructor;
import org.advisor.file.constants.FileStatus;
import org.advisor.file.entities.FileInfo;
import org.advisor.file.repositories.FileInfoRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class FileDoneService {

    private final FileInfoService infoService;
    private final FileInfoRepository repository;


    public void process(String gid, String location) {
        List<FileInfo> items = infoService.getList(gid, location, FileStatus.ALL);

        items.forEach(item -> item.setDone(true));

        repository.saveAllAndFlush(items);
    }

    public void process(String gid) {
        process(gid, null);
    }
}
