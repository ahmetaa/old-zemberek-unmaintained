package net.zemberek.istatistik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class KelimeIstatistikleri implements Istatistik{
	private HashMap kelimeler = new HashMap();
	private ArrayList kelimeListesi = new ArrayList(100);
	private static int LIMIT = 100000;
	
	
	public void isle(String giris){
		KelimeBilgisi kelime = (KelimeBilgisi)kelimeler.get(giris);
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
		for (Iterator it = kelimeler.values().iterator(); it.hasNext();) {
			kelimeListesi.add(it.next());
		}
		Collections.sort(kelimeListesi);
	}
	
	public ArrayList getKelimeListesi() {
		return kelimeListesi;
	}

}
