package net.zemberek.deney;

import java.io.IOException;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashSet;

import net.zemberek.bilgi.ZemberekAyarlari;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.DilAyarlari;
import net.zemberek.yapi.DilBilgisi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.TurkceDilBilgisi;

public class OnParmak {
	private DilBilgisi dilbilgisi;
	private Sozluk kokler;
	private ZemberekAyarlari zemberekayarlari;
	private DilAyarlari dilayarlari;

	public void init() {
		dilayarlari = new TurkiyeTurkcesi();
		zemberekayarlari = new ZemberekAyarlari(dilayarlari.locale()
				.getLanguage());
		dilbilgisi = new TurkceDilBilgisi(dilayarlari, zemberekayarlari);

		kokler = dilbilgisi.kokler();
	}

	public Collection<Kok> tumKokler() {
		return kokler.tumKokler();
	}

	/* test... */
	public void writeToFile() {
		OnParmak onparmak = new OnParmak();
		FileWriter fout = null;

		try {
			fout = new FileWriter("/home/baris/kokler.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Kok kok : onparmak.tumKokler()) {
			try {
				fout.write(kok.icerik() + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean checkKok(Kok kok, String harfler) {
		for (char k : kok.icerik().toCharArray()) {
			boolean inharfler = true;
			for (char c : harfler.toCharArray()) {
				if (c != k) {
					inharfler = false;
				} else {
					inharfler = true;
					break;
				}
			}
			if (!inharfler) {
				return false;
			}
		}
		return true;
	}

	public Collection<String> kokSec(String harfler) {
		HashSet<String> koklist = new HashSet<String>(100);
		for (Kok kok : tumKokler()) {
			if (checkKok(kok, harfler)) {
				koklist.add(kok.icerik());
			}
		}
		return koklist;
	}

	public static void main(String[] args) {
		OnParmak onparmak = new OnParmak();
		onparmak.init();
		for (String s : onparmak.kokSec("hasis")) {
			System.out.println(s);
		}
	}
}
