/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

import java.util.List;
import java.util.Set;

import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.TurkceHarf;

/**
 * ek uretim kuralinin islenmesinde kullanilan sinif icin ortak arayuz.
 * Her lehce kendi uretim sinifini kullanir.
 * User: ahmet
 * Date: Aug 22, 2005
 */
public interface EkUretici {

    /**
     * Kelime Cozumleme islemi icin ek uretimi.
     * @param ulanacak
     * @param giris
     * @param bilesenler ek uretim bilesenleri
     * @return uretilen ek, HarfDizisi cinsinden.
     */
    HarfDizisi cozumlemeIcinEkUret(HarfDizisi ulanacak,
                                   HarfDizisi giris,
                                   List<EkUretimBileseni> bilesenler);

    /**
     * Kelime uretimi icin ek uretimi.
     * @param ulanacak
     * @param sonrakiEk
     * @param bilesenler ek uretim bilesenleri.
     * @return uretilen ek, HarfDizisi cinsinden.
     */
    HarfDizisi olusumIcinEkUret(HarfDizisi ulanacak,
                                Ek sonrakiEk,
                                List<EkUretimBileseni> bilesenler);

    /**
     * Ek bilesenlerini kullarak bir ekin hangi harflerle baslayacagini kestirip sonuclari
     * bir set icerisinde dondurur.
     * @param bilesenler
     * @return olasi baslangic harfleri bir Set icerisinde.
     */
    Set<TurkceHarf> olasiBaslangicHarfleri(List<EkUretimBileseni> bilesenler);

    /**
     * bilesenlere gore en basta sesli harf olup olamayacagini belirler.
     * @param bilesenler
     * @return
     */
    boolean sesliIleBaslayabilir(List<EkUretimBileseni> bilesenler);
}
