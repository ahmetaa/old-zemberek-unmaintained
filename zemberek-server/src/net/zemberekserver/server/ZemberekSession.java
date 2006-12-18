/*
 * Created on 16.Ara.2004
 *
 */
package net.zemberekserver.server;

import net.gleamynode.netty2.Message;
import net.gleamynode.netty2.Session;
import net.gleamynode.netty2.SessionListener;


/**
 * @author MDA & ER
 *
 */
public class ZemberekSession  implements SessionListener{
    
    private int durum = BAGLANTI_YOK;
    public static final int BAGLANTI_YOK = 0;
    public static final int BAGLANTI_VAR = 1;
    private boolean sonlandi = false;
    
    Session session = null;
    ZemberekSessionListener dinleyici = null;
    
    public ZemberekSession(Session session) {
        this.session = session;
    }

    /** 
     * @see net.gleamynode.netty2.SessionListener#connectionEstablished(net.gleamynode.netty2.Session)
     */
    public void connectionEstablished(Session session) {
        System.out.println("Connection established!");
        dinleyici.zemberekSessionReady(this);
    }
    
    public int getDurum()
    {
    	return durum;
    }

    /** 
     * @see net.gleamynode.netty2.SessionListener#connectionClosed(net.gleamynode.netty2.Session)
     */
    public void connectionClosed(Session session) {
        System.out.println("Connection Closed!");
    }

    /** 
     * @see net.gleamynode.netty2.SessionListener#messageReceived(net.gleamynode.netty2.Session, net.gleamynode.netty2.Message)
     */
    public void messageReceived(Session session, Message message) {
        IletisimMesaji iletisimMesaji = (IletisimMesaji) message;
        //System.out.println("Hex: " + HexConverter.byteArrayToHexString(iletisimMesaji.getGovde(), " "));       
        try {
            ZemberekMesaji mesaj = Mesajlar.mesajCoz(iletisimMesaji);
            if(dinleyici != null)
                 dinleyici.messageReceived(this, mesaj);
        }
        catch (MesajException e) {
            e.printStackTrace();
        }
    }

    /** 
     * @see net.gleamynode.netty2.SessionListener#messageSent(net.gleamynode.netty2.Session, net.gleamynode.netty2.Message)
     */
    public void messageSent(Session session, Message message) {
    
    }

    /** 
     * @see net.gleamynode.netty2.SessionListener#sessionIdle(net.gleamynode.netty2.Session)
     */
    public void sessionIdle(Session session) {
    }

    /** 
     * @see net.gleamynode.netty2.SessionListener#exceptionCaught(net.gleamynode.netty2.Session, java.lang.Throwable)
     */
    public void exceptionCaught(Session session, Throwable cause) {
    }
    
    public void mesajYaz(ZemberekMesaji mesaj) {
        IletisimMesaji iletisimMesaji = Mesajlar.mesajOlustur(mesaj);
        //System.out.println("Gonderilen mesaj: " + mesaj.getMesaj());
        session.write(iletisimMesaji);
    }

    public boolean isSonlandi() {
        return sonlandi;
    }
    
    public void setDinleyici(ZemberekSessionListener dinleyici) {
        this.dinleyici = dinleyici;
    }
}
