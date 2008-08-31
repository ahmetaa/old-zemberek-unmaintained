/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tk.yapi.ek;

import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkOzelDurumu;

public class KiEkOzelDurumu extends EkOzelDurumu {

    private final HarfDizisi KI;

    public KiEkOzelDurumu(Alfabe alfabe) {
        KI = new HarfDizisi("ki", alfabe);
    }

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        if (kiyaslayici == null) return null;
        // eki olustur.
        HarfDizisi ek = ekUretici.cozumlemeIcinEkUret(kelime.icerik(), giris, uretimBilesenleri);
        //olusum.ekle(0, ekHarfi);
        int harfPozisyonu = kelime.boy() + ek.length();
        if (kiyaslayici.aradanKiyasla(giris, KI, harfPozisyonu))
            return ek;
        return null;
    }

    @Override
    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk) {
        if (sonrakiEk.ad().equals(TurkmenceEkAdlari.AT_YERGORKEZYAN_KI))
            return ekUretici.olusumIcinEkUret(kelime.icerik(), sonrakiEk, uretimBilesenleri);
        return null;
    }
}
