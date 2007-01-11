/************************************************
 *                                              *
 *  Zemberek DBus Arayüzü                       *
 *  @author Serkan Kaba <serkan_kaba@yahoo.com> *
 *                                              *
 ************************************************/

package net.zemberekserver.client;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusException;

import net.zemberekserver.server.dbus.ZemberekDbusInterface;

import java.util.List;
public class DBusClient {

	public static void main(String[] args) throws DBusException {
		DBusConnection conn = DBusConnection.getConnection(DBusConnection.SYSTEM);
		//DBusConnection conn = DBusConnection.getConnection(DBusConnection.SESSION);
		ZemberekDbusInterface zemberek = (ZemberekDbusInterface) conn.getRemoteObject("net.zemberekserver.server.dbus","/net/zemberekserver/server/dbus/ZemberekDbus",ZemberekDbusInterface.class);
		long start = System.currentTimeMillis();
		for(int i=0; i<10000; i++){
			if(i % 100 == 0) System.out.print(".");
			zemberek.kelimeDenetle("doğru");
		}
		System.out.println("Sure: " + (System.currentTimeMillis() - start));
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
