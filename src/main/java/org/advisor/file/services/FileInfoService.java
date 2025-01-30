package org.advisor.file.services;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.advisor.file.FileProperties;
import org.advisor.file.constants.FileStatus;
import org.advisor.file.entities.FileInfo;
import org.advisor.file.entities.QFileInfo;
import org.advisor.file.repositories.FileInfoRepository;
import org.advisor.global.libs.Utils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Objects;
import static org.springframework.data.domain.Sort.Order.asc;

@Lazy
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileInfoService  {
    private final FileInfoRepository infoRepository;
    private final FileProperties properties;
    private final HttpServletRequest request;
    private final Utils utils;

    public FileInfo get(Long seq) {
        FileInfo item = infoRepository.findById(seq).orElseThrow();
        addInfo(item);
        return item;
    }

    public List<FileInfo> getList(String gid, String location, FileStatus status) {
        status = Objects.requireNonNullElse(status, FileStatus.ALL);

        QFileInfo fileInfo = QFileInfo.fileInfo;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(fileInfo.gid.eq(gid));

        if (StringUtils.hasText(location)) {
            andBuilder.and(fileInfo.location.eq(location));
        }

        if (status != FileStatus.ALL) {
            andBuilder.and(fileInfo.done.eq(status == FileStatus.DONE));
        }

        List<FileInfo> items = (List<FileInfo>)infoRepository.findAll(andBuilder, Sort.by(asc("listOrder"), asc("createdAt")));

        items.forEach(this::addInfo);

        return items;
    }

    public List<FileInfo> getList(String gid, String location) {
        return getList(gid, location, FileStatus.DONE);
    }

    public List<FileInfo> getList(String gid) {
        return getList(gid, null);
    }

    public void addInfo(FileInfo item) {
        item.setFilePath(getFilePath(item));
        item.setFileUrl(getFileUrl(item));

        if (item.getContentType().contains("image/")) {
            item.setThumbUrl(String.format("%s/api/file/thumb?seq=%d", request.getContextPath(), item.getSeq()));
        }
    }

    public String getFilePath(FileInfo item) {
        Long seq = item.getSeq();
        String extension = Objects.requireNonNullElse(item.getExtension(), "");
        return String.format("%s%s/%s", properties.getPath(), getFolder(seq), seq + extension);
    }

    public String getFilePath(Long seq) {
        FileInfo item = infoRepository.findById(seq).orElseThrow();
        return getFilePath(item);
    }

    public String getFileUrl(FileInfo item) {
        Long seq = item.getSeq();
        String extension = Objects.requireNonNullElse(item.getExtension(), "");
        return utils.getUrl(String.format("%s%s/%s", properties.getUrl(), getFolder(seq), seq + extension));
    }

    public String getFileUrl(Long seq) {
        FileInfo item = infoRepository.findById(seq).orElseThrow();
        return getFileUrl(item);
    }

    private long getFolder(long seq) {
        return seq % 10L;
    }

}