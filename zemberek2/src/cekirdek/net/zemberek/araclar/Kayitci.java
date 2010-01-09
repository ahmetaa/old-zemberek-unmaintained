/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.araclar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

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
     *
     * @param ad logger adi.
     * @return Logger
     */
    public static Logger kayitciUret(String ad) {
        return kayitciUret(ad, Level.WARNING);
    }

    /**
     * handler seviyesinden farkli olarak istenilen seviyede kayitci uretilmesini saglar.
     *
     * @param ad    : logger adi
     * @param level : log level
     * @return Logger
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
        Level l = Level.WARNING;
        if (seviye.equals("INFO"))
            l = Level.INFO;
        else if (seviye.equals("FINE"))
            l = Level.FINE;
        Handler[] handlers = Logger.getLogger("").getHandlers();
        for (Handler handler : handlers) {
            handler.setLevel(l);
        }
    }

    private static class KayitBicimleyici extends Formatter {

        private final DateFormat format = new SimpleDateFormat("h:mm:ss");
        private final String lineSep = System.getProperty("line.separator");

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
                loggerName = loggerName.substring(pointLoc + 1);
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
}
