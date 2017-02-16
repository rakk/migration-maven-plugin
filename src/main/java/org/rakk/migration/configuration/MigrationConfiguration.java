package org.rakk.migration.configuration;

import org.rakk.migration.configuration.imports.ImportUpdate;
import org.rakk.migration.configuration.imports.ImportUpdates;
import org.rakk.migration.configuration.sources.Source;
import org.rakk.migration.configuration.sources.Sources;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.Writer;
import java.util.List;

@XmlRootElement
public class MigrationConfiguration {

    private String name;
    private Sources sources = new Sources();
    private ImportUpdates importUpdates = new ImportUpdates();

    private String originalFileName;
    private File moduleEntryPoint;

    @XmlElement
    public void setSources(Sources sources) {
        this.sources = sources;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public void setImportUpdates(ImportUpdates importUpdates) {
        this.importUpdates = importUpdates;
    }

    public Sources getSources() {
        return sources;
    }

    public ImportUpdates getImportUpdates() {
        return importUpdates;
    }

    public List<Source> getSourcesList() {
        return sources.getSource();
    }

    public List<ImportUpdate> getImportUpdatesList() {
        return importUpdates.getImportUpdate();
    }

    public String getName() {
        return name;
    }


    public void setModuleEntryPoint(File moduleEntryPoint) {
        this.moduleEntryPoint = moduleEntryPoint;
    }

    public File getModuleEntryPoint() {
        return moduleEntryPoint;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public static MigrationConfiguration getFromFile(File file, File moduleEntryPoint) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(MigrationConfiguration.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        MigrationConfiguration result = (MigrationConfiguration) jaxbUnmarshaller.unmarshal(file);
        result.setOriginalFileName(file.getName());
        result.setModuleEntryPoint(moduleEntryPoint);
        return result;
    }

    public static void write(MigrationConfiguration migrationConfiguration, Writer writer)
            throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(MigrationConfiguration.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(migrationConfiguration, writer);
    }

    public boolean hasSources() {
        return sources != null && getSourcesList().size() > 0;
    }
}
