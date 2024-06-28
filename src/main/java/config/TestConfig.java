package config;

import java.io.InputStream;


public class TestConfig extends Config {

    private static final InputStream inputStream = TestConfig.class.getClassLoader().getResourceAsStream("test.properties");

    private static TestConfig instance;


    private enum Key {

        PLAYER_CREATE_FILEPATH  ("player.create.filepath"),
        PLAYER_GET_FILEPATH     ("player.get.filepath"),
        PLAYER_GET_ALL_FILEPATH ("player.get.all.filepath"),
        PLAYER_UPDATE_FILEPATH  ("player.update.filepath"),
        PLAYER_DELETE_FILEPATH  ("player.delete.filepath"),
        SHEET_ENDPOINT          ("sheet.endpoint"),
        SHEET_POSITIVE          ("sheet.positive"),
        SHEET_NEGATIVE          ("sheet.negative");

        public final String field;

        Key(String field) {
            this.field = field;
        }
    }


    private TestConfig() {
        loadProperties(inputStream);
    }


    public String playerCreateFilepath() {
        return properties.getProperty(Key.PLAYER_CREATE_FILEPATH.field);
    }

    public String playerGetFilepath() {
        return properties.getProperty(Key.PLAYER_GET_FILEPATH.field);
    }

    public String sheetEndpoint() {
        return properties.getProperty(Key.SHEET_ENDPOINT.field);
    }

    public String sheetPositive() {
        return properties.getProperty(Key.SHEET_POSITIVE.field);
    }

    public String sheetNegative() {
        return properties.getProperty(Key.SHEET_NEGATIVE.field);
    }


    public static synchronized TestConfig instance() {
        if(instance == null)
            instance = new TestConfig();
        return instance;
    }
}
