package cotuba.cli;

import org.apache.commons.cli.*;

import java.nio.file.Path;

public class OptionsReaderCLI {
    private Path markdownDir;
    private String format;
    private Path output;
    private Boolean isVerboseMode;

    public OptionsReaderCLI(Path markdownDir, String format, Path output, Boolean isVerboseMode) {
        this.markdownDir = markdownDir;
        this.format = format;
        this.output = output;
        this.isVerboseMode = isVerboseMode;
    }

    private Options createOptions() {

    }

    private CommandLine parseArguments(String[] args, Options options) {

    }

    private void resolveMarkdownDirectory(CommandLine cmd) {

    }

    private void resolveFormat() {

    }

    private void resolveOutputFIle() {

    }

    private void resolveVerboseMode() {

    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Path getMarkdownDir() {
        return markdownDir;
    }

    public Path getOutput() {
        return output;
    }

    public void setMarkdownDir(Path markdownDir) {
        this.markdownDir = markdownDir;
    }

    public void setOutput(Path output) {
        this.output = output;
    }

    public void setVerboseMode(Boolean verboseMode) {
        isVerboseMode = verboseMode;
    }


    public String getFormat() {
        return format;
    }

    public Boolean getVerboseMode() {
        return isVerboseMode;
    }

    public OptionsReaderCLI(String[] args) {
        CommandLine cmd;
        CommandLineParser cmdParser = new DefaultParser();

        Options options = getOptions();
        HelpFormatter helper = new HelpFormatter();


        try {
            cmd = cmdParser.parse(options, args);
        } catch (IllegalArgumentException e) {
            helper.printHelp("cotuba", options);
            throw new IllegalArgumentException("Invalid option", e);
        }
    }

    public Options getOptions() {
        Options options = new Options();

        Option markdownDirectory = new Option("d", "markdownDir", true,
                "Diretório que contém os arquivos md. Default: diretório atual.");

        options.addOption(markdownDirectory);

        Option ebookFormat = new Option("f", "format", true,
                "Formato de saída do ebook. Pode ser: pdf ou epub. Default: pdf");
        options.addOption(ebookFormat);

        Option outputFiles = new Option("o", "output", true,
                "Arquivo de saída do ebook. Default: book.{formato}.");
        options.addOption(outputFiles);

        var isVerboseMode = new Option("v", "verbose", false,
                "Habilita modo verboso.");
        options.addOption(isVerboseMode);

        return options;
    }
}
