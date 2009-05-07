/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
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
    @Override
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