/************************************************
 *                                              *
 *  Zemberek DBus Arayüzü                       *
 *  @author Serkan Kaba <serkan_kaba@yahoo.com> *
 *                                              *
 ************************************************/

package net.zemberekserver.server.dbus;

import java.util.List;

import net.zemberek.erisim.Zemberek;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusException;

public class ZemberekDbus implements ZemberekDbusInterface {
	
	private static Zemberek zemberek;
	
	public ZemberekDbus() {
	}
	
	public boolean isRemote() {	return false; }
	
	public void start(Zemberek zemberek, String busName) {
		try {
			ZemberekDbus.zemberek = zemberek;
			//DBusConnection conn = DBusConnection.getConnection(DBusConnection.SYSTEM);
			DBusConnection conn = DBusConnection.getConnection(DBusConnection.SESSION);
			conn.requestBusName(busName);
			conn.exportObject("/net/zemberekserver/server/dbus/ZemberekDbus" , new ZemberekDbus());
			System.out.println("Zemberek DBus arayüzü başlatıldı. busName: " + busName);
		} catch (DBusException e) {
			e.printStackTrace();
			System.err.println("Zemberek DBus arayüzü başlatılırken hata");
		}
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

	public List<List<String>> kelimeAyristir(String giris) {
		return zemberek.kelimeAyristir(giris);
	}

	public boolean kelimeDenetle(String giris) {
		return zemberek.kelimeDenetle(giris);
	}

	public String[] oner(String giris) {
		return zemberek.oner(giris);
	}

	public String temizle(String giris) {
		return zemberek.temizle(giris);
	}
}
