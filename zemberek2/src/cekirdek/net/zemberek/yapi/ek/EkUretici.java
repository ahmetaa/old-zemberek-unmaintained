package net.zemberek.yapi.ek;

import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.TurkceHarf;

import java.util.List;
import java.util.Set;

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
     * @param bilesenler
     * @return uretilen ek, HarfDizisi cinsinden.
     */
    HarfDizisi cozumlemeIcinEkUret(HarfDizisi ulanacak,
                                   HarfDizisi giris,
                                   List<EkUretimBileseni> bilesenler);

    /**
     * Kelime uretimi icin ek uretimi.
     * @param ulanacak
     * @param sonrakiEk
     * @param bilesenler
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

}
