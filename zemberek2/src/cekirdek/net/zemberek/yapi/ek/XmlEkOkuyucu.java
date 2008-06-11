/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

import net.zemberek.araclar.Kayitci;
import net.zemberek.araclar.XmlYardimcisi;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * xml ek dosyasindan ek bilgilerini okur ve ekleri olusturur.
 * User: ahmet
 * Date: Aug 15, 2005
 */
public class XmlEkOkuyucu {

    private static Logger log = Kayitci.kayitciUret(XmlEkOkuyucu.class);

    private Map<String, Set<Ek>> ekKumeleri = new HashMap<String, Set<Ek>>() ;
    private Map<String, Ek> ekler = new HashMap<String, Ek>();

    private final String xmlEkDosyasi;
    private final EkUretici ekUretici;

    private final EkOzelDurumUretici ekOzelDurumUretici;
    private final EkKuralKelimesiCozumleyici kuralKelimesiCozumleyici;

    public XmlEkOkuyucu(String xmlEkDosyasi,
                        EkUretici ekUretici,
                        EkOzelDurumUretici ekOzelDurumUretici,
                        EkKuralKelimesiCozumleyici kuralKelimesiCozumleyici
    ) {
        this.xmlEkDosyasi = xmlEkDosyasi;
        this.ekUretici = ekUretici;
        this.ekOzelDurumUretici = ekOzelDurumUretici;
        this.kuralKelimesiCozumleyici = kuralKelimesiCozumleyici;

    }

    public Map<String, Ek> getEkler() {
        return ekler;
    }

    public void xmlOku() throws IOException {
        Document document = XmlYardimcisi.xmlDosyaCozumle(xmlEkDosyasi);

        // kok elemente ulas.
        Element kokElement = document.getDocumentElement();

        ilkEkleriOlustur(XmlYardimcisi.ilkEleman(kokElement, "ekler"));
        ekKumeleriniOlustur(XmlYardimcisi.ilkEleman(kokElement, "ek-kumeleri"));
        ekleriOlustur(XmlYardimcisi.ilkEleman(kokElement, "ekler"));
    }

    /**
     * xml dosyadan sadece eklerin adlarini okuyup Ek nesnelerin ilk hallerinin
     * olusturulmasini saglar.
     *
     * @param eklerElement tum ekleri iceren Ekler bileseni
     */
    private void ilkEkleriOlustur(Element eklerElement) {
        List<Element> tumEkler = XmlYardimcisi.elemanlar(eklerElement, "ek");
        // tum ekleri bos haliyle uret.
        for (Element ekElement : tumEkler) {
            String ekadi = ekElement.getAttribute("ad");
            if (ekler.containsKey(ekadi))
                throw new EkKonfigurasyonHatasi("Ek tekrari! " + ekadi);
            ekler.put(ekadi, new Ek(ekadi));
        }
    }

    /**
     * xml dosyadan ek kumelerini ayiklar. sonuclar ekKumeleri Map'ina atilir.
     *
     * @param ekKumeleriElement tum ek kumelerini iceren xml bileseni.
     */
    private void ekKumeleriniOlustur(Element ekKumeleriElement) {
        List<Element> xmlKumeler = XmlYardimcisi.elemanlar(ekKumeleriElement, "ek-kumesi");
        for (Element ekKumeEl : xmlKumeler) {
            String kumeAdi = ekKumeEl.getAttribute("ad");
            Set<Ek> kumeEkleri = new HashSet<Ek>();
            List<Element> xmlKumeEkleri = XmlYardimcisi.elemanlar(ekKumeEl, "ek");
            for (Element ekEl : xmlKumeEkleri) {
                String ekAdi = ekEl.getTextContent();
                Ek ek = this.ekler.get(ekAdi);
                if (ek == null) throw new EkKonfigurasyonHatasi("kume eki bulunamiyor!" + ekAdi);
                kumeEkleri.add(ek);
            }
            ekKumeleri.put(kumeAdi, kumeEkleri);
        }
    }

    /**
     * asil ek nesnelerinin olusturulma islemi burada olur.
     *
     * @param eklerElement tum ekleri iceren xml ekler bileseni.
     */
    private void ekleriOlustur(Element eklerElement) {
        List<Element> tumEkler = XmlYardimcisi.elemanlar(eklerElement, "ek");
        for (Element ekElement : tumEkler) {
            String ekAdi = ekElement.getAttribute("ad");
            Ek ek = this.ekler.get(ekAdi);
            // uretim kuralini oku ve ekleri uret.
            Attr uretimKurali = ekElement.getAttributeNode("uretim");
            if (uretimKurali == null)
                throw new EkKonfigurasyonHatasi("ek uretim kural kelimesi yok!" + ekAdi);

            ek.setArdisilEkler(ardisilEkleriOlustur(ek, ekElement));
            ek.setEkKuralCozumleyici(ekUretici);
            List<EkUretimBileseni> bilesenler = kuralKelimesiCozumleyici.cozumle(uretimKurali.getValue());
            ek.setUretimBilesenleri(bilesenler);
            List<EkOzelDurumu> ozelDurumlar = ozelDurumlariOku(ekElement);
            ek.setOzelDurumlar(ozelDurumlar);

            ekOzellikleriBelirle(ek, ekElement);

            ek.setSesliIleBaslayabilir(ekUretici.sesliIleBaslayabilir(bilesenler));

            ek.baslangicHarfleriEkle(ekUretici.olasiBaslangicHarfleri(bilesenler));
            for (EkOzelDurumu oz : ozelDurumlar) {
                ek.baslangicHarfleriEkle(ekUretici.olasiBaslangicHarfleri(oz.uretimBilesenleri()));
            }
        }
        log.fine("ek olusumu sonlandi.");
    }

    /**
     * HAL ve IYELIK eki ozellikleri burada belirlenir. ek iceriisne farkli ozellikler
     * eklenecekse burasi ona gore degistirilmeli.
     *
     * @param ek ozellikleri belirlenecek Ek
     * @param ekElement xml Ek bileseni.
     */
    private void ekOzellikleriBelirle(Ek ek, Element ekElement) {
        List<Element> ozellikler = XmlYardimcisi.elemanlar(ekElement, "ozellik");
        for (Element element : ozellikler) {
            String ozellik = element.getTextContent().trim();
            if (ozellik.equals("HAL"))
                ek.setHalEki(true);
            else if (ozellik.equals("IYELIK"))
                ek.setIyelikEki(true);
        }
    }

    private List<EkOzelDurumu> ozelDurumlariOku(Element ekElement) {
        List<EkOzelDurumu> ozelDurumlar = new ArrayList<EkOzelDurumu>();
        //xml ozel durumlarini al.
        List<Element> ozelDurumlarXml = XmlYardimcisi.elemanlar(ekElement, "ozel-durum");
        if (ozelDurumlarXml == null) return Collections.emptyList();

        for (Element element : ozelDurumlarXml) {
            String ozelDurumAdi = element.getAttribute("ad");
            EkOzelDurumu oz = ekOzelDurumUretici.uret(ozelDurumAdi);
            Attr uretimKurali = element.getAttributeNode("uretim");

            if (uretimKurali != null) {
                oz.setEkKuralCozumleyici(ekUretici);
                oz.setUretimBilesenleri(kuralKelimesiCozumleyici.cozumle(uretimKurali.getValue()));
            }

            List<Element> oneklerElements = XmlYardimcisi.elemanlar(element, "on-ek");
            if (oneklerElements != null) {
                Set<Ek> onekler = new HashSet<Ek>();
                for (Element onekEl : oneklerElements) {
                    String onekAdi = onekEl.getTextContent();
                    onekler.add(ekler.get(onekAdi));
                }
                oz.setOnEkler(onekler);
            }
            ozelDurumlar.add(oz);
        }
        return ozelDurumlar;
    }

    /**
     * Bir eke iliskin ardisil ekler belirlenir. ardisil ekler
     * a) ek kumelerinden
     * b) normal tek olarak
     * c) dogrudan baska bir ekin ardisil eklerinden kopyalanarak
     * elde edilir.
     * Ayrica eger oncelikli ekler belirtilmis ise bu ekler ardisil ek listeisnin en basina koyulur.
     *
     * @param ekElement :  ek xml bileseni..
     * @param anaEk     ardisil ekler eklenecek asil ek
     * @return Ek referans Listesi.
     */
    private List<Ek> ardisilEkleriOlustur(Ek anaEk, Element ekElement) {

        Set<Ek> ardisilEkSet = new HashSet<Ek>();
        Element ardisilEklerEl = XmlYardimcisi.ilkEleman(ekElement, "ardisil-ekler");
        if (ardisilEklerEl == null) return Collections.emptyList();

        // tek ekleri ekle.
        List<Element> tekArdisilEkler = XmlYardimcisi.elemanlar(ardisilEklerEl, "aek");
        for (Element element : tekArdisilEkler) {
            String ekAdi = element.getTextContent();
            Ek ek = this.ekler.get(ekAdi);
            if (ek == null) throw new EkKonfigurasyonHatasi(anaEk.ad() + " icin ardisil ek bulunamiyor! " + ekAdi);
            ardisilEkSet.add(ek);
        }

        // kume eklerini ekle.
        List<Element> kumeEkler = XmlYardimcisi.elemanlar(ardisilEklerEl, "kume");
        for (Element element : kumeEkler) {
            String kumeAdi = element.getTextContent();
            Set<Ek> kumeEkleri = ekKumeleri.get(kumeAdi);
            if (kumeEkleri == null) throw new EkKonfigurasyonHatasi("kume bulunamiyor..." + kumeAdi);
            ardisilEkSet.addAll(kumeEkleri);
        }

        //varsa baska bir ekin ardisil eklerini kopyala.
        Attr attr = ardisilEklerEl.getAttributeNode("kopya-ek");
        if (attr != null) {
            final String kopyaEkadi = attr.getValue();
            Ek ek = this.ekler.get(kopyaEkadi);
            if (ek == null) throw new EkKonfigurasyonHatasi(anaEk.ad() + " icin kopyalanacak ek bulunamiyor! " + kopyaEkadi);
            ardisilEkSet.addAll(ek.ardisilEkler());
        }

        List<Ek> ardisilEkler = new ArrayList<Ek>(ardisilEkSet.size());

        //varsa oncelikli ekleri oku ve ardisil ekler listesinin ilk basina koy.
        // bu tamamen performans ile iliskili bir islemdir.
        Element oncelikliEklerEl = XmlYardimcisi.ilkEleman(ekElement, "oncelikli-ekler");
        if (oncelikliEklerEl != null) {
            List<Element> oncelikliEkler = XmlYardimcisi.elemanlar(oncelikliEklerEl, "oek");
            for (Element element : oncelikliEkler) {
                String ekAdi = element.getTextContent();
                Ek ek = this.ekler.get(ekAdi);
                if (ek == null) throw new EkKonfigurasyonHatasi(anaEk.ad() + " icin oncelikli ek bulunamiyor! " + ekAdi);
                if (ardisilEkSet.contains(ek)) {
                    ardisilEkler.add(ek);
                    ardisilEkSet.remove(ek);
                } else log.warning(anaEk.ad() + "icin oncelikli ek:" + ekAdi + " bu ekin ardisil eki degil!");
            }
        }

        ardisilEkler.addAll(ardisilEkSet);
        return ardisilEkler;
    }
}

class EkKonfigurasyonHatasi extends RuntimeException {

    public EkKonfigurasyonHatasi(String message) {
        super(message);
    }
}