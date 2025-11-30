package cotuba.renderer;

import cotuba.domain.Chapter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public  interface MarkdownToHtmlRenderer {
    List<Chapter> chapters = new ArrayList<>();

    public List<Chapter> renderer(Path markdownDirectory);

    static MarkdownToHtmlRenderer create() {
        return new MarkdownToHtmlRendererWithCommonMark();
    }
}
