/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek;

import java.io.IOException;

import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.DilAyarlari;
import net.zemberek.yapi.DilBilgisi;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.TurkceDilBilgisi;

import org.junit.Before;

/**
 * User: ahmet
 * Date: Sep 23, 2006
 */
public class TemelTest {

    public static final String TEMP_DIR = "kaynaklar/temp/";
    public static final String TR_TEST_TEXT = "kaynaklar/tr/test/";

    protected DilBilgisi dilBilgisi;
    protected DilAyarlari dilAyarlari;
    protected Alfabe alfabe;

    @Before
    public void once() throws IOException {
        dilAyarlari = new TurkiyeTurkcesi();
        dilBilgisi = new TurkceDilBilgisi(dilAyarlari);
        alfabe = dilBilgisi.alfabe();
    }

    public HarfDizisi hd(String s) {
        return new HarfDizisi(s, alfabe);
    }
}
