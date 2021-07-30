package core;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TrelloProperties {
    protected static Properties testdataProperties = new Properties();
    protected static Properties credentialProperties = new Properties();

    {
        final ClassLoader loader = getClass().getClassLoader();
        try (InputStream testData = loader.getResourceAsStream("testdata.properties")) {
            testdataProperties.load(testData);
        } catch (IOException e) {
            throw new IOError(e);
        }
        try (InputStream testData = loader.getResourceAsStream("credentials.properties")) {
            credentialProperties.load(testData);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }
}
