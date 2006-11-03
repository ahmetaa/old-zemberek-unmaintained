package net.zemberek.bilgi;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

/**
 * User: ahmet
 * Date: Feb 13, 2006
 */
public class TestKaynakYukleyici extends TestCase {


    public void testProperties() throws IOException {
        // girilen bir dosyayi VM calisma dizini referans alinarak yuklemeye calisir.
        Properties props = new KaynakYukleyici().konfigurasyonYukle("test/net/zemberek/bilgi/test.properties");
        String test = props.getProperty("test");
        assertEquals(test, "test 1 2 3");
    }

    public void testPropertiesURI() throws IOException {
        // herhangi bir adresten (ya da dizinden) dosyayi yuklemeye calisir.
        URI uri = new File("test/net/zemberek/bilgi/test.properties").toURI();
        Properties props = new KaynakYukleyici().konfigurasyonYukle(uri);
        String test = props.getProperty("test");
        assertEquals(test, "test 1 2 3");
    }

    public void testPropertiesClasspath() throws IOException {
        // verilen dosyayi classpath icinden yuklemeye calisir.
        Properties props = new KaynakYukleyici().konfigurasyonYukle("net//zemberek//bilgi//test.properties");
        String test = props.getProperty("test");
        assertEquals(test, "test 1 2 3");
    }
}
