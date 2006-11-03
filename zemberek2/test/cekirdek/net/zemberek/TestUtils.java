/*
 * Created on 22.Eyl.2004
 */
package net.zemberek;

import junit.framework.AssertionFailedError;
import net.zemberek.bilgi.KaynakYukleyici;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author MDA & GBA
 */

public class TestUtils {
    /**
     * iki Collection'un elemanlarý birbirine eþitse (sýra önemsiz) ok. deðilse fail.
     *
     * @param a
     * @param b
     */
    public static void assertCollectionsEqual(final Collection a, final Collection b) {
        if (a.size() != b.size()) {
            throw new AssertionFailedError("Collectionlar eþit deðil");
        }
        final Collection copyOfB = new LinkedList(b);
        for (final Object object : a) {
            if (!copyOfB.contains(object)) {
                throw new AssertionFailedError("Collectionlar eþit deðil beklenen : " + a + " Eldeki : " + b);
            }
            copyOfB.remove(object);
        }
    }

    /**
     * iki Collection'un elemanlarý birbirine eþitse (sýra önemsiz) ok. deðilse fail.
     *
     * @param a
     * @param b
     */
    public static void assertCollectionContentsEqual(final Collection a, final Collection b) {
        if (a.size() != b.size()) {
            throw new AssertionFailedError("Collectionlar eþit deðil");
        }
        final Collection copyOfB = new LinkedList(b);
        for (final Object object : a) {
            if (!copyOfB.contains(object)) {
                throw new AssertionFailedError("Collectionlar eþit deðil beklenen : " + a + " Eldeki : " + b);
            }
            copyOfB.remove(object);
        }
    }

    /**
     * Diziler ayný mý kontrolü
     *
     * @param a
     * @param b
     */
    public static void assertArraysEqual(byte[] a, byte[] b) {
        if (a.length != b.length) {
            throw new AssertionFailedError("Diziler eþit deðil ");
        }

        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                throw new AssertionFailedError("Diziler eþit deðil");
            }
        }
    }

    /**
     * char dizileri için eþitlik kontrolü.
     *
     * @param a
     * @param b
     */
    public static void assertArraysEqual(char[] a, char[] b) {
        if (a.length != b.length) {
            throw new AssertionFailedError("Diziler eþit deðil ");
        }

        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                throw new AssertionFailedError("Diziler eþit deðil");
            }
        }
    }

    /**
     * char dizileri için eþitlik kontrolü.
     *
     * @param a
     * @param b
     */
    public static void assertArraysEqual(Object[] a, Object[] b) {
        if (a.length != b.length) {
            throw new AssertionFailedError("Diziler eþit deðil ");
        }

        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(b[i])) {
                throw new AssertionFailedError("Diziler eþit deðil");
            }
        }
    }

    public static void printList(List list) {
        System.out.println("Liste boyu: " + list.size());
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static void printArray(Object[] array) {
        System.out.println("Liste boyu: " + array.length);
        for (Object obj : array) {
            System.out.println(obj.toString());
        }
    }

    /**
     * basit satir okuyucu.
     * @param dosya
     * @return
     * @throws IOException
     */
    public static List<String> satirlariOku(String dosya) throws IOException {
        BufferedReader reader = new KaynakYukleyici("UTF-8").getReader(dosya);
        List<String> satirlar = new ArrayList();
        String s;
        while ((s = reader.readLine()) != null) {
            if (s.startsWith("#") || s.trim().length() == 0) continue;
            satirlar.add(s.trim());
        }
        reader.close();
        return satirlar;
    }
}