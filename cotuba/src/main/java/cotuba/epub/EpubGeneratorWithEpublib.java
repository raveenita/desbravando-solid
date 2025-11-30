package cotuba.epub;

import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class EpubGeneratorWithEpublib implements EpubGenerator {
    public EpubGeneratorWithEpublib() {}

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


}
