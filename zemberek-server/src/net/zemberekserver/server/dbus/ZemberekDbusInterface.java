/************************************************
 *                                              *
 *  Zemberek DBus Arayüzü                       *
 *  @author Serkan Kaba <serkan_kaba@yahoo.com> *
 *                                              *
 ************************************************/

package net.zemberekserver.server.dbus;

import java.util.List;
import org.freedesktop.dbus.DBusInterface;

public interface ZemberekDbusInterface extends DBusInterface {
	
	//public Kelime[] asciiCozumle(String giris);
	public String[] asciidenTurkceye(String giris);
	public String asciiyeDonustur(String giris);
	public String[] hecele(String giris);
	public List<List<String>> kelimeAyristir(String giris);
	//public Kelime[] kelimeCozumle(String giris);
	public boolean kelimeDenetle(String giris);
	public String[] oner(String giris);
	public String temizle(String giris);
	//public String kelimeUret(Kok giris, List ekler);
}
