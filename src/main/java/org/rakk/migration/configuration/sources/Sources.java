package org.rakk.migration.configuration.sources;

import org.apache.maven.plugin.logging.Log;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Sources {

    private List<Source> source = getDefault();

    @XmlElement
    public void setSource(List<Source> source) {
        this.source = source;
    }

    public List<Source> getSource() {
        return source;
    }

    private static List<Source> getDefault() {
        List<Source> result = new ArrayList<Source>();
        result.add(new Source("src/test/java", ".*\\.java"));
        result.add(new Source("src/test/groovy", ".*\\.groovy"));
        return result;
    }

}
