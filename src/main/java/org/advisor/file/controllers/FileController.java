
package org.advisor.file.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.advisor.file.entities.FileInfo;
import org.advisor.file.services.*;
import org.advisor.global.Exceptions.BadRequestException;
import org.advisor.global.libs.Utils;
import org.advisor.global.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final Utils utils;
    private final ImageService imageService;
    private final FileDeleteService deleteService;
    private final FileDoneService doneService;
    private final FileUploadService uploadService;
    private final FileDownloadService downloadService;
    private final FileInfoService infoService;
    private final ThumbnailService thumbnailService;


    @PostMapping("/upload")
    public JSONData upload(@RequestPart("file") MultipartFile[] files, @Valid RequestUpload form, Errors errors) {
        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        form.setFiles(files);
        if(form.isSingle()){
            deleteService.deletes(form.getGid(), form.getLocation());
        }
        List<FileInfo> uploadedFiles = uploadService.upload(form);

        if (form.isDone()) {
            doneService.process(form.getGid(), form.getLocation());
        }

        JSONData data = new JSONData(uploadedFiles);
        data.setStatus(HttpStatus.CREATED);

        return data;
    }

    @GetMapping("/download/{seq}")
    public void download(@PathVariable("seq") Long seq) {
        downloadService.process(seq);
    }

    @GetMapping("/info/{seq}")
    public JSONData info(@PathVariable("seq") Long seq) {
        FileInfo item = infoService.get(seq);
        return new JSONData(item);
    }

    @DeleteMapping("/delete/{seq}")
    public JSONData delete(@PathVariable("seq") Long seq) {
        FileInfo item = deleteService.delete(seq);
        return new JSONData(item);
    }

    @DeleteMapping({"/deletes/{gid}"})
    public JSONData deletes(@PathVariable("gid") String gid) {
        List<FileInfo> items = deleteService.deletes(gid);
        return new JSONData(items);
    }

    @GetMapping("/thumb")
    public void thumb(RequestThumb form, HttpServletResponse response) {
        String path = thumbnailService.create(form);
        if (!StringUtils.hasText(path)) {
            return;
        }

        File file = new File(path);
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            String contentType = Files.probeContentType(file.toPath());
            response.setContentType(contentType);

            OutputStream out = response.getOutputStream();
            out.write(bis.readAllBytes());

        } catch (IOException e) {
            throw new BadRequestException();
        }
    }

    @GetMapping("/select/{seq}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void select(@PathVariable("seq") Long seq) {
        imageService.select(seq);
    }
}