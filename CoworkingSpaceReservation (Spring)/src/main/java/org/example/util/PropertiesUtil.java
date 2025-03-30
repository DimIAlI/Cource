package org.example.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class PropertiesUtil {
    private final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public String getValue(String key) {
        return PROPERTIES.getProperty(key);
    }

    @SneakyThrows
    private void loadProperties() {
        try (InputStream resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(resourceAsStream);
        }
    }
}
