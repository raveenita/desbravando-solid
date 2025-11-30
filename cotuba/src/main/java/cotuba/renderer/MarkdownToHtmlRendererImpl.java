package cotuba.renderer;

import cotuba.domain.Chapter;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MarkdownToHtmlRendererImpl implements MarkdownToHtmlRenderer {
    List<Chapter> chapters = new ArrayList<>();

    public List<Chapter> renderer(Path markdownDirectory ) {
        return resolveMarkdownFiles(markdownDirectory)
                .stream()
                .map(markdownFile -> {
                    Chapter chapter = new Chapter();
                    Node document = parseMarkdownContent(chapter, markdownFile);

                    rendererToHTML(document, chapter);

                    return chapter;
                }).toList();

    }


        private void rendererToHTML(Node document, Chapter chapter) {
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String html = renderer.render(document);

            chapter.setContent(chapter.getContent());

            chapters.add(chapter);
        }

        private List<Path> resolveMarkdownFiles(Path markdownDirectory) {
            try {
                Stream<Path> markdownFiles = Files.list(markdownDirectory);
                PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");

                return markdownFiles
                        .filter(matcher::matches)
                        .sorted()
                        .toList();

            } catch (Exception e) {

            }

        }

        private Node parseMarkdownContent(Chapter chapter, Path markdownFile) {
            Node document = null;
            try {
                Parser parser = Parser.builder().build();
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

            return document;

        }


}
