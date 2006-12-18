/*
 * Created on 15.Ara.2004
 *
 */
package net.zemberekserver.server;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * @author MDA 
 * 
 */
public class Mesajlar {

    public static final HashMap mesajlar = new HashMap();
   
    public static byte[] mesajKodla(String mesaj){
        try {
            byte[] govde = mesaj.getBytes("UTF-8");
            String boy = ""  + (govde.length);
            byte[] boyBuffer = boy.getBytes();
            // toplam mesaj boyumuz "boy + boşluk + UTf-8 kodlanmış Stringin byte hali" şeklinde  
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
    
    /**
     * 
     * @param paket
     * @return
     * @throws StructException
     */
    public static IletisimMesaji mesajOlustur(ZemberekMesaji paket) {
            IletisimMesaji iletisimMesaji = new IletisimMesaji(mesajKodla(paket.getMesaj()));
            return iletisimMesaji;
    }

    /**
     * 
     * @param paket
     * @return
     * @throws StructException
     * @throws MesajException
     */
    public static ZemberekMesaji mesajCoz(IletisimMesaji paket) throws MesajException {
        return new StringMesaj(paket.getMesaj());
   }
    
    public static void kaydet(ZemberekMesaji mesaj) {
//       if(mesajlar.get(new Short(mesaj.getTip()))== null) {
//       mesajlar.put(new Short(mesaj.getTip()),mesaj);
//       log.info(mesaj.getTip() + " tipindeki mesaj kaydedildi");
//       }
//       else log.error(mesaj.getTip() + " tipindeki mesaj daha önce kaydedildi");
    }
}
