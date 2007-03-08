package org.trlucene.analysis;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class TurkishAnalyzer extends Analyzer {
    private String[] TURKISH_STOP_WORDS = { "bir", "ona", "ve", "ama", "fakat" };
    /**
     * Contains the stopwords used with the StopFilter.
     */
    private Set stopSet = new HashSet();

    /**
     * Contains words that should be indexed but not stemmed.
     */
    private Set exclusionSet = new HashSet();

    /**
     * Builds an analyzer.
     */
    public TurkishAnalyzer() {
      stopSet = StopFilter.makeStopSet(TURKISH_STOP_WORDS);
    }

    /**
     * Builds an analyzer with the given stop words.
     */
    public TurkishAnalyzer(String[] stopwords) {
      stopSet = StopFilter.makeStopSet(stopwords);
    }

    /**
     * Builds an analyzer with the given stop words.
     */
    public TurkishAnalyzer(Hashtable stopwords) {
      stopSet = new HashSet(stopwords.keySet());
    }

    /**
     * Builds an analyzer with the given stop words.
     */
    public TurkishAnalyzer(File stopwords) throws IOException {
      stopSet = WordlistLoader.getWordSet(stopwords);
    }

    /**
     * Builds an exclusionlist from an array of Strings.
     */
    public void setStemExclusionTable(String[] exclusionlist) {
      exclusionSet = StopFilter.makeStopSet(exclusionlist);
    }

    /**
     * Builds an exclusionlist from a Hashtable.
     */
    public void setStemExclusionTable(Hashtable exclusionlist) {
      exclusionSet = new HashSet(exclusionlist.keySet());
    }

    /**
     * Builds an exclusionlist from the words contained in the given file.
     */
    public void setStemExclusionTable(File exclusionlist) throws IOException {
      exclusionSet = WordlistLoader.getWordSet(exclusionlist);
    }

    /**
     * Creates a TokenStream which tokenizes all the text in the provided Reader.
     *
     * @return A TokenStream build from a StandardTokenizer filtered with
     *         StandardFilter, LowerCaseFilter, StopFilter, GermanStemFilter
     */
    public TokenStream tokenStream(String fieldName, Reader reader) {
      TokenStream result = new StandardTokenizer(reader);
      result = new StandardFilter(result);
      result = new LowerCaseFilter(result);
      result = new StopFilter(result, stopSet);
      result = new TurkishStemFilter(result, exclusionSet);
      return result;
    }

}