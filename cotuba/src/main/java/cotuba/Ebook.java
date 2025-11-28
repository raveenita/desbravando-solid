package cotuba;

import java.nio.file.Path;
import java.util.List;

public class Ebook {
    private String title;
    private String content;
    private String format;
    private Path outputPath;
    private List<cotuba.Chapter> chapters;

    public boolean isLastChapter(Chapter chapter) {
        return this.chapters.get(this.chapters.size() - 1).equals(chapter);
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setOutputPath(Path outputPath) {
        this.outputPath = outputPath;
    }

    public void setChapters(List<cotuba.Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getFormat() {
        return format;
    }

    public Path getOutputPath() {
        return outputPath;
    }

    public List<cotuba.Chapter> getChapters() {
        return chapters;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
