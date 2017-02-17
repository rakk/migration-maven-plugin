package org.rakk.migration.output;

public class Stats {
    private int importUpdateCount;

    public void incrementImportUpdateCount() {
        this.importUpdateCount++;
    }

    public String returnStats() {
        return "\nMigrations statistics:\n"
                + "\tUpdated imports in " + importUpdateCount + " files\n"
                + "\n";
    }
}
