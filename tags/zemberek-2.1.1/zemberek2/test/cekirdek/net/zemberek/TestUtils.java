/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 22.Eyl.2004
 */
package net.zemberek;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import junit.framework.AssertionFailedError;
import net.zemberek.bilgi.KaynakYukleyici;

/**
 * @author MDA & GBA
 */

public class TestUtils {
    /**
     * iki Collection'un elemanlar� birbirine e�itse (s�ra �nemsiz) ok. de�ilse fail.
     *
     * @param a
     * @param b
     */
    public static void assertCollectionsEqual(final Collection a, final Collection b) {
        if (a.size() != b.size()) {
            throw new AssertionFailedError("Collectionlar e�it de�il");
        }
        final Collection copyOfB = new LinkedList(b);
        for (final Object object : a) {
            if (!copyOfB.contains(object)) {
                throw new AssertionFailedError("Collectionlar e�it de�il beklenen : " + a + " Eldeki : " + b);
            }
            copyOfB.remove(object);
        }
    }

    /**
     * iki Collection'un elemanlar� birbirine e�itse (s�ra �nemsiz) ok. de�ilse fail.
     *
     * @param a
     * @param b
     */
    public static void assertCollectionContentsEqual(final Collection a, final Collection b) {
        if (a.size() != b.size()) {
            throw new AssertionFailedError("Collectionlar e�it de�il");
        }
        final Collection copyOfB = new LinkedList(b);
        for (final Object object : a) {
            if (!copyOfB.contains(object)) {
                throw new AssertionFailedError("Collectionlar e�it de�il beklenen : " + a + " Eldeki : " + b);
            }
            copyOfB.remove(object);
        }
    }

    public static boolean assertObjectArrayContentsEqual(Object[] d1, Object[] d2) {
        if (d1.length != d2.length) {
            throw new AssertionFailedError("Collectionlar e�it de�il");
        }
        for (Object o1 : d1) {
            boolean found = false;
            for (Object o2 : d2) {
                if (o1.equals(o2))
                    found = true;
            }
            if (!found)
                return false;
        }
        return true;
    }

    public static<T> void printList(List<T> list) {
        System.out.println("Liste boyu: " + list.size());
        for (T obj : list) {
            System.out.println(obj);
        }
    }


    public static <T> Set<T> makeSet(T... elements) {
        return new HashSet<T>(Arrays.asList(elements));
    }

    /**
     * basit satir okuyucu.
     *
     * @param dosya
     * @return 
     * @throws IOException
     */
    public static List<String> satirlariOku(String dosya) throws IOException {
        BufferedReader reader = new KaynakYukleyici("UTF-8").getReader(dosya);
        List<String> satirlar = new ArrayList<String>();
        String s;
        while ((s = reader.readLine()) != null) {
            if (s.startsWith("#") || s.trim().length() == 0) continue;
            satirlar.add(s.trim());
        }
        reader.close();
        return satirlar;
    }

    public static List<TestGirdisi> girdileriOku(String dosya) throws IOException {

        List<String> satirlar = satirlariOku(dosya);
        List<TestGirdisi> sonuc = new ArrayList<TestGirdisi>();
        for (String s : satirlar) {
            int esitlik = s.indexOf(':');
            if (esitlik == -1)
                throw new IllegalArgumentException("Satirda ':' simgesi bekleniyordu: " + s);
            String key = s.substring(0, esitlik).trim();
            String[] deger;
            if (s.length() > esitlik - 1)
                deger = s.substring(esitlik + 1).trim().split(",");
            else
                deger = new String[0];
            sonuc.add(new TestGirdisi(key, deger));
        }
        return sonuc;
    }

}