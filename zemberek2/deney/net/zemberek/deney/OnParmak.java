package net.zemberek.deney;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.zemberek.bilgi.ZemberekAyarlari;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.DilAyarlari;
import net.zemberek.yapi.DilBilgisi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.TurkceDilBilgisi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class OnParmak {
	private Document dom;
	private Element rootEl;
	
	private DilBilgisi dilbilgisi;
	private Sozluk kokler;
	private ZemberekAyarlari zemberekayarlari;
	private DilAyarlari dilayarlari;
	
	private String[] levels;
	private final int numLines = 20;
	private final int numWordsInLines = 8;
	private final int numRequiredWords = numLines * numWordsInLines;

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
			Random random = new Random();

			Element levelEl = dom.createElement("Level");
			Element newCharsEl = dom.createElement("NewCharacters");
			newCharsEl.appendChild(dom.createTextNode(level));
			levelEl.appendChild(newCharsEl);

			cur += level;
			Collection<String> kokler = kokSec(cur, level);
			if (kokler.size() < numRequiredWords) {
				for (int i  = 0; i < numRequiredWords - kokler.size(); ++i) {
					kokler.add(generateString(cur.toCharArray(), 5));
				}
			}

			Object[] kokArray = kokler.toArray();
			for (int i = 0; i < numLines; ++i) {
				Element lineEl = dom.createElement("Line");
				String line = "";
				for (int j = 0; j < numWordsInLines; ++j) {
					int index = random.nextInt(kokArray.length);
					line += kokArray[index] + " ";
				}
				line = line.trim();
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

	private String generateString(char[] chars, int length) {
		Random random = new Random();
		String str = "";
		int len = random.nextInt(length) + 1; 
		while (len > 0) {
			char c = chars[random.nextInt(chars.length)];
			str += c;
			--len;
		}
		return str; 
	}
	
	private Collection<Kok> tumKokler() {
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
	
	private Collection<String> kokSec(String harfler, String musthaveone) {
		HashSet<String> koklist = new HashSet<String>(100);
		for (Kok kok : tumKokler()) {
			if (checkKok(kok, harfler)) {
				boolean hasone = false;
				for (char c : musthaveone.toCharArray()) {
					if (kok.icerik().indexOf(c) != -1)
						hasone = true;
				}
				if (hasone) {
					koklist.add(kok.icerik());
				}
			}
		}
		return koklist;
	}

	public static void main(String[] args) {
		OnParmak onparmak = new OnParmak();
		String[] levels= {
				"jf", "kd", "ls", "şa", "ci",
				"nt", "vı", "me", "hr", "go",
				"bp", "ğu", "ün", "çz", "ö"
		};
		onparmak.init(levels);
		onparmak.createKTouchDocument();
		
		if (args.length == 1)
			onparmak.printToFile(args[0]);
		else
			onparmak.printToFile("/home/baris/turkish.ktouch.xml");
	}
}
