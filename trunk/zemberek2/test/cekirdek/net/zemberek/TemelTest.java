/*
 *  ***** BEGIN LICENSE BLOCK *****
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
 *  The Original Code is Zemberek Doðal Dil Ýþleme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akýn, Mehmet D. Akýn.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  ***** END LICENSE BLOCK *****
 */

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
