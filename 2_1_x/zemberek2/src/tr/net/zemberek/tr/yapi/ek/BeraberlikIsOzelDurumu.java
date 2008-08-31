/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tr.yapi.ek;

import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkOzelDurumu;

/**
 * -is fiil eki tek heceli koklere eklendiginde "-yis" cok heceli koke eklendiginde ise
 * "-is" seklinde olusur. "tak-IS-mak" "ye-yis-mek"
 * User: ahmet
 * Date: Sep 11, 2005
 */
public class BeraberlikIsOzelDurumu extends EkOzelDurumu {

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        if(kelime.icerik().sesliSayisi()<2)
          return ekUretici.cozumlemeIcinEkUret(kelime.icerik(), giris, uretimBilesenleri);
        else
          return null;
    }

    @Override
    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk){
        return cozumlemeIcinUret(kelime, null, null);
    }
}
