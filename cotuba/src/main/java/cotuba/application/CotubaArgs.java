package cotuba.application;

import cotuba.domain.EbookFormat;

import java.nio.file.Path;

public interface CotubaArgs {
    Path getMarkdownDirectory();
    EbookFormat getFormat();
    Path getOutputFile();
}
