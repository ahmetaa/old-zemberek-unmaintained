package net.zemberek.deney;

import java.util.Hashtable;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tm.yapi.Turkmence;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

public abstract class ZemberekFactory {

	private static Hashtable<String, Zemberek> zemberekNesneleri;
	static {
		zemberekNesneleri = new Hashtable<String, Zemberek>();
	}

	public static Zemberek getZemberek(String dilKodu) {
		if (zemberekNesneleri.containsKey(dilKodu))
			return zemberekNesneleri.get(dilKodu);
		else {
			Zemberek z = _getZemberek(dilKodu);
			zemberekNesneleri.put(dilKodu, z);
			return z;
		}
	}

	private static Zemberek _getZemberek(String dilKodu) {
		if (dilKodu.equalsIgnoreCase("tr"))
			return new Zemberek(new TurkiyeTurkcesi());
		else if (dilKodu.equalsIgnoreCase("tm"))
			return new Zemberek(new Turkmence());
		else
			throw new IllegalArgumentException("Geçersiz dil kodu");
	}

}