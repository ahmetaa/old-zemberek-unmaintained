package net.zemberek.az.yapi;

import net.zemberek.araclar.Kayitci;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.islemler.cozumleme.CozumlemeYardimcisi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HeceBulur;
import net.zemberek.yapi.TurkDili;
import net.zemberek.yapi.ek.EkYonetici;
import net.zemberek.yapi.kok.KokOzelDurumlari;

import java.util.logging.Logger;

/**
 * User: ahmet
 * Date: Sep 6, 2005
 */
public class AzeriTurkcesi implements TurkDili {

    private static Logger logger = Kayitci.kayitciUret(AzeriTurkcesi.class);

    public Alfabe alfabe() {
        return null;
    }

    public EkYonetici ekler() {
        return null;
    }

    public Sozluk kokler() {
        return null;
    }

    public KokOzelDurumlari kokOzelDurumlari() {
        return null;
    }

    public HeceBulur heceBulur() {
        return null;
    }

    public CozumlemeYardimcisi cozumlemeYardimcisi() {
        return null; 
    }
}
