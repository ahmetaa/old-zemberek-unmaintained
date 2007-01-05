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
		zemberekDbus = new ZemberekDbus();
		zemberekDbus.start(zemberek, Config.busName);
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
