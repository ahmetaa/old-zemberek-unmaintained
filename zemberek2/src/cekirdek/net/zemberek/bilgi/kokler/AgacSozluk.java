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
 *  The Original Code is Zemberek Doğal Dil İşleme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akın, Mehmet D. Akın.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

/*
 * Created on 23.Eki.2004
 */
package net.zemberek.bilgi.kokler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;


/**
 * Zemberek kütüphanesindeki tek sözlük gerçeklemesi Ağaç sözlüktür.
 * Ağaç sözlük, inşası sırasında verilen kök sözlüğü okuyucu nesnesini kullanarak
 * tüm kökleri okur ve bir ağaç yapısına yerleştirir.
 * Daha sonra bu sözlük nesnesinden çeşitli kök bulucu nesneleri oluşturulup
 * kullanılabilir.
 *
 * @author MDA & GBA
 */
public class AgacSozluk implements Sozluk {

    private KokAgaci agac = null;
    private AgacKokBulucuUretici agacKokBulucuFactory = null;
    private KokOzelDurumBilgisi ozelDurumlar;
    private int indeks = 0;

    /**
     * constructor.
     *
     * @param okuyucu: Sözlükler mutlaka bir sözlük okuyucu ile ilklendirilir.
     * @param alfabe : Kullanılan Türk dili alfabesi
     * @param ozelDurumlar : Dile ait kök özel durumlarını taşıyan nesne
     */
    public AgacSozluk(KokOkuyucu okuyucu, Alfabe alfabe, KokOzelDurumBilgisi ozelDurumlar) throws IOException {
        this.ozelDurumlar = ozelDurumlar;
        agac = new KokAgaci(new KokDugumu(), alfabe);
        Kok kok;
        while ((kok = okuyucu.oku()) != null) {
            ekle(kok);
        }
        agacKokBulucuFactory = new AgacKokBulucuUretici(this.agac);
    }

    /**
     * Constructor.
     * @param kokler
     * @param alfabe
     * @param ozelDurumlar
     */
    public AgacSozluk(List<Kok> kokler, Alfabe alfabe,
    		KokOzelDurumBilgisi ozelDurumlar) {
        agac = new KokAgaci(new KokDugumu(), alfabe);
        this.ozelDurumlar = ozelDurumlar;
        for(Kok kok : kokler){
            ekle(kok);
        }
        agacKokBulucuFactory = new AgacKokBulucuUretici(this.agac);
    }

    /**
     * Verilen bir kökü sözlükte arar.
     *
     * @param str: Aranan kök
     * @return Eğer aranan kök varsa, eş seslileri ile beraber kök nesnesini de
     * taşıyan bir List<Kok>, aranan kök yoksa null;
     */
    public List<Kok> kokBul(String str) {
        return agac.bul(str);
    }

    public Kok kokBul(String str, KelimeTipi tip) {
        List<Kok> kokler = agac.bul(str);
        for (Kok kok : kokler) {
            if(kok.tip()==tip) return kok;
        }
        return null;
    }

    public Collection<Kok> tumKokler() {
        HashSet<Kok> set = new HashSet<Kok>(100);
        KokAgaciYuruyucu yuruyucu = new KokAgaciYuruyucu(this, set);
        yuruyucu.agaciTara();
        return set;
    }

    /**
     * Verilen kökü sözlüğe ekler. Eklemeden once koke ait ozel durumlar varsa bunlar denetlenir.
     * Eger kok ozel durumlari kok yapisini bozacak sekilde ise ozel durumlarin koke uyarlanmis halleride
     * agaca eklenir. bu sekilde bozulmus kok formlarini iceren kelimeler icin kok bulma
     * islemi basari ile gerceklestirilebilir.
     *
     * @param kok: Sözlüğe eklenecek olan kök nesnesi.
     */
    public void ekle(Kok kok) {
        kok.setIndeks(indeks++);
        agac.ekle(kok.icerik(), kok);
        String[] degismisIcerikler = ozelDurumlar.ozelDurumUygula(kok);
        if (degismisIcerikler.length > 0) {
            for (String degismisIcerik : degismisIcerikler) {
                agac.ekle(degismisIcerik, kok);
            }
        }
    }

    /**
     * @return Returns the agac.
     */
    public KokAgaci getAgac() {
        return agac;
    }

    /**
     * Kök seçiciler, sözlükten alınan bir fabrika ile elde edilirler.
     * Örneğin:
     * <pre>
     * KokBulucu kokSecici = kokler.getKokBulucuFactory().getKesinKokBulucu();
     * </pre>
     */
    public KokBulucuUretici getKokBulucuFactory() {
        return agacKokBulucuFactory;
    }

    /**
     * Ağaç sözlük için fabrika gerçeklemesi
     *
     * @author MDA
     */
    class AgacKokBulucuUretici implements KokBulucuUretici {

        KokAgaci agac = null;

        public AgacKokBulucuUretici(KokAgaci agac) {
            this.agac = agac;
        }

        public KokBulucu getKesinKokBulucu() {
            return new KesinKokBulucu(this.agac);
        }

        public KokBulucu getToleransliKokBulucu(int tolerans) {
            return new ToleransliKokBulucu(this.agac, tolerans);
        }

        public KokBulucu getAsciiKokBulucu() {
            return new AsciiKokBulucu(this.agac);
        }
    }
}
