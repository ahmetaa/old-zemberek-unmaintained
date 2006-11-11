/*
copied and modified from SecondString project.
Copyright (c) 2003 Carnegie Mellon University
All rights reserved.
Developed by: 		Center for Automated Learning and Discovery
                      	Carnegie Mellon University
                        http://www.cald.cs.cmu.edu

     The design and implementation of this software was supported in
     part by National Science Foundation Grant No. EIA-0131884 to the
     National Institute of Statistical Sciences, and by a contract
     from the Army Research Office to the Center for Computer and
     Communications Security with Carnegie Mellon University.


Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal with the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

Redistributions of source code must retain the above copyright notice,
this list of conditions and the following disclaimers.
Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimers in the
documentation and/or other materials provided with the distribution.
Neither the names of the Center for Automated Learning and Discovery,
or Carnegie Mellon University, nor the names of its contributors may
be used to endorse or promote products derived from this Software
without specific prior written permission.  THE SOFTWARE IS PROVIDED
"AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS WITH THE SOFTWARE.

[This is an instance of the University of Illinois/NCSA Open Source
agreement, obtained from http://www.opensource.org/licenses/UoI-NCSA.php]*/


package net.zemberek.araclar;

/**
 * 
 * Winkler's reweighting scheme for distance metrics.  In the
 * literature, this was applied to the Jaro metric ('An Application of
 * the Fellegi-Sunter Model of Record Linkage to the 1990
 * U.S. Decennial Census' by William E. Winkler and Yves Thibaudeau.)
 */

public class JaroWinkler {

    /**
     * Verilen iki String'in benzerlik oranını Jaro-Winkler algortiması kullanarak hesaplar.
     * Oluşan sonuç 0-1 arasındadır, 1'e yaklaştıkça benzerlik artar, aynı stringler için "1" dir
     * @param source
     * @param target
     * @return Stringlerin benzerlik oranı. stringler aynı ise 1
     */
    public double benzerlikOrani(String source, String target) {
        double dist = jaroBenzerlikOrani(source, target);
        if (dist < 0 || dist > 1)
            throw new IllegalArgumentException("innerDistance should produce scores between 0 and 1");
        int prefLength = commonPrefixLength(4, source, target);
        dist = dist + prefLength * 0.1 * (1 - dist);
        return dist;
    }

    private double jaroBenzerlikOrani(String source, String target) {
        int halflen = halfLengthOfShorter(source, target);
        String common1 = commonChars(source, target, halflen);
        String common2 = commonChars(target, source, halflen);
        if (common1.length() != common2.length()) return 0;
        if (common1.length() == 0 || common2.length() == 0) return 0;

        int transpositions = transpositions(common1, common2);
        double dist =
                (common1.length() / ((double) source.length()) +
                common2.length() / ((double) target.length()) +
                (common1.length() - transpositions) / ((double) common1.length())) / 3.0;
        return dist;
    }

    private int halfLengthOfShorter(String source, String target) {
        return (source.length() > target.length()) ? target.length() / 2 + 1 : source.length() / 2 + 1;
    }

    private String commonChars(String s, String t, int halflen) {
        StringBuilder common = new StringBuilder();
        StringBuilder copy = new StringBuilder(t);
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            boolean foundIt = false;
            for (int j = Math.max(0, i - halflen); !foundIt && j < Math.min(i + halflen, t.length()); j++) {
                if (copy.charAt(j) == ch) {
                    foundIt = true;
                    common.append(ch);
                    copy.setCharAt(j, '*');
                }
            }
        }
        return common.toString();
    }

    private int transpositions(String common1, String common2) {
        int transpositions = 0;
        for (int i = 0; i < common1.length(); i++) {
            if (common1.charAt(i) != common2.charAt(i))
                transpositions++;
        }
        transpositions /= 2;
        return transpositions;
    }

    private static int commonPrefixLength(int maxLength, String common1, String common2) {
        int n = Math.min(maxLength, Math.min(common1.length(), common2.length()));
        for (int i = 0; i < n; i++) {
            if (common1.charAt(i) != common2.charAt(i)) return i;
        }
        return n; // first n characters are the same
    }

}
