/*
 *  ***** BEGIN LICENSE BLOCK *****
 *  Version: MPL 1.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is Zemberek Doðal Dil Ýþleme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akýn, Mehmet D. Akýn.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  ***** END LICENSE BLOCK *****
 */

/*
 * Created on 15.Mar.2004
 */
package net.zemberek.istatistik;

import net.zemberek.islemler.Heceleyici;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.DilBilgisi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MDA
 */
public class Istatistikler {
	private Heceleyici heceleyici;
    private HeceIstatistikleri heceIstatistikleri = new HeceIstatistikleri();
    private KarakterIstatistikleri karakterIstatistikleri = new KarakterIstatistikleri();
    private KokIstatistikleri kokIstatistikleri = new KokIstatistikleri();
    private EkIstatistikleri ekIstatistikleri = new EkIstatistikleri();
    private KelimeIstatistikleri kelimeIstatistikleri = new KelimeIstatistikleri();
    private KelimeIstatistikleri ikiliHarfIstatistikleri = new KelimeIstatistikleri();
    private IkiliIstatistikleri ikiliIstatistikleri = new IkiliIstatistikleri();
    private IkiliIstatistikleri kokIkiliIstatistikleri = new IkiliIstatistikleri();
    private IkiliIstatistikleri heceIkiliIstatistikleri = new IkiliIstatistikleri();
    
    private List altIstatistikler = null;
	private int kelimeSayisi = 0;
	private int hatalar = 0;
	private int dogrular = 0;

	private int heceLimit = 0;
	private int kokLimit = 0;
	private int kelimeLimit = 0;

    public Istatistikler(DilBilgisi dil) {
        this.heceleyici = new Heceleyici(dil.alfabe(), dil.heceBulucu());
    }


    public void setLimit(int heceLimit, int kokLimit, int kelimeLimit){
		this.heceLimit = heceLimit;
		this.kokLimit = kokLimit;
		this.kelimeLimit = kelimeLimit;
	}
    
    public void kokIstatistikGuncelle(Kok kok, Kelime kelime) {
        kokIstatistikleri.sonucGuncelle(kok, kelime);
    }

    public void ekIstatistikleriGuncelle(Kelime kelime) {
        ekIstatistikleri.istatistikGuncelle(kelime);
    }
    
    public void kelimeIstatistikGuncelle(Kelime kelime){
    	kelimeIstatistikleri.isle(kelime.icerikStr());
    }
    
    public void ikiliIstatistikGuncelle(Kok kok, Kelime kelime){
        ikiliIstatistikleri.sonucGuncelle(kelime.icerikStr());
    }

    public void karakterIstatistikGuncelle(Kok kok) {
    }

    public void processChar(char ch) {
    }

    public List getAltIstatistikler() {
        return altIstatistikler;
    }

    public void addAltIstatistik(Istatistikler istatistik) {
        altIstatistikler.add(istatistik);
    }

    public void removeAltIstatistik(Istatistikler istatistik) {
        altIstatistikler.remove(istatistik);
    }

    public HeceIstatistikleri getHeceIstatistikleri() {
        return heceIstatistikleri;
    }

    public ArrayList getKokListesi() {
        return kokIstatistikleri.getKokListesi();
    }

    public long getKelimeSayisi() {
        return kokIstatistikleri.getToplamKelimeSayisi();
    }

    public void sonlandir() {
        kokIstatistikleri.tamamla();
        heceIstatistikleri.tamamla();
        ekIstatistikleri.tamamla();
        karakterIstatistikleri.tamamla();
        kelimeIstatistikleri.tamamla();
        ikiliIstatistikleri.tamamla();
        kokIkiliIstatistikleri.tamamla();
        ikiliHarfIstatistikleri.tamamla();
        heceIkiliIstatistikleri.tamamla();
    }

    public KokIstatistikleri getKokIstatistikleri() {
        return kokIstatistikleri;
    }

    public EkIstatistikleri getEkIstatistikleri() {
        return ekIstatistikleri;
    }

	public void hepsiniGuncelle(String giris, Kelime[] kelimeler) {
		if(kelimeler == null){
			hatalar ++;
			return;
		}
        if (kelimeler.length > 0) {
        	dogrular ++;
        	kelimeSayisi++;
        	char[] karakterler = giris.toCharArray();
        	for(int i=0; i<karakterler.length; i++){
        		karakterIstatistikleri.processChar(karakterler[i]);
        	}
            Kelime kelime = kelimeler[0];
            kokIstatistikleri.sonucGuncelle(kelime.kok(), kelime);
            ekIstatistikleri.istatistikGuncelle(kelime);
            String[] heceler = heceleyici.hecele(giris);
            for (int j = 0; j < heceler.length; j++) {
                heceIstatistikleri.guncelle(heceler[j]);
                heceIkiliIstatistikleri.sonucGuncelle(heceler[j]);
            }
            kelimeIstatistikleri.isle(giris);
            for(int i=0; i<giris.length()-2; i++){
            	ikiliHarfIstatistikleri.isle(giris.substring(i,i+2));
            }
            ikiliIstatistikleri.sonucGuncelle(kelime.icerikStr());
            kokIkiliIstatistikleri.sonucGuncelle(kelime.kok().icerik());
        }
	}

	public KarakterIstatistikleri getKarakterIstatistikleri() {
		return karakterIstatistikleri;
	}

	public KelimeIstatistikleri getKelimeIstatistikleri() {
		return kelimeIstatistikleri;
	}
	public int getHeceLimit() {
		return heceLimit;
	}
	public void setHeceLimit(int heceLimit) {
		this.heceLimit = heceLimit;
	}
	public int getKelimeLimit() {
		return kelimeLimit;
	}
	public void setKelimeLimit(int kelimeLimit) {
		this.kelimeLimit = kelimeLimit;
	}
	public int getKokLimit() {
		return kokLimit;
	}
	public void setKokLimit(int kokLimit) {
		this.kokLimit = kokLimit;
	}

    public IkiliIstatistikleri getIkiliIstatistikleri() {
        return ikiliIstatistikleri;
    }

    public IkiliIstatistikleri getKokIkiliIstatistikleri() {
        return kokIkiliIstatistikleri;
    }

	public KelimeIstatistikleri getIkiliHarfIstatistikleri() {
		return ikiliHarfIstatistikleri;
	}


	public IkiliIstatistikleri getHeceIkiliIstatistikleri() {
		return heceIkiliIstatistikleri;
	}
}
