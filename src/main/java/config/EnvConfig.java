package config;

import java.io.InputStream;


public class EnvConfig extends Config {

    private static final InputStream inputStream = EnvConfig.class.getClassLoader().getResourceAsStream("env.properties");

    private static EnvConfig instance;


    private enum Key {

        BASE_URL    ("baseUrl"),
        HOST        ("host"),
        REFERER     ("referer");

        public final String field;

        Key(String field) {
            this.field = field;
        }
    }


    private EnvConfig() {
        loadProperties(inputStream);
    }


    public String baseUrl() {
        return properties.getProperty(Key.BASE_URL.field);
    }

    public String host() {
        return properties.getProperty(Key.HOST.field);
    }

    public String referer() {
        return properties.getProperty(Key.REFERER.field);
    }



    public static synchronized EnvConfig instance() {
        if(instance == null)
            instance = new EnvConfig();
        return instance;
    }
}
