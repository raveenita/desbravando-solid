package cotuba.html;

import cotuba.application.EbookGenerator;
import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
import cotuba.domain.EbookFormat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;

public class HtmlGenerator implements EbookGenerator {

    @Override
    public void generate(Ebook ebook) {
        Path outputPath = ebook.getOutputPath();

        try {
            Path htmlDirectory = Files.createDirectory(outputPath);

            int index = 1;

            for(Chapter chapter : ebook.getChapters()) {
                String chapterFilename = getChapterFilename(index, chapter);
                Path chapterFileContent = htmlDirectory.resolve(chapterFilename);

                String htmlTemplate = """
                    <!DOCTYPE html>
                    <html lang="pt-BR">
                        <head>
                            <meta charset="utf-8">
                            <title>%s</title>
                        </head>
                       <body>
                            %s
                       </body>
                    </html>
                """.formatted(chapter.getTitle(), chapter.getContent());

                Files.writeString(chapterFileContent, htmlTemplate, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao criar HTML:" + outputPath.toAbsolutePath(), e);
        }
    }

    @Override
    public boolean accept(EbookFormat format) {
        return EbookFormat.HTML.equals(format);
    }

    private String getChapterFilename(int index,  Chapter chapter) {
        return index + "-" + removeAccents(chapter.getTitle().toLowerCase()).replaceAll("[^\\w]", "") + ".html";
    }

    private String removeAccents(String content) {
        return Normalizer.normalize(content, Normalizer.Form.NFD).replaceAll("[\\p{ASCII}]", "");
    }
}
