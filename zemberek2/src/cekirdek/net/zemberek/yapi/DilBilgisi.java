/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi;

import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.islemler.DenetlemeCebi;
import net.zemberek.islemler.cozumleme.CozumlemeYardimcisi;
import net.zemberek.yapi.ek.EkYonetici;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;

/**
 * ir dile iliskin dil bilgisi bilesenlerine bu arayuz uzerinden erisilir.
 * User: ahmet
 * Date: Sep 6, 2005
 */
public interface DilBilgisi {

    /**
     * Dile ozel alfabe nesnesini dondurur.
     * @return alfabe.
     */
    Alfabe alfabe();

    /**
     * Dile ozgu ek oynetici nesnesini dondurur.
     * @return ekyonetici
     */
    EkYonetici ekler();

    /**
     * Kok bilgilerine ve kok secicilere erisimi saglayan
     * dile ozel Sozluk nesnesini dondurur.
     * @return sozluk
     */
    Sozluk kokler();

    /**
     * Dile ozgu kok ozel durumu bilgilerini tasiyan nesneyi dondurur.
     * @return ozeldurumbilgisi
     */
    KokOzelDurumBilgisi kokOzelDurumlari();

    /**
     * eger varsa dile ozgu hece bulma nesnesi.
     * @return hecebulma nesnesi
     */
    Heceleyici heceBulucu();

    /**
     * dile ozgu cozumleme yardimcisi nesnesi. bu nesne cozumleme sirasinda kullanilan
     * cesitli on ve art isleme, denetlemeCebi mekanizmalarini tasir.
     * @return cozumleme yardimcisi
     */
    CozumlemeYardimcisi cozumlemeYardimcisi();

    /**
     * Varsa Denetleme Cebi
     * @return
     */
    DenetlemeCebi denetlemeCebi();

    DilAyarlari dilAyarlari();

}
