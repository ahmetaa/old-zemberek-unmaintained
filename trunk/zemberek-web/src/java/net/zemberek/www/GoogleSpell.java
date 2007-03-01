/*
 * GoogleSpell.java
 *
 * Created on 24 Şubat 2007 Cumartesi, 09:04
 */

package net.zemberek.www;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.*;
import java.util.Hashtable;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.*;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import org.w3c.dom.*;
import net.zemberek.erisim.Zemberek;
/**
 *
 * @author firari
 * @version
 */
public class GoogleSpell extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/xml;");
        response.setCharacterEncoding("utf-8");
        
        PrintWriter out = response.getWriter();
        try {
            Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder domparser = factory.newDocumentBuilder();
            Document docIn = domparser.parse(request.getInputStream());
            Document docOut= domparser.newDocument();
            Element result = docOut.createElement("spellresult");
            Node root = docIn.getElementsByTagName("spellrequest").item(0);
            NamedNodeMap map = root.getAttributes();
            String textalreadyclipped = ((Attr)map.getNamedItem("textalreadyclipped")).getValue();
            String metin = root.getFirstChild().getTextContent();
            Hashtable<String, Integer> sonPozisyonlar = new Hashtable<String, Integer>();
            StringTokenizer tok = new StringTokenizer(metin, "!'#%&/()=?-_:.,;\"\r\n\t ");
            while (tok.hasMoreTokens()) {
                String kelime = tok.nextToken();
                int pos;
                if (sonPozisyonlar.containsKey(kelime))
                    pos = metin.indexOf(kelime, sonPozisyonlar.get(kelime) + kelime.length());
                else
                    pos = metin.indexOf(kelime);
                sonPozisyonlar.put(kelime,pos);
                if (!zemberek.kelimeDenetle(kelime)) {
                    Element error = docOut.createElement("c");
                    error.setAttribute("o",Integer.toString(pos));
                    error.setAttribute("l",Integer.toString(kelime.length()));
                    error.setAttribute("s","1");
                    String oneriStr="";
                    String[] oneriler=zemberek.oner(kelime);
                    for(int i=0;i<oneriler.length-1;i++)
                        oneriStr+=(oneriler[i]+"\t");
                    oneriStr+=oneriler[oneriler.length-1];
                    error.setTextContent(oneriStr);
                    result.appendChild(error);
                }
                sonPozisyonlar.put(kelime, pos);
            }
            result.setAttribute("error","0");
            result.setAttribute("clipped",textalreadyclipped);
            result.setAttribute("charschecked",Integer.toString(metin.length()));
            docOut.appendChild(result);
            OutputFormat format = new OutputFormat();
            format.setEncoding("UTF-8");
            format.setIndenting(true);
            format.setLineSeparator("\r\n");
            XMLSerializer serializer = new XMLSerializer(out,format);
            serializer.serialize(docOut);
        } catch(Exception e) {
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?><spellresult error=\"1\"/>");
            e.printStackTrace();
        }
        out.close();
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
