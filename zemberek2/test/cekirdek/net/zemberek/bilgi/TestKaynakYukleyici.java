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
