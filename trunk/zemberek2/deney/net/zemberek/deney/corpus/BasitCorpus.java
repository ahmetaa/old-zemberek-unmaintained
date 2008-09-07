package net.zemberek.deney.corpus;

import org.jmate.*;

import java.io.IOException;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.*;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.islemler.cozumleme.CozumlemeSeviyesi;
import net.zemberek.yapi.Kelime;

public class BasitCorpus implements KelimeDenetleyici {

    private static Pattern p = Pattern.compile("[^ \\t\\n,.]+");
    private Map<String, Integer> kelimeKullanim = Collects.newHashMap();
    private String corpusFile;
    private int limit = -1;
    private int toplamKelime = 0;

    public BasitCorpus(String corpusFile) {
        this.corpusFile = corpusFile;
        if (new File(corpusFile).exists()) {
            try {
                oku(corpusFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void oku(String file) throws IOException {
        List<String> lines = new SimpleFileReader
                .Builder(file)
                .encoding("utf-8")
                .filters(StringFilters.PASS_ONLY_TEXT)
                .trim()
                .build()
                .asStringList();
        for (String line : lines) {
            String[] strs = line.split("[|]");
            if (strs.length == 0)
                continue;
            if (strs.length == 1)
                kelimeKullanim.put(strs[0], 1);
            if (strs.length == 2)
                kelimeKullanim.put(strs[0], Integer.parseInt(strs[1]));
        }
    }

    private void addStringToMap(String s) {
        if (!kelimeKullanim.containsKey(s)) {
            kelimeKullanim.put(s, 1);
        } else {
            kelimeKullanim.put(s, kelimeKullanim.get(s) + 1);
        }
    }

    public static Collection<String> kelimeleriOku(String fileName) throws IOException {
        LineIterator li = new SimpleFileReader
                .Builder(fileName)
                .encoding("utf-8")
                .filters(StringFilters.PASS_ONLY_TEXT)
                .trim()
                .build()
                .getLineIterator();
        List<String> kelimeler = Collects.newArrayList();
        while (li.hasNext()) {
            String line = li.next().replaceAll("[1234567890(),.?!:;\"…|]", " ").replaceAll("[-]", "");
            Matcher m = p.matcher(line);
            while (m.find()) {
                kelimeler.add(m.group());
            }
        }
        return kelimeler;
    }

    public void createCorpus(String... files) throws IOException {
        kelimeKullanim = Collects.newHashMap();
        updateCorpus(files);
    }

    public void createLimitedCorpus(int limit, String... files) throws IOException {
        this.limit = limit;
        createCorpus(files);
    }

    public void updateCorpus(String... files) throws IOException {
        for (String file : files) {
            System.out.println("isleniyor:" + file);
            kelimeleriEkle(kelimeleriOku(file));
        }
        kaydet();
    }

    public void updateLimitedCorpus(int limit, String... files) throws IOException {
        this.limit = limit;
        updateCorpus(files);
    }

    private static Zemberek zemberek;

    private synchronized void kelimeleriEkle(Collection<String> coll) {
        // lazy loading. kotu ama olsun
        if (zemberek == null)
            zemberek = new Zemberek(new TurkiyeTurkcesi());

        System.out.println("toplam islenen kelime sayisi:" + coll.size());
        toplamKelime += coll.size();
        int kabulEdilen = 0, edilmeyen = 0, baslangic = kelimeKullanim.size();
        for (String s : coll) {
            Kelime[] k = zemberek.kelimeCozumle(s, CozumlemeSeviyesi.TUM_KOKLER);
            if (k.length > 0)
                kabulEdilen++;
            else
                edilmeyen++;
            for (Kelime kelime : k) {
                addStringToMap(zemberek.kelimeUretici().kelimeUret(kelime.kok(), kelime.ekler()));
            }
        }
        System.out.println("kabul edilen:   " + kabulEdilen);
        System.out.println("kabul edilmeyen:" + edilmeyen);
        System.out.println("yeni kelime:    " + (kelimeKullanim.size() - baslangic));
    }

    private void kaydet() throws IOException {
        List<StringIntPair> kelimeFreq = Collects.newArrayList();
        for (String s : kelimeKullanim.keySet())
            kelimeFreq.add(new StringIntPair(s, kelimeKullanim.get(s)));
        Collections.sort(kelimeFreq);
        if (limit > -1 && kelimeFreq.size() > limit)
            kelimeFreq = kelimeFreq.subList(0, limit);
        System.out.println("\ntoplam kelime:" + toplamKelime);
        System.out.println("\ntoplam tekil dogru kelime:" + kelimeKullanim.size());
        System.out.println("\nkaydedilen kelime:" + kelimeFreq.size());
        new SimpleFileWriter.Builder(corpusFile).encoding("utf-8").build().writeToStringLines(kelimeFreq);
    }

    @Override
    public boolean denetle(String s) {
        return kelimeKullanim.containsKey(s);
    }


    private class StringIntPair implements Comparable<StringIntPair> {

        final String str;
        final int freq;

        private StringIntPair(String str, int freq) {
            this.str = str;
            this.freq = freq;
        }

        public int compareTo(StringIntPair o) {
            return Comparators.compare(freq, o.freq);
        }

        @Override
        public String toString() {
            return str + "|" + freq;
        }
    }

    public static void main(String[] args) throws IOException {
        List<File> kaynaklar = Files.crawlDirectory(new File("corpus/kaynaklar/utf-8"));
        String[] filez = new String[kaynaklar.size()];
        for (int i = 0; i < filez.length; i++) {
            filez[i] = kaynaklar.get(i).getAbsolutePath();
        }
        new BasitCorpus("corpus/corpus.txt").createLimitedCorpus(300000, filez);
    }
}
