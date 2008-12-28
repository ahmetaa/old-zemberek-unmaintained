/* -*- Mode: C++; tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*- */
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

#ifndef mozZemberek_h__
#define mozZemberek_h__

#include "zemberek.h"
#include <mozISpellCheckingEngine.h>
#include <mozIPersonalDictionary.h>
#include <nsStringAPI.h>
#include <nsIGenericFactory.h>
#include <nsCOMPtr.h>
#include <nsIObserver.h>
#include <nsIUnicodeEncoder.h>
#include <nsIUnicodeDecoder.h>
#include <nsInterfaceHashtable.h>
#include <nsWeakReference.h>

#define MOZ_ZEMBEREK_CONTRACTID "@mozilla.org/spellchecker/zemberek;1"
#define MOZ_ZEMBEREK_CID         \
{ /* 15DDAC0D-D94C-4fc1-8F48-E1BB04A6F380} */  \
0x15DDAC0D, 0xD94C, 0x4fc1,                    \
{ 0x8F, 0x48, 0xE1, 0xBB, 0x04, 0xA6, 0xF3, 0x80} }

class mozZemberek : public mozISpellCheckingEngine,
                   public nsIObserver,
                   public nsSupportsWeakReference
{
public:
  NS_DECL_ISUPPORTS
  NS_DECL_MOZISPELLCHECKINGENGINE
  NS_DECL_NSIOBSERVER

  mozZemberek() : mZemberek(nsnull) { }
  virtual ~mozZemberek();

  nsresult Init();

  void LoadDictionaryList();
  void LoadDictionariesFromDir(nsIFile* aDir);

  // helper method for converting a word to the charset of the dictionary
  nsresult ConvertCharset(const PRUnichar* aStr, char ** aDst);

  static NS_METHOD registerExtension(nsIComponentManager *aCompMgr,
    nsIFile *aPath, const char *aLoaderString, const char *aType,
    const nsModuleComponentInfo *aInfo);

  static NS_METHOD unregisterExtension(nsIComponentManager *aCompMgr,
    nsIFile *aPath, const char *aLoaderString,
    const nsModuleComponentInfo *aInfo);

protected:
 
  nsCOMPtr<mozIPersonalDictionary> mPersonalDictionary;
  nsCOMPtr<nsIUnicodeEncoder>      mEncoder; 
  nsCOMPtr<nsIUnicodeDecoder>      mDecoder; 

  // Hashtable matches dictionary name to .aff file
  nsInterfaceHashtable<nsStringHashKey, nsIFile> mDictionaries;
  nsString  mDictionary;
  nsString  mLanguage;

  Zemberek  *mZemberek;
};

void logMessage(const char *fmt, ...);
#endif
