package cotuba.epub;

import cotuba.domain.Chapter;
import cotuba.domain.Ebook;

public interface EpubGenerator {
    public void generate (Ebook ebook);
}
