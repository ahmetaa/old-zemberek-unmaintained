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
