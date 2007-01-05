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
