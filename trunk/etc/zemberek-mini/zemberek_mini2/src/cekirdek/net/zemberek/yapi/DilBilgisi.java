package net.zemberek.yapi;

import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.islemler.cozumleme.CozumlemeYardimcisi;
import net.zemberek.yapi.ek.EkYonetici;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;

/**
 * Herhangi bir turkDili'nin asagidaki tum metodlari saglamasi gerekir.
 * User: ahmet
 * Date: Sep 6, 2005
 */
public interface DilBilgisi {

    /**
     * Dile ozel alfabe nesnesini dondurur.
     *
     * @return alfabe.
     */
    Alfabe alfabe();

    /**
     * Dile ozgu ek oynetici nesnesini dondurur.
     *
     * @return ekyonetici
     */
    EkYonetici ekler();

    /**
     * Kok bilgilerine ve kok secicilere erisimi saglayan
     * dile ozel Sozluk nesnesini dondurur.
     *
     * @return sozluk
     */
    Sozluk kokler();

    /**
     * Dile ozgu kok ozel durumu bilgilerini tasiyan nesneyi dondurur.
     *
     * @return ozeldurumbilgisi
     */
    KokOzelDurumBilgisi kokOzelDurumlari();

    /**
     * eger varsa dile ozgu hece bulma nesnesi.
     *
     * @return hecebulma nesnesi
     */
    HeceBulucu heceBulucu();

    /**
     * dile ozgu cozumleme yardimcisi nesnesi. bu nesne cozumleme sirasinda kullanilan
     * cesitli on ve art isleme, cep mekanizmalarini tasir.
     *
     * @return cozumleme yardimcisi
     */
    CozumlemeYardimcisi cozumlemeYardimcisi();

}
