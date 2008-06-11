/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.bilgi;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import org.junit.Test;
import org.junit.Assert;

/**
 * User: ahmet
 * Date: Feb 13, 2006
 */
public class TestKaynakYukleyici {


    @Test
    public void testProperties() throws IOException {
        // girilen bir dosyayi VM calisma dizini referans alinarak yuklemeye calisir.
        Properties props = new KaynakYukleyici().konfigurasyonYukle("test/cekirdek/net/zemberek/bilgi/test.properties");
        String test = props.getProperty("test");
        Assert.assertEquals(test, "test 1 2 3");
    }

    @Test
    public void testPropertiesURI() throws IOException {
        // herhangi bir adresten (ya da dizinden) dosyayi yuklemeye calisir.
        URI uri = new File("test/cekirdek/net/zemberek/bilgi/test.properties").toURI();
        Properties props = new KaynakYukleyici().konfigurasyonYukle(uri);
        String test = props.getProperty("test");
        Assert.assertEquals(test, "test 1 2 3");
    }

    @Test    
    public void testPropertiesClasspath() throws IOException {
        // verilen dosyayi classpath icinden yuklemeye calisir.
        Properties props = new KaynakYukleyici().konfigurasyonYukle("net//zemberek//bilgi//test.properties");
        String test = props.getProperty("test");
        Assert.assertEquals(test, "test 1 2 3");
    }
}
