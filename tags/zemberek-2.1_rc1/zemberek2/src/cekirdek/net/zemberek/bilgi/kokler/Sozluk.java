/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 07.Mar.2004
 */
package net.zemberek.bilgi.kokler;

import java.util.Collection;
import java.util.List;

import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;

/**
 * Birden fazla sozlukle calisabilmek icin Sozluk arayuzu. 
 *
 * @author MDA
 */
public interface Sozluk {
    /**
     * str seklinde yazilan tum kelime koklerini dondurur. str kokun istisna hali de olabilir.
     *
     * @param str
     * @return kok listesi.
     */
    public List<Kok> kokBul(String str);


    public Kok kokBul(String str, KelimeTipi tip);

    /**
     * sozluk icindeki normal ya da kok ozel durumu seklindeki tum kok iceriklerini bir
     * Koleksiyon nesnesi olarak dondurur.
     *
     * @return tum kokleri iceren Collection nesnesi
     */
    public Collection<Kok> tumKokler();

    /**
     * sozluge kok ekler.
     *
     * @param kok
     */
    public void ekle(Kok kok);

    /**
     * Bu metod kökbulucu fabrikası elde etmek için kullanılır. Gerçekleyen sözlük sınıfları bu
     * metodda kendi Kök bulucu fabrikası gerçeklemelerinin bir instancesini geri döndürmelidirler.
     *
     * @return Sözlük
     * @see AgacSozluk
     */
    public KokAdayiBulucuUretici kokBulucuFactory();

}

