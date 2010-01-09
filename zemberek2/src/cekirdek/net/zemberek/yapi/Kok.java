/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi;

import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.kok.KokOzelDurumTipi;
import net.zemberek.yapi.kok.KokOzelDurumu;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: aakin
 * Date: Feb 15, 2004
 * Time: 11:29:05 PM
 */
public class Kok {

    private static final KokOzelDurumu[] BOS_OZEL_DURUM_DIZISI = new KokOzelDurumu[0];
    public static final Kok BOS_KOK = new Kok("", KelimeTipi.ISIM);

    private int indeks;

    // Eger bir kok icinde alfabe disi karakter barindiriyorsa (nokta, tire gibi) orjinal hali bu
    // String icinde yer alir. Aksi halde null.
    private String asil;

    // Kok'un ozel karakterlerden temizlenmis hali. Genel olarak kok icerigi olarak bu String kullanilir.
    private String icerik;

    protected KelimeTipi tip;

    //performans ve kaynak tuketimini nedeniyle icin ozel durumlari Set yerine diziye koyduk.
    private KokOzelDurumu[] ozelDurumlar = BOS_OZEL_DURUM_DIZISI;

    private int frekans;

    public boolean ozelDurumVarmi() {
        return ozelDurumlar.length > 0;
    }

    public KokOzelDurumu[] ozelDurumDizisi() {
        return ozelDurumlar;
    }

    public boolean ozelDurumIceriyormu(KokOzelDurumTipi tip) {
        for (KokOzelDurumu oz : ozelDurumlar) {
            if (oz.indeks() == tip.indeks())
                return true;
        }
        return false;
    }

    /**
     * koke ozel durum ekler. burada dizi kullaniminda kaynak konusunda cimrilik ettigimizden
     * her yeni ozel durum icin dizi boyutunu bir buyuttuk. ayrica tekrar olmamasini da sagliyoruz.
     * Normalde bu islem Set icin cok daha kolay bir yapida olabilirdi set.add() ancak Set'in kaynak tuketimi
     * diziden daha fazla.
     *
     * @param ozelDurum
     */
    public void ozelDurumEkle(KokOzelDurumu ozelDurum) {
        if (ozelDurumlar.length == 0) {
            ozelDurumlar = new KokOzelDurumu[1];
            ozelDurumlar[0] = ozelDurum;
        } else {
            if (ozelDurumIceriyormu(ozelDurum.tip()))
                return;
            KokOzelDurumu[] yeni = new KokOzelDurumu[ozelDurumlar.length + 1];
            //burada elle dizi kopyalama yapiyoruz, cunku genellikle ozel durum sayisi 1
            for (int i = 0; i < ozelDurumlar.length; i++) {
                yeni[i] = ozelDurumlar[i];
            }
            yeni[ozelDurumlar.length] = ozelDurum;
            this.ozelDurumlar = yeni;
        }
    }

    /**
     * sadece ilk acilista kullanilan bir metod
     *
     * @param tip
     */
    public void ozelDurumCikar(KokOzelDurumTipi tip) {
        if (!ozelDurumIceriyormu(tip))
            return;
        KokOzelDurumu[] yeni = new KokOzelDurumu[ozelDurumlar.length - 1];
        int j = 0;
        for (KokOzelDurumu oz : ozelDurumlar) {
            if (!oz.tip().equals(tip))
                yeni[j++] = oz;
        }
        this.ozelDurumlar = yeni;
    }

    public Kok(String icerik) {
        this.icerik = icerik;
    }

    public Kok(String icerik, KelimeTipi tip) {
        this.icerik = icerik;
        this.tip = tip;
    }

    public String toString() {
        String strOzel = "";
        for (KokOzelDurumu ozelDurum : ozelDurumlar) {
            if (ozelDurum != null)
                strOzel += ozelDurum.kisaAd() + " ";
        }
        if (tip != null)
            return icerik + " " + tip + " " + strOzel;
        else return icerik;
    }

    public HarfDizisi ozelDurumUygula(Alfabe alfabe, Ek ek) {
        HarfDizisi dizi = new HarfDizisi(this.icerik, alfabe);
        for (KokOzelDurumu ozelDurum : ozelDurumlar) {
            if (ozelDurum.yapiBozucumu() && ozelDurum.olusabilirMi(ek))
                ozelDurum.uygula(dizi);
            if (!ozelDurum.olusabilirMi(ek) && ozelDurum.ekKisitlayiciMi())
                return null;
        }
        return dizi;
    }

    public boolean yapiBozucuOzelDurumVarmi() {
        if (ozelDurumlar.length == 0)
            return false;
        for (KokOzelDurumu ozelDurum : ozelDurumlar) {
            if (ozelDurum.yapiBozucumu())
                return true;
        }
        return false;
    }

    /**
     * kokun gercek icerigini dondurur. "icerik" parametresinde kokun genellikle
     * donusturulmus hali (kucuk harf ve noktalama isaretlerinden arinmis hali)
     * bulundugundan eger varsa kok icerisindeki "asil" hali, eger ozel ad ise bas harfi
     * buyuk yapilarak, degilse icerik aynen dondurulur.
     *
     * @param alfabe
     * @return
     */
    public String asilIcerikUret(Alfabe alfabe) {

        if (asil != null)
            return asil;
        if (icerik == null || icerik.length() == 0)
            return "";
        if (tip == KelimeTipi.OZEL) {
            if (icerik.length() > 1)
                return icerik.substring(0, 1).toUpperCase(alfabe.locale) + icerik.substring(1);
            else
                return icerik.toUpperCase(alfabe.locale);
        } else return icerik;
    }


    public final KelimeTipi tip() {
        return tip;
    }

    public final void setTip(KelimeTipi tip) {
        this.tip = tip;
    }

    public final void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public final String icerik() {
        return icerik;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Kok kok = (Kok) o;

        if (icerik != null ? !icerik.equals(kok.icerik) : kok.icerik != null) return false;
        if (ozelDurumlar != null ? !Arrays.equals(ozelDurumlar, kok.ozelDurumlar) : kok.ozelDurumlar != null) return false;
        if (tip != null ? !tip.equals(kok.tip) : kok.tip != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = (icerik != null ? icerik.hashCode() : 0);
        result = 29 * result + (tip != null ? tip.hashCode() : 0);
        result = 29 * result + (ozelDurumlar != null ? ozelDurumlar.hashCode() : 0);
        return result;
    }

    /**
     * @return Returns the indeks.
     */
    public int getIndeks() {
        return indeks;
    }

    /**
     * @param indeks The indeks to set.
     */
    public void setIndeks(int indeks) {
        this.indeks = indeks;
    }

    public void setFrekans(int kokIstatistigi) {
        this.frekans = kokIstatistigi;
    }

    public int getFrekans() {
        return frekans;
    }

    public String asil() {
        return asil;
    }

    public void setAsil(String asil) {
        this.asil = asil;
    }
}