package xyz.raichu.diplom.controller;

import org.springframework.web.bind.annotation.*;
import xyz.raichu.diplom.entity.File;
import xyz.raichu.diplom.repository.FileRepository;

/**
 * 01.06.2021
 * FileController
 * 02:13
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileRepository fileRepository;


    public FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @GetMapping("/{id}")
    public File get(@PathVariable Long id){
        return fileRepository.getOne(id);
    }

    @PostMapping
    public File save(@RequestBody File file){
        return fileRepository.save(file);
    }
}
