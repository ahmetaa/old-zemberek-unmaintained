/* -*- Mode: C++; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*- */
/*# ***** BEGIN LICENSE BLOCK *****
# Version: MPL 1.1/GPL 2.0/LGPL 2.1
#
# The contents of this file are subject to the Mozilla Public License Version
# 1.1 (the "License"); you may not use this file except in compliance with
# the License. You may obtain a copy of the License at
# http://www.mozilla.org/MPL/
#
# Software distributed under the License is distributed on an "AS IS" basis,
# WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
# for the specific language governing rights and limitations under the
# License.
#
# The Original Code is Mozilla Spellchecker Component.
#
# The Initial Developer of the Original Code is Faik Uygur.
# Portions created by the Initial Developer are Copyright (C) 2006
# TUBITAK/UEKAE. All Rights Reserved.
#
# Contributor(s): Faik Uygur <faik@pardus.org.tr>
# Contributor(s): Rail Aliev <rail@i-rs.ru>
#
# Mozilla Zemberek Client library is based on zpspell. The Initial Developer
# of the zpspell code is Baris Metin. Portions created by the Initial Developer
# Copyright (c) 2004, TUBITAK/UEKAE. All Rights Reserved.
#
# Alternatively, the contents of this file may be used under the terms of
# either the GNU General Public License Version 2 or later (the "GPL"), or
# the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
# in which case the provisions of the GPL or the LGPL are applicable instead
# of those above. If you wish to allow use of your version of this file only
# under the terms of either the GPL or the LGPL, and not to allow others to
# use your version of this file under the terms of the MPL, indicate your
# decision by deleting the provisions above and replace them with the notice
# and other provisions required by the GPL or the LGPL. If you do not delete
# the provisions above, a recipient may use your version of this file under
# the terms of any one of the MPL, the GPL or the LGPL.
#
# ***** END LICENSE BLOCK ***** */

#include <nsIGenericFactory.h>

#include "mozZemberek.h"

////////////////////////////////////////////////////////////////////////
// Define the contructor function for the objects
//
// NOTE: This creates an instance of objects by using the default constructor
//

NS_GENERIC_FACTORY_CONSTRUCTOR_INIT(mozZemberek, Init)

////////////////////////////////////////////////////////////////////////
// Define a table of CIDs implemented by this module along with other
// information like the function to create an instance, contractid, and
// class name.
//
static nsModuleComponentInfo components[] = {
    {
        "mozZemberek",
        MOZ_ZEMBEREK_CID,
        MOZ_ZEMBEREK_CONTRACTID,
        mozZemberekConstructor,
        mozZemberek::registerExtension,
        mozZemberek::unregisterExtension
    }
};

////////////////////////////////////////////////////////////////////////
// Implement the NSGetModule() exported function for your module
// and the entire implementation of the module object.
//
NS_IMPL_NSGETMODULE(mozZemberekModule, components)
