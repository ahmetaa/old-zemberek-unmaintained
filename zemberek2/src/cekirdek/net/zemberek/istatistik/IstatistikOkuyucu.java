/*
 * Created on 16.Haz.2005
 *
 */
package net.zemberek.istatistik;

import net.zemberek.bilgi.kokler.Sozluk;

import java.io.IOException;

public interface IstatistikOkuyucu {
    public KokIstatistikBilgisi oku(Sozluk sozluk) throws IOException;
}
