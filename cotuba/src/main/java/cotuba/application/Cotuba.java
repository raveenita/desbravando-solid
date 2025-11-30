package cotuba.application;

import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
import cotuba.epub.EpubGenerator;
import cotuba.epub.EpubGeneratorImpl;
import cotuba.pdf.PdfGenerator;
import cotuba.pdf.PdfGeneratorImpl;
import cotuba.renderer.MarkdownToHtmlRenderer;
import cotuba.renderer.MarkdownToHtmlRendererImpl;

import java.nio.file.Path;
import java.util.List;

public class Cotuba {
    public void execute(String format, Path markdownDirectory, Path outputFile) {
        MarkdownToHtmlRenderer markdownToHtmlRenderer = new MarkdownToHtmlRendererImpl();
        List<Chapter> chapters = markdownToHtmlRenderer.renderer(outputFile);
        Ebook ebook = new Ebook();

        ebook.setFormat(format);
        ebook.setOutputPath(outputFile);

        if ("pdf".equals(format)) {
            PdfGenerator pdfGenerator = new PdfGeneratorImpl();
            pdfGenerator.generate(ebook);

        } else if ("epub".equals(format)) {
            EpubGenerator epubGenerator = new EpubGeneratorImpl();
            epubGenerator.generate(ebook);
        }
    }
}
