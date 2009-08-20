/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import net.zemberek.araclar.Kayitci;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;

/**
 * Bu sinif dile ozel ek yonetici siniflar icin taban olarak kullanilir.
 * icerisinde cesitli ek bilgileri yer alir.
 * User: ahmet
 * Date: Sep 21, 2006
 */
public class TemelEkYonetici implements EkYonetici {

    protected static Logger logger = Kayitci.kayitciUret(TemelEkYonetici.class);

    public static final Ek BOS_EK = new Ek("BOS_EK");
    protected Map<String, Ek> ekler;
    protected Map<KelimeTipi, Ek> baslangicEkleri = new HashMap<KelimeTipi, Ek>();


    public TemelEkYonetici(Map<KelimeTipi, String> baslangicEkMap,
                           XmlEkOkuyucu okuyucu) throws IOException {

        long start = System.currentTimeMillis();
        okuyucu.xmlOku();
        ekler = okuyucu.getEkler();
        for (Entry<KelimeTipi, String> entry : baslangicEkMap.entrySet()) {
            KelimeTipi tip = entry.getKey();
            Ek ek = ekler.get(entry.getValue());
            if (ek != null)
                baslangicEkleri.put(tip, ek);
            else
                logger.warning(tip + " tipi icin baslangic eki " + baslangicEkMap.get(tip) + " bulunamiyor!");
        }
        logger.fine("ek okuma ve olusum suresii: " + (System.currentTimeMillis() - start) + "ms.");
    }

    /**
     * adi verilen Ek nesnesini bulur. Eger ek yok ise null doner.
     *
     * @param ekId - ek adi
     * @return istenen Ek nesnesi.
     */
    public Ek ek(String ekId) {
        Ek ek = ekler.get(ekId);
        if (ek == null)
            logger.severe("Ek bulunamiyor!" + ekId);
        return ekler.get(ekId);
    }

    /**
     * Kok nesnesinin tipine gore gelebilecek ilk ek'i dondurur.
     * Baslangic ekleri bilgisi dil tarafindan belirlenir.
     *
     * @param kok baslangic eki belirlemede kullanilacak kok.
     * @return ilk Ek, eger kok tipi baslangic ekleri <baslangicEkleri>
     *         haritasinda belirtilmemisse BOS_EK doner.
     */
    public Ek ilkEkBelirle(Kok kok) {
        Ek baslangicEki = baslangicEkleri.get(kok.tip());
        if (baslangicEki != null)
            return baslangicEki;
        else
            return BOS_EK;
    }
}
