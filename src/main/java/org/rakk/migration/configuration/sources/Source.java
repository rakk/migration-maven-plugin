package org.rakk.migration.configuration.sources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@XmlRootElement
public class Source {

    private String path;
    private String filePattern;
    private Pattern pattern;

    public Source(String path, String filePattern) {
        this.path = path;
        setFilePattern(filePattern);
    }

    public Source() {
    }

    public String getPath() {
        return path;
    }

    public String getFilePattern() {
        return filePattern;
    }

    @XmlElement
    public void setPath(String path) {
        this.path = path;
    }

    @XmlElement
    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    @Override
    public String toString() {
        return "[path:" + path + ", filePattern: " + filePattern + "]";
    }

    public boolean matches(String name) {
        return getPattern().matcher(name).matches();
    }

    public Pattern getPattern() {
        if (this.pattern == null ) {
            this.pattern = Pattern.compile(this.filePattern);
        }
        return this.pattern;
    }

    public boolean isValid() {
        return path != null && !path.trim().equals("")
                && filePattern != null && isValidPattern();
    }

    private boolean isValidPattern() {
        try {
            getPattern();
            return true;
        } catch(PatternSyntaxException ignore) {
        }
        return false;
    }

}
