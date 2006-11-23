package net.zemberek.islemler.cozumleme;

import net.zemberek.yapi.Kelime;

/**
 */
public interface KelimeCozumleyici {

    public static final Kelime[] BOS_KELIME_DIZISI = new Kelime[0];

    public Kelime[] cozumle(String strGiris);

    public boolean denetle(String strGiris);
}
