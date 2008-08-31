/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.istatistik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class KelimeIstatistikleri implements Istatistik{
	private HashMap<String, KelimeBilgisi> kelimeler = new HashMap<String, KelimeBilgisi>();
	private List<KelimeBilgisi> kelimeListesi = new ArrayList<KelimeBilgisi>(100);
	private static int LIMIT = 1000000;
	
	
	public void isle(String giris){
		KelimeBilgisi kelime = kelimeler.get(giris);
		if(kelime == null){
			if(kelimeler.size() == LIMIT) return;
			kelimeler.put(giris, new KelimeBilgisi(giris));
		} else{
			kelime.miktar++;
		}
	}
	
	public void guncelle() {
	}

	public void tamamla() {
		for (Iterator<KelimeBilgisi> it = kelimeler.values().iterator(); it.hasNext();) {
			kelimeListesi.add(it.next());
		}
		Collections.sort(kelimeListesi);
	}
	
	public List<KelimeBilgisi> getKelimeListesi() {
		return kelimeListesi;
	}

}
