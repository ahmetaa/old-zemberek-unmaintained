/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.demo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import net.zemberek.araclar.turkce.YaziIsleyici;

/**
 */
public class DemoPaneli {

    private JPanel temelIslemlerPaneli;
    private GirisAlani girisAlani;
    private CikisAlani cikisAlani = new CikisAlani();
    private DemoYonetici dy;
    private File currentDir = null;
    public static final int MAX_LOAD_STRING_BOY = 32000;


    public DemoPaneli(DemoYonetici dy) {
        this.dy = dy;
        configure();
    }

    public void yaziEkle(String text) {
        girisAlani.setYazi(text);
    }

    public JPanel getTemelIslemlerPaneli() {
        return temelIslemlerPaneli;
    }

    public void configure() {
        // ana paneli ve islem dugmelerinin yer alacagi paneli olustur
        temelIslemlerPaneli = new JPanel();
        JPanel buttonPanel = makeButtonPanel();
        // Islem dugmelerini kuzeye yerlestir
        temelIslemlerPaneli.setLayout(new BorderLayout());
        temelIslemlerPaneli.add(buttonPanel, BorderLayout.NORTH);

        //giris ve cikisin pencere buyudugunde ayni ende kalmasi icin onlari ayrica Grid Layout'a
        //sahip bir panele yerlestir. sonucta her ikisinide ana panelin merkezine koy.
        JPanel ioPanel = new JPanel(new GridLayout(2, 1));
        girisAlani = new GirisAlani(dy.ozelKarakterDizisiGetir());
        ioPanel.add(girisAlani.getMainPanel());
        ioPanel.add(cikisAlani.getMainPanel());
        temelIslemlerPaneli.add(ioPanel, BorderLayout.CENTER);
    }


    public JPanel makeButtonPanel() {
        JPanel pt = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());

        JPanel centerPanel = new JPanel(new FlowLayout());

        final JComboBox dilCombo = new JComboBox(TurkDiliTuru.values());
        dilCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TurkDiliTuru dilAdi = (TurkDiliTuru) dilCombo.getSelectedItem();
                try {
                    dy.dilSec(dilAdi);
                    girisAlani.setYazi("");
                    cikisAlani.setYazi("");
                    girisAlani.ozelKarakterDugmeAlaniOlustur(dy.ozelKarakterDizisiGetir());
                } catch (ClassNotFoundException e1) {
                    cikisAlani.setYazi("HATA: dile erisilemiyor:" + dilAdi);
                }
            }
        });
        topPanel.add(dilCombo);

        JButton btnLoad = SwingFactory.getRegularButton("Y\u00fckle");
        btnLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JFileChooser c;
                if (currentDir == null)
                    c = new JFileChooser();
                else
                    c = new JFileChooser(currentDir);

                int rVal = c.showOpenDialog(temelIslemlerPaneli);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        File f = c.getSelectedFile();
                        String yazi = YaziIsleyici.yaziOkuyucu(f.toString());
                        girisAlani.setYazi(yazi);
                        currentDir = f;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        topPanel.add(btnLoad);

        JButton btnClear;
        btnClear = SwingFactory.getRegularButton("Sil");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                girisAlani.setYazi("");
                cikisAlani.setYazi("");
            }
        });
        topPanel.add(btnClear);

        final JCheckBox htmlBox = new JCheckBox("HTML", true);
        htmlBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                if (selected) {
                    //cikisAlani.changeToHtml();
                } else {
                    //cikisAlani.changeToRegular();
                   // abstractButton.setText(newLabel);
                }
            }
        });
        topPanel.add(htmlBox);

        pt.add(topPanel, BorderLayout.NORTH);

        JButton btnDenetle;
        btnDenetle = SwingFactory.getRegularButton("Denetle");
        btnDenetle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cikisAlani.setYazi(dy.yaziDenetle(girisAlani.getYazi()));
            }
        });
        centerPanel.add(btnDenetle);

        JButton btnCozumle;
        btnCozumle = SwingFactory.getRegularButton("\u00c7\u00f6z\u00fcmle");
        btnCozumle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cikisAlani.setYazi(dy.yaziCozumle(girisAlani.getYazi()));
            }
        });
        centerPanel.add(btnCozumle);

        JButton btnDeascii;
        btnDeascii = SwingFactory.getRegularButton("Ascii->Tr");
        btnDeascii.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cikisAlani.setYazi(dy.asciiToTurkce(girisAlani.getYazi()));
            }
        });
        centerPanel.add(btnDeascii);

        JButton btnDeascii2;
        btnDeascii2 = SwingFactory.getRegularButton("Ascii->Tr 2");
        btnDeascii2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cikisAlani.setYazi(dy.asciiToTurkceTahmin(girisAlani.getYazi()));
            }
        });
        centerPanel.add(btnDeascii2);

        JButton btnascii;
        btnascii = SwingFactory.getRegularButton("Tr->Ascii");
        btnascii.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cikisAlani.setYazi(dy.turkceToAscii(girisAlani.getYazi()));
            }
        });
        centerPanel.add(btnascii);

        JButton btnHecele;
        btnHecele = SwingFactory.getRegularButton("Hecele");
        btnHecele.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cikisAlani.setYazi(dy.hecele(girisAlani.getYazi()));
            }
        });
        centerPanel.add(btnHecele);

        JButton btnOner;
        btnOner = SwingFactory.getRegularButton("\u00d6ner");
        btnOner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cikisAlani.setYazi(dy.oner(girisAlani.getYazi()));
            }
        });
        centerPanel.add(btnOner);

        pt.add(centerPanel, BorderLayout.CENTER);

        return pt;

    }

}
