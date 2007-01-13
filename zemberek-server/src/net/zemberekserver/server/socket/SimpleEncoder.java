package net.zemberekserver.server.socket;

import java.io.UnsupportedEncodingException;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class SimpleEncoder implements ProtocolEncoder {

	public void dispose(IoSession session) throws Exception {
	}

	public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
			throws Exception {
		try {
			byte[] govde = ((String)message).getBytes("UTF-8");
			byte[] boyBuffer = Integer.toString(govde.length).getBytes();
			// Toplam mesaj boyumuz "boy + boşluk + UTf-8 kodlanmış Stringin byte hali" şeklinde  
			byte[] mesajBytes = new byte[govde.length + boyBuffer.length + 1];
			System.arraycopy(boyBuffer, 0, mesajBytes, 0, boyBuffer.length);
			mesajBytes[boyBuffer.length] = ' ';
			System.arraycopy(govde, 0, mesajBytes, boyBuffer.length+1, govde.length);
			ByteBuffer buffer = ByteBuffer.allocate(mesajBytes.length);
			buffer.put(mesajBytes);
			buffer.flip();
			out.write(buffer);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
