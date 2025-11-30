package cotuba.renderer;

import cotuba.domain.Chapter;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public  interface MarkdownToHtmlRenderer {
    List<Chapter> chapters = new ArrayList<>();

    public List<Chapter> renderer(Path markdownDirectory);
}
