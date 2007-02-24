package net.zemberek.deney;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.DilAyarlari;

public class ZemberekDenetlemeAdayi {

	private Zemberek zemberek;

	public ZemberekDenetlemeAdayi(DilAyarlari dilAyarlari) {
		zemberek = new Zemberek(dilAyarlari);
	}
	
	/* net.zemberek.erisim.Zemberek için aday metod */
	public List<DenetlemeHatasi> metinDenetle(String text) {
		Hashtable<String, Integer> sonPozisyonlar = new Hashtable<String, Integer>();
		Vector<DenetlemeHatasi> ret = new Vector<DenetlemeHatasi>();
		StringTokenizer tok = new StringTokenizer(text, "!'#%&/()=?-_:.,;\"\r\n\t ");
		while (tok.hasMoreTokens()) {
			String kelime = tok.nextToken();
			int pos;
			if (sonPozisyonlar.containsKey(kelime))
				pos = text.indexOf(kelime, sonPozisyonlar.get(kelime) + kelime.length());
			else
				pos = text.indexOf(kelime);
			sonPozisyonlar.put(kelime,pos);
			if (!zemberek.kelimeDenetle(kelime))
				ret.add(new DenetlemeHatasi(pos, kelime, zemberek.oner(kelime)));
			sonPozisyonlar.put(kelime, pos);
		}
		return ret;
	}

	public static void main(String[] args) {
		ZemberekDenetlemeAdayi zemberek = new ZemberekDenetlemeAdayi(new TurkiyeTurkcesi());
		List<DenetlemeHatasi> hatalar = zemberek.metinDenetle("yalnış yanlış doğru yanlız yalnız htalı hatalı değil");
		for(DenetlemeHatasi hata: hatalar) {
			System.out.println("Hatalı kelime: "+hata.getHatalikelime());
			System.out.println("Offset: "+ hata.getHataPozisyonu());
			System.out.println("Öneriler: "+Arrays.toString(hata.getOneriler()));
			System.out.println("---------------------------");
		}
	}
}
