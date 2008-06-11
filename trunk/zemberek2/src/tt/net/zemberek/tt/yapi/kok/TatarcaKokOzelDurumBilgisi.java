/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tt.yapi.kok;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.EkYonetici;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;
import net.zemberek.yapi.kok.KokOzelDurumu;
import net.zemberek.yapi.kok.TemelKokOzelDurumBilgisi;
import net.zemberek.yapi.kok.Yumusama;

import java.util.ArrayList;
import java.util.List;

public class TatarcaKokOzelDurumBilgisi extends TemelKokOzelDurumBilgisi implements KokOzelDurumBilgisi {

    public TatarcaKokOzelDurumBilgisi(EkYonetici ekler, Alfabe alfabe) {
        super(ekler, alfabe);
        uret();
    }

    private void uret() {
        //TODO: buraya ilgili ozel durumlar eklenmeli
        ekle(uretici(TatarcaKokOzelDurumTipleri.SESSIZ_YUMUSAMASI, new Yumusama()).
                sesliEkIleOlusur(true).
                yapiBozucu(true));
    }


    public String[] ozelDurumUygula(Kok kok) {
        //kok icinde ozel durum yok ise cik..
        if (!kok.ozelDurumVarmi())
            return new String[0];

        HarfDizisi hdizi = new HarfDizisi(kok.icerik(), alfabe);

        List degismisIcerikler = new ArrayList(1);

        boolean yapiBozucuOzelDurumvar = false;
        // kok uzerindeki ozel durumlar basta sona taranip ozel durum koke uygulaniyor.

        for (KokOzelDurumu ozelDurum : kok.ozelDurumDizisi()) {
                ozelDurum.uygula(hdizi);
            if (ozelDurum.yapiBozucumu())
                yapiBozucuOzelDurumvar = true;
        }

        if (yapiBozucuOzelDurumvar)
            degismisIcerikler.add(hdizi.toString());

        return (String[]) degismisIcerikler.toArray(new String[degismisIcerikler.size()]);
    }

    public void ozelDurumBelirle(Kok kok) {
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
    }

    public void kokIcerikIsle(Kok kok, KelimeTipi tip, String icerik){}
}
