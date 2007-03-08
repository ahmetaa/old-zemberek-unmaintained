package test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import net.zemberek.araclar.TimeTracker;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.trlucene.analysis.TurkishAnalyzer;

public class Indexer {
    
    public static void index(File indexDir, File dataDir) throws IOException {
        if (!dataDir.exists() || !dataDir.isDirectory()) {
           throw new IOException(dataDir + " does not exist or is not a directory");
        }
        IndexWriter writer = new IndexWriter(indexDir, new TurkishAnalyzer(), true);
        indexDirectory(writer, dataDir);
        writer.close();
    }
    
    private static void indexDirectory(IndexWriter writer, File dir) throws IOException {
        File[] files = dir.listFiles();

        for (int i=0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) {
               indexDirectory(writer, f);  // recurse
            } else if (f.getName().endsWith(".txt")) {
               indexFile(writer, f);
            }
        }
    }    

    private static void indexFile(IndexWriter writer, File f) throws IOException {
        System.out.println("Indexing " + f.getName());
        Document doc = new Document();
        doc.add(Field.Text("contents", new FileReader(f)));
        doc.add(Field.Keyword("filename", f.getCanonicalPath()));
        writer.addDocument(doc);
    }    
    
    public static void main(String[] args) {
        // index some books..
        TimeTracker.startClock("i");
        try {
            Indexer.index(new File("idx"), new File("test"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("indexing complete. elapsed time: " + TimeTracker.stopClock("i"));
    }    
    
}
