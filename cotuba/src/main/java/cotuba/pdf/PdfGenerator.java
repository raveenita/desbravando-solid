package cotuba.pdf;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.property.AreaBreakType;
import cotuba.domain.Chapter;
import cotuba.domain.Ebook;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.util.List;

public class PdfGenerator {
    public PdfGenerator() {
    }

    public void generate(Ebook ebook) {
        try (
            PdfWriter writer = new PdfWriter(Files.newOutputStream(ebook.getOutputPath()));
            PdfDocument pdf = new PdfDocument(writer);
            Document pdfDocument = new Document(pdf)) {

            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");

            for (Chapter chapter : ebook.getChapters()) {
                String html = chapter.getContent();
                List<IElement> convertToElements = HtmlConverter.convertToElements(html);

                for (IElement element :convertToElements) {
                    pdfDocument.add((IBlockElement) element);

                }
            }
            // TODO: não adicionar página depois do último capítulo
            pdfDocument.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao renderizar para HTML o arquivo " + ebook.getTitle(), ex);
        }
    }
}
