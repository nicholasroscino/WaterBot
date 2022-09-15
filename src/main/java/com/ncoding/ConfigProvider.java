package com.ncoding;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigProvider {
    public static AppConfig provideConfig(Environment e) throws IOException {
        var filename = "src/main/resources/" + e.toString() + ".properties";

        BotConfig botConfig = new BotConfig();
        DatabaseConfig databaseConfig = new DatabaseConfig();

        FileReader reader = new FileReader(filename);

        Properties p = new Properties();
        p.load(reader);

        botConfig.setToken(p.getProperty("usertoken"));
        botConfig.setBotName(p.getProperty("botname"));
        databaseConfig.setDbUserName(p.getProperty("db_username"));
        databaseConfig.setDbPassword(p.getProperty("db_password"));
        databaseConfig.setDbEndpoint(p.getProperty("db_endpoint"));

        return new AppConfig(botConfig, databaseConfig);
    }
}
