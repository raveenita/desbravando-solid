package cotuba.domain;

import cotuba.application.EbookGenerator;
import cotuba.epub.EpubGenerator;
import cotuba.html.HtmlGenerator;
import cotuba.pdf.PdfGenerator;

public enum EbookFormat {
    PDF,
    EPUB,
    HTML;

    private EbookGenerator generator;

    public EbookGenerator getGenerator() {
        return generator;
    }
}
