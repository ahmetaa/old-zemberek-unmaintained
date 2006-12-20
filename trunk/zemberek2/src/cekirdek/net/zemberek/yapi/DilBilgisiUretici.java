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

package net.zemberek.yapi;

import java.io.IOException;


public class DilBilgisiUretici {


    public static final String TR_SINIF = "net.zemberek.tr.yapi.TurkiyeTurkcesi";
    public static final String TM_SINIF = "net.zemberek.tm.yapi.Turkmence";

    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            String dilAdi = args[0].toLowerCase().trim();
            uret(dilAdi);
            System.exit(0);

        } else {
            System.out.println("Dil adi girmelisiniz (tr,tm,az gibi)");
            System.exit(1);
        }
    }

    public static void uret(String dilAdi) throws IOException {
        DilAyarlari dilAyari = null;

        if (dilAdi.equals("tr"))
            dilAyari = dilAyarUret(TR_SINIF);
        else if (dilAdi.equals("tm"))
            dilAyari = dilAyarUret(TM_SINIF);
        else {
            System.out.println("Dil sinifi bulunamiyor:" + dilAdi);
            System.exit(1);
        }

        new TurkceDilBilgisi(dilAyari).ikiliKokDosyasiUret();


    }

    public static DilAyarlari dilAyarUret(String sinifadi) {
        Class c = null;
        try {
            c = Class.forName(sinifadi);
        } catch (ClassNotFoundException e) {
            System.out.println("Sinif bulunamadi!:" + sinifadi);
            System.exit(1);
        }
        try {
            return (DilAyarlari) c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}


