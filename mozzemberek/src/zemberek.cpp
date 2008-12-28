/*
  Copyright (c) 2004, TUBITAK/UEKAE

  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.

  Mozilla Zemberek Client library is based on zpspell. The Initial Developer
  of the zpspell code is Baris Metin.
*/

#include <iostream>
#include <vector>

#include "stdio.h"
#include "string.h"
#include "stdlib.h"

#include "zsconn.h"
#include "zstring.h"
#include "zemberek.h"

/* use myspell's limit for maxSug */
Zemberek::Zemberek() : maxSug(25)
{
    zsconn = new ZSConn();
}

Zemberek::~Zemberek()
{
    delete zsconn;
}

int Zemberek::spellCheck(const char *word) const
{
    ZString zstr = zsconn->checkString(word, 0);
    return (zstr.status() == Z_TRUE);
}

int Zemberek::getSuggestions(char ***suggestions, const char *word) const
{
    int ns = 0;
    //XXX: This check is unnecessary. we call this method only for misspelled words.
    ZString zstr = zsconn->checkString(word, 0);

    if ( zstr.status() == Z_FALSE ) {
        zstr.setSuggestions( zsconn->getSuggestions( zstr.str() ) );
        if ( zstr.suggestionCount() != 0 ) {
            zstr.setStatus( Z_SUGGESTION );
        }
    }

    *suggestions = NULL;

    if (zstr.status() != Z_SUGGESTION)
	return 0;

    char **wlst = (char **)calloc(maxSug, sizeof(char *));
    if (wlst == NULL) 
      return 0;
    
    vector<string>::const_iterator words_iter;
    const vector<string> &words = zstr.suggestions();

    for (words_iter = words.begin(); words_iter != words.end() && ns < maxSug; words_iter++, ns++) {
	wlst[ns] = strdup((*words_iter).c_str());
    }

    if (ns > 0)
	*suggestions = wlst;

    return ns;
}

char *Zemberek::get_dic_encoding()
{
    return strdup("UTF-8");
}
