/************************************************
 *                                              *
 *  Zemberek DBus Arayüzü                       *
 *  @author Serkan Kaba <serkan_kaba@yahoo.com> *
 *                                              *
 ************************************************/

package net.zemberekserver.dbus;


import java.util.List;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

public class ZemberekDbus implements IZemberek {
	
	private Zemberek zemberek;
	
	public ZemberekDbus(Zemberek zemberek) {
		if(zemberek == null)
			this.zemberek = new Zemberek(new TurkiyeTurkcesi());
		else
			this.zemberek = zemberek;
	}
	
	public boolean isRemote() {	return false; }
	
	public static void main(String args[]) {
		start(null);
	}
	
	public static void start(Zemberek zemberek) {
		try {
			DBusConnection conn = DBusConnection.getConnection(DBusConnection.SYSTEM);
			//DBusConnection conn = DBusConnection.getConnection(DBusConnection.SESSION);
			conn.requestBusName("net.zemberekserver.dbus");
			conn.exportObject("/net/zemberekserver/dbus/Zemberek",new ZemberekDbus(zemberek));
			System.out.println("Zemberek DBus arayüzü başlatıldı");
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
