/*
 * Created on 14.Ara.2004
 *
 */
package net.zemberekserver.client;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.gleamynode.netty2.IoProcessor;
import net.gleamynode.netty2.MessageRecognizer;
import net.gleamynode.netty2.OrderedEventDispatcher;
import net.gleamynode.netty2.Session;
import net.gleamynode.netty2.ThreadPooledEventDispatcher;
import net.zemberekserver.server.Config;
import net.zemberekserver.server.StringMesaj;
import net.zemberekserver.server.ZemberekMesaji;
import net.zemberekserver.server.ZemberekMessageRecognizer;
import net.zemberekserver.server.ZemberekSession;
import net.zemberekserver.server.ZemberekSessionListener;

/**
 * @author MDA 
 *
 */
public class TestClient extends Thread implements ZemberekSessionListener{

    private static final String HOSTNAME = "localhost";
    private static final int CONNECT_TIMEOUT = 3; // seconds

    private static IoProcessor ioProcessor = new IoProcessor();
    private static ThreadPooledEventDispatcher eventDispatcher = new OrderedEventDispatcher();
    private Session session = null;
    private String name;
    private ZemberekSession zemberekSession = null;

    public TestClient(String name) {
        this.name = name;
    }

    public void init() {
        try {
            ioProcessor.start();
            eventDispatcher.setThreadPoolSize(2);
            eventDispatcher.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String toString() {
        return this.name;
    }

    public void connect() {
        // prepare message recognizer
        MessageRecognizer recognizer = new ZemberekMessageRecognizer();
        // create a client session
        session = new Session(ioProcessor, new InetSocketAddress(HOSTNAME, Config.serverPort), recognizer, eventDispatcher);
        // set configuration
        session.getConfig().setConnectTimeout(CONNECT_TIMEOUT);
        zemberekSession = new ZemberekSession(session);
        zemberekSession.setDinleyici(this);
        session.addSessionListener(zemberekSession);
        session.start();
    }

    public void run() {
        init();
        connect();
        System.out.println(session.getSocketAddress() + " 'a Zemberek istemcisi olarak baglaniyorum. ");
        disconnect();
    }

    public void disconnect() {
        while (!zemberekSession.isSonlandi()) {
            try {
                Thread.sleep(400);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Disconnected! " +  name);
    }

    public static void main(String[] args) {
        TestClient client = new TestClient("Zemberek Test Client");
        client.start();
    }

    public void zemberekSessionReady(ZemberekSession session) {
        System.out.println("Zemberek oturumu hazır. Test başladı.");
        try{
        	session.mesajYaz(new StringMesaj("* tes"));
        	session.mesajYaz(new StringMesaj("& tes"));
        	session.mesajYaz(new StringMesaj("& XXX"));
        	session.mesajYaz(new StringMesaj("* MERHABA"));
        	session.mesajYaz(new StringMesaj("* s\u00f6\u011f\u00fc\u015f"));
        	session.mesajYaz(new StringMesaj("* MRHABA"));
        	session.mesajYaz(new StringMesaj("& MRHABA"));
        	session.mesajYaz(new StringMesaj("& LMA"));
        	session.mesajYaz(new StringMesaj("23423423"));
        	session.mesajYaz(new StringMesaj("* merhaba elma yesene hedehodo zemberek"));
        	session.mesajYaz(new StringMesaj("23423423"));
        }catch (Exception e){
        	System.err.println("Test başarısız.");
        	System.exit(0);
        }
        System.out.println("\nTest Başarılı. Zemberek Server çalışıyor.");
        
    }

    public void messageReceived(ZemberekSession session, ZemberekMesaji mesaj) {
        System.out.println("Mesaj geldi " + mesaj);
    }


}
