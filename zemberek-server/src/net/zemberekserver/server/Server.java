/*
 * Created on 14.Ara.2004
 *
 */
package net.zemberekserver.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;

import net.gleamynode.netty2.IoProcessor;
import net.gleamynode.netty2.MessageRecognizer;
import net.gleamynode.netty2.OrderedEventDispatcher;
import net.gleamynode.netty2.SessionServer;
import net.gleamynode.netty2.ThreadPooledEventDispatcher;
import net.zemberek.araclar.TimeTracker;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;


public class Server implements ZemberekSessionListener {

    private static final int DISPATCHER_THREAD_POOL_SIZE = 1;

    private IoProcessor ioProcessor = null;
    private ThreadPooledEventDispatcher eventDispatcher = null;
    private MessageRecognizer recognizer = null;
    private ZemberekServerSessionListener listener = null;
    private SessionServer server = null;
    private Zemberek zemberek; 

    public Server(int dispacterThreadPoolSize) {
        ioProcessor = new IoProcessor();
        eventDispatcher = new OrderedEventDispatcher();
        
        
        try {
            System.out.println("Zemberek sunucusu baslatiliyor.");
            TimeTracker.startClock("z");
            zemberek = new Zemberek(new TurkiyeTurkcesi());
            System.out.println("Zemberek kutuphanesi yuklenme suresi: " + TimeTracker.getElapsedTimeString("z"));
            TimeTracker.stopClock("z");
            
            ioProcessor.start();
            // start with a few event dispatcher threads
            eventDispatcher.setThreadPoolSize(dispacterThreadPoolSize);
            eventDispatcher.start();
            // prepare message recognizer
            recognizer = new ZemberekMessageRecognizer();
            // prepare session event listener which will provide communication workflow.
            listener = new ZemberekServerSessionListener(this);
            // prepare session server
            server = new SessionServer();
            server.setIoProcessor(ioProcessor);
            server.setEventDispatcher(eventDispatcher);
            server.setMessageRecognizer(recognizer);

            server.addSessionListener(listener);
            server.setBindAddress(new InetSocketAddress(Config.serverPort));

            // open the server port, accept connections, and start communication
            System.out.println(DISPATCHER_THREAD_POOL_SIZE + " dagitici thread hazir, " + Config.serverPort + " portundan dinliyorum.");
            server.start();
            System.out.println("Zemberek Sunucusu basariyla baslatildi.");
            net.zemberekserver.dbus.ZemberekDbus.start(zemberek);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void stop() {
        server.stop();
        eventDispatcher.stop();
        ioProcessor.stop();
    }

    public static void main(String[] args) throws Throwable {
        try {
            if (args[0] != null) {
                Config.confFile = args[0];
            }
        }
        catch (Exception ex) {

        }
        new Server(DISPATCHER_THREAD_POOL_SIZE);
    }

    public void zemberekSessionReady(ZemberekSession session) {
       } 
 
    public void messageReceived(ZemberekSession session, ZemberekMesaji mesaj) {
        try{
        //System.out.println("Alinan mesaj: " + mesaj );
        String[] parcalar = mesaj.getMesaj().trim().split(" ");
        if (parcalar.length < 2) session.mesajYaz(new StringMesaj("?"));
        if (parcalar[0].equalsIgnoreCase("*")) {
            for (int i = 1; i < parcalar.length; i++) {
                if (zemberek.kelimeDenetle(parcalar[i].trim())) {
                    session.mesajYaz(new StringMesaj("*"));
                } else {
                    session.mesajYaz(new StringMesaj("#"));
                }
            }
        } else if (parcalar[0].equalsIgnoreCase("&")) {
            String[] liste = oner(parcalar[1].trim());
            //System.out.println(liste);
            if (liste.length == 0) {
                session.mesajYaz(new StringMesaj("#"));
            }
            else{
                String cevap = "& (";
                for (int i = 0; i < liste.length; i++) {
                    cevap += liste[i];
                    if (i < liste.length - 1)
                        cevap += ",";
                }
                cevap += ")";
                try {
                    session.mesajYaz(new StringMesaj(new String(cevap.getBytes(),"UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    session.mesajYaz(new StringMesaj("?"));
                }
            }
        }
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
    
    public String[] oner(String kelime) {
        return zemberek.oner(kelime);
    }    
}
