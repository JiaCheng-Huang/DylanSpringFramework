package org.dylan.springframework.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Jiacheng Huang
 * @date 2019/03/04 00:17
 */
public class PropertiesLoader implements Loader {

    private String name;

    public PropertiesLoader(String name) {
        this.name = name;
    }

    public Properties load() {
        Properties properties = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream(name);
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
}
