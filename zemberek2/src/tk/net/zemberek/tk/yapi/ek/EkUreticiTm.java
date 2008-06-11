/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tk.yapi.ek;

import net.zemberek.tk.yapi.TurkmenceSesliUretici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.TurkceHarf;
import net.zemberek.yapi.ek.*;

import java.util.List;
import java.util.logging.Logger;

public class EkUreticiTm extends TemelEkUretici implements EkUretici {

    private static Logger log = Logger.getLogger(EkUreticiTm.class.getName());
    private final TurkceHarf HARF_a;
    private final TurkceHarf HARF_aa;
    private TurkmenceSesliUretici sesliUretici;

    public EkUreticiTm(Alfabe alfabe) {
        HARF_a = alfabe.harf('a');
        HARF_aa = alfabe.harf(Alfabe.CHAR_aa);
        this.sesliUretici = new TurkmenceSesliUretici(alfabe);
    }

    public HarfDizisi cozumlemeIcinEkUret(HarfDizisi ulanacak, HarfDizisi giris, List<EkUretimBileseni> bilesenler) {
        HarfDizisi sonuc = new HarfDizisi();
        for (int i = 0; i < bilesenler.size(); i++) {
            EkUretimBileseni ekUretimBileseni =  bilesenler.get(i);
            TurkceHarf harf = ekUretimBileseni.harf;
            final TurkceHarf sonSesli = ulanacak.sonSesli();
            switch ((TemelEkUretimKurali)ekUretimBileseni.kural) {
                case HARF:
                    sonuc.ekle(harf);
                    break;
                case SESSIZ_Y:
                    if (!ulanacak.sonSesli().inceSesliMi())
                        sonuc.ekle(HARF_a);
                    else
                        sonuc.ekle(HARF_aa);
                    break;
                case KAYNASTIR:
                    if (ulanacak.sonHarf().sesliMi())
                        sonuc.ekle(harf);
                    break;
                case SERTLESTIR:
                    if (ulanacak.sonHarf().sertMi())
                        sonuc.ekle(harf.sertDonusum());
                    else
                        sonuc.ekle(harf);
                    break;
                case SESLI_AA:
                    if (i == 0 && ulanacak.sonHarf().sesliMi())
                        break;
                    sonuc.ekle(sesliUretici.sesliBelirleAA(sonSesli));
                    break;
                case SESLI_AE:
                    if (i == 0 && ulanacak.sonHarf().sesliMi())
                        break;
                    sonuc.ekle(sesliUretici.sesliBelirleAE(sonSesli));
                    break;
                case SESLI_IU:
                    // eger eklenilecek olan kelime selsi ile bitiyorsa ekleme.
                    if (i == 0 && ulanacak.sonHarf().sesliMi())
                        break;
                    // eger eklenecek kelime birden fazla heceli ise i ekle.
                    if (ulanacak.sesliSayisi() > 1) {
                        sonuc.ekle(sesliUretici.sesliBelirleII(sonSesli));
                        break;
                    }
                    // eger bu son bilesen ise ve hece sayisi henuz ikiden fazla degilse
                    if (i == bilesenler.size() - 1) {
                        //eger giris yok ise (olusum icin ek uretiliyor) i olarak ekle.
                        if (giris == null)
                            sonuc.ekle(sesliUretici.sesliBelirleII(sonSesli));
                        else if (giris.sesliSayisi() >= 2 &&
                                !giris.sonHarf().sesliMi())
                            sonuc.ekle(sesliUretici.sesliBelirleIU(sonSesli));
                        else
                            sonuc.ekle(sesliUretici.sesliBelirleII(sonSesli));
                        break;
                    }
                    sonuc.ekle(sesliUretici.sesliBelirleIU(sonSesli));
                    break;
            }
        }
        return sonuc;
    }

}