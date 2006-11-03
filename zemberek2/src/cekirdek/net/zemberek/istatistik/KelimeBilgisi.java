/**
 * 
 */
package net.zemberek.istatistik;

class KelimeBilgisi implements Comparable {
	String kelime;
	int miktar = 1;
	
	public KelimeBilgisi(String kelime){
		this.kelime = kelime;
	}
	
	public int compareTo(Object o) {
		return ((KelimeBilgisi)o).miktar - this.miktar;
	}
}