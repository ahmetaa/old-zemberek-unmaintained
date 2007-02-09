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
 *  The Original Code is "Zemberek Sunucu"
 *
 *  The Initial Developer of the Original Code is
 *  Serkan Kaba.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *   Mehmet D. Akin
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberekserver.server.dbus;

import java.util.List;
import org.freedesktop.dbus.DBusInterface;

public interface ZemberekDbusInterface extends DBusInterface {
	
	//public Kelime[] asciiCozumle(String giris);
	public String[] asciidenTurkceye(String giris);
	public String asciiyeDonustur(String giris);
	public String[] hecele(String giris);
	public List<List<String>> kelimeAyristir(String giris);
	//public Kelime[] kelimeCozumle(String giris);
	public boolean kelimeDenetle(String giris);
	public String[] oner(String giris);
	public String temizle(String giris);
	//public String kelimeUret(Kok giris, List ekler);
}
