/************************************************
 *                                              *
 *  Zemberek DBus Arayüzü                       *
 *  @author Serkan Kaba <serkan_kaba@yahoo.com> *
 *                                              *
 ************************************************/

package net.zemberekserver.client;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusException;

import net.zemberekserver.dbus.IZemberek;

import java.util.List;
public class TestDBus {

	public static void main(String[] args) throws DBusException {
		DBusConnection conn = DBusConnection.getConnection(DBusConnection.SYSTEM);
		//DBusConnection conn = DBusConnection.getConnection(DBusConnection.SESSION);
		IZemberek zemberek = (IZemberek) conn.getRemoteObject("net.zemberekserver.dbus","/net/zemberekserver/dbus/Zemberek",IZemberek.class);
		System.out.println(zemberek.kelimeDenetle("doğru"));
		System.out.println(zemberek.kelimeDenetle("yalnış"));
		String[] oneriler=zemberek.oner("yalnış");
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
