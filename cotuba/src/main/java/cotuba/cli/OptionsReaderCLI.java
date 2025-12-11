package cotuba.cli;

import cotuba.application.CotubaArgs;
import cotuba.domain.EbookFormat;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class OptionsReaderCLI implements CotubaArgs {

    private Path markdownDirectory;
    private EbookFormat format;
    private Path outputFile;
    private boolean verboseMode = false;

    public OptionsReaderCLI(String[] args) {
        Options options = createOptions();
        CommandLine cmd = parseArgs(args, options);
        resolveMarkdownDirectory(cmd);
        resolveFormat(cmd);
        resolveOutputFile(cmd);
        resolveVerboseMode(cmd);
    }

    @Override
    public EbookFormat getFormat() {
        return format;
    }

    public void setFormat(EbookFormat format) {
        this.format = format;
    }

    public void setMarkdownDirectory(Path markdownDirectory) {
        this.markdownDirectory = markdownDirectory;
    }

    public void setOutputFile(Path outputFile) {
        this.outputFile = outputFile;
    }

    public void setVerboseMode(boolean verboseMode) {
        this.verboseMode = verboseMode;
    }

    public Path getMarkdownDirectory() {
        return markdownDirectory;
    }

    public Path getOutputFile() {
        return outputFile;
    }

    public boolean isVerboseMode() {
        return verboseMode;
    }

    private Options createOptions() {
        var options = new Options();

        var opcaoDeDiretorioDosMD = new Option("d", "dir", true,
                "Diretório que contém os arquivos md. Default: diretório atual.");
        options.addOption(opcaoDeDiretorioDosMD);

        var opcaoDeFormatoDoEbook = new Option("f", "format", true,
                "Formato de saída do ebook. Pode ser: pdf ou epub. Default: pdf");
        options.addOption(opcaoDeFormatoDoEbook);

        var opcaoDeArquivoDeSaida = new Option("o", "output", true,
                "Arquivo de saída do ebook. Default: book.{formato}.");
        options.addOption(opcaoDeArquivoDeSaida);

        var opcaoModoVerboso = new Option("v", "verbose", false,
                "Habilita modo verboso.");
        options.addOption(opcaoModoVerboso);
        return options;
    }

    private CommandLine parseArgs(String[] args, Options options) {
        CommandLineParser cmdParser = new DefaultParser();
        var helper = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = cmdParser.parse(options, args);
        } catch (ParseException e) {
            helper.printHelp("cotuba", options);
            throw new IllegalArgumentException("Opção inválida", e);
        }
        return cmd;
    }

    private void resolveMarkdownDirectory(CommandLine cmd) {
        String markdownDir = cmd.getOptionValue("dir");
        if (markdownDir != null) {
            Path markdownDirPath = Paths.get(markdownDir);
            if (!Files.isDirectory(markdownDirectory)) {
                throw new IllegalArgumentException(markdownDir + " não é um diretório.");
            }
        } else {
            markdownDirectory = Paths.get("");
        }
    }

    private void resolveFormat(CommandLine cmd) {
        String ebookFormat = cmd.getOptionValue("format");

        format = EbookFormat.valueOf(ebookFormat.toUpperCase());

    }

    private void resolveOutputFile(CommandLine cmd) {
        try {
            String markdownOutputFile = cmd.getOptionValue("output");

            if (markdownOutputFile != null) {
                outputFile = Paths.get(markdownOutputFile);
            } else {
                outputFile = Paths.get("book." + format.name().toLowerCase());
            }
            if (Files.isDirectory(outputFile)) {
                // deleta arquivos do diretório recursivamente
                Files.walk(outputFile).sorted(Comparator.reverseOrder())
                        .map(Path::toFile).forEach(File::delete);
            } else {
                Files.deleteIfExists(outputFile);
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private void resolveVerboseMode(CommandLine cmd) {
        verboseMode = cmd.hasOption("verbose");
    }
}