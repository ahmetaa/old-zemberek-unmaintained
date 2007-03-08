package test;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.trlucene.analysis.TurkishAnalyzer;

public class AnalysisDemo {
//    private static final String[] strings = {
//        "The quick brown fox jumped over the lazy dogs",
//        "XY&Z Corporation - xyz@example.com"
//    };

    private static final String[] strings = {
        "Kahverengi Çevik tilki, tembel köpeğin üzerinden atladı.",
        "XY&Z Firması - xyz@pardus.org.tr"
    };
    
    private static final Analyzer[] analyzers = new Analyzer[]{
        new WhitespaceAnalyzer(),
        new SimpleAnalyzer(),
        new StopAnalyzer(),
        //new StandardAnalyzer(),
        new TurkishAnalyzer(),
    };

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < strings.length; i++) {
            analyze(strings[i]);
        }
    }

    private static void analyze(String text) throws IOException {
        System.out.println("Analzying \"" + text + "\"");
        for (int i = 0; i < analyzers.length; i++) {
            Analyzer analyzer = analyzers[i];
            System.out.println("\t" + analyzer.getClass().getName() + ":");
            System.out.print("\t\t");
            TokenStream stream = analyzer.tokenStream("contents", new StringReader(text));
            while (true) {
                Token token = stream.next();
                if (token == null) break;

                System.out.print("[" + token.termText() + "] ");
            }
            System.out.println("\n");
        }
    }

}