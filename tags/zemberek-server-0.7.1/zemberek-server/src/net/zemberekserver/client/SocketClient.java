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
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberekserver.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.zemberekserver.server.Defaults;
import net.zemberekserver.server.socket.ZemberekProtocolCodecFactory;

import org.apache.mina.common.ConnectFuture;
import org.apache.mina.common.DefaultIoFilterChainBuilder;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketConnector;
import org.apache.mina.transport.socket.nio.SocketConnectorConfig;

public class SocketClient extends Thread{

	Random r = new Random();
	private ClientProtocolHandler handler = null;
	private IoSession session;
	SocketConnector connector = new SocketConnector();

	public boolean connect() {
		
		SocketAddress address =  new InetSocketAddress( Defaults.HOST_ADDRESS, Defaults.PORT_NUMBER);
        handler = new ClientProtocolHandler();
        	
		if (session != null && session.isConnected()) {
			throw new IllegalStateException("Already connected. Disconnect first.");
		}

		try {
			SocketConnectorConfig config = new SocketConnectorConfig();
			DefaultIoFilterChainBuilder chain = config.getFilterChain();
	        chain.addLast( "codec", new ProtocolCodecFilter( new ZemberekProtocolCodecFactory() ) );
			ConnectFuture future1 = connector.connect(address, handler, config);
			future1.join(Defaults.DEFAULT_TIMEOUT);
			if (!future1.isConnected()) {
				System.out.println("Server not running.");
				return false;
			}
			session = future1.getSession();
			System.out.println("Session created!");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void run(){
		connect();
		long start = System.currentTimeMillis();
		for (int i=0; i<10000; i++){
			if(i%100 == 0) System.out.print(".");
			handler.sendTestMessage(new TestMessage("* doğru", "*"));
		}
		System.out.println("Sure: " + (System.currentTimeMillis() - start));
//			startTest(handler);
//		System.out.println("\nTest Başarılı. Zemberek Server çalışıyor.");
	}
	
	
	public static void main(String[] args) {
		for(int i=0; i<1; i++) {
			SocketClient client = new SocketClient();
			client.start();
		}
	}

    public void startTest(ClientProtocolHandler handler) {
        System.out.println("Zemberek oturumu hazır. Test başladı.");
        try{
        	handler.sendTestMessage(new TestMessage("* test", "*"));
        	handler.sendTestMessage(new TestMessage("* tset", "#"));
        	handler.sendTestMessage(new TestMessage("* yapabilirlerse", "*"));
        	handler.sendTestMessage(new TestMessage("* imbiklerin", "*"));
        	handler.sendTestMessage(new TestMessage("* mrhaba", "#"));
        	handler.sendTestMessage(new TestMessage("& mrhaba", "& (merhaba)"));
        }catch (Exception e){
        	e.printStackTrace();
        	System.err.println("Test başarısız.");
        	System.exit(0);
        }
    }
}


class ClientProtocolHandler extends IoHandlerAdapter{

	ConcurrentLinkedQueue<TestMessage> queue = new ConcurrentLinkedQueue<TestMessage>();
	IoSession session = null;
	TestMessage currentMessage = null;
	
	public ClientProtocolHandler(){
		
	}
	
	public void exceptionCaught(IoSession session, Throwable exception)
			throws Exception {
		exception.printStackTrace();
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		byte[] response = (byte[])message;
		String str = new String(response, "UTF-8");
		//System.out.println("Gelen: " + str + " Beklenen: " + currentMessage.response);
		if (str.equalsIgnoreCase(currentMessage.response)){
			
		}
		synchronized(this){
			this.notify();
		}
	}

	public void sessionCreated(IoSession session) throws Exception {
		this.session = session;
		System.out.println("Client Session Created");
	}
	
	public synchronized void sendTestMessage(TestMessage msg){
		queue.add(msg);
		sendMessage();
	}

	private void sendMessage() {
		synchronized(this){
			while(!queue.isEmpty()){
				currentMessage = queue.remove();
				session.write(currentMessage.request);
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

class TestMessage {
	String request;
	String response;
	
	public TestMessage(String request, String response) {
		this.request = request;
		this.response = response;
	}
}