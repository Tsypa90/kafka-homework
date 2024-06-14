package org.homework.util;

import lombok.extern.slf4j.Slf4j;
import org.homework.Producer;

import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertiesHelper {
    public static final String TOPIC_ONE = "topic1";
    public static final String TOPIC_TWO = "topic2";

    public static Properties getProperties() {
        Properties props = null;
        try (InputStream input = Producer.class.getClassLoader().getResourceAsStream("application.properties")) {

            props = new Properties();

            if (input == null) {
                throw new Exception("Sorry, unable to find config.properties");
            }

            props.load(input);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }

        return props;
    }
}
