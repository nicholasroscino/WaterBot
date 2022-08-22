package com.ncoding;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigProvider {
    public static BotConfig provideConfig(Environment e) throws IOException {
        var filename = "src/main/resources/" + e.toString() + ".properties";

        BotConfig botConfig = new BotConfig();
        FileReader reader = new FileReader(filename);

        Properties p = new Properties();
        p.load(reader);

        botConfig.setToken(p.getProperty("usertoken"));
        botConfig.setBotName(p.getProperty("botname"));

        return botConfig;
    }
}
