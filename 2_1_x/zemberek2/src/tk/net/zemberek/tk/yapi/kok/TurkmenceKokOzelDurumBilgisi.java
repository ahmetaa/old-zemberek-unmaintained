/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tk.yapi.kok;

import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.ARA_SESLI_DUSMESI;
import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.GENIS_ZAMAN;
import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.IKILEME;
import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.ISIM_SON_SESLI_DUSMESI;
import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.ISIM_TAMLAMASI;
import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.ISLIK_ARA_SESLI_DUSMESI;
import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.KESMESIZ;
import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.KICELTME;
import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.SESSIZ_YUMUSAMASI;
import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.TERS_SESLI_GOSULMA;
import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.TIRE;
import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.YALIN;
import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.YEKELIK_KISI_BOZUMASI;
import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.ZAMIR_IM;
import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.ZAMIR_IN;
import static net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumTipleri.ZAMIR_SESLI_OZEL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.EkYonetici;
import net.zemberek.yapi.kok.AraSesliDusmesi;
import net.zemberek.yapi.kok.BosHarfDizisiIslemi;
import net.zemberek.yapi.kok.Ciftleme;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;
import net.zemberek.yapi.kok.KokOzelDurumu;
import net.zemberek.yapi.kok.SonHarfDusmesi;
import net.zemberek.yapi.kok.SonSesliIncelt;
import net.zemberek.yapi.kok.TemelKokOzelDurumBilgisi;
import net.zemberek.yapi.kok.Ulama;
import net.zemberek.yapi.kok.YeniIcerikAta;
import net.zemberek.yapi.kok.Yumusama;

/**
 * User: ahmet
 * Date: Sep 20, 2006
 */
public class TurkmenceKokOzelDurumBilgisi extends TemelKokOzelDurumBilgisi implements KokOzelDurumBilgisi {


    private void uret() {

        ekle(uretici(SESSIZ_YUMUSAMASI, new Yumusama()).
                sesliEkIleOlusur(true).
                yapiBozucu(true));

        ekle(uretici(ARA_SESLI_DUSMESI, new AraSesliDusmesi()).yapiBozucu(true));

        ekle(uretici(IKILEME,new Ciftleme()).yapiBozucu(true));

        ekle(uretici(ISLIK_ARA_SESLI_DUSMESI, new AraSesliDusmesi()).yapiBozucu(true));

        ekle(uretici(KICELTME, new SonHarfDusmesi()).yapiBozucu(true));

        Map<String, String> benSenDonusum = new HashMap<String, String>();
        benSenDonusum.put("ben", "ban");
        benSenDonusum.put("sen", "san");
        ekle(uretici(YEKELIK_KISI_BOZUMASI, new YeniIcerikAta(alfabe, benSenDonusum)).yapiBozucu(true));

        ekle(uretici(TERS_SESLI_GOSULMA, new SonSesliIncelt(alfabe)).yapiBozucu(true));


        ekle(uretici(ISIM_SON_SESLI_DUSMESI, new SonHarfDusmesi()).secimlik(true).yapiBozucu(true));

        ekle(uretici(ISIM_TAMLAMASI, new BosHarfDizisiIslemi()).ekKisitlayici(true));

        HarfDizisi n  = new HarfDizisi("n", alfabe);
        ekle(uretici(ZAMIR_SESLI_OZEL, new Ulama(n)).yapiBozucu(true));

        bosOzelDurumEkle(YALIN, GENIS_ZAMAN,KESMESIZ, TIRE, ZAMIR_IM,ZAMIR_IN);
    }

    public TurkmenceKokOzelDurumBilgisi(EkYonetici ekler, Alfabe alfabe) {
        super(ekler, alfabe);
        uret();
    }

    public String[] ozelDurumUygula(Kok kok) {
        //kok icinde ozel durum yok ise cik..
        if (!kok.ozelDurumVarmi())
            return new String[0];

        HarfDizisi hdizi = new HarfDizisi(kok.icerik(), alfabe);

        List<String> degismisIcerikler = new ArrayList<String>(1);

        //ara sesli dusmesi nedeniyle bazen yapay oarak kok'e ters sesli etkisi ozel durumunun eklenmesi gerekir.
        // nakit -> nakde seklinde. normal kosullarda "nakda" olusmasi gerekirdi.
        boolean eskiSonsesliInce = false;
        if (kok.ozelDurumIceriyormu(ARA_SESLI_DUSMESI))
            eskiSonsesliInce = hdizi.sonSesli().inceSesliMi();

        boolean yapiBozucuOzelDurumvar = false;
        // kok uzerindeki ozel durumlar basta sona taranip ozel durum koke uygulaniyor.

        for (KokOzelDurumu ozelDurum : kok.ozelDurumDizisi()) {
            if (!ozelDurum.equals(KICELTME))
                ozelDurum.uygula(hdizi);
            if (ozelDurum.yapiBozucumu())
                yapiBozucuOzelDurumvar = true;
        }
        // ara sesli dusmesi durumunda dusen sesi ile dustukten sonra olusan seslinin farkli olmasi durumunda
        // kok'e bir de ters sesli ek ozel durumu ekleniyor.,
        if (kok.ozelDurumIceriyormu(ARA_SESLI_DUSMESI)) {
            if (!hdizi.sonSesli().inceSesliMi() && eskiSonsesliInce)
                kok.ozelDurumEkle(ozelDurumlar.get(TERS_SESLI_GOSULMA));
        }

        //   System.out.println("");
        if (yapiBozucuOzelDurumvar)
            degismisIcerikler.add(hdizi.toString());

        if (kok.ozelDurumVarmi() &&
                kok.ozelDurumIceriyormu(KICELTME) &&
                kok.ozelDurumIceriyormu(SESSIZ_YUMUSAMASI)) {
            HarfDizisi tempDizi = new HarfDizisi(kok.icerik(), alfabe);
            ozelDurumlar.get(KICELTME).uygula(tempDizi);
            degismisIcerikler.add(tempDizi.toString());
        }
        // yani ozel durumlar eklenmis olabileceginden koke ods'u tekrar koke esle.
        return degismisIcerikler.toArray(new String[degismisIcerikler.size()]);
    }

    public void ozelDurumBelirle(Kok kok) {
        char sonChar = kok.icerik().charAt(kok.icerik().length() - 1);
        if (kok.tip() == KelimeTipi.ISIM && alfabe.harf(sonChar).sesliMi()) {
            kok.ozelDurumEkle(ozelDurumlar.get(ISIM_SON_SESLI_DUSMESI));
        }
    }

    public void duzyaziOzelDurumOku(Kok kok, String okunanIcerik, String[] parcalar) {
        for (int i = 2; i < parcalar.length; i++) {

            String ozelDurumAdi = parcalar[i];

            //diger ozel durumlarin elde edilmesi..
            KokOzelDurumu oz = ozelDurum(ozelDurumAdi);
            if (oz != null) {
                kok.ozelDurumEkle(oz);
            } else {
                logger.warning("Hatali kok bileseni" + kok.icerik() + " Token: " + ozelDurumAdi);
            }
        }

        //kisaltmalari ve ozel karakter iceren kokleri asil icerik olarak ata.
        if (kok.tip() == KelimeTipi.KISALTMA)
            kok.setAsil(okunanIcerik);
    }

    public void kokIcerikIsle(Kok kok, KelimeTipi tip, String icerik) {
    }
}
