package cotuba.epub;

import cotuba.application.EbookGenerator;
import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
import cotuba.domain.EbookFormat;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class EpubGenerator implements EbookGenerator {
    public EpubGenerator() {}

    @Override
    public void generate (Ebook ebook) {
         Book epub = new Book();
         Path outputFile = ebook.getOutputPath();


        for (Chapter chapter : ebook.getChapters()) {
            String html = chapter.getContent();
            String chapterTitle = chapter.getTitle();

            epub.addSection("Capítulo", new Resource(html.getBytes(), MediatypeService.XHTML));

        }

        var epubWriter = new EpubWriter();

        try {
            epubWriter.write(epub, Files.newOutputStream(outputFile));
            Files.newOutputStream(outputFile);
        } catch (IOException ex) {
            throw new IllegalStateException("Erro ao criar arquivo EPUB: " + outputFile.toAbsolutePath(), ex);
        }

        System.out.println("Arquivo gerado com sucesso: " + outputFile);
    }

    @Override
    public boolean accept(EbookFormat format) {
        return EbookFormat.EPUB.equals(format);
    }
}
