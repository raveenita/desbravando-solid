package cotuba.application;

import cotuba.CotubaConfig;
import cotuba.cli.OptionsReaderCLI;
import cotuba.domain.Chapter;
import cotuba.renderer.MarkdownToHtmlRenderer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;


public class Main {

  public static void main(String[] args) {
      OptionsReaderCLI options = new OptionsReaderCLI(args);
      MarkdownToHtmlRenderer markdownToHtml = new MarkdownToHtmlRenderer();
      List<Chapter> chapters = markdownToHtml.render(options.getOutputFile());

      try {
          ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CotubaConfig.class);
          Cotuba cotuba = applicationContext.getBean(Cotuba.class);
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
