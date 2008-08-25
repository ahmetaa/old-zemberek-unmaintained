package net.zemberekserver.client;
import java.util.List;

import org.freedesktop.dbus.DBusInterface;
public interface ZemberekDbusInterface extends DBusInterface
{

  public List<String> asciidenTurkceye(String giris);
  public String asciiyeDonustur(String giris);
  public List<String> hecele(String giris);
  public List<List<String>> kelimeAyristir(String giris);
  public boolean kelimeDenetle(String giris);
  public List<String> oner(String giris);

}
