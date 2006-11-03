package net.zemberek;

import junit.framework.TestCase;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.*;

import java.io.IOException;

/**
 * User: ahmet
 * Date: Sep 23, 2006
 */
public class TemelTest extends TestCase {

    protected DilBilgisi dilBilgisi;
    protected DilAyarlari dilAyarlari;
    protected Alfabe alfabe;


    public void setUp() throws IOException {
        dilAyarlari = new TurkiyeTurkcesi();
        dilBilgisi = new TurkceDilBilgisi(dilAyarlari);
        alfabe = dilBilgisi.alfabe();
    }

    public HarfDizisi hd(String s) {
        return new HarfDizisi(s, alfabe);
    }
}
