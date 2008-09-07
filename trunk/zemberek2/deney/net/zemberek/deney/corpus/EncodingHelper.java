package net.zemberek.deney.corpus;

import org.jmate.SimpleFileReader;
import org.jmate.SimpleFileWriter;
import org.jmate.Files;
import org.jmate.IOs;
import static org.jmate.Preconditions.checkNotNull;

import java.io.*;
import java.util.List;
import java.util.Arrays;

import net.zemberek.yapi.Alfabe;

/**
 * Created by IntelliJ IDEA.
 * User: ahmetaa
 * Date: Sep 7, 2008
 * Time: 12:57:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class EncodingHelper {

    public static void convertTurkishUtf8(File kaynak, File hedef) throws IOException {
        if (possibleUtf8(kaynak)) {
            System.out.println("possible utf-8, copy directly." + kaynak.getName());
            Files.copy(kaynak, hedef);
        } else {
            String s = new SimpleFileReader(kaynak).asString();
            s = s.replaceAll("ý", String.valueOf(Alfabe.CHAR_ii));
            s = s.replaceAll("ð", String.valueOf(Alfabe.CHAR_gg));
            s = s.replaceAll("þ", String.valueOf(Alfabe.CHAR_ss));
            s = s.replaceAll("Ý", String.valueOf(Alfabe.CHAR_II));
            s = s.replaceAll("Þ", String.valueOf(Alfabe.CHAR_SS));
            new SimpleFileWriter.Builder(hedef).encoding("utf-8").build().writeString(s);
        }

    }

    private static final byte[] bomBytes = new byte[]{(byte) 0xef, (byte) 0xbb, (byte) 0xbf};

    static boolean possibleUtf8(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        try {
            byte[] bomRead = new byte[bomBytes.length];
            if (is.read(bomRead, 0, bomBytes.length) == -1) {
                return false;
            }
            if (Arrays.equals(bomRead, bomBytes)) {
                return true;
            } else return false;
        } finally {
            IOs.closeSilently(is);
        }
    }


    public static void converDir(String kaynak, String hedef) throws IOException {
        List<File> files = Files.crawlDirectory(new File(kaynak), new Files.ExtensionFilter("txt"));
        for (File file : files) {
            System.out.println("Converting:" + file.getName());
            String fileName = file.getName().replace(".txt", "-utf8.txt");
            convertTurkishUtf8(file, new File(hedef, fileName));
        }
    }

    public static void main(String[] args) throws IOException {
/*        convertTurkishUtf8(new File("corpus/kaynaklar/test/tolstoy_dirilis.txt"),
                new File("corpus/kaynaklar/test/tolstoy-dirilis-utf8.txt")
        );*/
        converDir("corpus/kaynaklar/orjinal", "corpus/kaynaklar/utf-8");
    }

}
