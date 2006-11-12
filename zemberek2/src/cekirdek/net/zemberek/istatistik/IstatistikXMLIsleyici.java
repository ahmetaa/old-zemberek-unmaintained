/*
 * Created on 10.Tem.2004
 */
package net.zemberek.istatistik;

import net.zemberek.araclar.IstatistikAraclari;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.EkYonetici;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author MDA & GBA
 */
public class IstatistikXMLIsleyici {

    //factory.setNamespaceAware(true);
    XmlPullParser parser = null;
    XmlSerializer serializer = null;
    HashMap kelimeCebi = new HashMap(100);
    EkYonetici ekYonetici;

    public IstatistikXMLIsleyici(EkYonetici yonetici) {
        try {
            this.ekYonetici = yonetici;
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance(System.getProperty(XmlPullParserFactory.PROPERTY_NAME), null);
            parser = factory.newPullParser();
            serializer = factory.newSerializer();
        } catch (XmlPullParserException e) {
            System.out.println("XML Pull Parser Olusturulamadi.");
            e.printStackTrace();
        }
    }

    /**
     * @param fileName
     */
    public void parseDocument(String fileName, Sozluk sozluk) {
        Kok aktifKok = null;
        EkZinciri aktifZincir = null;
        GenelKokIstatistikBilgisi kokIstatistigi;
        try {
            ZipFile zipFile = new ZipFile(fileName);
            ZipEntry entry = zipFile.getEntry("cep.xml");
            parser.setInput(zipFile.getInputStream(entry), "ISO-8859-9");

            while (true) {
                int event = parser.next();
                if (event == XmlPullParser.START_TAG) {
                    String name = parser.getName();
                    if (name.equals("Kok")) {
                        //Kok kok = new Kok(parser.getAttributeValue(0)); // Kok icerigi
                        List<Kok> adaylar = sozluk.kokBul(parser.getAttributeValue(0));
                        aktifKok = adaylar.iterator().next();
                        kokIstatistigi = new GenelKokIstatistikBilgisi(aktifKok);
                        //aktifKok.setFrekans(kokIstatistigi);
                    }
                    if (name.equals("EkZinciri")) {
                        //String zincir = parser.getAttributeValue(0); // Ek zinciri
                        double oran = Double.parseDouble(parser.getAttributeValue(0).trim());
                        String ekler = parser.getAttributeValue(1);
                        aktifZincir = new EkZinciri(ekYonetici, ekler, oran);
                        if (aktifKok != null) {
                            //System.out.println("Eklenen kelime: " + aktifKok.icerik() + zincir);
                            //kelimeCebi.put(aktifKok.icerik() + zincir, aktifKok);
                          //  ((GenelKokIstatistikBilgisi) aktifKok.getFrekans()).ekZinciriEkle(aktifZincir);
                        }
                    }
                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                        String s = parser.getAttributeValue(i);
                        if (name.equals("Kok") && parser.getAttributeName(i).equals("o")) {
                            //((GenelKokIstatistikBilgisi) aktifKok.getFrekans()).setKullanimFrekansi(Integer.parseInt(parser.getAttributeValue(i)));
                        }
                        //System.out.println(i + ". Attr: " + parser.getAttributeName(i)
                        //        + " Value: " + parser.getAttributeValue(i));
                    }
                } else if (event == XmlPullParser.END_TAG) {
                    String endTag = parser.getName();
                    if (endTag.equals("Kok")) {
                        //aktifKok.getIstatistikler().ekListesiGuncelle(aktifZincir.ekler());
                        //System.out.println("Aktif Kok: " + aktifKok.icerik());
                    }

                } else if (event == XmlPullParser.TEXT) {
                    // text bu durumda işimize yaramıyor.
                } else if (event == XmlPullParser.END_DOCUMENT) {
                    break;
                } // end else if
            } // end while
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Format:
     * <pre>
     * <p/>
     * <istatistikler>
     * <kok v="elma" o="0.00061">
     * <p/>
     *   <ekzinciri v="larda" o="4.1353" z="ISIM_LER ISIM_DA"/>
     *   <ekzinciri v="da" o="3.2345" z="ISIM_DA"/>
     * </kok>
     * <p/>
     * <kok v="armut" o="0.1234">
     * ...
     * </kok>
     * ...
     * </istatistikler>
     * <p/>
     * </pre>
     *
     * @param outputFile
     * @param istatistikler
     * @param miktar
     */
    public void istatistikleriYaz(String outputFile, Istatistikler istatistikler, int miktar) {
        try {
            DecimalFormat df = new DecimalFormat("#0.00000");
            ZipOutputStream zipOutput = new ZipOutputStream(new FileOutputStream(new File(outputFile)));
            zipOutput.putNextEntry(new ZipEntry("cep.xml"));
            serializer.setOutput(zipOutput, "ISO-8859-9");

            ArrayList kokListesi = istatistikler.getKokListesi();
            serializer.startDocument("ISO-8859-9", null);
            serializer.text("\n ");
            if (miktar > kokListesi.size()) {
                miktar = kokListesi.size();
            }
            serializer.startTag(null, "istatistikler");
            for (int i = 0; i < miktar; i++) {
                GenelKokIstatistikBilgisi ist = (GenelKokIstatistikBilgisi) kokListesi.get(i);
                serializer.startTag(null, "Kok");
                serializer.attribute(null, "v", ist.getKok().icerik());
                serializer.attribute(null, "o", IstatistikAraclari.onbindeHesaplaStr(ist.getKullanimSayisi(), istatistikler.getKelimeSayisi()));
                ArrayList ekler = ist.getEkListesi();
                for (int j = 0; j < ekler.size(); j++) {
                    EkZinciri zincir = (EkZinciri) ekler.get(j);
                    //double yuzde = IstatistikAraclari.yuzdeHesapla(zincir.getKullanimFrekansi(), ist.getKullanimSayisi());
                    if (zincir.getKullanimFrekansi() > 0.1D) {
                        serializer.text("\n  ");
                        serializer.startTag(null, "EkZinciri");
                        //serializer.attribute(null, "v", zincir.getEklerStr());
                        serializer.attribute(null, "o", df.format(zincir.getKullanimFrekansi()));
                        serializer.attribute(null, "z", zincir.getStringRep());
                        serializer.endTag(null, "EkZinciri");
                    }
                }
                serializer.text("\n");
                serializer.endTag(null, "Kok");
                serializer.text("\n");
            }
            serializer.endTag(null, "istatistikler");
            serializer.endDocument();
            zipOutput.closeEntry();
            zipOutput.close();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap getKelimeCebi() {
        return kelimeCebi;
    }

    public static void main(String[] args) throws IOException {
/*        IstatistikToplayici toplayici = new IstatistikToplayici(new Zemberek(new TurkiyeTurkcesi()).cozumleyici());
        toplayici.metinIsle("kaynaklar/tr/metinler/Arthur C_ Clarke - 2001 - Bir Uzay Efsanesi - 1.txt");
        toplayici.metinIsle("kaynaklar/tr/metinler/Arthur C_ Clarke - 2010 - Bir Uzay Efsanesi - 2.txt");
        toplayici.metinIsle("kaynaklar/tr/metinler/Arthur C_ Clarke - 2062 - Bir Uzay Efsanesi - 3.txt");
        toplayici.metinIsle("kaynaklar/tr/metinler/Arthur C_ Clarke - Rama Yle Bulu_ma.txt");
        toplayici.metinIsle("kaynaklar/tr/metinler/ahmet-hamdi-tanpinar-huzur.txt");
        toplayici.metinIsle("kaynaklar/tr/metinler/Ahmet Altan - Ysyan G�nlerinde A_k.txt");
        toplayici.metinIsle("kaynaklar/tr/metinler/bedwyr.txt");
        toplayici.metinIsle("kaynaklar/tr/metinler/Buzyeli Vadisi 2 - Gumus Damarlari.txt");
        toplayici.metinIsle("kaynaklar/tr/metinler/Cengiz Aytmatov - Beyaz Gemi.txt");
        toplayici.metinIsle("kaynaklar/tr/metinler/Cengiz Aytmatov - Cemile.txt");
        toplayici.metinIsle("kaynaklar/tr/metinler/Cengiz Aytmatov -DY_Y KURDUN RUYALARI.txt");
        toplayici.metinIsle("kaynaklar/tr/metinler/CENGYZ AYTMATOV-TOPRAK.txt");
        toplayici.metinIsle("kaynaklar/tr/metinler/Ahmet Altan - Aldatmak.txt");
        toplayici.metinIsle("kaynaklar/tr/metinler/Ahmet Altan - Kirar Gogsune Bastirirken.txt");
        toplayici.metinIsle("kaynaklar/tr/metinler/Ahmet Altan - Kyly� Yarasy Gibi.txt");
        toplayici.metinIsle("kaynaklar/tr/metinler/Ahmet Altan - Sudaki Yz.txt");


        toplayici.sonlandir();

        //IstatistikXMLIsleyici parserx = new IstatistikXMLIsleyici();
        System.out.println("BinaryIstatistikler yaziliyor.");
        BinaryIstatistikYazici binaryYazici = new BinaryIstatistikYazici();
        binaryYazici.initialize("kaynaklar/tr/bilgi/stats.bin");
        binaryYazici.yaz(toplayici.getIstatistikler());
        System.out.println("Binary Istatistikler okunuyor.");
        BinaryIstatistikOkuyucu okuyucu = new BinaryIstatistikOkuyucu();
        okuyucu.initialize("kaynaklar/tr/bilgi/stats.bin");

        //okuyucu.oku(IstatistikToplayici.getSozluk());
//        parserx.istatistikleriYaz("kaynaklar/kb/stats.zem", toplayici.getIstatistikler(), 15000);
//        BinarySozlukOkuyucu bso = new BinarySozlukOkuyucu();
//        bso.initialize("kaynaklar/kb/binary-kokler.bin");
//        AgacSozluk kokler = new AgacSozluk(bso);
//        TimeTracker.startClock("parse");
//        parserx.xmlDosyaCozumle("kaynaklar/kb/stats.zem", kokler);
//        System.out.println("Parsing Complete. " + TimeTracker.getElapsedTimeString("parse"));
//        TimeTracker.stopClock("parse");

//        System.out.println("Cepteki toplam kelime say�s�: " + parserx.getKelimeCebi().size());
        System.out.println("Complete.");*/
    }
}
