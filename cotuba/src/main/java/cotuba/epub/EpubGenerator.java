package cotuba.epub;

import cotuba.domain.Ebook;

public interface EpubGenerator {
    public void generate (Ebook ebook);

    static EpubGenerator create() {
        return new EpubGeneratorWithEpublib();
    }

}
