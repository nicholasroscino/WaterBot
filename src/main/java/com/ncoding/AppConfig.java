package com.ncoding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AppConfig {
    private BotConfig botConfig;
    private DatabaseConfig databaseConfig;
}
