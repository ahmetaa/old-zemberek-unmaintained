/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tk.yapi.ek;

import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.tk.yapi.TurkmenceSesliUretici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.TurkceHarf;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkOzelDurumu;

public class MeEkOzelDurumu extends EkOzelDurumu {

    private Alfabe alfabe;
    TurkmenceSesliUretici sesliUretici;

    public MeEkOzelDurumu(Alfabe alfabe, TurkmenceSesliUretici uretici) {
        this.alfabe = alfabe;
        this.sesliUretici = uretici;
    }

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        if (kiyaslayici == null) return null;
        // eki olustur.
        HarfDizisi ek = ekUretici.cozumlemeIcinEkUret(kelime.icerik(), giris, uretimBilesenleri);
        TurkceHarf ekHarfi = sesliUretici.sesliBelirleAA(kelime.icerik());
        HarfDizisi olusum = new HarfDizisi("m", alfabe);
        olusum.ekle(1, ekHarfi);

        int harfPozisyonu = kelime.boy() + ek.length();
        if (kiyaslayici.aradanKiyasla(giris, olusum, harfPozisyonu - 2))
            return ek;
        return null;
    }

    @Override
    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk) {
        if ((sonrakiEk.ad().equals(TurkmenceEkAdlari.IMEK_ZAMAN_KEN)) ||
                (sonrakiEk.ad().equals(TurkmenceEkAdlari.ISLIK_ISLEG_E)))
            return ekUretici.olusumIcinEkUret(kelime.icerik(), sonrakiEk, uretimBilesenleri);
        return null;
    }
}
