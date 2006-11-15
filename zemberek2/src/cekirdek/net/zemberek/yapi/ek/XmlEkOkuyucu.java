package net.zemberek.yapi.ek;

import net.zemberek.araclar.Kayitci;
import net.zemberek.araclar.XmlYardimcisi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.TurkceHarf;
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

    private Map<String, Set<Ek>> ekKumeleri = new HashMap();
    private Map<String, Ek> ekler = new HashMap();

    private final String xmlEkDosyasi;
    private final EkUretici ekUretici;
    private final Alfabe alfabe;

    private final EkOzelDurumUretici ekOzelDurumUretici;

    public XmlEkOkuyucu(String xmlEkDosyasi,
                        EkUretici ekUretici,
                        EkOzelDurumUretici ekOzelDurumUretici,
                        Alfabe alfabe) {
        this.xmlEkDosyasi = xmlEkDosyasi;
        this.ekUretici = ekUretici;
        this.ekOzelDurumUretici = ekOzelDurumUretici;
        this.alfabe = alfabe;
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
     * @param eklerElement
     */
    private void ilkEkleriOlustur(Element eklerElement) {
        List<Element> tumEkler = XmlYardimcisi.elemanlar(eklerElement, "ek");
        // tum ekleri bos haliyle uret.
        for (Element ekElement : tumEkler) {
            String ekadi = ekElement.getAttribute("ad");
            if (ekler.containsKey(ekadi))
                exit("Ek tekrari! " + ekadi);
            ekler.put(ekadi, new Ek(ekadi));
        }
    }

    /**
     * xml dosyadan ek kumelerini ayiklar. sonuclar ekKumeleri Map'ina atilir.
     *
     * @param ekKumeleriElement
     */
    private void ekKumeleriniOlustur(Element ekKumeleriElement) {
        List<Element> xmlKumeler = XmlYardimcisi.elemanlar(ekKumeleriElement, "ek-kumesi");
        for (Element ekKumeEl : xmlKumeler) {
            String kumeAdi = ekKumeEl.getAttribute("ad");
            Set<Ek> kumeEkleri = new HashSet();
            List<Element> xmlKumeEkleri = XmlYardimcisi.elemanlar(ekKumeEl, "ek");
            for (Element ekEl : xmlKumeEkleri) {
                String ekAdi = ekEl.getTextContent();
                Ek ek = this.ekler.get(ekAdi);
                if (ek == null) exit("kume eki bulunamiyor!" + ekAdi);
                kumeEkleri.add(ek);
            }
            ekKumeleri.put(kumeAdi, kumeEkleri);
        }
    }

    /**
     * asil ek nesnelerinin olusturulma islemi burada olur.
     * @param eklerElement
     */
    private void ekleriOlustur(Element eklerElement) {
        List<Element> tumEkler = XmlYardimcisi.elemanlar(eklerElement, "ek");
        for (Element ekElement : tumEkler) {
            String ekAdi = ekElement.getAttribute("ad");
            Ek ek = this.ekler.get(ekAdi);
            // uretim kuralini oku ve ekleri uret.
            Attr uretimKurali = ekElement.getAttributeNode("uretim");
            if (uretimKurali == null)
                exit("ek uretim kural kelimesi yok!" + ekAdi);

            ek.setArdisilEkler(ardisilEkleriOlustur(ek, ekElement));
            ek.setEkKuralCozumleyici(ekUretici);
            List<EkUretimBileseni> bilesenler = ekUretimKelimesiCozumle(uretimKurali.getValue());
            ek.setUretimBilesenleri(bilesenler);
            List<EkOzelDurumu> ozelDurumlar = ozelDurumlariOku(ekElement);
            ek.setOzelDurumlar(ozelDurumlar);

            ekOzellikleriBelirle(ek, ekElement);
            xmlDisiEkOzellikleriBelirle(ek, bilesenler);
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
     * @param ek
     * @param ekElement
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
        List<EkOzelDurumu> ozelDurumlar = new ArrayList();
        //xml ozel durumlarini al.
        List<Element> ozelDurumlarXml = XmlYardimcisi.elemanlar(ekElement, "ozel-durum");
        if (ozelDurumlarXml == null) return Collections.EMPTY_LIST;

        for (Element element : ozelDurumlarXml) {
            String ozelDurumAdi = element.getAttribute("ad");
            EkOzelDurumu oz = ekOzelDurumUretici.uret(ozelDurumAdi);
            Attr uretimKurali = element.getAttributeNode("uretim");

            if (uretimKurali != null) {
                oz.setEkKuralCozumleyici(ekUretici);
                oz.setUretimBilesenleri(ekUretimKelimesiCozumle(uretimKurali.getValue()));
            }

            List<Element> oneklerElements = XmlYardimcisi.elemanlar(element, "on-ek");
            if (oneklerElements != null) {
                Set<Ek> onekler = new HashSet();
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
     * @return Ek referans Listesi.
     * @param anaEk ardisil ekler eklenecek asil ek
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
            if (ek == null) exit(anaEk.ad() + " icin ardisil ek bulunamiyor! " + ekAdi);
            ardisilEkSet.add(ek);
        }

        // kume eklerini ekle.
        List<Element> kumeEkler = XmlYardimcisi.elemanlar(ardisilEklerEl, "kume");
        for (Element element : kumeEkler) {
            String kumeAdi = element.getTextContent();
            Set<Ek> kumeEkleri = ekKumeleri.get(kumeAdi);
            if (kumeEkleri == null) exit("kume bulunamiyor..." + kumeAdi);
            ardisilEkSet.addAll(kumeEkleri);
        }

        //varsa baska bir ekin ardisil eklerini kopyala.
        Attr attr = ardisilEklerEl.getAttributeNode("kopya-ek");
        if (attr != null) {
            final String kopyaEkadi = attr.getValue();
            Ek ek = this.ekler.get(kopyaEkadi);
            if (ek == null) exit(anaEk.ad() + " icin kopyalanacak ek bulunamiyor! " + kopyaEkadi);
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
                if (ek == null) exit(anaEk.ad() + " icin oncelikli ek bulunamiyor! " + ekAdi);
                if (ardisilEkSet.contains(ek)) {
                    ardisilEkler.add(ek);
                    ardisilEkSet.remove(ek);
                } else log.warning(anaEk.ad() + "icin oncelikli ek:" + ekAdi + " bu ekin ardisil eki degil!");
            }
        }

        ardisilEkler.addAll(ardisilEkSet);
        return ardisilEkler;
    }

    /**
     * ciddi hata durumunda sistmein mesaj vererek yazilimdan cikmasi saglanir.
     *
     * @param mesaj
     */
    private void exit(String mesaj) {
        log.severe("Ek dosyasi okuma sorunu:" + mesaj);
        System.exit(1);
    }


    /**
     * bazi ek ozellikleri konfigurasyon dosyasinda yer almaz, ekler okunduktan sonra
     * bilesenlere gore otomatik olarak belirlenir.
     *
     * @param ek
     * @param bilesenler
     */
    public void xmlDisiEkOzellikleriBelirle(Ek ek, List<EkUretimBileseni> bilesenler) {
        for (int i = 0; i < bilesenler.size(); i++) {
            EkUretimBileseni uretimBileseni = bilesenler.get(i);
            TurkceHarf harf = uretimBileseni.harf();
            if (i == 0 || (i == 1 && bilesenler.get(0).kural() == UretimKurali.KAYNASTIR)) {

                if (harf.sesliMi())
                    ek.setSesliIleBaslayabilir(true);
                switch (uretimBileseni.kural()) {
                    case SESLI_AA:
                    case SESLI_AE:
                    case SESLI_IU:
                        ek.setSesliIleBaslayabilir(true);
                        break;
                }
            } else {
                break;
            }
        }
    }

    // ek uretim kural kelimesinde kullanilan parcalarin dilbilgisi kurali karsiliklarini tutan tablo.
    private static final Map<Character, UretimKurali> kuralTablosu = new HashMap();

    static {
        kuralTablosu.put('A', UretimKurali.SESLI_AE);
        kuralTablosu.put('I', UretimKurali.SESLI_IU);
        kuralTablosu.put('E', UretimKurali.SESLI_AA);
        kuralTablosu.put('Y', UretimKurali.SESSIZ_Y);
        kuralTablosu.put('+', UretimKurali.KAYNASTIR);
        kuralTablosu.put('>', UretimKurali.SERTLESTIR);
    }

    private final Set<Character> sesliKurallari =
            new HashSet<Character>(Arrays.asList(new Character[]{'A', 'I', 'E', 'Y'}));
    private final Set<Character> harfKurallari =
            new HashSet<Character>(Arrays.asList(new Character[]{'+', '>'}));

    private List<EkUretimBileseni> ekUretimKelimesiCozumle(String uretimKelimesi) {
        if (uretimKelimesi == null || uretimKelimesi.length() == 0)
            return Collections.emptyList();
        List<EkUretimBileseni> bilesenler = new ArrayList();
        for (EkUretimBileseni bilesen : new EkKuralCozumleyici(uretimKelimesi)) {
            bilesenler.add(bilesen);
        }
        return bilesenler;
    }

    /**
     * Basit bir tokenizer. Iterable yapidadir, yani kural kelimesine gore
     * her iterasyonda eger varsa yeni bir EkUretimBileseni uretir.
     */
    class EkKuralCozumleyici implements Iterable<EkUretimBileseni> {

        private final String uretimKelimesi;
        private int pointer;

        public EkKuralCozumleyici(String uretimKelimesi) {
            this.uretimKelimesi = uretimKelimesi.trim().replaceAll("[ ]", "");
        }

        public Iterator<EkUretimBileseni> iterator() {
            return new BilesenIterator();
        }

        class BilesenIterator implements Iterator<EkUretimBileseni> {

            public boolean hasNext() {
                return uretimKelimesi != null && pointer < uretimKelimesi.length();
            }

            public EkUretimBileseni next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("bilesen kalmadi!");
                }
                char p = uretimKelimesi.charAt(pointer++);
                //ardisil harf ile iliskili kuralmi
                if (harfKurallari.contains(p)) {
                    if (pointer == uretimKelimesi.length())
                        throw new IllegalArgumentException(p + " kuralindan sonra normal harf bekleniyordu!");
                    char h = uretimKelimesi.charAt(pointer++);
                    if (sesliKurallari.contains(h))
                        throw new IllegalArgumentException(p + " kuralindan sonra sesli uretim kurali gelemez:" + h);
                    return new EkUretimBileseni(kuralTablosu.get(p), alfabe.harf(h));
                } else if (sesliKurallari.contains(p)) {
                    return new EkUretimBileseni(kuralTablosu.get(p), Alfabe.TANIMSIZ_HARF);
                } else if (alfabe.harf(p) != null && Character.isLowerCase(p)) {
                    return new EkUretimBileseni(UretimKurali.HARF, alfabe.harf(p));
                } else {
                    throw new IllegalArgumentException(p + "  simgesi cozumlenemiyor.. kelime:" + uretimKelimesi);
                }
            }

            public void remove() {
            }
        }
    }
}