package net.zemberek.araclar;

import net.zemberek.bilgi.KaynakYukleyici;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ahmet
 * Date: Nov 24, 2005
 */
public class XmlYardimcisi {

    public static Document xmlDosyaCozumle(String xmlFile)
            throws IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder loader = null;
        try {
            loader = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        // xml dokumani yukle.
        InputStream is = new KaynakYukleyici("UTF-8").getStream(xmlFile);
        Document doc = null;
        try {
            doc = loader.parse(is);
        } catch (SAXException e) {
            e.printStackTrace();
        }
        if (is != null) is.close();
        return doc;
    }

    public static List<Element> elemanlar(Element parent, String childName) {
        NodeList nodeList = parent.getElementsByTagName(childName);
        List<Element> elements = new ArrayList();
        for (int i = 0; i < nodeList.getLength(); i++)
            elements.add((Element) nodeList.item(i));
        return elements;
    }

    public static Element ilkEleman(Element parent, String childName) {
        return (Element) parent.getElementsByTagName(childName).item(0);
    }

}
