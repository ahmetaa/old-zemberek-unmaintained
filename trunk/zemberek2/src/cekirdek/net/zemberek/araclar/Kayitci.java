package net.zemberek.araclar;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;

/**
 * User: ahmet
 * Date: Jan 17, 2006
 */
public class Kayitci {

    private static final ConsoleHandler ch = new ConsoleHandler();

    static {
        ch.setFormatter(new KayitBicimleyici());
    }

    /**
     * default Logger uretici. seviye:WARNING
     * @param ad
     * @return
     */
    public static Logger kayitciUret(String ad) {
        Logger logger = Logger.getLogger(ad);
        logger.setUseParentHandlers(false);
        logger.addHandler(ch);
        logger.setLevel(Level.WARNING);
        return logger;
    }

    /**
     * handler seviyesinden farkli olarak istenilen seviyede kayitci uretilmesini saglar.
     * @param ad
     * @param level
     * @return
     */
    public static Logger kayitciUret(String ad, Level level) {
        Logger logger = Logger.getLogger(ad);
        logger.setUseParentHandlers(false);
        logger.addHandler(ch);
        logger.setLevel(level);
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
