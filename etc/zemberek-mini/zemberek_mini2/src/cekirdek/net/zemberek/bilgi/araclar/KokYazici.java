/*
 * Created on 27.Mar.2004
 */
package net.zemberek.bilgi.araclar;

import net.zemberek.yapi.Kok;

import java.io.IOException;
import java.util.List;

/**
 * @author MDA
 */
public interface KokYazici {

    void yaz(List<Kok> kokler) throws IOException;

}
