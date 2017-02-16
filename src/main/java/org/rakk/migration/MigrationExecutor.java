package org.rakk.migration;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.maven.plugin.logging.Log;
import org.rakk.migration.configuration.MigrationConfiguration;
import org.rakk.migration.configuration.imports.ImportUpdate;
import org.rakk.migration.configuration.sources.Source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.regex.Pattern;

public class MigrationExecutor {

    private static Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");

    private MigrationConfiguration migrationConfiguration;
    private Log log;

    public MigrationExecutor(MigrationConfiguration migrationConfiguration, Log log) {
        this.migrationConfiguration = migrationConfiguration;
        this.log = log;
    }

    public void execute() {
        for (Source source : migrationConfiguration.getSourcesList()) {
            log.info(String.format("Running migration '%s' for source: %s", migrationConfiguration.getName(), source));
            Collection<File> files = findAllFiles(migrationConfiguration.getModuleEntryPoint(), source);
            execute(migrationConfiguration, source, files);
        }
    }

    private void execute(MigrationConfiguration migrationConfiguration, Source source, Collection<File> files) {
        for (File file : files) {
            log.info("\tRunning on file: " + file.getName());
            try {
                String fileContent = IOUtils.toString(new FileInputStream(file), Charset.defaultCharset());
                updateImports(migrationConfiguration, file, fileContent);
            } catch (IOException e) {
                log.error("Cannot read file: " + file.getAbsolutePath());
                log.debug("Cannot read file because of: {}", e);
            }
        }
    }

    private void updateImports(MigrationConfiguration migrationConfiguration, File file, String fileContent) throws IOException {
        for (ImportUpdate importUpdate : migrationConfiguration.getImportUpdatesList()) {
            Pattern importPattern = Pattern.compile("import " + escapeSpecialRegexChars(importUpdate.getOldPath()));
            String newFileContent = fileContent.replaceAll(importPattern.pattern(), "import " + importUpdate.getNewPath());
            if (!fileContent.equals(newFileContent)) {
                log.info("Updating import " + importUpdate.getOldPath() + " to " + importUpdate.getNewPath());
                IOUtils.write(newFileContent, new FileOutputStream(file), Charset.defaultCharset());
            }
        }
    }

    private Collection<File> findAllFiles(File moduleEntryPoint, Source source) {
        return FileUtils.listFiles(moduleEntryPoint, new FileFilter(source), new DirFilter());
    }

    private class FileFilter implements IOFileFilter {

        private Source source;

        FileFilter(Source source) {
            this.source = source;
        }

        public boolean accept(File file) {
            return file != null && source.matches(file.getName());
        }

        public boolean accept(File file, String s) {
            return accept(file);
        }
    }

    private class DirFilter implements IOFileFilter {

        public boolean accept(File file) {
            return true;
        }

        public boolean accept(File file, String s) {
            return true;
        }
    }

    private String escapeSpecialRegexChars(String str) {
        return SPECIAL_REGEX_CHARS.matcher(str).replaceAll("\\\\$0");
    }

}
