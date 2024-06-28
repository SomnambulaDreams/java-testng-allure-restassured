package config;

import java.io.InputStream;


public class SuiteConfig extends Config {

    private static final InputStream inputStream = SuiteConfig.class.getClassLoader().getResourceAsStream("suite.properties");

    private static SuiteConfig instance;


    private enum Key {

        SUITE       ("suite"),
        MAX_RETRIES ("max.retries");

        public final String field;

        Key(String field) {
            this.field = field;
        }
    }


    private SuiteConfig() {
        loadProperties(inputStream);
    }


    public String suite() {
        return properties.getProperty(Key.SUITE.field);
    }

    public int maxRetries() {
        return Integer.parseInt(properties.getProperty(Key.MAX_RETRIES.field));
    }



    public static synchronized SuiteConfig instance() {
        if(instance == null)
            instance = new SuiteConfig();
        return instance;
    }
}
