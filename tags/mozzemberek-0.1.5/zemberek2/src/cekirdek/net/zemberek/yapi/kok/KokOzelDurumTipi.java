/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.kok;

/**
 * User: ahmet
 * Date: Sep 7, 2006
 */
public interface KokOzelDurumTipi {

    /**
     * ozel durumun tam adini temsil eder.
     * @return ad
     */
    String ad();

    /**
     * ozel durumun duz yazi dosyasindaki ifade edilen halini temsil eder.
     * @return kisaAd
     */
    String kisaAd();

    /**
     * ozel durum indeksi enum ordinal() bilgisi ile uretilir.
     * (yani enum bilesninin sira indeksi)
     * bu bilgi ikili kok olusumu sirasinda ozel durumu temsil etmek icin dosyaya yazilir.
     * okuma sirasinda bu indeks ile ozel duruma erisilir. Eger bir sekilde ozel durumlarin
     * enum iceirsindeki sirasi gelistirme asamasinda degistirilirse ikili kok dosyasinin
     * tekrar uretilmesi gerekir..
     * @return ozel durum indeksi.
     */
    int indeks();

    /**
     * bu ozel durumun olusamasina neden olacak eklerin adlari.
     * @return 0 yada n uzunluklu dizi.
     */
    String[] ekAdlari();
}
