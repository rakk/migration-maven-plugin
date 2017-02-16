package org.rakk.migration.configuration.imports;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ImportUpdate {

    private String oldPath;
    private String newPath;

    public ImportUpdate(String oldPath, String newPath) {
        this.oldPath = oldPath;
        this.newPath = newPath;
    }

    public ImportUpdate() {
    }

    @XmlElement
    public void setOldPath(String oldPath) {
        this.oldPath = oldPath;
    }

    @XmlElement
    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }

    public String getOldPath() {
        return oldPath;
    }

    public String getNewPath() {
        return newPath;
    }
}
