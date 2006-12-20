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

package net.zemberek.araclar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Java Logger icin basit tek satirlik bicimleyici. Java
 */
public class KayitBicimleyici extends Formatter {

    private static final DateFormat format = new SimpleDateFormat("h:mm:ss");
    private static final String lineSep = System.getProperty("line.separator");

    /**
     * Java logger icin tek satirlik cikti uretir.
     */
    public String format(LogRecord record) {
        String loggerName = record.getLoggerName();
        if (loggerName == null) {
            loggerName = "root";
        }
        int pointLoc = loggerName.lastIndexOf('.');
        if (pointLoc != -1)
            loggerName = loggerName.substring(pointLoc+1);
        StringBuilder output = new StringBuilder()
                .append(record.getLevel()).append(' ').append(':')
                .append(record.getMessage()).append(' ')
                .append("[")
                .append(loggerName).append('|')
                .append(format.format(new Date(record.getMillis())))
                .append(']')
                .append(lineSep);
        return output.toString();
    }

}