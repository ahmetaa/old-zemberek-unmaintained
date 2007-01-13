package net.zemberekserver.server.socket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.zemberek.erisim.Zemberek;

import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;

public class ZemberekServerProtocolHandler extends IoHandlerAdapter {
    private Set<IoSession> sessions = Collections.synchronizedSet( new HashSet<IoSession>() );
    private Zemberek zemberek;
	
	public ZemberekServerProtocolHandler(Zemberek zemberek) {
		this.zemberek = zemberek;
	}

	public void exceptionCaught(IoSession session, Throwable exception)	throws Exception {
		exception.printStackTrace();
		session.close();
		sessions.remove(session);
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		byte[] msg = (byte[])message;
		String content = new String(msg, "UTF-8");
//		System.out.println("Content: " + content);
		session.write(processMessage(content));
	}

	/**
	 * Very simple ISpell like protocol. 
	 * Input : "* word" Output : "*" if correct, "#" if incorrect, "?" if error
	 * Input : "& word" Output : "& (suggestion1, suggestion2, suggestion3, ...)" , "#" if no suggestions.
	 * 
	 * TODO: Method is made syncronized to avoid possible thread-safety issues in zemberek library
	 * We should have tests to be sure zemberek core library is thread safe.
	 * @param mesaj
	 * @return
	 */
	private synchronized String processMessage(String mesaj) {
        try{
            //System.out.println("Alinan mesaj: " + mesaj );
            String[] parcalar = mesaj.trim().split(" ");
            if (parcalar.length < 2) {
            	return "?";
            }
            if (parcalar[0].equals("*")) {
                for (int i = 1; i < parcalar.length; i++) {
                    if (zemberek.kelimeDenetle(parcalar[i].trim())) {
                        return "*";
                    } else {
                    	return "#";
                    }
                }
            } else if (parcalar[0].equalsIgnoreCase("&")) {
                String[] liste = zemberek.oner(parcalar[1].trim());
                if (liste.length == 0) {
                	return "#";
                }
                else{
                    String cevap = "& (";
                    for (int i = 0; i < liste.length; i++) {
                        cevap += liste[i];
                        if (i < liste.length - 1)
                            cevap += ",";
                    }
                    cevap += ")";
                    return cevap;
                }
            }
            }catch(Exception e){
                e.printStackTrace();
            }
            // anything else, return error
            return "?";
	}

	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("Session closed");
		sessions.remove(session);
	}

	public void sessionCreated(IoSession session) throws Exception {
		//TODO: Konfigürasyona göre sadece yerel bağlantılara izin ver.
		System.out.println("Session created");
		if(!sessions.contains(session)){
			sessions.add(session);
		}
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		
	}
}