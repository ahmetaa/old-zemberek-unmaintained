/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.demo;

import net.zemberek.araclar.turkce.YaziIsleyici;

import java.io.File;
import java.io.IOException;
/**
 */
public class DemoMain {

    private MainFrame mainFrame = new MainFrame();

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void main(String[] args) {
        DemoMain demoMain = new DemoMain();
        String s = "";
        if (args.length > 0) {
            String fileName = args[0];
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println(fileName + " adli dosya bulunamadi..");
            } else {
                try {
                    s = YaziIsleyici.yaziOkuyucu(fileName);
                } catch (IOException e) {
                    System.out.println("Dosya erisiminde sorun var.");
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        DemoPaneli demoPaneli = new DemoPaneli(new DemoYonetici());
        demoPaneli.yaziEkle(s);
        demoMain.getMainFrame().setContent(demoPaneli.getTemelIslemlerPaneli());
    }
}