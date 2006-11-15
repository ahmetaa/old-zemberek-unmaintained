package net.zemberek.tr.yapi.ek;

import net.zemberek.tr.yapi.TurkceSesliUretici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.TurkceHarf;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkUretici;
import net.zemberek.yapi.ek.EkUretimBileseni;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.logging.Logger;


public class EkUreticiTr implements EkUretici {

    private static Logger logger = Logger.getLogger(EkUreticiTr.class.getName());
    private TurkceSesliUretici sesliUretici;
    private Alfabe alfabe;

    public EkUreticiTr(Alfabe alfabe) {
        this.sesliUretici = new TurkceSesliUretici(alfabe);
        this.alfabe = alfabe;
    }

    public HarfDizisi cozumlemeIcinEkUret(HarfDizisi ulanacak, HarfDizisi giris, List<EkUretimBileseni> bilesenler) {
        HarfDizisi sonuc = new HarfDizisi();
        TurkceHarf sonSesli = ulanacak.sonSesli();
        for (int i = 0; i < bilesenler.size(); i++) {
            EkUretimBileseni ekUretimBileseni = bilesenler.get(i);
            final TurkceHarf harf = ekUretimBileseni.harf();
            switch (ekUretimBileseni.kural()) {
                case HARF:
                    sonuc.ekle(harf);
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
                case SESLI_AE:
                    if (i == 0 && ulanacak.sonHarf().sesliMi())
                        break;
                    else {
                        sonSesli = sesliUretici.sesliBelirleAE(sonSesli);
                        sonuc.ekle(sonSesli);
                    }
                    break;
                case SESLI_IU:
                    if (i == 0 && ulanacak.sonHarf().sesliMi())
                        break;
                    else {
                        sonSesli = sesliUretici.sesliBelirleIU(sonSesli);
                        sonuc.ekle(sonSesli);
                    }
                    break;
            }
        }
        return sonuc;
    }

    public HarfDizisi olusumIcinEkUret(HarfDizisi ulanacak, Ek sonrakiEk, List<EkUretimBileseni> bilesenler) {
        //TODO: gecici olarak bu sekilde
        return cozumlemeIcinEkUret(ulanacak, null, bilesenler);
    }

    public Set<TurkceHarf> olasiBaslangicHarfleri(List<EkUretimBileseni> bilesenler) {
        Set<TurkceHarf> kume = new HashSet(4);
        for (int i=0; i< bilesenler.size(); i++) {
            EkUretimBileseni bilesen = bilesenler.get(i);
            switch (bilesen.kural()) {
                case HARF:
                    kume.add(bilesen.harf());
                    return kume;
                case KAYNASTIR:
                    kume.add(bilesen.harf());
                    break;
                case SERTLESTIR:
                    kume.add(bilesen.harf());
                    kume.add(bilesen.harf().sertDonusum());
                    return kume;
                case SESLI_AE:
                      kume.add(alfabe.harf('a'));
                      kume.add(alfabe.harf('e'));
                      if(i>0)
                        return kume;
                case SESLI_IU:
                      kume.add(alfabe.harf('i'));
                      kume.add(alfabe.harf('u'));
                      kume.add(alfabe.harf(Alfabe.CHAR_ii));
                      kume.add(alfabe.harf(Alfabe.CHAR_uu));
                      if(i>0)
                        return kume;                    
            }
        }
        return kume;
    }


}