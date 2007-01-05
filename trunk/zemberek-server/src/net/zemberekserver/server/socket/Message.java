package net.zemberekserver.server.socket;

import java.io.UnsupportedEncodingException;

public class Message {
	
	public static byte[] encodeMessage(String mesaj){
		try {
			byte[] govde = mesaj.getBytes("UTF-8");
			byte[] boyBuffer = Integer.toString(govde.length).getBytes();
			// Toplam mesaj boyumuz "boy + boşluk + UTf-8 kodlanmış Stringin byte hali" şeklinde  
			byte[] mesajBytes = new byte[govde.length + boyBuffer.length + 1];
			System.arraycopy(boyBuffer, 0, mesajBytes, 0, boyBuffer.length);
			mesajBytes[boyBuffer.length] = ' ';
			System.arraycopy(govde, 0, mesajBytes, boyBuffer.length+1, govde.length);
			return mesajBytes;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
