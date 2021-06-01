package xyz.raichu.diplom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import xyz.raichu.diplom.entity.File;
import xyz.raichu.diplom.entity.Phrase;
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

    public FileService(FileRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void export(Long id, OutputStream outputStream) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        repository.findById(id).ifPresent(file -> {
            file.getPhrases().forEach(phrase -> {
                try {
                    writer.write(file.getName() + " " + format(phrase));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
        writer.close();
    }

    private String format(Phrase phrase){
        StringBuilder sb = new StringBuilder(phrase.getText());
        sb.append(" ");
        phrase.getCodes().forEach(code -> {
            try {
                var dp = mapper.readValue(code.getCode(), DocumentPosition.class);
                sb.append(String.format("%03d %03d %03d", dp.getPage(), dp.getLine(), dp.getWordPos()));
                sb.append(" ");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        sb.append("\n");
        return sb.toString();
    }

    @Transactional
    public File save(File file){
        return repository.save(file);
    }

    public File getOne(Long id){
        return repository.getOne(id);
    }
}
