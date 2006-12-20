/*
 *  ***** BEGIN LICENSE BLOCK *****
 *
 *  Version: MPL 1.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is "Zemberek Dogal Dil Isleme Kutuphanesi"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

/*
 * Created on 09.Nis.2004
 */
package net.zemberek.bilgi.araclar;

import net.zemberek.bilgi.KaynakYukleyici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;
import net.zemberek.araclar.Kayitci;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
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

    private static Logger log = Kayitci.kayitciUret(DuzYaziKokOkuyucu.class);
    private Alfabe alfabe;
    private KokOzelDurumBilgisi ozelDurumlar;
    protected BufferedReader reader;
    private static final Pattern AYIRICI_PATTERN = Pattern.compile("[ ]+");
    private Map<String, KelimeTipi> kokTipAdlari = new HashMap();

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
        if(reader!=null)
            reader.close();
        return list;
    }

    public Kok oku() throws IOException {
        String line;
        while (reader.ready()) {
            line = reader.readLine().trim();
            if (line.startsWith("#") || line.length() == 0) continue;

            String tokens[] = AYIRICI_PATTERN.split(line, -1);
            if (tokens == null || tokens.length < 2) {
                log.warning("Eksik bilgi!" + line);
                continue;
            }
            String icerik = tokens[0];
            Kok kok = new Kok(icerik);

            // ayikla() ile kok icerigi kucuk harfe donusturuluyor ve '- vs 
            // isaretler siliniyor.
            kok.setIcerik(alfabe.ayikla(kok.icerik()));

            // kelime tipini belirle. ilk parca mutlaka kok tipini belirler
            if (kokTipAdlari.containsKey(tokens[1])) {
                KelimeTipi tip = kokTipAdlari.get(tokens[1]);
                kok.setTip(tip);                
                ozelDurumlar.kokIcerikIsle(kok, tip, icerik);

            } else
                log.warning("Kok tipi bulunamadi!" + line);

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
