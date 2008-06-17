/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.TurkceHarf;


/**
 * -ki zaman eki bazi durumlarda -ki yerine kU seklinde olusur. (dun-kU gibi.)
 * Bu ozel durum sayilacagindan bi sinifa ihtiyac duyuldu.
 * User: ahmet
 * Date: Aug 15, 2005
 */
public class ZamanKiOzelDurumu extends EkOzelDurumu {

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        TurkceHarf sonSesli = kelime.icerik().sonSesli();
        if (sonSesli.charDeger()=='u' || sonSesli.charDeger()==Alfabe.CHAR_uu)
            return ekUretici.cozumlemeIcinEkUret(kelime.icerik(), giris, uretimBilesenleri);
        else
            return null;
    }

    @Override
    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk){
        return cozumlemeIcinUret(kelime, null, null);
    }
}
