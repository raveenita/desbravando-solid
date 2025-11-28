package cotuba;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MarkdownToHtmlRenderer {
    public List<Chapter> renderer(Path markdownDir) {
        Chapter chapter = new Chapter();
        List<Chapter> chapters = new ArrayList<>();

        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");

        try (Stream<Path> markdownFiles = Files.list(markdownDir)) {
            markdownFiles
                    .filter(matcher::matches)
                    .sorted()
                    .forEach(markdownFile -> {
                        Parser parser = Parser.builder().build();
                        Node document = null;
                        try {
                            document = parser.parseReader(Files.newBufferedReader(markdownFile));
                            document.accept(new AbstractVisitor() {
                                @Override
                                public void visit(Heading heading) {
                                    if (heading.getLevel() == 1) {
                                        // capítulo
                                        String title = ((Text) heading.getFirstChild()).getLiteral();
                                        chapter.setTitle(title);
                                    } else if (heading.getLevel() == 2) {
                                        // seção
                                    } else if (heading.getLevel() == 3) {
                                        // título
                                    }
                                }

                            });
                        } catch (Exception ex) {
                            throw new IllegalStateException("Erro ao fazer parse do arquivo " + markdownFile, ex);
                        }

                        try {
                            HtmlRenderer renderer = HtmlRenderer.builder().build();
                            String html = renderer.render(document);

                            chapter.setContent(chapter.getContent());
                            chapters.add(chapter);

                        } catch (Exception ex) {
                            throw new IllegalStateException("Erro ao renderizar para HTML o arquivo " + markdownFile, ex);
                        }
                    });
        } catch (IOException ex) {
            throw new IllegalStateException("Erro tentando encontrar arquivos .md em " + markdownDir.toAbsolutePath(), ex);
        }

        return chapters;
    }
}
