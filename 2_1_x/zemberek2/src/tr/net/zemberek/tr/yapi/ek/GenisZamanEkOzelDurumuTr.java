/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tr.yapi.ek;

import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkOzelDurumu;

/**
 * Genis zaman ozel durumu. Normalde fiillere genis zaman eki eklenedeginde "ir, ur, Ur, Ir"
 * olusur. Ancak
 * a)fiil tek heceli (bunun da istisnalari var)
 * b)fiil tek heceli fiil eklenmesi ile olusmus bilesik fiilse (addetmek gibi)
 * "ar er" olusur
 * <p/>
 * User: ahmet
 * Date: Aug 15, 2005
 */
public class GenisZamanEkOzelDurumuTr extends EkOzelDurumu {

    public GenisZamanEkOzelDurumuTr() {
    }

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        if (kelime.sonEk().ad().equals(TurkceEkAdlari.FIIL_KOK)
             && kelime.kok().ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.GENIS_ZAMAN))
            return ekUretici.cozumlemeIcinEkUret(kelime.icerik(), giris, uretimBilesenleri);
        else
            return null;
    }

    @Override
    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk) {
        return cozumlemeIcinUret(kelime, null, null);
    }
}
