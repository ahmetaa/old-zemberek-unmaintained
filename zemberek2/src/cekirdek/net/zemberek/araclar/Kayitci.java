package net.zemberek.araclar;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * User: ahmet
 * Date: Jan 17, 2006
 */
public class Kayitci {

    private static final ConsoleHandler ch = new ConsoleHandler();
    static {
        ch.setFormatter(new KayitBicimleyici());
    }

    public static Logger kayitciUret(String ad) {
        Logger logger = Logger.getLogger(ad);
        logger.setUseParentHandlers(false);
        logger.addHandler(ch);
        return logger;
    }

    public static Logger kayitciUret(Class clazz) {
        return kayitciUret(clazz.getName());
    }

}
