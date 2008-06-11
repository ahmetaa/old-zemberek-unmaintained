/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.kok;

import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;

/**
 * User: ahmet
 * Date: Aug 28, 2006
 */
public interface KokOzelDurumBilgisi {

    KokOzelDurumu ozelDurum(KokOzelDurumTipi tip);

    /**
     * kisaAd ile belirtilen ozel durumu dondurur.
     * @param kisaAd
     * @return ozelDurum ya da null.
     */
    KokOzelDurumu ozelDurum(String kisaAd);

    KokOzelDurumu ozelDurum(int indeks);

    String[] ozelDurumUygula(Kok kok);

    /**
     * Bazi ozel durumlar dogrudan kaynak kok dosyasinda yer almaz. bu ozel durumlari bu metod
     * tespit eder ve koke ekler.
     *
     * @param kok
     */
    void ozelDurumBelirle(Kok kok);

    /**
     * Duz yazi kok listesinden okunan dile ozel ozel durumlarin kok'e atanmasi islemi burada yapilir.
     *
     * @param kok
     * @param okunanIcerik
     * @param parcalar
     */
    void duzyaziOzelDurumOku(Kok kok, String okunanIcerik, String[] parcalar);

    /**
     * Ozellikle duz yazi dosyadan kok okumada kok icerigi tip ve dile gore on islemeden gecirilebilir
     * Ornegin turkiye turkcesinde eger kok icinde "mek" mastar eki bulunuyorsa bu silinir.
     * @param tip
     * @param icerik
     */
    void kokIcerikIsle(Kok kok, KelimeTipi tip, String icerik);

}
