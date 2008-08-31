/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.kok;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import net.zemberek.araclar.Kayitci;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkYonetici;

/**
 * User: ahmet
 * Date: Aug 29, 2006
 */
public class TemelKokOzelDurumBilgisi {

    protected static Logger logger = Kayitci.kayitciUret(TemelKokOzelDurumBilgisi.class);

    protected EkYonetici ekYonetici;
    protected Alfabe alfabe;
    protected Map<KokOzelDurumTipi, KokOzelDurumu> ozelDurumlar = new HashMap<KokOzelDurumTipi, KokOzelDurumu>();
    protected Map<String, KokOzelDurumu> kisaAdOzelDurumlar = new HashMap<String, KokOzelDurumu>();

    public static final int MAX_OZEL_DURUM_SAYISI = 30;
    protected KokOzelDurumu[] ozelDurumDizisi = new KokOzelDurumu[MAX_OZEL_DURUM_SAYISI];

    public TemelKokOzelDurumBilgisi(EkYonetici ekYonetici, Alfabe alfabe) {
        this.ekYonetici = ekYonetici;
        this.alfabe = alfabe;
    }

    public KokOzelDurumu ozelDurum(int indeks) {
        if (indeks < 0 || indeks >= ozelDurumDizisi.length)
            throw new IndexOutOfBoundsException("istenilen indeksli ozel durum mevcut degil:" + indeks);
        return ozelDurumDizisi[indeks];
    }

    public KokOzelDurumu kisaAdIleOzelDurum(String ozelDurumKisaAdi) {
        return kisaAdOzelDurumlar.get(ozelDurumKisaAdi);
    }


    protected KokOzelDurumu.Uretici uretici(KokOzelDurumTipi tip, HarfDizisiIslemi islem) {

        // bir adet kok ozel durumu uretici olustur.
        KokOzelDurumu.Uretici uretici = new KokOzelDurumu.Uretici(tip, islem);

        // eger varsa kok adlarini kullanarak iliskili ekleri bul ve bir Set'e ata.
        String[] ekAdlari = tip.ekAdlari();
        if (ekAdlari.length > 0) {
            Set<Ek> set = new HashSet<Ek>(ekAdlari.length);
            for (String s : ekAdlari) {
                Ek ek = ekYonetici.ek(s);
                if (ek != null) {
                    set.add(ek);
                } else {
                    logger.warning(s + " eki bulunamadigindan kok ozel durumuna eklenemedi!");
                }
            }
            // ureticiye seti ata.
            uretici.gelebilecekEkler(set);
        }
        return uretici;
    }

    protected void ekle(KokOzelDurumu.Uretici uretici) {
        //tum
        KokOzelDurumu ozelDurum = uretici.uret();
        ozelDurumlar.put(ozelDurum.tip(), ozelDurum);
        ozelDurumDizisi[ozelDurum.indeks()] = ozelDurum;
        kisaAdOzelDurumlar.put(ozelDurum.kisaAd(), ozelDurum);
    }

    protected void bosOzelDurumEkle(KokOzelDurumTipi... tipler) {
        for (KokOzelDurumTipi tip : tipler) {
            ekle(uretici(tip,new BosHarfDizisiIslemi()));
        }
    }

    public KokOzelDurumu ozelDurum(String kisaAd) {
        return kisaAdOzelDurumlar.get(kisaAd);
    }

    public KokOzelDurumu ozelDurum(KokOzelDurumTipi tip) {
        return ozelDurumlar.get(tip);
    }
}
