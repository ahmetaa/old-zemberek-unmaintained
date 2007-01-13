package net.zemberekserver.server;

import java.io.FileInputStream;
import java.util.Properties;


/**
 * 
 * Genel konfigurasyon bilgileri de burada tutulur.
 */

public class Config {
    public static int serverPort;
    public static boolean useDbus;
    public static boolean useSockets;
    public static boolean allowRemote;
    public static String busName;
    
    private static Properties properties = null;

    static {
        try {
            properties = new Properties();
            String confFile = System.getProperty("ConfigFile");
            if(confFile== null)
            	confFile=Defaults.CONFIG_FILE;
            properties.load(new FileInputStream(confFile));
            serverPort = getPropertyValue("PORT_NUMBER", Defaults.PORT_NUMBER);
            useDbus = getPropertyValue("USE_DBUS", Defaults.USE_DBUS);
            useSockets = getPropertyValue("USE_SOCKETS", Defaults.USE_SOCKETS);
            busName = getPropertyValue("BUS_NAME", Defaults.BUS_NAME);
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
