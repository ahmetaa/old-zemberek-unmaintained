/*
 * Created on 07.Mar.2004
 */
package net.zemberek.bilgi.kokler;

import net.zemberek.yapi.Kok;
import net.zemberek.yapi.KelimeTipi;

import java.util.Collection;
import java.util.List;

/**
 * Birden fazla sozlukle calisabilmek icin Sozluk arayuzu. Kaynak sözlükteki kýsaltmalarýn
 * anlamlarý:
 * <pre>
 * #       : Yorum. Bu satýrlar okuyucular ihmal edilir.
 * IS      : Ýsim
 * FI      : Fiil
 * SI      : Sýfat
 * SA      : Sayý
 * OZ      : Özel isim
 * ZA      : Zamir
 * YUM     : Yumuþama. Örnek: kitap – kitabý (kitapý deðil)
 * DUS     : Harf Düþmesi nutuk – nutka (nutuka deðil)
 * TERS    : Ters dönüþüm saat – saate (saata deðil)
 * YAL     : Kelime sadece yalýn olarak kullanýlýr
 * GEN     : Geniþ zaman istisnasý
 * </pre>
 *
 * @author MDA & GBA
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
     * Bu metod kökseçici fabrikasý elde etmek için kullanýlýr. Gerçekleyen sözlük sýnýflarý bu
     * metodda kendi Kök Seçici fabrikasý gerçeklemelerinin bir instancesini geri döndürmelidirler.
     *
     * @return Sözlük
     * @see AgacSozluk
     */
    public KokBulucuUretici getKokBulucuFactory();

    public KokAgaci getAgac();
}

