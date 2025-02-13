package com.mycommerce.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * reads the properties file configuration.properties
 */
public class ConfigReader {

    private static Properties properties;

    static {

        try {
            properties = new Properties();
            FileInputStream input = new FileInputStream("configuration.properties");
            properties.load(input);
            input.close();
        }  catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    public static String get(String keyName) {
        if (properties == null) {
            throw new IllegalStateException("Properties file is not loaded properly.");
        }
        return properties.getProperty(keyName);
    }

    public static void set(String keyName, String value) {
        properties.setProperty(keyName, value);
    }

}