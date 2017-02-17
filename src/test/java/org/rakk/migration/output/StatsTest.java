package org.rakk.migration.output;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;


public class StatsTest {

    @Test
    public void shouldReturnCorrectStats() throws IOException {
        Stats stats = new Stats();

        for(int i = 0; i < 10; i++) {
            stats.incrementImportUpdateCount();
        }

        InputStream expectedConfig = getClass().getResourceAsStream("/stats/exportedStats.expected.txt");
        assertEquals(IOUtils.toString(expectedConfig, Charset.defaultCharset()), stats.returnStats());
    }

}
