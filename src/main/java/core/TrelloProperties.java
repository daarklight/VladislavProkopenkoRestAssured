package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class TrelloProperties {

    public static final Properties TEST_DATA_PROPERTIES = getProperties("testdata.properties");
    public static final Properties CREDENTIAL_PROPERTIES = getProperties("credentials.properties");

    public static Properties getProperties(String filename) {
        Properties properties = new Properties();
        try {
            File filePath = new File("src/main/resources/", filename);
            FileInputStream fileInputStream = new FileInputStream(filePath);
            properties.load(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IOError(e);
        }
        return properties;
    }
}
