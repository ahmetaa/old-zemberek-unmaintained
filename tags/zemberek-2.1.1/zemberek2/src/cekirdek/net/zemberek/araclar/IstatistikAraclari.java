/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.araclar;

import java.text.DecimalFormat;

/**
 * Bazi basit  yuzde hesaplamalar�nda kullannilan fonksiyonlar. 
 * TODO: istatistik paketine alinmasi dusunulebilir.
 * @author MDA
 */
public class IstatistikAraclari {

    public static DecimalFormat df = new DecimalFormat("#0.000");
    public static DecimalFormat df2 = new DecimalFormat("#0.00000");

    /**
     * Verilen girisin toplamın yüzde kaçını oluşturduğunu döndürür.
     * @param input
     * @param total
     * @return input, toplamin %kaci ise.
     * Eğer total 0 ise -1 
     * 
     */
    public static double yuzdeHesapla(long input, long total) {
        if (total == 0) return -1;
        return (double) (input * 100) / total;
    }

    /**
     * Yuzde hesaplamasının aynısı, sadece formatlı String olarak döndürür.
     * @param input : giriş 
     * @param total : toplam
     * @return
     */
    public static String yuzdeHesaplaStr(long input, long total) {
        if (total == 0) return "0";
        return df.format((double) (input * 100) / total);
    }

    /**
     * Gene yuzde hesabı. ama bu sefer virgülden sonra 5 basamak hassasiyet
     * @return
     */
    public static String onbindeHesaplaStr(long input, long total) {
        if (total == 0) return "0";
        return df2.format((double) (input * 100) / total);
    }
}
