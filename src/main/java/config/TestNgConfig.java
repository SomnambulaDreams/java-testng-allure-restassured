package config;

import java.io.InputStream;


public class TestNgConfig extends Config {

    private static final InputStream inputStream = TestNgConfig.class.getClassLoader().getResourceAsStream("testng.properties");

    private static TestNgConfig instance;


    private enum Key {

        SUITE                       ("suite"),
        THREAD_COUNT                ("thread.count"),
        DATA_PROVIDER_THREAD_COUNT  ("data.provider.thread.count"),
        MAX_RETRIES                 ("max.retries");

        public final String field;

        Key(String field) {
            this.field = field;
        }
    }


    private TestNgConfig() {
        loadProperties(inputStream);
    }


    public String suite() {
        return properties.getProperty(Key.SUITE.field);
    }

    public int threadCount() {
        return Integer.parseInt(properties.getProperty(Key.THREAD_COUNT.field));
    }

    public int dataProviderThreadCount() {
        return Integer.parseInt(properties.getProperty(Key.DATA_PROVIDER_THREAD_COUNT.field));
    }

    public int maxRetries() {
        return Integer.parseInt(properties.getProperty(Key.MAX_RETRIES.field));
    }



    public static synchronized TestNgConfig instance() {
        if(instance == null)
            instance = new TestNgConfig();
        return instance;
    }
}
