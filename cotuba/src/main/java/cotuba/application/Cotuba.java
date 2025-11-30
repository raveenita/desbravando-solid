package cotuba.application;

import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
import cotuba.epub.EpubGenerator;
import cotuba.pdf.PdfGenerator;
import cotuba.renderer.MarkdownToHtmlRenderer;

import java.nio.file.Path;
import java.util.List;

public class Cotuba {
    public void execute(CotubaArgs options) {
        MarkdownToHtmlRenderer markdownToHtmlRenderer = MarkdownToHtmlRenderer.create();
        List<Chapter> chapters = markdownToHtmlRenderer.renderer(options.getOutputFile());
        Ebook ebook = new Ebook();

        ebook.setFormat(options.getFormat());
        ebook.setOutputPath(options.getOutputFile());

        if ("pdf".equals(options.getFormat())) {
            PdfGenerator pdfGenerator = PdfGenerator.create();
            pdfGenerator.generate(ebook);

        } else if ("epub".equals(options.getFormat())) {
            EpubGenerator epubGenerator = EpubGenerator.create();
            epubGenerator.generate(ebook);
        }
    }
}
