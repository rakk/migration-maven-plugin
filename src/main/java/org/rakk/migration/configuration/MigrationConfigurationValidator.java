package org.rakk.migration.configuration;

import org.apache.maven.plugin.logging.Log;
import org.rakk.migration.configuration.sources.Source;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MigrationConfigurationValidator {

    private Log log;

    public MigrationConfigurationValidator(Log log) {
        this.log = log;
    }

    public boolean isValid(MigrationConfiguration migrationConfiguration) {
        return isNameValid(migrationConfiguration) && areValidSources(migrationConfiguration);
    }

    private boolean isNameValid(MigrationConfiguration migrationConfiguration) {
        boolean result = migrationConfiguration.getName() != null
                && !migrationConfiguration.getName().trim().equals("");
        if (!result) {
            log.error("Name should not be empty in file:" + migrationConfiguration.getOriginalFileName());
        }
        return result;
    }

    private boolean areValidSources(MigrationConfiguration migrationConfiguration) {
        boolean result = migrationConfiguration.hasSources()
                && areValidSources(migrationConfiguration.getSourcesList(), migrationConfiguration);
        if (!result) {
            log.error("Missing sources configuration in migration: " + migrationConfiguration.getName());
        }
        return result;
    }

    private boolean areValidSources(List<Source> sourceList, MigrationConfiguration migrationConfiguration) {
        boolean result = true;
        for (int i = 0; result && i < sourceList.size(); i++) {
            Source source = sourceList.get(i);
            if (!source.isValid()) {
                log.error("Invalid source configuration " + source + " in migration " + migrationConfiguration.getName());
                result = false;
            }
        }
        return result;
    }

}
