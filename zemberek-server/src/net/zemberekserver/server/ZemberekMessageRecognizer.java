package net.zemberekserver.server;

import java.nio.ByteBuffer;

import net.gleamynode.netty2.Message;
import net.gleamynode.netty2.MessageParseException;
import net.gleamynode.netty2.MessageRecognizer;

/**
 * MDA & ER & HO 
 */
public class ZemberekMessageRecognizer implements MessageRecognizer {

	public ZemberekMessageRecognizer() {
	}

	public Message recognize(ByteBuffer buf) throws MessageParseException {
//		if (buf.remaining() < ZemberekMesaji.BASLIK_BOY)
//			return null;
//        
        return new IletisimMesaji();
	}
}