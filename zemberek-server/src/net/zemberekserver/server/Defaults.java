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

public class Defaults {
	public static final String CONFIG_FILE="config/conf.ini";
	public static final int PORT_NUMBER = 10444;
	public static final String HOST_ADDRESS = "127.0.0.1";
	public static final long DEFAULT_TIMEOUT = 3000; //ms
	public static final String BUS_NAME = "net.zemberekserver.server.dbus";
	public static final boolean USE_SOCKETS = true;
	public static final boolean USE_DBUS = true;
	public static final boolean ALLOW_REMOTE = false;
	public static final boolean USE_DBUS_SYSTEM_CONNECTION = true;
}
