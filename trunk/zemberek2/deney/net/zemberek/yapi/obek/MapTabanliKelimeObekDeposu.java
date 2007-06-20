/*
 *  ***** BEGIN LICENSE BLOCK *****
 *
 *  Version: MPL 1.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is "Zemberek Dogal Dil Isleme Kutuphanesi"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.yapi.obek;

import net.zemberek.yapi.Kok;
import net.zemberek.bilgi.kokler.Sozluk;

import java.util.*;

public class MapTabanliKelimeObekDeposu implements KelimeObekDeposu {

    private Map<Kok, Set<KelimeObegi>> kokObekKumeTablosu = new HashMap<Kok, Set<KelimeObegi>>();
    private Set<KelimeObegi> obekler = new HashSet<KelimeObegi>();


    public KelimeObekDeposu ekle(KelimeObegi obek) {
        if (obek == null)
            throw new NullPointerException("null eklenemez..");
        obekler.add(obek);
        for (KelimeObekBileseni obekBileseni : obek.bilesenler()) {
            if (kokObekKumeTablosu.containsKey(obekBileseni.kok)) {
                kokObekKumeTablosu.get(obekBileseni.kok).add(obek);
            } else {
                Set<KelimeObegi> s = new HashSet(2);
                s.add(obek);
                kokObekKumeTablosu.put(obekBileseni.kok, s);
            }
        }
        return this;
    }

    public KelimeObekDeposu ekle(KelimeObegi... obekler) {
        if (obekler == null)
            throw new NullPointerException("null eklenemez..");
        for (KelimeObegi obek : obekler)
            ekle(obek);
        return this;
    }

    public List<KelimeObegi> obekAra(Kok... kokler) {

        Set<Set<KelimeObegi>> setset = new HashSet<Set<KelimeObegi>>(kokler.length);

        for (Kok word : kokler) {
            Set<KelimeObegi> set = kokObekKumeTablosu.get(word);
            if (set == null)
                return Collections.emptyList();
            else
                setset.add(set);
        }

        if (setset.size() == 1)
            return new ArrayList<KelimeObegi>(setset.iterator().next());

        Set<KelimeObegi> min = Collections.min(setset, setBoyKiyaslayici );
        setset.remove(min);

        Set<KelimeObegi> resultSet = new HashSet<KelimeObegi>(min.size());
        for (KelimeObegi phrase : min) {
            for (Set<KelimeObegi> set : setset) {
                if (!set.contains(phrase))
                    break;
                else
                    resultSet.add(phrase);
            }
        }
        return new ArrayList<KelimeObegi>(resultSet);
    }

    private final SetBoyKiyaslayici setBoyKiyaslayici = new SetBoyKiyaslayici();

    class SetBoyKiyaslayici implements Comparator<Set> {

        public int compare(Set o1, Set o2) {
            if (o1 == null && o2 == null) return -0;
            if (o1 == null) return 1;
            if (o2 == null) return -1;
            return o1.size() - o2.size();
        }
    }

    public boolean var(KelimeObegi aday) {        
        return obekler.contains(aday);
    }
}
