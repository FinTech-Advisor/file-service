package org.advisor.file.services;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.advisor.file.FileProperties;
import org.advisor.file.controllers.RequestThumb;
import org.advisor.file.entities.FileInfo;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@Lazy
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class ThumbnailService {

    private final FileProperties properties;
    private final FileInfoService infoService;

    public String create(RequestThumb form) {

        Long seq = form.getSeq();
        String url = form.getUrl();
        int width = Math.max(form.getWidth(), 50);
        int height = Math.max(form.getHeight(), 50);

        String thumbPath = getThumbPath(seq, url, width, height);
        File file = new File(thumbPath);
        if (file.exists()) {
            return thumbPath;
        }

        try {
            if (seq != null && seq > 0L) {
                FileInfo item = infoService.get(seq);
                Thumbnails.of(item.getFilePath())
                        .size(width, height)
                        .toFile(file);
            } else {
                thumbPath = null;
            }
        } catch (Exception e) {}

        return thumbPath;
    }

    public String getThumbPath(Long seq, String url, int width, int height) {
        String thumbPath = properties.getPath() + "thumbs/";
        if (seq != null && seq > 0L) {
            FileInfo item = infoService.get(seq);

            thumbPath = thumbPath + String.format("%d/%d_%d_%d%s", seq % 10L, seq, width, height, item.getExtension());
        } else if (StringUtils.hasText(url)) {
            String extension = url.lastIndexOf(".") == -1 ? "" : url.substring(url.lastIndexOf("."));
            if (StringUtils.hasText(extension)) {
                extension = extension.split("[?#]")[0];
            }
            thumbPath = thumbPath + String.format("urls/%d_%d_%d%s", Objects.hash(url), width, height, extension);
        }

        File file = new File(thumbPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        return thumbPath;
    }
}