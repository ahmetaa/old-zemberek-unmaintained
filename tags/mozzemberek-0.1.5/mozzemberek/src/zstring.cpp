/*
  Copyright (c) 2004, TUBITAK/UEKAE

  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.

  Please read the COPYING file.
*/

#include <iostream>
#include <sstream>

#include "zstring.h"

ZString::ZString(const string& str, int offset )
    : _str(str), _offset(offset),
      _status(Z_UNKNOWN)
{}


/* set */
void ZString::setStatus( enum Z_CHECK_RESULT status )
{
    _status = status;
}

void ZString::setSuggestions( const vector<string>& suggestions)
{
    _suggestions = suggestions;
}

void ZString::addSuggestion( const string& suggestion )
{
    _suggestions.push_back( suggestion );
}

/* get */
int ZString::offset() const
{
    return _offset;
}

const string& ZString::str() const
{
    return _str;
}

enum Z_CHECK_RESULT ZString::status() const
{
    return _status;
}

int ZString::suggestionCount() const
{
    return _suggestions.size();
}

const string ZString::suggestionString() const
{
    stringstream sstr;

    vector<string>::const_iterator it = _suggestions.begin();
    int len = _suggestions.size();
    for (int i=0 ; i < len ; ++i, ++it ) {
        sstr << *it;
        if ( i < len-1 ) {
            sstr << ", ";
        }
    }

    return sstr.str();
}

const vector<string>& ZString::suggestions() const
{
    return _suggestions;
}

