package com.ncoding;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DatabaseConfig {
    private String dbUserName;
    private String dbPassword;
    private String dbEndpoint;
}
