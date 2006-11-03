package net.zemberek.az.islemler;

import net.zemberek.islemler.DenetlemeCebi;
import net.zemberek.islemler.cozumleme.CozumlemeYardimcisi;
import net.zemberek.yapi.*;
import net.zemberek.yapi.ek.TurkceKelimeOzellikleri;
import static net.zemberek.yapi.ek.TurkceKelimeOzellikleri.*;

import java.util.EnumSet;

/**
 * Bu sinif Turkiye Turkcesine ozel cesitli islemleri icerir.
 * User: ahmet
 * Date: Sep 11, 2005
 */
public class AzericeCozumlemeYardimcisi implements CozumlemeYardimcisi {

    private Alfabe alfabe;
    private DenetlemeCebi cep;

    public AzericeCozumlemeYardimcisi(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    public void setCep(DenetlemeCebi cep) {
        this.cep=cep;
    }

    public void kelimeBicimlendir(Kelime kelime) {
    }

    public boolean kelimeBicimiDenetle(Kelime kelime, String giris) {
        return true;
    }

    public boolean kokGirisDegismiVarsaUygula(Kok kok, HarfDizisi kokDizi, HarfDizisi girisDizi) {
        return true;
    }

    public EnumSet<TurkceKelimeOzellikleri> ozellikBelirle(HarfDizisi dizi) {
        EnumSet<TurkceKelimeOzellikleri> ozellikler = EnumSet.noneOf(TurkceKelimeOzellikleri.class);
        if (dizi == null) {
            return ozellikler;
        }
        final TurkceHarf sonHarf = dizi.sonHarf();
        final TurkceHarf sonSesli;
        if (sonHarf.sesliMi()) {
            ozellikler.add(SON_HARF_SESLI);
            sonSesli = sonHarf;
        } else {
            if (sonHarf.sertMi())
                ozellikler.add(SON_HARF_SERT);
            sonSesli = dizi.sonSesli();
        }
        if (sonSesli.inceSesliMi())
            ozellikler.add(SON_SESLI_INCE);
        else
            ozellikler.add(SON_SESLI_KALIN);
        if (sonSesli.duzSesliMi())
            ozellikler.add(SON_SESLI_YUVARLAK);
        else if (sonSesli.yuvarlakSesliMi())
            ozellikler.add(SON_SESLI_DUZ);
        return ozellikler;
    }

    public boolean cepteAra(String str) {
        if(cep!=null)
          return cep.kontrol(str);
        else return false;
    }
}
