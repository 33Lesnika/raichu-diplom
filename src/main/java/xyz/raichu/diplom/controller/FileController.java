package xyz.raichu.diplom.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import xyz.raichu.diplom.entity.File;
import xyz.raichu.diplom.service.FileService;

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
    public StreamingResponseBody downloadFile(@PathVariable Long id){
        return (outputStream -> fileService.export(id, outputStream));
    }

    @PostMapping
    public File save(@RequestBody File file){
        return fileService.save(file);
    }
}
