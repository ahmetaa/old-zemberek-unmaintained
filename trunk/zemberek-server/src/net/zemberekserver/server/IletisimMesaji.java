/*
 * Created on 15.Ara.2004
 *
 */
package net.zemberekserver.server;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import net.gleamynode.netty2.Message;
import net.gleamynode.netty2.MessageParseException;

/**
 * @author MDA
 *
 */
public class IletisimMesaji implements Message {
    
    protected int mesajBoy;
    private int index = 0;
    private int kalan;
    private String mesaj = null;
    private byte[] mesajBytes = null; 
    private byte[] temp = new byte[10];
    private int idx = 0;
    private boolean boyOkundu = false;

    public IletisimMesaji(byte[] mesajBuffer) {
        try {
            this.mesajBytes = mesajBuffer;
            this.mesaj = new String(mesajBuffer, "UTF-8");
            kalan = mesajBytes.length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    
    public IletisimMesaji(){
    }
    
    /**
     * @see net.gleamynode.netty2.Message#read(java.nio.ByteBuffer)
     */
    public boolean read(ByteBuffer buffer) throws MessageParseException {
        try {
            // Once mesaj boyunu oku. Mesaj boyu da string olarak geldiği için
            // rakam olduğu müddetçe oku.
            if (false == boyOkundu) {
                while (buffer.hasRemaining()) {
                    byte read = buffer.get();
                    if (read >= '0' && read <= '9') {
                        if (idx > 6) throw new MessageParseException("Mesaj boyu çok büyük!. ");
                        temp[idx++] = (byte)(read);
                    } else
                        break;
                }
                if (idx > 0) {
                    String boyString = new String(temp, 0, idx);
                    mesajBoy = Integer.parseInt(boyString);
                    //System.out.println("Gelen stringin (UTF-8) boyu: " + mesajBoy);
                    boyOkundu = true;
                }
            }
            
            idx = 0;
            mesajBytes = new byte[mesajBoy];
            while (buffer.hasRemaining()) {
                byte read = buffer.get();
                //System.out.println("Okunan byte: " + read);
                mesajBytes[idx++] = read;
                if (idx == mesajBoy) {
                    mesaj = new String(mesajBytes, "UTF-8");
//                    System.out.println("Alinan UTF-8 mesaj:" + mesaj 
//                            + " Hex: " + HexConverter.byteArrayToHexString(mesajBytes, " "));
                    return true;
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new MessageParseException("Desteklenmeyen Kodlama. ", e);
        }
        return false;
    }

    /** 
     * @see net.gleamynode.netty2.Message#write(java.nio.ByteBuffer)
     */
    public boolean write(ByteBuffer buffer) {
        int yazilacakMiktar ;
        if (mesajBytes.length > buffer.remaining())
            yazilacakMiktar = buffer.remaining();
        else
            yazilacakMiktar = mesajBytes.length;
        
        //System.out.println("Buffera yazıldı index " + index + " yazılacak miktar " + yazilacakMiktar + " Remaining " + buffer.remaining() + " Kalan " + kalan );
        buffer.put(mesajBytes, index, yazilacakMiktar);
        index = index + yazilacakMiktar;
        kalan = kalan - yazilacakMiktar;
        if (kalan == 0) return true;
        return false;
    }
    
    public String toString() {
        return mesaj;  
    }
    public byte[] getGovde() {
        return mesajBytes;
    }

    /**
     * @return Returns the mesajBoy.
     */
    public int getMesajBoy() {
        return mesajBytes.length;
    }

    public String getMesaj() {
        return mesaj;
    }
    
}
