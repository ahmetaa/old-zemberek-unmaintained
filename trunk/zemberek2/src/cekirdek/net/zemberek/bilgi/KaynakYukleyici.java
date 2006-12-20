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
 *  The Original Code is "Zemberek Dogal Dil Isleme Kutuphanesi"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.bilgi;

import net.zemberek.araclar.Kayitci;

import java.io.*;
import java.net.URI;
import java.util.Properties;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;
import java.nio.charset.Charset;

/**
 * Bu sinifin asil amaci Zemberek kaynaklarina (bilgi, ek dosayalari gibi) hem proje icerisinden hem de
 * Dagitim sirasinda olusturulan jar kutuphane icinden hata olusamadan seffaf bicimde erisilmesini saglamaktir.
 * Zemberek, gelistirme sirasinda bilgi dosyalarina proje kokunde yer alan kaynaklar/tr/...' den
 * normal dizin erisim yontemleri ile erisirken
 * Dagitim sirasinda bu bilgi dosyalari jar icine yerlestirildiginden bilgi dosyalarina erisim
 * classpath kaynak erisim  yontemi ile yapilir (  this.getClass().getResourceAsStream...)
 * <p/>
 * aakin,Apr 24, 2005
 */
public class KaynakYukleyici {

    private static Logger log = Kayitci.kayitciUret(KaynakYukleyici.class);
    private final String encoding;
    /**
     * UTF metinlerin en basinda BOM adi verilen bir isaret bilgisi yer alabiliyor (UTF-8'de yer
     * almasi gerekmiyor aslinda). Bu bilgi Java tarafindan UTF-8 icin goz ardi edilmiyor.
     * Windows altinda olusturulan duz metinlerde bu bilgi koyuldugu icin Java'da okumada probleme yol acabiliyor.
     * Bu nedenleutf-8 icin BOM bilgisinin yer alip almadiginin denetlenmesi gerekiyor. asagidaki byte dizisi
     * UTF-8 icerisinde yer alan BOM bilgisini ifade ediyor.
     */
    private static final byte[] bomBytes = new byte[]{(byte) 0xef, (byte) 0xbb, (byte) 0xbf};

    /**
     * Default constructor. okuma sirasinda sistemde varsayilan kodlama kullanilir.
     */
    public KaynakYukleyici() {
        encoding = Charset.defaultCharset().name();
        log.fine("Kaynak yukleyici olusturuluyor. varsayilan karakter seti:" + encoding);
    }

    /**
     * kaynak erisim islemleri verilen encoding ile gerceklestirilir.
     *
     * @param encoding
     */
    public KaynakYukleyici(String encoding) {
        this.encoding = encoding.toUpperCase();
        log.fine("Kaynak yukleyici olusturuluyor. varsayilan karakter seti:" + encoding);
    }

    /**
     * Girilen kaynaga once class path disindan erismeye calisir. Eger dosya bulunamazsa
     * bu defa ayni dosyaya classpath icerisinden erismeye calisir
     * (ozellikle jar icinden okumada kullanilir.).
     *
     * @param kaynakAdi
     * @return kaynak risimi icin Buffered reader.
     */
    public BufferedReader getReader(String kaynakAdi) throws IOException {
        InputStream is = getStream(kaynakAdi);
        if (is == null)
            throw new IOException(kaynakAdi + " erisimi saglanamadi, Elde edilen Stream degeri null!");
        return new BufferedReader(new InputStreamReader(is, encoding));
    }

    /**
     * istenilen kaynaga erisimin mumkun olup olmadigina bakar. Bazi secimlik kaynaklarin erisiminde
     * bu metoddan yararlanilabilir.
     *
     * @param kaynakAdi
     * @return true-> kaynak erisiminde hata olusmadi false-> kaynak erisiminde hata olustu ya da kaynak=null
     */
    public boolean kaynakMevcutmu(String kaynakAdi) {
        if(new File(kaynakAdi).exists() || this.getClass().getResource("/"+kaynakAdi)!=null)
          return true;
        else
          return false;
    }

    /**
     * eger encoding UTF-8 ise dosyanin icerisinde BOM bilgisinin olup olmadigina bakar.
     * Gelen stream PushBackInputStream'a donusturulur.
     * Eger BOM mevcut degil ise Stream icerisindeki okunan 3 karakter geri gidilir. BOM
     * mevcut ise dogrudan olusturulan stream dondurulur.
     *
     * @param is
     * @return
     * @throws IOException
     */
    private InputStream utf8BomDenetle(InputStream is) throws IOException {
        if (is == null)
            throw new IOException("inputStream is null. throwing exception");
        if (encoding != null && !encoding.equalsIgnoreCase("UTF-8"))
            return is;
        PushbackInputStream pis = new PushbackInputStream(is, bomBytes.length);
        byte[] okunanBom = new byte[bomBytes.length];
        if (pis.read(okunanBom, 0, bomBytes.length) == -1) {
            return is;
        }
        if (!Arrays.equals(okunanBom, bomBytes)) {
            pis.unread(okunanBom);
        }
        return pis;
    }

    /**
     * belirtilen kaynagi Stream olarak once classpath kokunden (jar ise jar icinden) yuklemeye calisir.
     * Eger kaynak bulunamazsa dosya sisteminden yuklemeye calisir (calisilan dizine goreceli olarak.)
     * Onceligi classpath erisimine vermek mantikli cunku dagitimda kaynak erisimi buyuk ihtimalle
     * classpath icerisinden gerceklestirilir.
     *
     * @param kaynakAdi
     * @return
     * @throws IOException
     */
    public InputStream getStream(String kaynakAdi) throws IOException {
        InputStream stream = null;
        try {
            // classpath icinden yuklemeye calis.
            stream = utf8BomDenetle(this.getClass().getResourceAsStream("/" + kaynakAdi));
            log.info("classpath kaynak erisimi saglandi:" + kaynakAdi + " kodlama:" + encoding);
        } catch (IOException e) {
            // proje ici kaynak erisimi yapmaya calis.
            stream = utf8BomDenetle(new FileInputStream(kaynakAdi));
            if (stream == null)
                throw new IOException("Kaynak erisim hatasi: " + kaynakAdi);
            log.info("Proje ici kaynak erisimi saglandi:" + kaynakAdi + " kodlama:" + encoding);
        }
        return stream;
    }

    /**
     * properties formatina benzer yapidaki dosyayi kodlamali olarak okur.
     * Normal properties dosyalari ASCII
     * okundugundan turkce karakterlere uygun degil. Dosya icindeki satirlarin
     * anahtar=deger seklindeki satirlardan olusmasi gerekir. dosya icindeki yorumlar
     * # yorum seklinde ifade edilir.
     *
     * @param dosyaAdi
     * @return
     * @throws IOException
     */
    public Map<String, String> kodlamaliOzellikDosyasiOku(String dosyaAdi) throws IOException {
        BufferedReader reader = null;
        Map<String, String> ozellikler;
        try {
            reader = getReader(dosyaAdi);
            ozellikler = new HashMap<String, String>();
            while (reader.ready()) {
                String line = reader.readLine().trim();
                if (line.length() == 0 || line.charAt(0) == '#')
                    continue;
                int esitlik = line.indexOf('=');
                if (esitlik == -1)
                    throw new IllegalArgumentException("Ozellik satirinda '=' simgesi bekleniyordu");
                String key = line.substring(0, esitlik).trim();
                if (line.length() > esitlik - 1)
                    ozellikler.put(key, line.substring(esitlik + 1).trim());
                else
                    ozellikler.put(key, "");
            }
            return ozellikler;
        } finally {
            if (reader != null) reader.close();
        }
    }

    /**
     * Properties dosyasi yukler.
     *
     * @param uri
     * @return
     * @throws IOException
     */
    public Properties konfigurasyonYukle(URI uri) throws IOException {
        Properties props = new Properties();
        props.load(getStream(uri.toURL().getPath()));
        log.info("Dis properties stream erisimi saglandi:" + uri + " kodlama:" + encoding);
        return props;
    }

    public Properties konfigurasyonYukle(String dosya) throws IOException {
        Properties props = new Properties();
        props.load(getStream(dosya));
        log.info("properties kaynak erisimi saglandi:" + dosya + " kodlama:" + encoding);
        return props;
    }
}
