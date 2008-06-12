/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 11.Tem.2004
 */
package net.zemberek.istatistik;


class KelimeZinciri implements Comparable<KelimeZinciri> {
    int kullanim = 1;
    String ikili = null;

    public KelimeZinciri(String ikili) {
        this.ikili = ikili;
    }

    public void hit() {
        kullanim++;
    }

    public int getKullanim() {
        return this.kullanim;
    }
    
    public String toString(){
        return ikili + " (" + kullanim + ")";
    }

    public int compareTo(KelimeZinciri o) {
        return o.kullanim - this.kullanim;
    }
}