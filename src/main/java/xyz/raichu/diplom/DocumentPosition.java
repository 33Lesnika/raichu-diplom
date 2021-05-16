package xyz.raichu.diplom;

/**
 * 06.05.2021
 * xyz.raichu.diplom.DocumentPosition
 * 18:06
 */
public class DocumentPosition {
    private int page;
    private int line;
    private int word;

    public DocumentPosition(int page, int line, int word) {
        this.page = page;
        this.line = line;
        this.word = word;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getWord() {
        return word;
    }

    public void setWord(int word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "xyz.raichu.diplom.DocumentPosition{" +
                "page=" + page +
                ", line=" + line +
                ", word=" + word +
                '}';
    }
}
