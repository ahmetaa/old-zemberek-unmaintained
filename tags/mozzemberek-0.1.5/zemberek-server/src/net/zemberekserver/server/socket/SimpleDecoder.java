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
    	while(buffer.hasRemaining()){
    		// First read the message length. Length is ASCII coded decimal, like "12" , 
    		// one byte each character. We read while it is a number 
    		if (false == boyOkundu) {
    			byte read = buffer.get();
    			if (read >= '0' && read <= '9' && index<7) {
    				temp[index++] = (byte)(read);
    			}
    			// So , we have a legal message length info, convert it into an int, 
    			// reset counters, prepare a buffer.
    			else{
    				if(read != ' ' || index == 0 || index > 7) {
    					throw new ProtocolDecoderException("Zemberek soket protokol hatası");
    				}
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
//  			System.out.println("Index: " + index + " Okunacak miktar: "
//  			+ okunacakMiktar + " Bufferde Kalan: " + buffer.remaining()
//  			+ " Kalan : " + kalan);
    			buffer.get(govde, index, okunacakMiktar);
    			index = index + okunacakMiktar;
    			kalan = kalan - okunacakMiktar;
    			if (kalan == 0) {
    				out.write(govde);
    				reset();
    			}
    		}
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