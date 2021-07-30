package core;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TrelloProperties {
    protected static Properties properties = new Properties();

    {
        final ClassLoader loader = getClass().getClassLoader();
        try (InputStream testData = loader.getResourceAsStream("testdata.properties")) {
            properties.load(testData);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }
}
