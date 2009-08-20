package test;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.trlucene.analysis.TurkishAnalyzer;

public class Tester {
    
    public static void search(File indexDir, String q)  throws Exception{
        Directory fsDir = FSDirectory.getDirectory(indexDir, false);
        IndexSearcher is = new IndexSearcher(fsDir);

        Query query = QueryParser.parse(q, "contents", new TurkishAnalyzer());
        Hits hits = is.search(query);
        System.out.println("Found " + hits.length() + " document(s) that matched query '" + q + "':");
        for (int i = 0; i < hits.length(); i++) {
            Document doc = hits.doc(i);
            System.out.println(doc.get("filename"));
        }
    }
    
    public static void main(String[] args) {
        try {
            search(new File("idx"), "bilgisayar");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
