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
 * Created on 24.Eki.2004
 */
package net.zemberek.bilgi.kokler;

import net.zemberek.yapi.Kok;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Kök düğümü sınıfı Kök ağacının yapıtaşıdır. Her düğüm, kökler, eşseli kökler,
 * değişmiş halleri ifade eden bir string ve uygun �ekilde bellek kullanımı için 
 * hazırlanmış özel bir alt düğüm listesi nesnesi taşır.
 * <p/>
 * çeşitli nedenlerle değişikliğe uğrayabilecek olan kökler ağaca eklenirken
 * değişmiş halleri ile beraber eklenirler. Örneğin kitap kökü hem kitab hem de
 * kitap hali ile sözlüğe eklenir, ancak bu iki kelime için oluşan düğüm de
 * aynı kökü gösterirler. Böylece Kitabına gibi kelimeler için kök adayları
 * aranırken kitap köküne erişilmiş olur.
 * <p/>
 * Eş sesli olan kökler aynı düğüme bağlanırlar. Ağacın oluşumu sırasında ilk 
 * gelen kök düğümdeki kök değişkenne, sonradan gelenler de esSesliler listesine 
 * eklenirler. Arama sırasında bu kök te aday olarak döndürülür.
 *
 * @author MDA
 */
public class KokDugumu {

    private AltDugumListesi altDugumler = null;
    // Her düğüm bir harfle ifade edilir.
    private char harf;
    // eş seslileri taşıyan liste (Kok nesneleri taşır)
    private List<Kok> esSesliler = null;
    // Düğümğn taşıdığı kök
    private Kok kok = null;
    // Kökün değişmiş halini tutan string
    private CharSequence kelime = null;

    public KokDugumu() {
    }

    public KokDugumu(char harf) {
        this.harf = harf;
    }

    public KokDugumu(char harf, CharSequence icerik, Kok kok) {
        this.harf = harf;
        this.kok = kok;
        if (!icerik.equals(kok.icerik())) this.kelime = icerik;
    }

    /**
     * Verilen karakteri taşıyan alt düğümü getirir.
     *
     * @param in
     * @return Eğer verilen karakteri taşıyan bir alt düğüm varsa
     * o düğümü, yoksa null.
     */
    public final KokDugumu altDugumGetir(char in) {
        if (altDugumler == null)
            return null;
        else
            return altDugumler.altDugumGetir(in);
    }

    /**
     * Verilen düğümü bu düğüme alt düğüm olarak ekler.
     * Dönüş değeri eklenen düğümdür
     *
     * @param dugum
     * @return Eklenen düğüm
     */
    public final KokDugumu addNode(KokDugumu dugum) {
        if (altDugumler == null) {
            altDugumler = new AltDugumListesi();
        }
        altDugumler.ekle(dugum);
        return dugum;
    }

    /**
     * @return tum alt dugumler. dizi olarak.
     */
    public final KokDugumu[] altDugumDizisiGetir() {
        if (altDugumler == null) {
            return new KokDugumu[0];
        }
        return altDugumler.altDugumlerDizisiGetir();
    }

    public final boolean altDugumVarMi(){
        return !(altDugumler == null || altDugumler.size() == 0);
    }
    /**
     * Eğer Düğüme bağlı bir kök zaten varsa esSesli olarak ekle, 
     * yoksa sadece kok'e yaz.
     *
     * @param kok
     */
    public final void kokEkle(Kok kok) {
        if (this.kok != null) {
            if (esSesliler == null) esSesliler = new ArrayList<Kok>(1);
            esSesliler.add(kok);
        } else {
            this.kok = kok;
        }
    }

    public final Kok getKok() {
        return this.kok;
    }

    public final List<Kok> getEsSesliler() {
        return esSesliler;
    }

    public final CharSequence getKelime() {
        if (kelime != null) return kelime;
        if (kok != null) return kok.icerik();
        return null;
    }

    public final void setKelime(CharSequence kelime) {
        this.kelime = kelime;
    }

    /**
     * @return düğüme bağlı kök ve eş seslilerin hepsini bir listeye 
     * koyarak geri döndürür.
     */
    public final List<Kok> tumKokleriGetir() {
        if (kok != null) {
            ArrayList<Kok> kokler = new ArrayList<Kok>();
            kokler.add(kok);
            if (esSesliler != null) {
                kokler.addAll(esSesliler);
            }
            return kokler;
        }
        return null;
    }

    /**
     * Verilen collectiona düğüme bağlı tüm kökleri ekler. 
     *
     * @param kokler
     */
    public final void tumKokleriEkle(List<Kok> kokler) {
        if (kok != null && !kokler.contains(kok)) {
            kokler.add(kok);
            if (esSesliler != null) {
                kokler.addAll(esSesliler);
            }
        }
    }

    public final void temizle() {
        this.kok = null;
        this.kelime = null;
        this.esSesliler = null;
    }

    public final void kopyala(KokDugumu kaynak) {
        this.kok = kaynak.getKok();
        this.kelime = kaynak.getKelime();
        this.esSesliler = kaynak.getEsSesliler();
    }

    public final char getHarf() {
        return harf;
    }

    public final void setHarf(char harf) {
        this.harf = harf;
    }

    /**
     * Düğümün ve alt düğümlerinin ağaç yapısı �eklinde string gösterimini döndürür.
     * sadece debug amaçlıdır.
     *
     * @param level
     * @return dugumun string halini dondurur.
     */
    public final String getStringRep(int level) {
        char[] indentChars = new char[level * 2];
        for (int i = 0; i < indentChars.length; i++)
            indentChars[i] = ' ';
        String indent = new String(indentChars);
        String str = indent + " Harf: " + harf;
        if (kelime != null) {
            str += " [Kelime: " + kelime + "] ";
        }
        if (kok != null) {
            str += " [Kok: " + kok + "] ";
        }

        if (esSesliler != null) {
            str += " [Es sesli: ";
            for (Kok anEsSesliler : esSesliler) {
                str += (anEsSesliler) + " ";
            }
            str += " ]";
        }

        KokDugumu[] subNodes = altDugumDizisiGetir();
        if (subNodes != null) {
            str += "\n " + indent + " Alt dugumler:\n";
            for (KokDugumu subNode : subNodes) {
                if (subNode != null) {
                    str += subNode.getStringRep(level + 1) + "\n";
                }
            }
        }
        return str;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("harf:").append(harf);
        if(altDugumler != null)
            buf.append(" alt dugum sayisi:").append(altDugumler.size());
        return buf.toString();
    }

    /**
     * Kök agacindaki düğümlerin alt düğümleri için bu sinifi kullanirlar.
     * Özellikle bellek kullaniminin önemli oldugu Zemberek-Pardus ve OOo 
     * eklentisi gibi uygulamalarda bu yapinin kullanilmasi bellek kazanci 
     * getirmektedir. 
     * Asagidaki sinifta alt dugum sayisi CEP_BUYUKLUGU degerinden
     * az ise sadece CEP_BUYUKLUGU elemanli bir dizi acar. Bu dizi üzerinde 
     * Arama yapmak biraz daha yavas olsa da ortalama CEP_BUYUKLUGU/2 aramada 
     * sonuca erişildiği için verilen ceza minimumda kalir. 
     *
     */
    private static final int CEP_BUYUKLUGU = 3;
    private final class AltDugumListesi {
        KokDugumu[] dugumler = new KokDugumu[CEP_BUYUKLUGU];
        int index = 0;
        HashMap<Character, KokDugumu> tumDugumler = null;

        /**
         * Verilen düğümü alt düğüm olarak ekler. eger alt düğümlerinin sayisi
         * CEP_BUYUKLUGU degerini asmissa bir HashMap oluşturur
         * @param dugum
         */
        public final void ekle(KokDugumu dugum) {
            if (index == CEP_BUYUKLUGU) {
                if (tumDugumler == null) {
                    tumDugumler = new HashMap<Character, KokDugumu>(CEP_BUYUKLUGU + 2);
                    for (int i = 0; i < CEP_BUYUKLUGU; i++) {
                        tumDugumler.put(dugumler[i].getHarf(), dugumler[i]);
                    }
                    dugumler = null;
                }
                tumDugumler.put(dugum.getHarf(), dugum);
            } else {
                dugumler[index++] = dugum;
            }
        }

        /**
         * Verilen karaktere sahip alt düğümü döndürür.
         * @param giris
         * @return ilgili KokDugumu
         */
        public final KokDugumu altDugumGetir(char giris) {
            if (dugumler != null) {
                for (int i=0 ; i< index; i++) {
                    if (dugumler[i].getHarf() == giris) {
                        return dugumler[i];
                    }
                }
                return null;
            } else {
                return tumDugumler.get(giris);
            }
        }

        /**
         * Alt düğümleri dizi olarak döndürür.
         * @return KokDugumu[] cinsinden alt düğümler dizisi
         */
        public final KokDugumu[] altDugumlerDizisiGetir() {
            if (dugumler != null){
                return dugumler;
            }
            else{
                return tumDugumler.values().toArray(new KokDugumu[tumDugumler.values().size()]);
            }
        }
        
        public final int size(){
            if (dugumler != null){
                return index;
            } else {
                return tumDugumler.size();
            }
        }
    }
    
}