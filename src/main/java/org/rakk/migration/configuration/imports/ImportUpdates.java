package org.rakk.migration.configuration.imports;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ImportUpdates {

    private List<ImportUpdate> importUpdate;

    public ImportUpdates(List<ImportUpdate> importUpdate) {
        this.importUpdate = importUpdate;
    }

    public ImportUpdates() {
    }

    @XmlElement
    public void setImportUpdate(List<ImportUpdate> importUpdate) {
        this.importUpdate = importUpdate;
    }

    public List<ImportUpdate> getImportUpdate() {
        return importUpdate;
    }
}
