package org.rakk.migration;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.rakk.migration.configuration.MigrationConfiguration;
import org.rakk.migration.configuration.MigrationConfigurationProvider;
import org.rakk.migration.configuration.MigrationConfigurationValidator;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "migrate", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class Migration extends AbstractMojo {


    @Parameter(defaultValue = "${session.executionRootDirectory}", property = "migrationConfigurationLocation",
            required = true)
    private File migrationConfigurationLocation;

    @Parameter(defaultValue = "${project.basedir}", property = "moduleEntryPoint", required = true)
    private File moduleEntryPoint;

    private MigrationConfigurationProvider configProvider;

    private MigrationConfigurationValidator configValidator;

    public Migration() {
        configProvider = new MigrationConfigurationProvider(getLog());
        configValidator = new MigrationConfigurationValidator(getLog());
    }

    public void execute() throws MojoExecutionException {
        stopIfNotExists(migrationConfigurationLocation);

        getLog().info(String.format("Searching for migration configuration in '%s'",
                migrationConfigurationLocation.getAbsoluteFile()));

        List<File> configurationFiles = configProvider.getConfigurationFiles(migrationConfigurationLocation);

        List<MigrationConfiguration> configs = getConfigurations(configurationFiles);
        stopIfNotValid(configs);

        for (MigrationConfiguration config : configs) {
            getLog().info("execute migration executor");
            new MigrationExecutor(config, getLog()).execute();
        }
    }

    private void stopIfNotValid(List<MigrationConfiguration> configs) {
        for (MigrationConfiguration config : configs) {
            if (!configValidator.isValid(config)){
                System.exit(1);
            }
        }
    }

    private List<MigrationConfiguration> getConfigurations(List<File> configurationFiles) {
        List<MigrationConfiguration> result = new ArrayList<MigrationConfiguration>();
        for (File file : configurationFiles) {
            MigrationConfiguration migrationConfiguration = null;
            try {
                migrationConfiguration = MigrationConfiguration.getFromFile(file, moduleEntryPoint);
                result.add(migrationConfiguration);
            } catch (JAXBException e) {
                getLog().error(String.format("Invalid configuration in file: '%s'", file.getName()));
                getLog().error("Check stacktrace for more details", e);
                System.exit(1);
            }
        }
        return result;
    }

    private void stopIfNotExists(File migrationConfigurationLocation) {
        String configurationPath = null;
        if (migrationConfigurationLocation == null || !migrationConfigurationLocation.exists()) {
            if (migrationConfigurationLocation != null) {
                configurationPath = migrationConfigurationLocation.getAbsolutePath();
            }
            getLog().error(String.format("Configuration location '%s' does not exist.",
                    configurationPath));
            System.exit(1);
        }
    }
}
