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

package net.zemberekserver.server.dbus;

import java.util.List;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberekserver.server.Config;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

public class ZemberekDbus implements ZemberekDbusInterface {
	
	private Zemberek zemberek;
	
	public ZemberekDbus(Zemberek zemberek) {
		this.zemberek=zemberek;
	}
	
	public boolean isRemote() {	return false; }
	
	public static void start(Zemberek zemberek, String busName) {
		try {
			DBusConnection conn;
			if (Config.useDbusSystemConnection){
				 conn = DBusConnection.getConnection(DBusConnection.SYSTEM);
			}
			else {
				conn = DBusConnection.getConnection(DBusConnection.SESSION);
			}			
			conn.requestBusName(busName);
			conn.exportObject("/net/zemberekserver/server/dbus/ZemberekDbus" , new ZemberekDbus(zemberek));
			System.out.println("Zemberek DBus arayüzü başlatıldı. busName: " + busName);
		} catch (DBusException e) {
			e.printStackTrace();
			System.err.println("Zemberek DBus arayüzü başlatılırken hata");
		}
	}
	
	public static void main(String[] args) {
		start(new Zemberek(new TurkiyeTurkcesi()),Config.busName);
	}

	public String[] asciidenTurkceye(String giris) {
		return zemberek.asciidenTurkceye(giris);
	}

	public String asciiyeDonustur(String giris) {
		return zemberek.asciiyeDonustur(giris);
	}

	public String[] hecele(String giris) {
		return zemberek.hecele(giris);
	}

	public List<String[]> kelimeAyristir(String giris) {
		return zemberek.kelimeAyristir(giris);
	}

	public boolean kelimeDenetle(String giris) {
		return zemberek.kelimeDenetle(giris);
	}

	public String[] oner(String giris) {
		return zemberek.oner(giris);
	}
}
