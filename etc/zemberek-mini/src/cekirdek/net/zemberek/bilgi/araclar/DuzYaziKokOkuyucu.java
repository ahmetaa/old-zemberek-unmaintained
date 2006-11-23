/*
 * Created on 09.Nis.2004
 */
package net.zemberek.bilgi.araclar;

import net.zemberek.bilgi.KaynakYukleyici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * BinarySozlukOkuyucu sınıfı düzyazı olarak düzenlenmiş sözlüğü okur.
 * Aşağıdaki kod parçası bir binary sözlükteki tüm kökleri okur.
 * <pre>
 * ...
 * KokOkuyucu okuyucu = new DuzYaziKokOkuyucu();
 * okuyucu.initialize("kaynaklar/kb/duzyazi-kokler.txt");
 * Kok kok = null;
 * while((kok = sozlukOkuyucu.oku())!= null){
 *      ekle(kok); // Elde edilen kök nesnesi ile ne gerekiyorsa yap.
 * }
 * ...
 * </pre>
 *
 * @author MDA
 */
public class DuzYaziKokOkuyucu implements KokOkuyucu {

    private Alfabe alfabe;
    private KokOzelDurumBilgisi ozelDurumlar;
    protected BufferedReader reader;
    private static final Pattern AYIRICI_PATTERN = Pattern.compile("[ ]+");
    private Map kokTipAdlari = new HashMap();

    // Eger farkli turk dillerine ait kok dosyalarinda farkli turden tip adlari 
    // kullanildiysa bu isimleri KelimeITplerine esleyen bir Map olusturulup bu
    // constructor kullanilabilir. Map icin ornek diger constructor icerisinde 
    // yer almaktadir.
    public DuzYaziKokOkuyucu(String dosyaAdi,
                             KokOzelDurumBilgisi ozelDurumlar,
                             Alfabe alfabe,
                             Map<String, KelimeTipi> kokTipAdlari) throws IOException {
        reader = new KaynakYukleyici("UTF-8").getReader(dosyaAdi);
        this.ozelDurumlar = ozelDurumlar;
        this.alfabe = alfabe;
        this.kokTipAdlari = kokTipAdlari;
    }

    public List hepsiniOku() throws IOException {
        ArrayList list = new ArrayList();
        Kok kok = null;
        while ((kok = oku()) != null) {
            list.add(kok);
        }
        if (reader != null)
            reader.close();
        return list;
    }

    public Kok oku() throws IOException {
        String line = null;
        while (reader.ready()) {
            line = reader.readLine().trim();
            if (line.startsWith("#") || line.length() == 0) continue;

            String tokens[] = AYIRICI_PATTERN.split(line, -1);
            if (tokens == null || tokens.length < 2) {
                System.out.println("Eksik bilgi!" + line);
                continue;
            }
            String icerik = tokens[0];
            Kok kok = new Kok(icerik);

            // ayikla() ile kok icerigi kucuk harfe donusturuluyor ve '- vs 
            // isaretler siliniyor.
            kok.setIcerik(alfabe.ayikla(kok.icerik()));

            // kelime tipini belirle. ilk parca mutlaka kok tipini belirler
            if (kokTipAdlari.containsKey(tokens[1])) {
                KelimeTipi tip = (KelimeTipi) kokTipAdlari.get(tokens[1]);
                kok.setTip(tip);
                ozelDurumlar.kokIcerikIsle(kok, tip, icerik);

            } else
                System.out.println("Kok tipi bulunamadi!" + line);

            // kok ozelliklerini ekle.
            ozelDurumlar.duzyaziOzelDurumOku(kok, icerik, tokens);

            // bazi ozel durumlar ana dosyada yer almaz, algoritma ile uretilir.
            // bu ozel durumlari koke ekle.
            ozelDurumlar.ozelDurumBelirle(kok);

            return kok;
        }
        return null;
    }
}
