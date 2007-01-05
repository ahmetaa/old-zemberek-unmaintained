package net.zemberekserver.server.socket;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class SimpleEncoder implements ProtocolEncoder {

	public void dispose(IoSession session) throws Exception {
	}

	public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
			throws Exception {
		byte[] msg = (byte[]) message;
		ByteBuffer buffer = ByteBuffer.allocate(msg.length);
		buffer.put(msg);
		buffer.flip();
		out.write(buffer);
	}

}
