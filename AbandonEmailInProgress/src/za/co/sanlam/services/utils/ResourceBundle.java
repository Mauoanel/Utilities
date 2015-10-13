package za.co.sanlam.services.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ResourceBundle {

    static Logger logger = Logger.getLogger(ResourceBundle.class);

    private static final Properties prop = new Properties();

    static {

        try {
            prop.load(new FileInputStream("../config/config.properties"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getProperty(String key) {

        return prop.getProperty(key);
    }

    public static void main(String s[]) {
        System.out.println(prop);
    }
}
