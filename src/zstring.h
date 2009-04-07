/*
  Copyright (c) 2004, TUBITAK/UEKAE

  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.

  Please read the COPYING file.
*/

#ifndef ZSTRING_H
#define ZSTRING_H

#include <string>
#include <vector>

using namespace std;

enum Z_CHECK_RESULT {
    Z_TRUE = 0,
    Z_FALSE,
    Z_SUGGESTION,
    Z_UNKNOWN
};

class ZString
{
public:
    ZString(const string& str, int offset );

    void setStatus( enum Z_CHECK_RESULT status );
    void setSuggestions( const vector<string>& suggestions);
    void addSuggestion( const string& suggestion );

    int offset() const;
    const string& str() const;
    enum Z_CHECK_RESULT status() const;
    const vector<string>& suggestions() const;
    int suggestionCount() const;
    const string suggestionString() const;

private:
    int _offset;
    enum Z_CHECK_RESULT _status;
    const string _str;
    vector<string> _suggestions;
};

#endif // ZSTRING_H
