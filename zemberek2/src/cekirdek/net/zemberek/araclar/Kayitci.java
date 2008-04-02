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

package net.zemberek.araclar;

import java.util.logging.*;

/**
 * Java kayit tutma sisemi icin yardimci metodlar.
 * User: ahmet
 * Date: Jan 17, 2006
 */
public class Kayitci {

    private static final ConsoleHandler ch = new ConsoleHandler();

    static {
        try {
            ch.setFormatter(new KayitBicimleyici());
        } catch (SecurityException e) {
            System.out.println("Guvenlik erisim problemi. Varsayilan kayit bicimleyici kullanilacak.");
        }
    }

    /**
     * default Logger uretici. seviye:WARNING
     * @param ad logger adi.
     * @return Logger
     */
    public static Logger kayitciUret(String ad) {
        return kayitciUret(ad, Level.WARNING);
    }

    /**
     * handler seviyesinden farkli olarak istenilen seviyede kayitci uretilmesini saglar.
     * @param ad : logger adi
     * @param level : log level
     * @return  Logger
     */
    public static Logger kayitciUret(String ad, Level level) {
        Logger logger = Logger.getLogger(ad);
        try {
            logger.setUseParentHandlers(false);
            logger.addHandler(ch);
            logger.setLevel(level);
        } catch (SecurityException e) {
            System.out.println("Guvenlik erisim problemi. Varsayilan kayitci kullanilacak.");
        }
        return logger;
    }

    public static Logger kayitciUret(Class clazz) {
        return kayitciUret(clazz.getName());
    }

    //henuz kullanilmiyor.
    public static void genelKayitSeviyesiAyarla(String seviye) {
        Level l=Level.WARNING;
        if(seviye.equals("INFO"))
          l=Level.INFO;
        else if(seviye.equals("FINE"))
          l=Level.FINE;
        Handler[] handlers = Logger.getLogger("").getHandlers();
        for (Handler handler : handlers) {
            handler.setLevel(l);
        }
    }
}
