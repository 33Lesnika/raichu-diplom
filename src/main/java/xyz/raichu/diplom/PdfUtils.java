package xyz.raichu.diplom;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * 06.05.2021
 * PdfUtils
 * 19:33
 */
public class PdfUtils {
    public static List<DocumentPosition> findFordPosition(InputStream is, Set<String> givenWords) throws IOException {
        List<DocumentPosition> positions = new ArrayList<>();
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
                    String[] words = line.trim().split("\\s+");
                    for (int wordNumber = 0; wordNumber < words.length; wordNumber++) {
//                        if (words[wordNumber].contains(word)) {
                        if (givenWords.contains(words[wordNumber])) {
                            System.out.printf("Coords: page %d line %d word %d%n", pageNumber, lineNumber, wordNumber + 1);
                            positions.add(new DocumentPosition(pageNumber, lineNumber, wordNumber + 1, words[wordNumber]));
                        }
                    }
                    lineNumber++;
                }
            }
        }
        return positions;
    }
}
