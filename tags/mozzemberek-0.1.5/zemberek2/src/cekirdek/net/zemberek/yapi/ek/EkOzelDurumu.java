/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;

/**
 * Ek ozel durumu ek'e benzer bir yapiya sahiptir. Farkli olarak bazi ozel durumlarda yer alan
 * onek listesi de bu sinifin bir parametresidir.
 * User: ahmet
 * Date: Aug 24, 2005
 */
public abstract class EkOzelDurumu {

    protected String ad;
    protected Set<Ek> onEkler= Collections.emptySet();
    protected EkUretici ekUretici;
    protected List<EkUretimBileseni> uretimBilesenleri;

    public abstract HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici);

    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk)
    {
        return ekUretici.olusumIcinEkUret(kelime.icerik(), sonrakiEk, uretimBilesenleri);
    }

    public String ad() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public Set<Ek> getOnEkler() {
        return onEkler;
    }

    public void setOnEkler(Set<Ek> onEkler) {
        this.onEkler = onEkler;
    }

    public void setEkKuralCozumleyici(EkUretici ekUretici) {
        this.ekUretici = ekUretici;
    }

    public void setUretimBilesenleri(List<EkUretimBileseni> uretimBilesenleri) {
        this.uretimBilesenleri = uretimBilesenleri;
    }

    public List<EkUretimBileseni> uretimBilesenleri() {
        return uretimBilesenleri;
    }
}
