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

import java.util.Hashtable;

import net.zemberek.erisim.Zemberek;
import net.zemberek.yapi.DilAyarlari;

public abstract class ZemberekFactory {

	private static Hashtable<String, Zemberek> zemberekNesneleri;
	static {
		zemberekNesneleri = new Hashtable<String, Zemberek>();
	}

	public static Zemberek getZemberek(String dilKodu) {
		synchronized (zemberekNesneleri) {
			if (zemberekNesneleri.containsKey(dilKodu))
				return zemberekNesneleri.get(dilKodu);
			else {
				Zemberek z = _getZemberek(dilKodu);
				zemberekNesneleri.put(dilKodu, z);
				return z;
			}
		}
	}

	private static Zemberek _getZemberek(String dilKodu) {
		try {
			DilAyarlari dil = null;
			if (dilKodu.equalsIgnoreCase("tr") || dilKodu.equalsIgnoreCase("tk")) {
				if (dilKodu.equalsIgnoreCase("tr"))
					dil = (DilAyarlari) Class.forName("net.zemberek.tr.yapi.TurkiyeTurkcesi").newInstance();
				else if (dilKodu.equalsIgnoreCase("tk"))
					dil = (DilAyarlari) Class.forName("net.zemberek.tk.yapi.Turkmence").newInstance();
				if(dil !=null) return new Zemberek(dil);
				return null;
			}
			else
				throw new IllegalArgumentException("Geçersiz dil kodu");

		} catch (Exception e) {
			throw new RuntimeException("Belirtilen dil desteği bulunamadı");
		}
	}

}