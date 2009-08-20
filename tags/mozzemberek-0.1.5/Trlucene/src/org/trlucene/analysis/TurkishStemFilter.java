package org.trlucene.analysis;

import java.io.IOException;
import java.util.Set;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

public class TurkishStemFilter extends TokenFilter {

    /**
     * The actual token in the input stream.
     */
    private Token token = null;
    private TurkishStemmer stemmer = null;
    private Set exclusionSet = null;

    public TurkishStemFilter( TokenStream in )
    {
      super(in);
      stemmer = new TurkishStemmer();
    }

    /**
     * Builds a GermanStemFilter that uses an exclusiontable.
     */
    public TurkishStemFilter( TokenStream in, Set exclusionSet )
    {
      this( in );
      this.exclusionSet = exclusionSet;
    }

    /**
     * @return  Returns the next token in the stream, or null at EOS
     */
    public final Token next()
      throws IOException
    {
      if ( ( token = input.next() ) == null ) {
        return null;
      }
      // Check the exclusiontable
      else if ( exclusionSet != null && exclusionSet.contains( token.termText() ) ) {
        return token;
      }
      else {
        String s = stemmer.stem( token.termText() );
        // If not stemmed, dont waste the time creating a new token
        if ( !s.equals( token.termText() ) ) {
          return new Token( s, token.startOffset(),
            token.endOffset(), token.type() );
        }
        return token;
      }
    }

    /**
     * Set a alternative/custom GermanStemmer for this filter.
     */
    public void setStemmer( TurkishStemmer stemmer )
    {
      if ( stemmer != null ) {
        this.stemmer = stemmer;
      }
    }

    /**
     * Set an alternative exclusion list for this filter.
     */
    public void setExclusionSet( Set exclusionSet )
    {
      this.exclusionSet = exclusionSet;
    }

}
