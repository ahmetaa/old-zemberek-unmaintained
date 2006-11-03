package net.zemberek.islemler;

import junit.framework.TestCase;

import java.io.IOException;

/**
 */
public class TestHataliKodlamaTemizleyici extends TestCase {

    public void testTemizleyici() throws IOException {
        HataliKodlamaTemizleyici h = new HataliKodlamaTemizleyici();
        h.initialize();
        String giris = " ";
        System.out.println(h.temizle(giris));
    }
}
