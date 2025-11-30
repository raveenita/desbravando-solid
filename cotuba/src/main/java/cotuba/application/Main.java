package cotuba.application;

import cotuba.cli.OptionsReaderCLI;
import cotuba.domain.Chapter;
import cotuba.renderer.MarkdownToHtmlRendererImpl;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;


public class Main {

  public static void main(String[] args) {
      OptionsReaderCLI optionsReaderCLI = new OptionsReaderCLI(args);
      MarkdownToHtmlRendererImpl markdownToHtml = new MarkdownToHtmlRendererImpl();
      String format = optionsReaderCLI.getFormat();
      Path markdownDirectory = optionsReaderCLI.getMarkdownDirectory();
      Path outputFile = optionsReaderCLI.getOutputFile();
      List<Chapter> chapters = markdownToHtml.renderer(outputFile);

      try {
          Cotuba cotuba = new Cotuba();
          cotuba.execute(format, markdownDirectory, outputFile);

      } catch (Exception ex) {
          boolean isVerboseMode = optionsReaderCLI.isVerboseMode();

          System.err.println(ex.getMessage());
          PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");

          System.out.println("Arquivo gerado com sucesso" + outputFile);

          if (isVerboseMode) {
              ex.printStackTrace();
          }
          System.exit(1);
      }
  }

}
