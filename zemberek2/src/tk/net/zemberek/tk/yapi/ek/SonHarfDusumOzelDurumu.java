/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tk.yapi.ek;

import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.ek.EkOzelDurumu;

/**
 * User: ahmet
 * Date: May 10, 2006
 */
public class SonHarfDusumOzelDurumu extends EkOzelDurumu {

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        if(kelime.kok().ozelDurumIceriyormu(TurkmenceKokOzelDurumTipleri.ISIM_SON_SESLI_DUSMESI) &&
                kelime.ekler().size()<=1)
        {
           return ekUretici.cozumlemeIcinEkUret(kelime.icerik(), giris, uretimBilesenleri);
        } else
        {
            return null;
        }
    }
}
