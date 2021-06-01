package xyz.raichu.diplom.controller;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import xyz.raichu.diplom.entity.File;
import xyz.raichu.diplom.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 01.06.2021
 * FileController
 * 02:13
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{id}")
    public File get(@PathVariable Long id){
        return fileService.getOne(id);
    }

    @GetMapping("/download/{id}")
    public StreamingResponseBody downloadFile(HttpServletResponse response, @PathVariable Long id){
        var file = fileService.getOne(id);
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        ContentDisposition attachment = ContentDisposition
                .builder("attachment")
                .filename(file.getName() + ".txt", StandardCharsets.UTF_8).build();
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, attachment.toString());
        return (outputStream -> fileService.export(file, outputStream));
    }

    @PostMapping
    public File save(@RequestBody File file){
        return fileService.save(file);
    }
}
