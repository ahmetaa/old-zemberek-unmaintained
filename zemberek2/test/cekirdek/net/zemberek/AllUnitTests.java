package net.zemberek;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.zemberek.araclar.TestTimeTracker;
import net.zemberek.araclar.turkce.TestExtractor;
import net.zemberek.araclar.turkce.TestTurkishTokenStream;
import net.zemberek.bilgi.araclar.TestDuzMetinSozlukYazici;
import net.zemberek.islemler.TestHeceleyici;
import net.zemberek.islemler.cozumleme.TestKelimeCozumleyici;
import net.zemberek.yapi.TestHarfDizisi;
import net.zemberek.yapi.TestHeceBulur;
import net.zemberek.yapi.TestKok;
import net.zemberek.yapi.alfabe.TestAlfabe;
import net.zemberek.yapi.ek.TestFiilEkleri;
import net.zemberek.yapi.ek.TestIsimEkleri;
import net.zemberek.yapi.ek.TestSayiEkleri;

/**
 */
public class AllUnitTests {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        //ekler
        suite.addTestSuite(TestFiilEkleri.class);
        suite.addTestSuite(TestIsimEkleri.class);
        suite.addTestSuite(TestSayiEkleri.class);
        //islemler
        suite.addTestSuite(TestKelimeCozumleyici.class);
        suite.addTestSuite(TestHeceleyici.class);
        suite.addTestSuite(TestHeceBulur.class);
        //kokler
        suite.addTestSuite(TestDuzMetinSozlukYazici.class);
        //yapi
        suite.addTestSuite(TestHarfDizisi.class);
        suite.addTestSuite(TestKok.class);
        //alfabe
        suite.addTestSuite(TestAlfabe.class);
        //araclar
        suite.addTestSuite(TestTurkishTokenStream.class);
        suite.addTestSuite(TestTimeTracker.class);
        suite.addTestSuite(TestExtractor.class);

        return suite;
    }
}
