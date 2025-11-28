package cotuba;

import java.nio.file.Path;
import java.util.List;

public class Cotuba {
    public void execute(String format, Path markdownDirectory, Path outputFile) {
        List<Chapter> chapters = new MarkdownToHtmlRenderer().renderer(outputFile);
        Ebook ebook = new Ebook();

        ebook.setFormat(format);
        ebook.setOutputPath(outputFile);

        if ("pdf".equals(format)) {
            PdfGenerator pdfGenerator = new PdfGenerator();
            pdfGenerator.generate(ebook);

        } else if ("epub".equals(format)) {
            EpubGenerator epubGenerator = new EpubGenerator();
            epubGenerator.generate(ebook);
        }
    }
}
