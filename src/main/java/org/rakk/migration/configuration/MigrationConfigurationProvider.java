package org.rakk.migration.configuration;

import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MigrationConfigurationProvider {

    private static final Pattern CONFIGURATION_PATTERN = Pattern.compile(".*\\.migration\\.xml");

    private Log log;

    public MigrationConfigurationProvider(Log log) {
        this.log = log;
    }

    public List<File> getConfigurationFiles(File entryPoint) {
        List<File> result = new ArrayList<File>();
        if (entryPoint != null) {
            for (File file : entryPoint.listFiles()) {
                processFile(result, file);
            }
        }
        return result;
    }

    private void processFile(List<File> result, File file) {
        if (existsAndIsFile(file) && isConfigFile(file)) {
            log.debug("Found migration configuration: " + file.getName());
            result.add(file);
        }
    }

    private boolean existsAndIsFile(File file) {
        if (file != null) {
            if (file.exists()) {
                if (file.isFile()) {
                    return true;
                } else {
                    log.debug(String.format("Skipping directory: %s", file.getName()));
                }
            } else {
                log.debug(String.format("File does not exist: '%s'", file.getAbsoluteFile()));
            }
        } else {
            log.debug("Skipping 'null' file");
        }
        return false;
    }

    private boolean isConfigFile(File file) {
        String name = file.getName();
        if (CONFIGURATION_PATTERN.matcher(name).matches()) {
            return true;
        } else {
            log.debug(String.format("Skipping file: '%s'", name));
        }
        return false;
    }
}
