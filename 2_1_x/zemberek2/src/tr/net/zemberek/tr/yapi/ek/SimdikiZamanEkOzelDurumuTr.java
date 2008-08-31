/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tr.yapi.ek;

import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.tr.yapi.TurkceSesliUretici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.TurkceHarf;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkOzelDurumu;

/**
 * Ekler icin simdiki zaman ozel durumu. Simdiki zaman eki sesli ile biten bir koke (ya da eke)
 * eklendiginde son sesliyi dusurur.
 * <p/>
 * User: ahmet
 * Date: Aug 15, 2005
 */
public class SimdikiZamanEkOzelDurumuTr extends EkOzelDurumu {

    private final Alfabe alfabe;
    private TurkceSesliUretici sesliUretci;

    public SimdikiZamanEkOzelDurumuTr(Alfabe alfabe) {
        this.alfabe = alfabe;
        sesliUretci = new TurkceSesliUretici(alfabe);
    }

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        if (kiyaslayici == null) return null;
        // eki olustur.
        HarfDizisi ek = ekUretici.cozumlemeIcinEkUret(kelime.icerik(), giris, uretimBilesenleri);
        TurkceHarf ekHarfi = sesliUretci.sesliBelirleIU(kelime.icerik());
        HarfDizisi olusum = new HarfDizisi("yor", alfabe);
        olusum.ekle(0, ekHarfi);
        int harfPozisyonu = kelime.boy() + ek.length();
        if (kiyaslayici.aradanKiyasla(giris, olusum, harfPozisyonu))
            return ek;
        return null;
    }

    @Override
    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk){
        if(sonrakiEk.ad().equals(TurkceEkAdlari.FIIL_SIMDIKIZAMAN_IYOR))
          return ekUretici.olusumIcinEkUret(kelime.icerik(),sonrakiEk, uretimBilesenleri);
        return null;
    }

}
