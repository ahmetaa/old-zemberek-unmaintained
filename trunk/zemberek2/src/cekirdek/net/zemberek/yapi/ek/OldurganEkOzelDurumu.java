/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.TurkceHarf;

/**
 * Bu ozel durum oldurganlik eki "t"'nin sadece bazi durumlarda olusmasini saglar.
 * bu sartlar,
 * - kok son harfi sesli ise uret. (aratmak, yatirtmak gibi.)
 * - kok son harf sessiz ise, ama r ya da l ise ve gece sayisi birden fazla ise uret.
 * (kopar-t-mak, kucul-t-mek, oturtmak,  )
 * TODO: bazi koklere yukaridaki sartlar saglansa bile oldurganlik eki eklenemez
 * Ornegin, geltmek,  olmaz. Bu durum kok ozel durumu ile saglanabilir.
 * User: ahmet
 * Date: Aug 29, 2005
 */
public class OldurganEkOzelDurumu extends EkOzelDurumu {

    private final HarfDizisi T;

    public OldurganEkOzelDurumu(Alfabe alfabe) {
        T = new HarfDizisi("t",alfabe);
    }

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        TurkceHarf son = kelime.sonHarf();
        if (son.sesliMi() || ((son.charDeger()=='r') || son.charDeger()==('l'))
                && kelime.icerik().sesliSayisi() > 1) {
            return T;
        }
        return null;
    }

    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk) {
        return cozumlemeIcinUret(kelime, null, null);
    }
}
