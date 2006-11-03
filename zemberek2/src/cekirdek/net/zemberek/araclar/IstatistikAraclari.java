/*
 * Created on 19.Ara.2004
 *
 */
package net.zemberek.araclar;

import java.text.DecimalFormat;

/**
 * Bazi basit  yuzde hesaplamalarýnda kullannilan fonksiyonlar. 
 * TODO: istatistik paketine alinmasi dusunulebilir.
 * @author MDA
 */
public class IstatistikAraclari {

    public static DecimalFormat df = new DecimalFormat("#0.000");
    public static DecimalFormat df2 = new DecimalFormat("#0.00000");

    /**
     * Verilen giriþin toplamýn yüzde kaçýný oluþturduðunu döndürür.
     * @param input
     * @param total
     * @return input, toplamin %kacini olusturuyor sa o deðer.
     * Eðer total 0 ise -1 
     * 
     */
    public static double yuzdeHesapla(long input, long total) {
        if (total == 0) return -1;
        return (double) (input * 100) / total;
    }

    /**
     * yuzde hesaplamasýnýn aynýsý, sadece formatlý String olarak döndürür.
     * @param input
     * @param total
     * @return
     */
    public static String yuzdeHesaplaStr(long input, long total) {
        if (total == 0) return "0";
        return df.format((double) (input * 100) / total);
    }

    /**
     * Gene yuzde hesabý. ama bu sefer virgülden sonra 5 basamak hassasiyet
     * TODO: ismi hatalý aslýnda. Onbinde-bir hassasiyetle yüzde hesapla gibi bir þey olmalýydý.
     * @return
     */
    public static String onbindeHesaplaStr(long input, long total) {
        if (total == 0) return "0";
        return df2.format((double) (input * 100) / total);
    }
}
