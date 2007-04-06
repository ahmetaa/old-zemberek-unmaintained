package net.zemberek.deney;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import net.zemberek.bilgi.ZemberekAyarlari;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.DilAyarlari;
import net.zemberek.yapi.DilBilgisi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.TurkceDilBilgisi;

public class OnParmak {
	private Document dom;
	private Element rootEl;
	
	private DilBilgisi dilbilgisi;
	private Sozluk kokler;
	private ZemberekAyarlari zemberekayarlari;
	private DilAyarlari dilayarlari;
	
	private String[] levels;

	public void init(String[] levels) {
		dilayarlari = new TurkiyeTurkcesi();
		zemberekayarlari = new ZemberekAyarlari(dilayarlari.locale().getLanguage());
		dilbilgisi = new TurkceDilBilgisi(dilayarlari, zemberekayarlari);

		kokler = dilbilgisi.kokler();
		this.levels= levels;
	}
	
	public void createKTouchDocument() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		createKTouchTree();
	}
	
	private void createKTouchTree() {
		rootEl = dom.createElement("KTouchLecture");
		dom.appendChild(rootEl);
		Element titleEl = dom.createElement("Title");
		Text titleText = dom.createTextNode("Türkçe (Zemberek ile otomatik)");
		titleEl.appendChild(titleText);
		Element commentEl = dom.createElement("Comment");
		Text commentText = dom.createTextNode(
				"KTouch 10 parmak klaye dersi. Zemberek tarafından otomatik olarak oluşturulmuştur.");
		commentEl.appendChild(commentText);
		rootEl.appendChild(titleEl);
		rootEl.appendChild(commentEl);

		createKTouchLevels();
	}
	
	private void createKTouchLevels() {
		Element levelsEl = dom.createElement("Levels");
		rootEl.appendChild(levelsEl);
		
		String cur = "";
		for (String level: this.levels) {
			Element levelEl = dom.createElement("Level");
			Element newCharsEl = dom.createElement("NewCharacters");
			newCharsEl.appendChild(dom.createTextNode(level));
			levelEl.appendChild(newCharsEl);

			cur += level;
			for (int i = 0; i < 10; ++i) {
				String line = "";
				for (String kok : kokSec(cur, level, 10, 10*i)) {
					line += kok + " ";
				}
				line = line.trim();
				Element lineEl = dom.createElement("Line");
				lineEl.appendChild(dom.createTextNode(line));
				levelEl.appendChild(lineEl);
			}

			levelsEl.appendChild(levelEl);
		}
	}

	public void printToFile(String filePath) {
		OutputFormat format = new OutputFormat(dom);
		format.setIndenting(true);
		XMLSerializer serializer;
		try {
			serializer = new XMLSerializer(
					new FileOutputStream(new File(filePath)), format);
			serializer.serialize(dom);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Collection<Kok> tumKokler() {
		return kokler.tumKokler();
	}
	
	private boolean checkKok(Kok kok, String harfler) {
		for (char k : kok.icerik().toCharArray()) {
			boolean inharfler = true;
			for (char c : harfler.toCharArray()) {
				if (c != k) {
					inharfler = false;
				}
				else {
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
	
	public Collection<String> kokSec(String harfler, String musthaveone, int count, int from) {
		HashSet<String> koklist = new HashSet<String>(100);
		int i = 0;
		for (Kok kok : tumKokler()) {
			if (from != 0) {
				--from;
				continue;
			}
			if (i == count)
				break;
			if (checkKok(kok, harfler)) {
				boolean hasone = false;
				for (char c : musthaveone.toCharArray()) {
					if (kok.icerik().indexOf(c) != -1)
						hasone = true;
				}
				if (hasone) {
					koklist.add(kok.icerik());
					++i;
				}
			}
		}
		return koklist;
	}

	public static void main(String[] args) {
		OnParmak onparmak = new OnParmak();
		String[] lectures = {
				"jf", "kd", "ls", "şa", "ci",
				"nt", "vı", "me", "hr", "go",
				"bp", "ğu", "ün", "çz", "ö"
		};
		onparmak.init(lectures);
		onparmak.createKTouchDocument();
		onparmak.printToFile("/home/baris/deneme.xml");
	}
}
