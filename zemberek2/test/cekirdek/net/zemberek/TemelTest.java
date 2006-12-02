package net.zemberek;

import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.*;
import org.junit.Before;

import java.io.IOException;

/**
 * User: ahmet
 * Date: Sep 23, 2006
 */
public class TemelTest {

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
