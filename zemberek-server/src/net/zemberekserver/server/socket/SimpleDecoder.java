package net.zemberekserver.server.socket;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderException;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class SimpleDecoder implements ProtocolDecoder {

    public static int MAX_MESSAGE_SIZE = 1024*1024; //1MB
    private boolean boyOkundu = false;
    private int mesajBoy;
    private int kalan = 0;
    private byte[] govde;

    private byte[] temp = new byte[10];
    private int okunacakMiktar;
    private int index = 0;
    
	public void decode(IoSession session, ByteBuffer buffer,
			ProtocolDecoderOutput out) throws Exception {
		
        // First read the message length. Length is binary coded decimal, like "12" , 
		// one byte each character. We read while it is a number 
        if (false == boyOkundu) {
            while (buffer.hasRemaining()) {
                byte read = buffer.get();
                if (read >= '0' && read <= '9') {
                    if (index > 7) {
                    	throw new ProtocolDecoderException("Mesaj boyu çok büyük!. ");
                    }
                    temp[index++] = (byte)(read);
                } else
                    break;
            }
            // So , we have a legal message length info, convert it into an int, 
            // reset counters, prepare a buffer.
            if (index > 0) {
                String boyString = new String(temp, 0, index);
                mesajBoy = Integer.parseInt(boyString);
                //System.out.println("Gelen stringin (UTF-8) boyu: " + mesajBoy);
                boyOkundu = true;
                kalan = mesajBoy;
                index = 0;
                govde = new byte[mesajBoy];
            }
        }
        
        if(kalan > 0){
        	// Reading body.
        	okunacakMiktar = kalan > buffer.remaining() ? buffer.remaining() : kalan;
//        	System.out.println("Index: " + index + " Okunacak miktar: "
//        			+ okunacakMiktar + " Bufferde Kalan: " + buffer.remaining()
//        			+ " Kalan : " + kalan);
        	buffer.get(govde, index, okunacakMiktar);
        	index = index + okunacakMiktar;
        	kalan = kalan - okunacakMiktar;
        }
		if (kalan == 0) {
			out.write(govde);
			reset();
        }
	}
	
	private void reset(){
		boyOkundu = false;
		index = 0;
		kalan = 0;
	}		

	public void dispose(IoSession session) throws Exception {
	}

	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
	}
}