package xyz.raichu.diplom.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.raichu.diplom.entity.Code;
import xyz.raichu.diplom.entity.File;
import xyz.raichu.diplom.entity.Phrase;
import xyz.raichu.diplom.model.DocumentPosition;
import xyz.raichu.diplom.repository.FileRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Set;

/**
 * 06.05.2021
 * PdfUtils
 * 19:33
 */
@Service
@Slf4j
public class PdfService {

    private final UserService userService;
    private final FileRepository fileRepository;

    public PdfService(UserService userService, FileRepository fileRepository) {
        this.userService = userService;
        this.fileRepository = fileRepository;
    }

    @Transactional
    public File findFordPosition(InputStream is, Set<String> givenWords, String filename) throws IOException {
//        List<DocumentPosition> positions = new ArrayList<>();
        File file = new File();
        file.setName(filename);
        file.setUser(userService.getCurrentUser());
        try (PDDocument doc = PDDocument.load(is)) {
            PDFTextStripper stripper = new PDFTextStripper();
            int maxPages = doc.getNumberOfPages();
            for (int pageNumber = 1; pageNumber <= maxPages; pageNumber++) {
                stripper.setStartPage(pageNumber);
                stripper.setEndPage(pageNumber);
                String page = stripper.getText(doc);
                Scanner scanner = new Scanner(page);
                int lineNumber = 1;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (!StringUtils.hasText(line)) continue;
                    for (String givenWord : givenWords) {
                        if (line.contains(givenWord)) {
                            var index = line.indexOf(givenWord);
                            String[] words = line.substring(0, index).trim().split("\\s+");
                            var dp = new DocumentPosition(pageNumber, lineNumber, words.length + 1, givenWord);
                            Phrase phrase = null;
                            for (Phrase p : file.getPhrases()) {
                                if (p.getText().equals(givenWord)) {
                                    phrase = p;
                                }
                            }
                            if (phrase == null) {
                                phrase = new Phrase();
                                phrase.setText(givenWord);
                                file.getPhrases().add(phrase);
                            }
                            phrase.getCodes().add(new Code(null, dp.toString()));
//                            positions.add(dp);
                        }
                    }
                    lineNumber++;
                }
            }
        }
//        fileRepository.save(file);
        return file;
    }
}
