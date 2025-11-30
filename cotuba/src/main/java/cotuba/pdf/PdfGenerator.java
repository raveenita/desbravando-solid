package cotuba.pdf;

import cotuba.domain.Ebook;

public interface PdfGenerator {
    public void generate(Ebook ebook);

    static PdfGenerator create() {
        return new PdfGeneratorWithIText();
    }
}
