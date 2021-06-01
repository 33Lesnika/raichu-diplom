package xyz.raichu.diplom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import xyz.raichu.diplom.entity.File;
import xyz.raichu.diplom.entity.Phrase;
import xyz.raichu.diplom.entity.User;
import xyz.raichu.diplom.model.DocumentPosition;
import xyz.raichu.diplom.repository.FileRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * 02.06.2021
 * FileService
 * 01:02
 */
@Service
public class FileService {
    private final FileRepository repository;
    private final ObjectMapper mapper;
    private final UserService userService;

    public FileService(FileRepository repository, ObjectMapper mapper, UserService userService) {
        this.repository = repository;
        this.mapper = mapper;
        this.userService = userService;
    }

    public void export(File file, OutputStream outputStream) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        file.getPhrases().forEach(phrase -> {
            try {
                writer.write(format(phrase, file.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }

    private String format(Phrase phrase, String filename) {
        StringBuilder sb = new StringBuilder();
        phrase.getCodes().forEach(code -> {
            try {
                var dp = mapper.readValue(code.getCode(), DocumentPosition.class);
                sb.append(String.format("%s %s %03d %03d %03d%n", filename, phrase.getText(), dp.getPage(), dp.getLine(), dp.getWordPos()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return sb.toString();
    }

    @Transactional
    public File save(File file) {
        return repository.save(file);
    }

    public File getOne(Long id) {
        return repository.getOne(id);
    }

    public Iterable<File> getMyFiles(){
        var currentUser = userService.getCurrentUser();
        return repository.findAllByUser(currentUser);
    }
}
