/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;

/**
 * Bu ozel durum ekin kendinden onceki bazi eklerde normalden farkli olusturulmasini
 * saglar. en yaygin kullanilan ek ozle durumu bu.
 *
 * <p/>
 * User: ahmet
 * Date: Aug 15, 2005
 */
public final class OnEkOzelDurumu extends EkOzelDurumu {

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        if (this.onEkler.contains(kelime.sonEk()))
            return ekUretici.cozumlemeIcinEkUret(kelime.icerik(), giris, uretimBilesenleri);
        else
            return null;
    }

    @Override
    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk){
        return cozumlemeIcinUret(kelime, null, null);
    }
}
