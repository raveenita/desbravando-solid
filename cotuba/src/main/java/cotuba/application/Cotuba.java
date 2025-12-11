package cotuba.application;

import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
import cotuba.epub.EpubGenerator;
import cotuba.pdf.PdfGenerator;
import cotuba.renderer.MarkdownToHtmlRenderer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Cotuba {
    private final List<EbookGenerator> generators;
    private final MarkdownToHtmlRenderer renderer;

    public Cotuba(List<EbookGenerator> ebookGenerators, MarkdownToHtmlRenderer renderer) {
        this.generators = ebookGenerators;
        this.renderer = renderer;
    }

    public void execute(CotubaArgs options) {
        MarkdownToHtmlRenderer renderer = new MarkdownToHtmlRenderer();
        List<Chapter> chapters = renderer.render(options.getOutputFile());
        Ebook ebook = new Ebook();

        EbookGenerator ebookGenerator = generators.stream()
                        .filter(generator -> generator.accept(ebook.getFormat()))
                        .findAny()
                                .orElseThrow(() -> new IllegalArgumentException("Formato do ebook inválido" + ebook.getFormat()));

        ebookGenerator.generate(ebook);

        ebook.setFormat(options.getFormat());
        ebook.setOutputPath(options.getOutputFile());

    }
}
