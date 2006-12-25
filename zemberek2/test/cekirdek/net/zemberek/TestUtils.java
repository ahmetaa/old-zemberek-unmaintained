/*
 *  ***** BEGIN LICENSE BLOCK *****
 *
 *  Version: MPL 1.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is "Zemberek Dogal Dil Isleme Kutuphanesi"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

/*
 * Created on 22.Eyl.2004
 */
package net.zemberek;

import junit.framework.AssertionFailedError;
import net.zemberek.bilgi.KaynakYukleyici;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

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

    public static boolean assertObjectArrayContentsEqual(Object[] d1, Object[] d2) {
        if (d1.length != d2.length) {
            throw new AssertionFailedError("Collectionlar eþit deðil");
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

    public static void printList(List list) {
        System.out.println("Liste boyu: " + list.size());
        for (Object obj : list) {
            System.out.println(obj);
        }
    }


    public static <T> Set<T> makeSet(T... elements) {
        return new HashSet(Arrays.asList(elements));
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
        List<String> satirlar = new ArrayList();
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
        List<TestGirdisi> sonuc = new ArrayList();
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