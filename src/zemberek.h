/*
  Copyright (c) 2004, TUBITAK/UEKAE

  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.

  Please read the COPYING file.
*/

#ifndef ZEMBEREK_H
#define ZEMBEREK_H

#include "zsconn.h"

using namespace std;

class Zemberek
{
public:
    Zemberek();
    Zemberek(const char host[], const int port);
    ~Zemberek();

    int getSuggestions(char ***suggestions, const char *word) const;
    int spellCheck(const char *word) const;
    char *get_dic_encoding();

private:
    const short maxSug;
    ZSConn *zsconn;
};

#endif
