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
 *  Ahmet A. Akin, Mehmet D. Akin, Guychmyrat Amanmyradov.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.tm.yapi.ek;

import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.tm.yapi.TurkmenceSesliUretici;
import net.zemberek.tm.yapi.kok.TurkmenceKokOzelDurumTipleri;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.TurkceHarf;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkOzelDurumu;

/**
 * edilgen cati eki -il ya da -in seklinde olusabilir. (yap-il-mak, gel-in-mek)
 * ayrica fiillerde sesli dusmesi oldugu durumlarda ozel sekilde uretilmesi gerekir.
 * (kavurmak -> kavr-ul-mak, kavr-Il-mak degil.)
 * User: ahmet
 * Date: Aug 29, 2005
 */
public class EdilgenOzelDurumu extends EkOzelDurumu {

    private Alfabe alfabe;
    private TurkmenceSesliUretici sesliUretici;

    public EdilgenOzelDurumu(Alfabe alfabe ) {
        this.alfabe = alfabe;
        this.sesliUretici = new TurkmenceSesliUretici(alfabe);
    }

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        TurkceHarf son = kelime.sonHarf();
        if(son.sesliMi())
          return new HarfDizisi(0);
        HarfDizisi sonuc = new HarfDizisi();
        if (kelime.kok().ozelDurumIceriyormu(TurkmenceKokOzelDurumTipleri.ISLIK_ARA_SESLI_DUSMESI)) {
            //eger ara sesli dusmesi olmussa eklenecek seslinin dusen sesliye gore
            //belirlenmesi gerekir. yani, "kavurmak" koku ve "kavrulmuS" girisini dusunelim,
            //ara sesli dusmesi ozel durumu nedeniyle "u" harfi kokten duserek "kavr" haline
            //gelir. Ancak koke bu haliyle edilgenlik ekini eklemeye kalkarsak "kavrIlmIS"
            //seklinde yanlis bir kelime ortaya cikardi. Bu nedenle burada dusen eke gore hangi
            // harfin eklenecegi belirleniyor.
            HarfDizisi kok = new HarfDizisi(kelime.kok().icerik(), alfabe);
            TurkceHarf kokAsilSesli = kok.sonSesli();
            sonuc.ekle(sesliUretici.sesliBelirleIU(kokAsilSesli));
        } else
            sonuc.ekle(sesliUretici.sesliBelirleIU(kelime.icerik()));
        if (son.equals(alfabe.harf('l')))
            sonuc.ekle(alfabe.harf('n'));
        else
            sonuc.ekle(alfabe.harf('l'));
        return sonuc;
    }

    @Override
    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk){
       return cozumlemeIcinUret(kelime, null, null);
    }

}
