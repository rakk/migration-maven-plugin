package org.rakk.migration.steps;

import org.apache.commons.io.IOUtils;
import org.rakk.migration.configuration.MigrationConfiguration;
import org.rakk.migration.configuration.imports.ImportUpdate;
import org.rakk.migration.configuration.imports.ImportUpdates;
import org.rakk.migration.output.Stats;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

public class UpdateImportsStep {


    private static Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");
    private Stats stats;

    public UpdateImportsStep(Stats stats) {
        this.stats = stats;
    }

    public void update(ImportUpdates config, File file) throws IOException {
        String fileContent = IOUtils.toString(new FileInputStream(file), Charset.defaultCharset());
        String newFileContent = fileContent;
        for (ImportUpdate importUpdate : config.getImportUpdate()) {
            Pattern importPattern = Pattern.compile("import " + escapeSpecialRegexChars(importUpdate.getOldPath()));
            newFileContent = newFileContent.replaceAll(importPattern.pattern(), "import " + importUpdate.getNewPath());
        }
        if (!fileContent.equals(newFileContent)) {
            IOUtils.write(newFileContent, new FileOutputStream(file), Charset.defaultCharset());
            stats.incrementImportUpdateCount();
        }
    }

    private String escapeSpecialRegexChars(String str) {
        return SPECIAL_REGEX_CHARS.matcher(str).replaceAll("\\\\$0");
    }
}
