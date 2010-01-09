/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.demo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;


/**
 */
class GirisAlani {
    private JPanel mainPanel;
    private JTextArea inputArea;
    private char[] ozelKarakterler;
    private JPanel buttonPanel;

    public GirisAlani(char[] ozelKarakterler) {
        this.ozelKarakterler = ozelKarakterler;
        configure();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setYazi(String yazi) {
        inputArea.setText(yazi);
    }

    public String getYazi() {
        return inputArea.getText();
    }

    private void configure() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new TitledBorder(new EmptyBorder(2,2,2,2), "Giri\u015f alan\u0131"));
        ozelKarakterDugmeAlaniOlustur(ozelKarakterler);
        mainPanel.add(makeInputPanel(), BorderLayout.CENTER);
    }


    private JPanel makeInputPanel() {
        JPanel pq = new JPanel(new BorderLayout());
        inputArea = new JTextArea();
        inputArea.setBorder(new EmptyBorder(1, 3, 1, 3));
        inputArea.setLineWrap(true);

        // jTextPanel scroll bar icermez. O yuzden onu scroll pane'e ekliyoruz.
        JScrollPane ps = new JScrollPane();
        ps.getViewport().add(inputArea);

        pq.add(ps,BorderLayout.CENTER);
        return pq;
    }

    public void ozelKarakterDugmeAlaniOlustur(char[] ozelKarakterler) {

        if(buttonPanel!=null)
          mainPanel.remove(buttonPanel);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
        //makechars
        for (char ozelKarakter : ozelKarakterler) {
            JButton button = SwingFactory.getRegularButton(String.valueOf(ozelKarakter));
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    String turkceChar = ((JButton) evt.getSource()).getText();
                    int pos = inputArea.getCaretPosition();
                    inputArea.insert(turkceChar, pos);
                    inputArea.setCaretPosition(pos + 1);
                    inputArea.requestFocus();
                }
            });
            buttonPanel.add(button);
        }
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.repaint();
        mainPanel.validate();
    }

}
