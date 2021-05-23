package xyz.raichu.diplom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.raichu.diplom.entity.File;
import xyz.raichu.diplom.repository.FileRepository;

/**
 * 24.05.2021
 * AdminController
 * 04:40
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final FileRepository fileRepository;

    public AdminController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @GetMapping
    public Iterable<File> getAll(){
        return fileRepository.findAll();
    }
}
