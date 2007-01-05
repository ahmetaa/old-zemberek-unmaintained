package net.zemberekserver.server.socket;


import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class ZemberekProtocolCodecFactory implements ProtocolCodecFactory{
	
	public ProtocolDecoder getDecoder() throws Exception {
		return new SimpleDecoder();
	}
	public ProtocolEncoder getEncoder() throws Exception {
		return new SimpleEncoder();
	}

}
