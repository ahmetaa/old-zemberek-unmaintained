package net.zemberek.deney;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.zemberek.araclar.IstatistikAraclari;
import net.zemberek.araclar.TusTakimi;
import net.zemberek.araclar.turkce.TurkceMetinOkuyucu;

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
 * - serçe parmağı için yüksek zaman değeri verilecektir.
 * - Baş parmaklar hep sabit değerler alacaktır (sadece boşluk)
 * - 6 şar parmaktan sorumlu işaret parmaklarında çaprazlardaki tuşlara daha yüksek zaman değerleri verilecektir.
 * - Parmağın sorumluluğundaki uzak tuşlar için mesafe hesaba katılacaktır. 
 * - Arka arkaya yazılan harfler aynı parmak tarafından yapılıyorsa iki harfin zaman değeri doğrudan toplanacaktır
 * - Arka arkaya yazılan harfler aynı elin farklı parmaklarıyla yapılıyorsa ilk harf zamanı + ikinci harfin zaman değerinin yarısı alınacaktır
 * - Arka arkaya yazılan harflerde iki el arasında geçiş oluyorsa ilk harf zamanı + ikinci harfin 1/3 zaman değeri alınacaktır.
 * - Parmaklar belli bir süre işlev yapmamışsa otomatik olarak doğal konumlarına geri geliyor vasayılacaktır.
 * Şu anda hesaba katılmayan durumlar:
 * - Akıl :) normalde kelime yazılırken parmaklar otomatik olarak o tuşa doğru yönelecekti ve parmak hareket cezası azalacaktır  
 * @author mdakin
 *
 */
public class FQKlavyeTesti {
	
	enum El {SOL, SAG};
	
	public static DecimalFormat df2 = new DecimalFormat("#0.00000");
	
	Yazici qKlavyeYazan;
	Yazici fKlavyeYazan;

	HashMap <String, Ikili> ikililer = new HashMap<String, Ikili>();
	long toplamKullanim = 0;

	/**
	 * En çok kullanılan ikilileri yükle. ikililer.txt dosyasından.
	 *
	 */
	public void ikilileriYukle(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader("ikililer.txt"));
			long toplam = 0;
			while (true){
				String satir = reader.readLine();
				if(satir == null) break;
				String[] parcalar = satir.split(" ");
				String ikilistr = parcalar[2];
				int kullanim =  Integer.parseInt(parcalar[4]);
				toplam += kullanim;
				ikililer.put(ikilistr, new Ikili(ikilistr, kullanim));
			}
			toplamKullanim = toplam;
			System.out.println("Toplam ikili sayisi: " + toplamKullanim);
			for(Ikili i : ikililer.values()){
				i.oran = IstatistikAraclari.yuzdeHesapla(i.kullanim , toplam);
			}
		} catch (FileNotFoundException e) {
			System.out.println("ikili dosyasi bulunamadi.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public FQKlavyeTesti(){
		qKlavyeYazan = new Yazici(TusTakimi.trQ());
		fKlavyeYazan = new Yazici(TusTakimi.trF());
		ikilileriYukle();
	}
	
	
	public List<Ikili> problemliIkililerinKullanimi(List<String> problemliIkililer){
		List<Ikili> secilenler = new ArrayList<Ikili>();
		for(String s : problemliIkililer){
			Ikili i = ikililer.get(s);
			if(i != null) {
				secilenler.add(i);
			}
		}
		// Sırala
		Collections.sort(secilenler);
		return secilenler;
	}
	
	public void test(){
		System.out.println("Q test: ");
		long toplam = 0;
		List<Ikili> list = problemliIkililerinKullanimi(qKlavyeYazan.problemliIkililer());
		for(Ikili i : list){
			System.out.println( i );
			toplam += i.kullanim;
		}
		System.out.println("Q toplam: " + toplam +  " %" + IstatistikAraclari.yuzdeHesaplaStr(toplam, toplamKullanim) +  "\n");
		
		toplam = 0;
		System.out.println("F test: ");
		list = problemliIkililerinKullanimi(fKlavyeYazan.problemliIkililer());
		for(Ikili i : list){
			System.out.println( i );
			toplam += i.kullanim;
		}
		System.out.println("F toplam: " + toplam + " %" + IstatistikAraclari.yuzdeHesaplaStr(toplam, toplamKullanim));
		
	}
	
	private void yaz(String str) {
		qKlavyeYazan.yaz(str);
		fKlavyeYazan.yaz(str);
	}
	
	public void sonuc(){
		System.out.println("Q toplam enerji: " + qKlavyeYazan.toplam);
		System.out.println("F toplam enerji: " + fKlavyeYazan.toplam);
	}
	
	public static void main(String[] args) {
		FQKlavyeTesti test = new FQKlavyeTesti();
//		TurkceMetinOkuyucu okuyucu = new TurkceMetinOkuyucu();
//		String[] kelimeler =  okuyucu.MetinOku("/home/mdakin/books/secme/Anayasa.txt");
//		String[] kelimeler =  okuyucu.MetinOku("/home/mdakin/books/secme/kral.txt");
//		for(String s : kelimeler){
//			test.yaz(s);
//		}
		test.test();
	}
	
	class Ikili implements Comparable<Ikili>{
		String ikili;
		int kullanim;
		double oran;
		
		public Ikili(String ikili, int kullanim){
			this.ikili = ikili;
			this.kullanim = kullanim;
		}
		
		public String toString(){
			return ikili + " " + kullanim + " % " + df2.format(oran);
		}

		public int compareTo(Ikili o) {
			return o.kullanim - this.kullanim;
		}

	}
	
	class Yazici {
		HashMap<Character, Parmak> karakterParmakMap = new HashMap<Character, Parmak>();
		List<Parmak> parmaklar = new ArrayList<Parmak>();
		// Parmakların sorumlu oldukları tuş koordinatları
		int[][] solSerceXY = new int[][]{{0,0},{1,0},{2,0},{3,0},{0,1}};
		int[][] solYuzukXY = new int[][]{{0,2},{1,1},{2,1},{3,1}};
		int[][] solOrtaXY = new int[][]{{0,3},{1,2},{2,2},{3,2}};
		int[][] solIsaretXY = new int[][]{{0,4},{1,3},{2,3},{3,3},{0,5},{1,4},{2,4},{3,4}};
		
		int[][] sagIsaretXY = new int[][]{{0,6},{1,5},{2,5},{3,5},{0,7},{1,6},{2,6},{3,6}};
		int[][] sagOrtaXY = new int[][]{{0,8},{1,7},{2,7},{3,7}};
		int[][] sagYuzukXY = new int[][]{{0,9},{1,8},{2,8},{3,8}};
		int[][] sagSerceXY = new int[][]{{0,10},{1,9},{2,9},{3,9},{1,10}, {2,10}, {2,11}, {1,11}};
		
		// Parmaklar :) Başparmak yok şimdilik
		Parmak solSerce;
		Parmak solYuzuk; 
		Parmak solOrta;
		Parmak solIsaret; 

		Parmak sagSerce;
		Parmak sagYuzuk; 
		Parmak sagOrta;
		Parmak sagIsaret;
		
		char sonBasilan;
		long toplam;
		Parmak oncekiParmak = null;
		char oncekiKarakter = '#';
		
		public Yazici(TusTakimi t){
			 solSerce = new Parmak("Sol Serçe", El.SOL, 2, 0, 11, 8, solSerceXY, t);
			 solYuzuk = new Parmak("Sol Yuzuk", El.SOL, 2, 1, 10, 7, solYuzukXY, t); 
			 solOrta = new Parmak("Sol Orta",  El.SOL, 2, 2, 10, 7,solOrtaXY, t);
			 solIsaret = new Parmak("Sol Isaret",  El.SOL, 3, 1, 9, 6, solIsaretXY, t); 

			 sagSerce = new Parmak("Sag Serçe",  El.SAG, 2, 6, 10, 8, sagSerceXY, t);
			 sagYuzuk = new Parmak("Sag Yuzuk",  El.SAG, 2, 7, 9, 7, sagYuzukXY, t); 
			 sagOrta = new Parmak("Sag Orta",  El.SAG, 2, 8, 9, 7, sagOrtaXY, t);
			 sagIsaret = new Parmak("Sag Isaret",  El.SAG, 2, 9, 8, 6, sagIsaretXY, t);
			 
			 parmakKaydet(solSerce);
			 parmakKaydet(solYuzuk);
			 parmakKaydet(solOrta);
			 parmakKaydet(solIsaret);
			 
			 parmakKaydet(sagSerce);
			 parmakKaydet(sagYuzuk);
			 parmakKaydet(sagOrta);
			 parmakKaydet(sagIsaret);
			 
		}
		
		public void parmakKaydet(Parmak parmak){
			parmaklar.add(parmak);
			for(char c : parmak.sorumluKarakterler){
				karakterParmakMap.put(c, parmak);
			}
		}
		
		public void yaz(String giris){
			for(char c: giris.toCharArray()){
				Parmak parmak = karakterParmakMap.get(c);
				if (parmak != oncekiParmak) {
					oncekiKarakter = '#';
					if (oncekiParmak != null) oncekiParmak.ilkPozisyon();
				}
				if(parmak != null){
					parmak.bas(c, oncekiKarakter, oncekiParmak);
					toplam += parmak.zaman;
					oncekiParmak = parmak;
					oncekiKarakter = c;
				} else {
					System.out.println("Karakter bulunamadı: " + c);
				}
			}
		}
		
		public List<String> problemliIkililer(){
			List<String> tumIkililer = new ArrayList<String>();
			for(Parmak p : parmaklar){
				tumIkililer.addAll(p.problemliIkililer());
			}
			return tumIkililer;
		}
		
		public long toplamSure(){
			return toplam;
		}
		
	}
	
	class Parmak {
		String isim;
		int hiz;
		int uzanmaSuresi;
		// parmağın doğal duruş koordinatı
		int dogalSatir;
		int sogalSutun;
		// parmağın o anda bulunduğu koordinatlar.
		int i;
		int j;
		// Sağ el sol el?
		El el;
		
		char[] sorumluKarakterler;
		int[][] sorumluKoordinatlar;
		
		// Parmağın sorumlu olduğu koordinatlar:
		TusTakimi.KarakterKoordinati[] sorumlu;
		long zaman = 0;
		TusTakimi tusTakimi;
		
		public void ilkPozisyon(){
			i = dogalSatir;
			j = sogalSutun;
		}
		
		/**
		 * 
		 * @param isim : Parmağın ismi. Örn: Sol Serçe 
		 * @param el : Hangi el? El.SOL veya El.SAG
		 * @param satir : Parmağın doğal duruş yerleşim koordinatı satır değeri
		 * @param sutun : Parmağın doğal duruş yerleşim koordinatı sütun değeri
		 * @param hiz : Parmağın doğal hızı. yüksek değerler yavaşlığı ifade eder! Serçe için yüksek, işaret için düşük
		 * @param uzanmaSuresi : Parmağın uzanma süresi, kısa ve zayıf parmaklar için yüksek değer. Yine serçe için yüksek, işaret için düşük
		 * @param sorumluKoordinatlar : Parmağın sorumlu olduğu koordinatlar.
		 * @param tusTakimi : Parmağın kullaınldığı tuş takımı
		 */
		Parmak(String isim, El el, int satir, int sutun, int hiz, int uzanmaSuresi, int[][] sorumluKoordinatlar, TusTakimi tusTakimi){
			this.isim = isim;
			this.dogalSatir = satir;
			this.sogalSutun = sutun;
			this.hiz = hiz;
			this.uzanmaSuresi = uzanmaSuresi;
			this.sorumluKoordinatlar = sorumluKoordinatlar;
			this.sorumluKarakterler = new char[sorumluKoordinatlar.length];
			this.tusTakimi = tusTakimi;
			sorumlu = new TusTakimi.KarakterKoordinati[sorumluKoordinatlar.length];
			for (int i=0; i<sorumluKoordinatlar.length ; i++){
				 sorumlu[i] = tusTakimi.koordinat(sorumluKoordinatlar[i][0], sorumluKoordinatlar[i][1]);
				 sorumluKarakterler[i] = tusTakimi.karakter(sorumluKoordinatlar[i][0], sorumluKoordinatlar[i][1]);
				 System.out.println( isim + ": "  + sorumlu[i].toString());
			}
		}
		
		public List<String> problemliIkililer(){
			ArrayList<String> liste = new ArrayList<String>();
			for (char c1 : sorumluKarakterler)
				for (char c2 : sorumluKarakterler)	{
					if (c1 != c2){
						liste.add(""+ c1 + c2);
					}
			}
			return liste;
		}

		public void bas(char c, char oncekiKarakter, Parmak oncekiParmak) {
			long tusaBasmaSuresi = hiz;
			long uzanma = uzanmaSuresi;
			// tusa uzanma Suresi
			if(oncekiKarakter == '#'){
				zaman += uzanma;
			}
			else {
				zaman += (tusTakimi.mesafeHesapla(oncekiKarakter, c)/4);
			}
			
			if(oncekiParmak != null) {
				if(oncekiParmak.el != this.el){
					tusaBasmaSuresi -= 2;
				}
				if(oncekiParmak.el == this.el){
					tusaBasmaSuresi -= 1;
				}
				if(oncekiParmak == this){
					zaman += tusaBasmaSuresi;
				}
			}
			
		}

	}
}

