package cotuba.application;

import java.nio.file.Path;

public interface CotubaArgs {
    Path getMarkdownDirectory();
    String getFormat();
    Path getOutputFile();
}
