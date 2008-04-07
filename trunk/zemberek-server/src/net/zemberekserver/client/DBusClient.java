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
 *  Serkan Kaba.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *   Mehmet D. Akin
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberekserver.client;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import net.zemberekserver.server.Config;

import java.util.List;
public class DBusClient {

	public static void main(String[] args) throws DBusException {
		DBusConnection conn;
		if (Config.useDbusSystemConnection){
			 conn = DBusConnection.getConnection(DBusConnection.SYSTEM);
		}
		else {
			conn = DBusConnection.getConnection(DBusConnection.SESSION);
		}		
		ZemberekDbusInterface zemberek = (ZemberekDbusInterface) conn.getRemoteObject("net.zemberekserver.server.dbus","/net/zemberekserver/server/dbus/ZemberekDbus",ZemberekDbusInterface.class);
		long start = System.currentTimeMillis();
		for(int i=0; i<10000; i++){
			if(i % 100 == 0) System.out.print(".");
			zemberek.kelimeDenetle("doğru");
		}
		System.out.println("Sure: " + (System.currentTimeMillis() - start));
		System.out.println(zemberek.kelimeDenetle("doğru"));
		System.out.println(zemberek.kelimeDenetle("yalnış"));
		List<String> oneriler=zemberek.oner("yalnış");
		for ( String oneri: oneriler ) {
			System.out.println(oneri);
		}
		
		List<List<String>> ayristirmalar = zemberek.kelimeAyristir("alayım");
		for(List<String> ayristirma: ayristirmalar) {
			for(String parca: ayristirma) {
				System.out.print(parca+" ");
			}
			System.out.println();
		}
	}

}
