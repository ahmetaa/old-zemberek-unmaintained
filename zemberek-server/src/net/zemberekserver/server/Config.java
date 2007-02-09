/*
 *  ***** BEGIN LICENSE BLOCK *****
 *
 *  Version: MPL 1.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is "Zemberek Sunucu"
 *
 *  The Initial Developer of the Original Code is
 *  Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *   Serkan Kaba
 *
 *  ***** END LICENSE BLOCK *****
 */

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
	public static boolean useDbusSystemConnection;


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
			allowRemote = getPropertyValue("ALLOW_REMOTE", Defaults.ALLOW_REMOTE);
			useDbusSystemConnection = getPropertyValue("USE_DBUS_SYSTEM_CONNECTION", Defaults.USE_DBUS_SYSTEM_CONNECTION);
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
