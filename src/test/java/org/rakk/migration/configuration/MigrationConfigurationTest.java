package org.rakk.migration.configuration;

import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.rakk.migration.configuration.imports.ImportUpdate;
import org.rakk.migration.configuration.imports.ImportUpdates;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;


public class MigrationConfigurationTest {

    @Test
    public void shouldWriteCorrectXml() throws JAXBException, IOException {
        StringWriter stringWriter = new StringWriter();
        MigrationConfiguration exampleConfiguration = new MigrationConfiguration();
        ImportUpdates importUpdates = new ImportUpdates();
        importUpdates.setImportUpdate(Arrays.asList(
                new ImportUpdate("old.path.ClassName", "new.path.ClassName"),
                new ImportUpdate("old.sql.path.OldSqlName", "new.sql.NewSQl")
        ));
        exampleConfiguration.setImportUpdates(importUpdates);

        MigrationConfiguration.write(exampleConfiguration, stringWriter);
        String output = stringWriter.toString();
        InputStream expectedConfig = getClass().getResourceAsStream("/configuration/expected.config.xml");
        assertEquals(IOUtils.toString(expectedConfig, Charset.defaultCharset()), output);
    }

}
