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
 *  Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *   Serkan Kaba
 *
 *  ***** END LICENSE BLOCK *****
 */
package net.zemberekserver.server;

import java.io.IOException;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberekserver.server.dbus.ZemberekDbus;
import net.zemberekserver.server.socket.SocketServer;

public class ZemberekServer {
	Zemberek zemberek;
	SocketServer socketServer = null;
	ZemberekDbus zemberekDbus = null;
	
	public void init(){
		zemberek = new Zemberek( new TurkiyeTurkcesi());
	}
	
	public void initSocketServer() throws IOException{
		socketServer = new SocketServer();
		socketServer.init(zemberek, Config.serverPort, Config.allowRemote);
	}
	
	public void initDBusServer(){
		ZemberekDbus.start(zemberek, Config.busName);
	}
	
	public void shutdown(){
		if (socketServer != null) {
			socketServer.shutdown();
		}
		if (zemberekDbus != null) {
//			zemberekDbus.shutdown();
		}
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ZemberekServer server = new ZemberekServer();
		server.init();
		if(Config.useSockets){
			try {
				server.initSocketServer();
			} catch (IOException e) {
				e.printStackTrace();
				server.shutdown();
			}
		}
		if(Config.useDbus){
			server.initDBusServer();
		}
		System.out.println("Baslatim zamani: " + (System.currentTimeMillis() - start) + "ms.");
	}
}
