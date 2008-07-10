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
 *  Serkan Kaba.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.deney;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import net.zemberek.erisim.Zemberek;
import net.zemberek.yapi.DilAyarlari;

public class ZemberekDenetlemeAdayi {

	private Zemberek zemberek;

	public ZemberekDenetlemeAdayi(DilAyarlari dilAyarlari) {
		zemberek = new Zemberek(dilAyarlari);
	}
	
	public ZemberekDenetlemeAdayi(String dilKodu) {
		zemberek = ZemberekFactory.getZemberek(dilKodu);
	}
	
	/* net.zemberek.erisim.Zemberek için aday metod */
	public List<DenetlemeHatasi> metinDenetle(String text) {
		Hashtable<String, Integer> sonPozisyonlar = new Hashtable<String, Integer>();
		Vector<DenetlemeHatasi> ret = new Vector<DenetlemeHatasi>();
		StringTokenizer tok = new StringTokenizer(text, "!'#%&/()=?-_:.,;\"\r\n\t ");
		while (tok.hasMoreTokens()) {
			String kelime = tok.nextToken();
			int pos;
			if (sonPozisyonlar.containsKey(kelime))
				pos = text.indexOf(kelime, sonPozisyonlar.get(kelime) + kelime.length());
			else
				pos = text.indexOf(kelime);
			sonPozisyonlar.put(kelime,pos);
			if (!zemberek.kelimeDenetle(kelime))
				ret.add(new DenetlemeHatasi(pos, kelime, zemberek.oner(kelime)));
			sonPozisyonlar.put(kelime, pos);
		}
		return ret;
	}

	public static void main(String[] args) {
		//ZemberekDenetlemeAdayi zemberek = new ZemberekDenetlemeAdayi(new TurkiyeTurkcesi());
		ZemberekDenetlemeAdayi zemberek = new ZemberekDenetlemeAdayi("tr");
		List<DenetlemeHatasi> hatalar = zemberek.metinDenetle("yalnış yanlış doğru yanlız yalnız htalı hatalı değil");
		for(DenetlemeHatasi hata: hatalar) {
			System.out.println("Hatalı kelime: "+hata.getHatalikelime());
			System.out.println("Offset: "+ hata.getHataPozisyonu());
			System.out.println("Öneriler: "+Arrays.toString(hata.getOneriler()));
			System.out.println("---------------------------");
		}
	}
}
