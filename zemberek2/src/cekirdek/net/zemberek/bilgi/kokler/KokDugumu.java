/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 24.Eki.2004
 */
package net.zemberek.bilgi.kokler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.zemberek.yapi.Kok;

/**
 * Kök düğümü sınıfı Kök ağacının yapıtaşıdır. Her düğüm, kökler, eşseli kökler,
 * değişmiş halleri ifade eden bir string ve uygun şekilde bellek kullanımı için
 * hazırlanmış özel bir alt düğüm listesi nesnesi taşır.
 * <p/>
 * Çeşitli nedenlerle değişikliğe uğrayabilecek olan kökler ağaca eklenirken
 * değişmiş halleri ile beraber eklenirler. Örneğin kitap kökü hem kitab hem de
 * kitap hali ile sözlüğe eklenir, ancak bu iki kelime için oluşan düğüm de
 * aynı kökü gösterirler. Böylece "kitabına" gibi kelimeler için kök adayları
 * aranırken "kitap" köküne erişilmiş olur.
 * <p/>
 * Eş sesli olan kökler aynı düğüme bağlanırlar. Ağacın oluşumu sırasında ilk
 * gelen kök düğümdeki kök değişkenine, sonradan gelenler de esSesliler listesine
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
     *         o düğümü, yoksa null.
     */
    public final KokDugumu altDugumBul(char in) {
        if (altDugumler == null)
            return null;
        else
            return altDugumler.altDugum(in);
    }

    /**
     * Verilen düğümü bu düğüme alt düğüm olarak ekler.
     * Dönüş değeri eklenen düğümdür
     *
     * @param dugum
     * @return Eklenen düğüm
     */
    public final KokDugumu dugumEkle(KokDugumu dugum) {
        if (altDugumler == null) {
            altDugumler = new AltDugumListesi();
        }
        altDugumler.ekle(dugum);
        return dugum;
    }

    /**
     * @return tum alt dugumler. dizi olarak.
     */
    public final KokDugumu[] altDugumDizisi() {
        if (altDugumler == null) {
            return new KokDugumu[0];
        }
        return altDugumler.altDugumlerDizisi();
    }

    public final boolean altDugumVarMi() {
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

    public final Kok kok() {
        return this.kok;
    }

    public final List<Kok> esSesliler() {
        return esSesliler;
    }

    public final CharSequence kelime() {
        if (kelime != null) return kelime;
        if (kok != null) return kok.icerik();
        return null;
    }

    public final void setKelime(CharSequence kelime) {
        this.kelime = kelime;
    }

    /**
     * @return düğüme bağlı kök ve eş seslilerin hepsini bir listeye
     *         koyarak geri döndürür.
     */
    public List<Kok> tumKokler() {
        if (kok != null) {
            ArrayList<Kok> kokler = new ArrayList<Kok>(2);
            kokler.add(kok);
            if (esSesliler != null) {
                kokler.addAll(esSesliler);
            }
            return kokler;
        }
        return null;
    }

    /**
     * @return düğüme bağlı tum köklerin icerigi "icerik" ile ayni olanlairni dondurur
     *         koyarak geri döndürür.
     */
    public List<Kok> tumKokler(String icerik) {
        if (kok != null) {
            ArrayList<Kok> kokler = new ArrayList<Kok>(2);
            //if(kok.icerik().equals(icerik))
            kokler.add(kok);
            if (esSesliler != null) {
                kokler.addAll(esSesliler);
//                for (Kok esKok : esSesliler) {
//                    if(esKok.icerik().equals(icerik))
//                       kokler.add(esKok);
//                }
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
        }
        if (esSesliler != null) {
            kokler.addAll(esSesliler);
        }
    }

    public final void temizle() {
        this.kok = null;
        this.kelime = null;
        this.esSesliler = null;
    }

    public final void kopyala(KokDugumu kaynak) {
        this.kok = kaynak.kok();
        this.kelime = kaynak.kelime();
        this.esSesliler = kaynak.esSesliler();
    }

    public final char harf() {
        return harf;
    }

    public final void setHarf(char harf) {
        this.harf = harf;
    }

    /**
     * Düğümün ve alt düğümlerinin ağaç yapısı şeklinde string gösterimini döndürür.
     * sadece debug amaçlıdır.
     *
     * @param level
     * @return dugumun string halini dondurur.
     */
    public final String goster(int level) {
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

        KokDugumu[] subNodes = altDugumDizisi();
        if (subNodes != null) {
            str += "\n " + indent + " Alt dugumler:\n";
            for (KokDugumu subNode : subNodes) {
                if (subNode != null) {
                    str += subNode.goster(level + 1) + "\n";
                }
            }
        }
        return str;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("harf:").append(harf);
        if (altDugumler != null)
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
     */
    private static final int CEP_BUYUKLUGU = 3;

    private final class AltDugumListesi {
        KokDugumu[] dugumler = new KokDugumu[CEP_BUYUKLUGU];
        int index = 0;
        HashMap<Character, KokDugumu> tumDugumler = null;

        /**
         * Verilen düğümü alt düğüm olarak ekler. eger alt düğümlerinin sayisi
         * CEP_BUYUKLUGU degerini asmissa bir HashMap oluşturur
         *
         * @param dugum
         */
        public final void ekle(KokDugumu dugum) {
            if (index == CEP_BUYUKLUGU) {
                if (tumDugumler == null) {
                    tumDugumler = new HashMap<Character, KokDugumu>(CEP_BUYUKLUGU + 2);
                    for (int i = 0; i < CEP_BUYUKLUGU; i++) {
                        tumDugumler.put(dugumler[i].harf(), dugumler[i]);
                    }
                    dugumler = null;
                }
                tumDugumler.put(dugum.harf(), dugum);
            } else {
                dugumler[index++] = dugum;
            }
        }

        /**
         * Verilen karaktere sahip alt düğümü döndürür.
         *
         * @param giris
         * @return ilgili KokDugumu
         */
        public final KokDugumu altDugum(char giris) {
            if (dugumler != null) {
                for (int i = 0; i < index; i++) {
                    if (dugumler[i].harf() == giris) {
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
         *
         * @return KokDugumu[] cinsinden alt düğümler dizisi
         */
        public final KokDugumu[] altDugumlerDizisi() {
            if (dugumler != null) {
                return dugumler;
            } else {
                return tumDugumler.values().toArray(new KokDugumu[tumDugumler.values().size()]);
            }
        }

        public final int size() {
            if (dugumler != null) {
                return index;
            } else {
                return tumDugumler.size();
            }
        }
    }

}