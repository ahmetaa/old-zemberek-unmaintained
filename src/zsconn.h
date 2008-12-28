/*
  Copyright (c) 2004, TUBITAK/UEKAE

  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.

  Please read the COPYING file.
*/

/*
  ZemberekServer Connection.
*/

#ifndef ZSCONN_H
#define ZSCONN_H

#include "zstring.h"

using namespace std;

class ZSConn
{
public:
    ZSConn();
    ~ZSConn();

    ZString checkString( const string& str, int offset ) const;
    vector<string> getSuggestions (const string& str ) const;

private:
    int _conn;

    enum Z_CHECK_RESULT spellCheck( const string& str ) const;
    string recvResult() const;
};

#endif
