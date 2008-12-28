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

#include "mozZemberek.h"
#include <nsIObserverService.h>
#include <nsISimpleEnumerator.h>
#include <nsIDirectoryEnumerator.h>
#include <nsIFile.h>
#include <nsDirectoryServiceUtils.h>
#include <nsDirectoryServiceDefs.h>
#include <mozISpellI18NManager.h>
#include <nsICategoryManager.h>
#include <nsICharsetConverterManager.h>
#include <nsUnicharUtilCIID.h>
#include <nsUnicharUtils.h>
#include <nsIConsoleService.h>
#include <prmem.h>
#include <nsCRT.h>
#include <stdlib.h>
#include <stdarg.h>
#include <prprf.h>

static NS_DEFINE_CID(kCharsetConverterManagerCID, NS_ICHARSETCONVERTERMANAGER_CID);
static NS_DEFINE_CID(kUnicharUtilCID, NS_UNICHARUTIL_CID);

NS_IMPL_ISUPPORTS3(mozZemberek,
                   mozISpellCheckingEngine,
                   nsIObserver,
                   nsISupportsWeakReference)

nsresult
mozZemberek::Init()
{
  if (!mDictionaries.Init())
    return NS_ERROR_OUT_OF_MEMORY;

  nsCOMPtr<nsIObserverService> obs =
    do_GetService("@mozilla.org/observer-service;1");
  if (obs) {
    obs->AddObserver(this, "profile-do-change", PR_TRUE);
  }

  return NS_OK;
}

mozZemberek::~mozZemberek()
{
  mPersonalDictionary = nsnull;
  delete mZemberek;
}

/* attribute wstring dictionary; */
NS_IMETHODIMP mozZemberek::GetDictionary(PRUnichar **aDictionary)
{
  NS_ENSURE_ARG_POINTER(aDictionary);

  if (mDictionary.IsEmpty())
    return NS_ERROR_NOT_INITIALIZED;

  *aDictionary = ToNewUnicode(mDictionary);
  return *aDictionary ? NS_OK : NS_ERROR_OUT_OF_MEMORY;
}

/* set the Dictionary.
 * This also Loads the dictionary and initializes the converter using the dictionaries converter
 */
NS_IMETHODIMP mozZemberek::SetDictionary(const PRUnichar *aDictionary)
{
  NS_ENSURE_ARG_POINTER(aDictionary);

  if (mDictionary.Equals(aDictionary))
    return NS_OK;

    nsString newDict(aDictionary);
    nsresult rv = NS_OK;

    if (!newDict.Equals(NS_LITERAL_STRING("tr-TR")))
        return NS_ERROR_FAILURE;

    if (!mDictionary.Equals(newDict))
    {
        mDictionary = aDictionary;
        if (mZemberek)
            delete mZemberek;

        mZemberek = new Zemberek();
	    if (!mZemberek)
		  return NS_ERROR_OUT_OF_MEMORY;

        nsCOMPtr<nsICharsetConverterManager> ccm = do_GetService(kCharsetConverterManagerCID, &rv);
        NS_ENSURE_SUCCESS(rv, rv);

        rv = ccm->GetUnicodeDecoder(mZemberek->get_dic_encoding(), getter_AddRefs(mDecoder));
        NS_ENSURE_SUCCESS(rv, rv);
        rv = ccm->GetUnicodeEncoder(mZemberek->get_dic_encoding(), getter_AddRefs(mEncoder));
        if (mEncoder && NS_SUCCEEDED(rv))
        {
            mEncoder->SetOutputErrorBehavior(mEncoder->kOnError_Signal, nsnull, '?');
        }

        NS_ENSURE_SUCCESS(rv, rv);
        mLanguage.Assign(newDict);

        return rv;
    }
    else
    {
        return NS_OK;
    } 

}

/* readonly attribute wstring language; */
NS_IMETHODIMP mozZemberek::GetLanguage(PRUnichar **aLanguage)
{
  NS_ENSURE_ARG_POINTER(aLanguage);

  if (mDictionary.IsEmpty())
    return NS_ERROR_NOT_INITIALIZED;

  *aLanguage = ToNewUnicode(mLanguage);
  return *aLanguage ? NS_OK : NS_ERROR_OUT_OF_MEMORY;
}

/* readonly attribute boolean providesPersonalDictionary; */
NS_IMETHODIMP mozZemberek::GetProvidesPersonalDictionary(PRBool *aProvidesPersonalDictionary)
{
  NS_ENSURE_ARG_POINTER(aProvidesPersonalDictionary);

  *aProvidesPersonalDictionary = PR_FALSE;
  return NS_OK;
}

/* readonly attribute boolean providesWordUtils; */
NS_IMETHODIMP mozZemberek::GetProvidesWordUtils(PRBool *aProvidesWordUtils)
{
  NS_ENSURE_ARG_POINTER(aProvidesWordUtils);

  *aProvidesWordUtils = PR_FALSE;
  return NS_OK;
}

/* readonly attribute wstring name; */
NS_IMETHODIMP mozZemberek::GetName(PRUnichar * *aName)
{
  return NS_ERROR_NOT_IMPLEMENTED;
}

/* readonly attribute wstring copyright; */
NS_IMETHODIMP mozZemberek::GetCopyright(PRUnichar * *aCopyright)
{
  return NS_ERROR_NOT_IMPLEMENTED;
}

/* attribute mozIPersonalDictionary personalDictionary; */
NS_IMETHODIMP mozZemberek::GetPersonalDictionary(mozIPersonalDictionary * *aPersonalDictionary)
{
  *aPersonalDictionary = mPersonalDictionary;
  NS_IF_ADDREF(*aPersonalDictionary);
  return NS_OK;
}

NS_IMETHODIMP mozZemberek::SetPersonalDictionary(mozIPersonalDictionary * aPersonalDictionary)
{
  mPersonalDictionary = aPersonalDictionary;
  return NS_OK;
}

NS_IMETHODIMP mozZemberek::GetDictionaryList(PRUnichar ***aDictionaries, PRUint32 *aCount)
{
    if (!aDictionaries || !aCount)
        return NS_ERROR_NULL_POINTER;

    *aDictionaries = 0;
    *aCount = 0;

    PRUnichar **tmpPtr = (PRUnichar **) NS_Alloc(sizeof(PRUnichar *));
    if (!tmpPtr)
        return NS_ERROR_OUT_OF_MEMORY;

        if (mZemberek)
            delete mZemberek;

        mZemberek = new Zemberek();
	    if (!mZemberek)
		  return NS_ERROR_OUT_OF_MEMORY;
	    
        nsAutoString zemberekDictName(NS_LITERAL_STRING("tr-TR").get());
        tmpPtr[0] = ToNewUnicode(zemberekDictName);
        *aCount = 1;
        *aDictionaries = tmpPtr;
        return NS_OK;

    //NS_Free(tmpPtr);	
    return NS_OK;
}

nsresult mozZemberek::ConvertCharset(const PRUnichar* aStr, char ** aDst)
{
  NS_ENSURE_ARG_POINTER(aDst);
  NS_ENSURE_TRUE(mEncoder, NS_ERROR_NULL_POINTER);

  PRInt32 outLength;
  PRInt32 inLength = nsCRT::strlen(aStr);
  nsresult rv = mEncoder->GetMaxLength(aStr, inLength, &outLength);
  NS_ENSURE_SUCCESS(rv, rv);

  *aDst = (char *) nsMemory::Alloc(sizeof(char) * (outLength+1));
  NS_ENSURE_TRUE(*aDst, NS_ERROR_OUT_OF_MEMORY);

  rv = mEncoder->Convert(aStr, &inLength, *aDst, &outLength);
  if (NS_SUCCEEDED(rv))
    (*aDst)[outLength] = '\0'; 

  return rv;
}

/* boolean Check (in wstring word); */
NS_IMETHODIMP mozZemberek::Check(const PRUnichar *aWord, PRBool *aResult)
{
  NS_ENSURE_ARG_POINTER(aWord);
  NS_ENSURE_ARG_POINTER(aResult);
  NS_ENSURE_TRUE(mZemberek, NS_ERROR_FAILURE);

  char *charsetWord;
  nsresult rv = ConvertCharset(aWord, &charsetWord);
  NS_ENSURE_SUCCESS(rv, rv);

  *aResult = mZemberek->spellCheck(charsetWord);

  NS_Free(charsetWord);

  if (!*aResult && mPersonalDictionary) 
    rv = mPersonalDictionary->Check(aWord, mLanguage.get(), aResult);
  
  return rv;
}

/* void Suggest (in wstring word, [array, size_is (count)] out wstring suggestions, out PRUint32 count); */
NS_IMETHODIMP mozZemberek::Suggest(const PRUnichar *aWord, PRUnichar ***aSuggestions, PRUint32 *aSuggestionCount)
{
  NS_ENSURE_ARG_POINTER(aSuggestions);
  NS_ENSURE_ARG_POINTER(aSuggestionCount);
  NS_ENSURE_TRUE(mZemberek, NS_ERROR_FAILURE);

  nsresult rv;
  *aSuggestionCount = 0;
  
  char *charsetWord;
  rv = ConvertCharset(aWord, &charsetWord);
  NS_ENSURE_SUCCESS(rv, rv);

  char ** wlst;
  *aSuggestionCount = mZemberek->getSuggestions(&wlst, charsetWord);
  NS_Free(charsetWord);

  if (*aSuggestionCount) {    
    *aSuggestions  = (PRUnichar **)nsMemory::Alloc(*aSuggestionCount * sizeof(PRUnichar *));    
    if (*aSuggestions) {
      PRUint32 index = 0;
      for (index = 0; index < *aSuggestionCount && NS_SUCCEEDED(rv); ++index) {
        // Convert the suggestion to utf16     
        PRInt32 inLength = nsCRT::strlen(wlst[index]);
        PRInt32 outLength;
        rv = mDecoder->GetMaxLength(wlst[index], inLength, &outLength);
        if (NS_SUCCEEDED(rv))
        {
          (*aSuggestions)[index] = (PRUnichar *) nsMemory::Alloc(sizeof(PRUnichar) * (outLength+1));
          if ((*aSuggestions)[index])
          {
            rv = mDecoder->Convert(wlst[index], &inLength, (*aSuggestions)[index], &outLength);
            if (NS_SUCCEEDED(rv))
              (*aSuggestions)[index][outLength] = 0;
          } 
          else
            rv = NS_ERROR_OUT_OF_MEMORY;
        }
      }

      if (NS_FAILED(rv))
        NS_FREE_XPCOM_ALLOCATED_POINTER_ARRAY(index, *aSuggestions); // free the PRUnichar strings up to the point at which the error occurred
    }
    else // if (*aSuggestions)
      rv = NS_ERROR_OUT_OF_MEMORY;
  }
  
  NS_FREE_XPCOM_ALLOCATED_POINTER_ARRAY(*aSuggestionCount, wlst);
  return rv;
}

NS_IMETHODIMP
mozZemberek::Observe(nsISupports* aSubj, const char *aTopic,
                    const PRUnichar *aData)
{
  NS_ASSERTION(!strcmp(aTopic, "profile-do-change"),
               "Unexpected observer topic");

  return NS_OK;
}

NS_METHOD mozZemberek::registerExtension(nsIComponentManager *aCompMgr,
  nsIFile *aPath, const char *aLoaderString, const char *aType,
  const nsModuleComponentInfo*aInfo)
{
    nsresult rv;
    nsString mozZemberekLibName;

    aPath->GetPath(mozZemberekLibName);

    logMessage("mozzemberek installed at %s",
        NS_ConvertUTF16toUTF8(mozZemberekLibName).get());

    nsCOMPtr<nsICategoryManager> catMgr =
        do_GetService(NS_CATEGORYMANAGER_CONTRACTID);
    if (!catMgr)
    {
        logMessage("mozZemberek::registerExtension: Failed to get nsICategoryManager");
        return NS_ERROR_FAILURE;
    }

    // Register category entry for this module
    // The value of the category entry is the absolute path to mozzemberek
    // shared library.
    rv = catMgr->AddCategoryEntry("spell-check-engine", MOZ_ZEMBEREK_CONTRACTID,
        NS_ConvertUTF16toUTF8(mozZemberekLibName).get(),
        PR_TRUE, PR_TRUE, NULL);
    if (NS_FAILED(rv))
    {
        logMessage("Failed to register category entry of spellchecker");
        return rv;
    }

    return NS_OK;
}

NS_METHOD mozZemberek::unregisterExtension(nsIComponentManager *aCompMgr,
    nsIFile *aPath, const char *aLoaderString, const nsModuleComponentInfo *aInfo)
{
    nsresult rv;

    nsCOMPtr<nsICategoryManager> catMgr =
        do_GetService(NS_CATEGORYMANAGER_CONTRACTID);
    if (!catMgr)
        return NS_ERROR_FAILURE;

    rv = catMgr->DeleteCategoryEntry("spell-check-engine", MOZ_ZEMBEREK_CONTRACTID,
        PR_TRUE);
    if (NS_FAILED(rv))
        return rv;

    return NS_OK;
}

void logMessage(const char *fmt, ...)
{
    va_list args;
    va_start(args, fmt);
    char *msg = PR_vsmprintf(fmt, args);
    va_end(args);

    nsCOMPtr<nsIConsoleService> aConsoleService =
        do_GetService("@mozilla.org/consoleservice;1");

    if (aConsoleService)
    {
        nsCString tmp(msg);
        aConsoleService->LogStringMessage(NS_ConvertUTF8toUTF16(tmp).get());
    }
    else 
    {
        fputs(msg, stdout);
    }
    
    PR_Free(msg);
}
