package net.zemberek.az;

import net.zemberek.araclar.CokluKokDosyasiYazici;
import net.zemberek.araclar.Kayitci;
import net.zemberek.bilgi.araclar.IkiliKokYazici;
import net.zemberek.tr.yapi.KokTipiAdlari;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Bilgi dosyalarinda degisiklik yapildiginda bu sinifin isletilmesi gerekir.
 * Basitce xml ya da text tabanli dosyalari ikili formlarina donusturur.
 * User: ahmet
 * Date: Sep 7, 2005
 */
public class BinaryBilgiUret {

    private static Logger logger = Kayitci.kayitciUret(BinaryBilgiUret.class);

    public static void main(String[] args) throws IOException {
        //kokleri duz yazi dosyalardan oku
/*
        List tumKokler = new CokluKokDosyasiYazici(KokTipiAdlari.getKokTipAdlari()).parcalardanSozlukOlustur(new String[]{
                "kaynaklar/az/bilgi/kokler.txt"}
                , TurkceKokOzelDurumlari.ref(),
                TurkceAlfabe.ref());

        logger.info("Kok sayisi: " + tumKokler.size());

        // kokleri ikili olarak kaydet.
        IkiliKokYazici ozelYazici = new IkiliKokYazici("kaynaklar/az/bilgi/binary-kokler.bin");
        ozelYazici.yaz(tumKokler);
*/
    }
}
