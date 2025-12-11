package cotuba.application;

import cotuba.domain.Ebook;
import cotuba.domain.EbookFormat;
import cotuba.epub.EpubGenerator;
import cotuba.pdf.PdfGenerator;

import java.util.HashMap;
import java.util.Map;

public interface EbookGenerator {
    public void generate (Ebook ebook);
    boolean accept(EbookFormat ebookFormat);

    static EbookGenerator create(EbookFormat format) {
       return format.getGenerator();
    }
}
