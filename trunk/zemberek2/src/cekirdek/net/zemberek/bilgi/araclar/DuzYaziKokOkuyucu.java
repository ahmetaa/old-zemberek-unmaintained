/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.bilgi.araclar;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import net.zemberek.araclar.Kayitci;
import net.zemberek.bilgi.KaynakYukleyici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.Kisaltma;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;

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

    private static Logger log = Kayitci.kayitciUret(DuzYaziKokOkuyucu.class);
    private Alfabe alfabe;
    private KokOzelDurumBilgisi ozelDurumlar;
    protected BufferedReader reader;
    private static final Pattern AYIRICI_PATTERN = Pattern.compile("[ ]+");
    private Map<String, KelimeTipi> kokTipAdlari = new HashMap<String, KelimeTipi>();

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

    public List<Kok> hepsiniOku() throws IOException {
        ArrayList<Kok> list = new ArrayList<Kok>();
        Kok kok;
        while ((kok = oku()) != null) {
            list.add(kok);
        }
        if (reader != null)
            reader.close();
        return list;
    }

    public Kok oku() throws IOException {
        String line;
        while (reader.ready()) {
            line = reader.readLine().trim();
            if (line.startsWith("#") || line.length() == 0) continue;

            String tokens[] = AYIRICI_PATTERN.split(line);
            if (tokens == null || tokens.length < 2) {
                log.warning("Eksik bilgi!" + line);
                continue;
            }

            String asil = tokens[0];

            // ayikla() ile kok icerigi kucuk harfe donusturuluyor ve '- vs
            // isaretler siliniyor.
            String icerik = alfabe.ayikla(asil);
            Kok kok;

            // kelime tipini belirle. ilk parca mutlaka kok tipini belirler
            if (kokTipAdlari.containsKey(tokens[1])) {
                KelimeTipi tip = kokTipAdlari.get(tokens[1]);
                if (tip == KelimeTipi.KISALTMA)
                    kok = new Kisaltma(icerik);
                else
                    kok = new Kok(icerik, tip);
                ozelDurumlar.kokIcerikIsle(kok, tip, icerik);

            } else
                throw new IllegalArgumentException("Kok tipi bulunamadi!" + line);

            if (!asil.equals(icerik))
                kok.setAsil(asil);

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
