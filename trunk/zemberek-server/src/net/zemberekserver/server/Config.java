package net.zemberekserver.server;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Sistemde ortak kullanılan nesnelerin referanslarını tutmak için kullanılır.
 * Genel konfigurasyon bilgileri de burada tutulur.
 */

public class Config {
    public static String confFile = "config/conf.ini";; 
    public static String logFile = null; 
    
    public static int serverPort = 10444;
    private static Properties properties = null;

    public static void readConfFile(String confFile) {
        FileInputStream fileStream;
        try {
            fileStream = new FileInputStream(confFile);
            System.out.println(confFile + " isimli konfigürasyon dosyasi okundu.");
            properties = new Properties();
            properties.load(fileStream);
            serverPort = getPropertyValue("SERVER_PORT", serverPort);
            fileStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getPropertyValue(String propertyName, String defaultValue) {
        String propertyValue = properties.getProperty(propertyName);
        return propertyValue != null ? propertyValue.trim() : defaultValue;
    }

    private static boolean getPropertyValue(String propertyName, boolean defaultValue) {
        String propertyValue = properties.getProperty(propertyName);
        return propertyValue != null ? propertyValue.trim().equalsIgnoreCase("YES") : defaultValue;
    }

    private static int getPropertyValue(String propertyName, int defaultValue) {
        int val;
        String propertyValue = properties.getProperty(propertyName);
        if (propertyValue == null || propertyValue.equals("")) {
            return defaultValue;
        }
        try {
            val = Integer.parseInt(propertyValue.trim());
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }
        return val;
    }
 } 
