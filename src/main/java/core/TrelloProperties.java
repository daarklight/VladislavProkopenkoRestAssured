package core;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TrelloProperties {
    public static final Properties TESTDATA_PROPERTIES = new Properties();
    public static final Properties CREDENTIAL_PROPERTIES = new Properties();

    {
        final ClassLoader loader = getClass().getClassLoader();
        try (InputStream testData = loader.getResourceAsStream("testdata.properties")) {
            TESTDATA_PROPERTIES.load(testData);
        } catch (IOException e) {
            throw new IOError(e);
        }
        try (InputStream testData = loader.getResourceAsStream("credentials.properties")) {
            CREDENTIAL_PROPERTIES.load(testData);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }
}
