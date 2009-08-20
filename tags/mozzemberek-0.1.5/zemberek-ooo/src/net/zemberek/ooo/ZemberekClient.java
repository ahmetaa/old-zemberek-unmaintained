/*
 * Created on 21.A�u.2005
 *
 */
package net.zemberek.ooo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ZemberekClient {
    private static int ZEMBEREK_SERVER_PORT = 10444;
    private static int SOCKET_READ_TIMEOUT = 2000; // 2sn.
    InputStream stream = null;
    OutputStream os = null;
    InputStreamReader reader = null;
    Socket socket = null;
    
    public ZemberekClient() throws Exception{
        connect();
    }
    
    public byte[] mesajKodla(String mesaj){
        try {
            byte[] govde = mesaj.getBytes("UTF-8");
            String boy = ""  + (govde.length);
            byte[] boyBuffer = boy.getBytes();
            // toplam mesaj boyumuz "boy + boşluk + UTf-8 kodlanmış Stringin byte hali" �eklinde  
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
    
    public void connect() throws Exception{
        socket = new Socket();
        // 250ms de bağlanamazsak bağlantı yok kabul ediyoruz.
        socket.connect(new InetSocketAddress((InetAddress)null, ZEMBEREK_SERVER_PORT), 250);
        socket.setTcpNoDelay(true);
        socket.setSoTimeout(SOCKET_READ_TIMEOUT);
        socket.setSoLinger(false, 0);
        stream = socket.getInputStream();
        os = socket.getOutputStream();
        reader = new InputStreamReader(socket.getInputStream(),"UTF-8");
    }
    
    private static byte[] temp = new byte[10];
    public String readMessage() throws Exception{
        boolean boyOkundu = false;
        int mesajBoy = 0;
        int idx = 0;
        // Start timeout timer
        char[] buffer = new char[1000];

        while(true){
            char c = (char)reader.read();
            if (false == boyOkundu) {
                byte read = (byte)c;
                if (read >= '0' && read <= '9') {
                    if (idx > 6) throw new Exception("Mesaj boyu çok büyük!. ");
                    temp[idx++] = (byte)(read);
                } else{
                    if (idx > 0) {
                        String boyString = new String(temp, 0, idx);
                        mesajBoy = Integer.parseInt(boyString);
                        boyOkundu = true;
                        idx =0;
                        continue;
                    }else{
                        throw new Exception("Mesaj formatı hatalı");
                    }
                }
            }
            else{
                buffer[idx++] = c;
                if (idx == mesajBoy) {
                    String mesaj = new String(buffer,0,idx);
                    //System.out.println("Alinan UTF-8 mesaj:" + mesaj); 
//                  + " Hex: " + HexConverter.byteArrayToHexString(mesajBytes, " "));
                    return mesaj;
                }
            }
        }
    }
    
    public void disconnect(){
        try {
        if(socket != null){
            if(stream != null)
                stream.close();
            if(os != null)
                os.close();
            socket.close();
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(byte[] message) throws IOException{
        os.write(message);
        os.flush();
        //System.out.println("Mesaj gonderildi.");
    }
    
    public static void main(String[] args) {
        try {
            ZemberekClient client = new ZemberekClient();
            byte[] kodlanmisMesaj = client.mesajKodla("* merhaba");
            client.sendMessage(kodlanmisMesaj);
            String res = client.readMessage();
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
