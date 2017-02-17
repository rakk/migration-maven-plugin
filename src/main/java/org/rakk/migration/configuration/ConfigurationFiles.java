package org.rakk.migration.configuration;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.rakk.migration.configuration.sources.Source;

import java.io.File;
import java.util.Collection;

public class ConfigurationFiles {
    public static Collection<File> findAllFiles(File moduleEntryPoint, Source source) {
        return FileUtils.listFiles(moduleEntryPoint, new FileFilter(source), new DirFilter());
    }

    private static class FileFilter implements IOFileFilter {

        private Source source;

        FileFilter(Source source) {
            this.source = source;
        }

        public boolean accept(File file) {
            return file != null && source.matches(file.getName());
        }

        public boolean accept(File file, String s) {
            return accept(file);
        }
    }

    private static class DirFilter implements IOFileFilter {

        public boolean accept(File file) {
            return true;
        }

        public boolean accept(File file, String s) {
            return true;
        }
    }
}
