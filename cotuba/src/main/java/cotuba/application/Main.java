package cotuba.application;

import cotuba.cli.OptionsReaderCLI;
import cotuba.domain.Chapter;
import cotuba.renderer.MarkdownToHtmlRendererWithCommonMark;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;


public class Main {

  public static void main(String[] args) {
      OptionsReaderCLI options = new OptionsReaderCLI(args);
      MarkdownToHtmlRendererWithCommonMark markdownToHtml = new MarkdownToHtmlRendererWithCommonMark();
      List<Chapter> chapters = markdownToHtml.renderer(options.getOutputFile());

      try {
          Cotuba cotuba = new Cotuba();
          cotuba.execute(options);

      } catch (Exception ex) {
          boolean isVerboseMode = options.isVerboseMode();

          System.err.println(ex.getMessage());
          PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");

          System.out.println("Arquivo gerado com sucesso" + options.getOutputFile());

          if (isVerboseMode) {
              ex.printStackTrace();
          }
          System.exit(1);
      }
  }

}
