package net.zemberek.deney;

import java.util.List;

import net.zemberek.araclar.TusTakimi;

/**
 * F-Q klavye testi sınıfı uzun zamandır tartışması yapılan F-Q klavye kullanımı kavgasına
 * yeni malzemeler eklemek için tasarlandı.
 * 
 * Türkçede kullanılan kelimelerin yapıları ve harf geçişleri yeterince büyük bir metin grubu kullanılarak
 * Q ve F klavye yerleşimleri ve parmak hareketleri sırasında harcanan zaman (enerji) hesaplanarak aralarındaki
 * fark niteliksel olarak ölçülmeye çalışılacaktır.
 * 
 * Ayrıca Türkçe için olabilecek en iyi klavye yerleşimi için de bir kestirim yapılacaktır.
 * 
 * Varsayımlar:
 * - Büyük harf olmadığı varsayılıyor (şimdilik)
 * - Sadece temel noktalama işaretleri hesaba katılıyor.
 * - 10 Parmak yazım metodu dikkate alınacaktır.
 * - Simülasyonlarda parmak hareketleri dikkate alınacaktır.
 * - Parmakların o anda bulundukları yerin hemen altlarında bulunan harflere minimum zaman değeri verilecektir.
 * - Sağ el parmaklarına daha düşük zaman değeri verilecektir (yazarın Sağlak olduğu varsayımı ile)
 * - işaret parmakları için daha az zaman değeri verilecektir. ?
 * - Baş parmaklar hep sabit değerler alacaktır (sadece boşluk)
 * - 6 şar parmaktan sorumlu işaret parmaklarında çaprazlardaki tuşlara daha yüksek zaman değerleri verilecektir.
 * - Parmağın sorumlluluğundaki uzak tuşlar için mesafe hesaba katılacaktır. 
 * - Arka arkaya yazılan harfler aynı parmak tarafından yapılıyorsa iki harfin zaman değeri doğrudan toplanacaktır
 * - Arka arkaya yazılan harfler aynı elin farklı parmaklarıyla yapılıyorsa ilk harf zamanı + ikinci harfin zaman değerinin yarısı alınacaktır
 * - Arka arkaya yazılan harflerde iki el arasında geçiş oluyorsa ilk harf zamanı + ikinci harfin 1/3 zaman değeri alınacaktır.
 * - Parmaklar belli bir süre işlev yapmamışsa otomatik olarak doğal konumlarına geri geliyor vasayılacaktır.
 * @author mdakin
 *
 */
public class FQKlavyeTesti {

	int[][] solSerceXY = new int[][]{{0,0},{1,0},{2,0},{3,0},{0,1}};
	int[][] solYuzukXY = new int[][]{{0,2},{1,1},{2,1},{3,1}};
	int[][] solOrtaXY = new int[][]{{0,3},{1,2},{2,2},{3,2}};
	int[][] solIsaretXY = new int[][]{{0,4},{1,3},{2,3},{3,3},{0,5},{1,4},{2,4},{3,4}};

	int[][] sagIsaretXY = new int[][]{{0,6},{1,5},{2,5},{3,5},{0,7},{1,6},{2,6},{3,6}};
	int[][] sagOrtaXY = new int[][]{{0,8},{1,7},{2,7},{3,7}};
	int[][] sagYuzukXY = new int[][]{{0,9},{1,8},{2,8},{3,8}};
	int[][] sagSerceXY = new int[][]{{0,10},{1,9},{2,9},{3,9}};

	
	private Parmak qSolSerce = new Parmak("Sol Serçe", 0, 1, 10, solSerceXY, TusTakimi.trQ());
	private Parmak qSolYuzuk = new Parmak("Sol Yuzuk", 0, 1, 10, solYuzukXY, TusTakimi.trQ()); 
	private Parmak qSolOrta = new Parmak("Sol Orta", 0, 1, 10, solOrtaXY, TusTakimi.trQ());
	private Parmak qSolIsaret = new Parmak("Sol Isaret", 0, 1, 10, solIsaretXY, TusTakimi.trQ()); 

	private Parmak qSagSerce = new Parmak("Sag Serçe", 0, 1, 10, sagSerceXY, TusTakimi.trQ());
	private Parmak qSagYuzuk = new Parmak("Sag Yuzuk", 0, 1, 10, sagYuzukXY, TusTakimi.trQ()); 
	private Parmak qSagOrta = new Parmak("Sag Orta", 0, 1, 10, sagOrtaXY, TusTakimi.trQ());
	private Parmak qSagIsaret = new Parmak("Sag Isaret", 0, 1, 10, sagIsaretXY, TusTakimi.trQ()); 

	
	public static void main(String[] args) {
		FQKlavyeTesti test = new FQKlavyeTesti();
	}
	
	class Parmak {
		String isim;
		int hiz;
		// parmağın doğal duruş koordinatı
		int satir;
		int sutun;
		// parmağın o anda bulunduğu koordinatlar.
		int i;
		int j;
		
		char[] sorumluKarakterler;
		// Parmağın sorumlu olduğu koordinatlar:
		TusTakimi.KarakterKoordinati[] sorumlu;
		
		Parmak(String isim, int satir, int sutun, int hiz, int[][] sorumluKoordinatlar, TusTakimi tusTakimi){
			this.isim = isim;
			this.satir = satir;
			this.sutun = sutun;
			this.hiz = hiz;
			sorumlu = new TusTakimi.KarakterKoordinati[sorumluKoordinatlar.length];
			for (int i=0; i<sorumluKoordinatlar.length ; i++){
				 sorumlu[i] = tusTakimi.koordinat(sorumluKoordinatlar[i][0], sorumluKoordinatlar[i][1]);
				 System.out.println( isim + ": "  + sorumlu[i].toString());
			}
		}

	}
}
