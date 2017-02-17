package org.rakk.migration;

import org.apache.maven.plugin.logging.Log;
import org.rakk.migration.configuration.ConfigurationFiles;
import org.rakk.migration.configuration.MigrationConfiguration;
import org.rakk.migration.configuration.sources.Source;
import org.rakk.migration.output.Stats;
import org.rakk.migration.steps.UpdateImportsStep;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class MigrationExecutor {

    private MigrationConfiguration migrationConfiguration;
    private Log log;
    private Stats stats = new Stats();
    private UpdateImportsStep updateImportStep;


    public MigrationExecutor(MigrationConfiguration migrationConfiguration, Log log) {
        this.migrationConfiguration = migrationConfiguration;
        this.log = log;
        this.updateImportStep = new UpdateImportsStep(this.stats);
    }

    public void execute() {
        for (Source source : migrationConfiguration.getSourcesList()) {
            log.info(String.format("Running migration '%s' for source: %s", migrationConfiguration.getName(), source));
            Collection<File> files = ConfigurationFiles.findAllFiles(migrationConfiguration.getModuleEntryPoint(), source);
            execute(migrationConfiguration, source, files);
        }
        log.info(this.stats.returnStats());
    }

    private void execute(MigrationConfiguration migrationConfiguration, Source source, Collection<File> files) {
        for (File file : files) {
            try {
                updateImportStep.update(migrationConfiguration.getImportUpdates(), file);
            } catch (IOException e) {
                log.error("Cannot read file: " + file.getAbsolutePath());
                log.debug("Cannot read file because of: {}", e);
            }
        }
    }
}
