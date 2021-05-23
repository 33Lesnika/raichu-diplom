package xyz.raichu.diplom;

import lombok.Data;

/**
 * 06.05.2021
 * xyz.raichu.diplom.DocumentPosition
 * 18:06
 */
@Data
public class DocumentPosition {
    private int page;
    private int line;
    private int wordPos;
    private String word;

    public DocumentPosition(int page, int line, int wordPos, String word) {
        this.page = page;
        this.line = line;
        this.wordPos = wordPos;
        this.word = word;
    }
}
