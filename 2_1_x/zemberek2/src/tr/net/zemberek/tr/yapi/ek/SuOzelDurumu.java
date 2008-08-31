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
 * 'su' kokune kaynastirma iceren cesitli ekler eklendiginde normal kaynastirma harfi yerine
 * 'y' harfi kullanilir.
 */
public class SuOzelDurumu extends EkOzelDurumu {

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        if(kelime.gercekEkYok() && kelime.kok().ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.SU_OZEL_DURUMU))
           return ekUretici.cozumlemeIcinEkUret(kelime.icerik(), giris, uretimBilesenleri);
        return null;
    }

    @Override
    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk) {
        return cozumlemeIcinUret(kelime, null, null);
    }
}
