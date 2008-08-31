/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.bilgi;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import java.util.logging.Logger;

import net.zemberek.araclar.Kayitci;

/**
 * User: ahmet
 * Date: Feb 17, 2006
 */
public final class ZemberekAyarlari {

    private static Logger logger = Kayitci.kayitciUret(ZemberekAyarlari.class);

    private Properties konfigurasyon;
    private boolean oneriDeasciifierKullan = true;
    private int oneriMax = 12;
    private boolean oneriKokFrekansKullan = true;
    private boolean disKaynakErisimi = false;
    private boolean oneriBilesikKelimeKullan = true;
    private boolean cepKullan = true;

    private URI bilgiEk;
    private URI bilgiKokler;
    private URI bilgiAlfabe;
    private URI bilgiDizini;
    private URI bilgiCep;


    /**
     * classpath kokunden zemberek_'locale_str'.properties dosyasina erismeye calisir.
     * Bu dosyanin normalde projede yer almis olmasi gerekir. Eger bulunamazsa sistem
     * varsayilan degerleri kullanir.
     *
     * @param dilKisaAdi kisa ad (tr,az vs)
     */
    public ZemberekAyarlari(String dilKisaAdi) {
        try {
            konfigurasyon = new KaynakYukleyici().konfigurasyonYukle("zemberek_" + dilKisaAdi + ".properties");
        } catch (IOException e) {
            logger.warning("Konfigurasyon dosyasina erisilemiyor! varsayilan degerler kullanilacak");
        }
        konfigurasyonOku(konfigurasyon);
    }


    /**
     * Isaret edilen Properties dosyasindan verilere erisilmesye calisilir.
     *
     * @param ayarlar
     */
    public ZemberekAyarlari(Properties ayarlar) {
        this.konfigurasyon = ayarlar;
        konfigurasyonOku(ayarlar);
    }

    private void konfigurasyonOku(Properties ayarlar) {
        try {
            oneriDeasciifierKullan = boolOku(ayarlar, "oneri.deasciifierKullan");
            oneriKokFrekansKullan = boolOku(ayarlar, "oneri.kokFrekansKullan");
            oneriBilesikKelimeKullan = boolOku(ayarlar, "oneri.bilesikKelimeKullan");
            oneriMax = Integer.parseInt(ayarlar.getProperty("oneri.max"));
            disKaynakErisimi = boolOku(ayarlar, "bilgi.disKaynakErisimi");
            cepKullan = boolOku(ayarlar, "denetleme.cepKullan");
            if (disKaynakErisimi) {
                File dizin = new File(ayarlar.getProperty("bilgi.dizin"));
                bilgiDizini = dizin.toURI();
                bilgiEk = new File(dizin, ayarlar.getProperty("bilgi.ekler")).toURI();
                bilgiKokler = new File(dizin, ayarlar.getProperty("bilgi.kokler")).toURI();
                bilgiAlfabe = new File(dizin, ayarlar.getProperty("bilgi.harf")).toURI();
                bilgiCep = new File(dizin, ayarlar.getProperty("bilgi.harf")).toURI();
            }
        } catch (NumberFormatException e) {
            logger.severe("property erisim hatasi! Muhtemel tip donusum problemi.. varsayilan parametreler kullanilacak ");
        } catch (Exception e) {
            logger.severe("property erisim hatasi! property yer almiyor, ya da adi yanlis yazilmis olabilir. varsayilan konfigurasyon kullanilacak.");
        }
    }

    private boolean boolOku(Properties ayarlar, String anahtar) {
        return Boolean.parseBoolean(ayarlar.getProperty(anahtar));
    }

    public Properties getKonfigurasyon() {
        return konfigurasyon;
    }

    public boolean oneriDeasciifierKullan() {
        return oneriDeasciifierKullan;
    }

    public boolean oneriBilesikKelimeKullan() {
        return oneriBilesikKelimeKullan;
    }

    public int getOneriMax() {
        return oneriMax;
    }

    public boolean oneriKokFrekansKullan() {
        return oneriKokFrekansKullan;
    }

    public URI getBilgiEk() {
        return bilgiEk;
    }

    public URI getBilgiKokler() {
        return bilgiKokler;
    }

    public URI getBilgiDizini() {
        return bilgiDizini;
    }

    public URI getBilgiAlfabe() {
        return bilgiAlfabe;
    }

    public URI getBilgiCep() {
        return bilgiCep;
    }

    public boolean disKaynakErisimi() {
        return disKaynakErisimi;
    }

    public boolean cepKullan() {
        return cepKullan;
    }

}
