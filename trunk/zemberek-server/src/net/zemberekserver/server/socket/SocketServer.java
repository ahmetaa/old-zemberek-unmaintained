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

package net.zemberekserver.server.socket;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

import org.apache.mina.common.DefaultIoFilterChainBuilder;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.common.IoAcceptorConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

public class SocketServer {

	IoAcceptor acceptor;
	DefaultIoFilterChainBuilder chain;

	/**
	 * 
	 * @param allowRemote 
	 * @param portNumber 
	 * @throws IOException
	 */
	public void init(Zemberek zemberek, int portNumber, boolean allowRemote) throws IOException {
        acceptor = new SocketAcceptor();
        IoAcceptorConfig config = new SocketAcceptorConfig();
        chain = config.getFilterChain();
        chain.addLast( "codec", new ProtocolCodecFilter( new ZemberekProtocolCodecFactory() ) );
        //Sokete bagla.
        acceptor.bind(
                new InetSocketAddress( portNumber ),
                new ZemberekServerProtocolHandler(zemberek),
                config );
        System.out.println( "Soket sunucusu başlatıldı. " + portNumber + " portundan dinliyorum.");
	}
	
	public void shutdown(){
		acceptor.unbindAll();
	}
	
	public static void main(String[] args) {
		SocketServer server = new SocketServer();
		try {
			server.init(new Zemberek(new TurkiyeTurkcesi()), 1234, false);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
