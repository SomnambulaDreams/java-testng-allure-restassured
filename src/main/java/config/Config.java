package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Config {

    protected Properties properties = new Properties();


    protected void loadProperties(InputStream inputStream) {
        try {
            this.properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
