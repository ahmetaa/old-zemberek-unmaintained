/*************************************************************************
 *
 *  $RCSfile: TurkishHyphenator.java,v $
 *
 *  $Revision: 1.4 $
 *
 *  last change: $Author: mdakin $ $Date: 2005/08/07 10:10:56 $
 *
 *  The Contents of this file are made available subject to the terms of
 *  the BSD license.
 *  
 *  Copyright (c) 2003 by Sun Microsystems, Inc.
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. Neither the name of Sun Microsystems, Inc. nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 *  FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 *  COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 *  BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 *  OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 *  TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 *  USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *     
 *************************************************************************/
package net.zemberek.ooo.hyphenator;

// uno 
import java.util.ArrayList;

import net.zemberek.ooo.OneInstanceFactory;
import net.zemberek.ooo.ZemberekLibrary;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.Locale;
import com.sun.star.lang.XInitialization;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.lang.XServiceDisplayName;
import com.sun.star.lang.XServiceInfo;
import com.sun.star.lang.XSingleServiceFactory;
import com.sun.star.lib.uno.helper.ComponentBase;
import com.sun.star.linguistic2.XHyphenatedWord;
import com.sun.star.linguistic2.XHyphenator;
import com.sun.star.linguistic2.XLinguServiceEventBroadcaster;
import com.sun.star.linguistic2.XLinguServiceEventListener;
import com.sun.star.linguistic2.XPossibleHyphens;
import com.sun.star.uno.AnyConverter;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
@SuppressWarnings("unchecked")
public class TurkishHyphenator extends ComponentBase implements
        XHyphenator,
        XLinguServiceEventBroadcaster,
        XInitialization,
        XServiceDisplayName,
        XServiceInfo
{
    public static Locale turkishLocale = new Locale("tr", "TR", "");
    public static Locale emptyLocale = new Locale();
    PropChgHelper_Hyph          aPropChgHelper;
    ArrayList                   aEvtListeners;
    boolean                     bDisposing;

    public TurkishHyphenator()
    {
        // names of relevant properties to be used
        String[] aProps = new String[]
            {
                "IsIgnoreControlCharacters",
                "IsUseDictionaryList",
                "IsGermanPreReform",
                "HyphMinLeading",
                "HyphMinTrailing",
                "HyphMinWordLength"
            };
        aPropChgHelper  = new PropChgHelper_Hyph( (XHyphenator) this, aProps );
        aEvtListeners   = new ArrayList();;
        bDisposing      = false;
    }

    private boolean IsEqual( Locale aLoc1, Locale aLoc2 )
    {
        return aLoc1.Language.equals( aLoc2.Language ) &&
               aLoc1.Country .equals( aLoc2.Country )  &&
               aLoc1.Variant .equals( aLoc2.Variant );  
    }

    private boolean GetValueToUse( 
            String          aPropName,
            boolean         bDefaultVal,
            PropertyValue[] aProps )
    {
        boolean bRes = bDefaultVal;

        try
        {
            // use temporary value if supplied
            for (int i = 0;  i < aProps.length;  ++i)
            {
                if (aPropName.equals( aProps[i].Name ))
                {
                    Object aObj = aProps[i].Value;
                    if (AnyConverter.isBoolean( aObj ))
                    {
                        bRes = AnyConverter.toBoolean( aObj );
                        return bRes;
                    }
                }
            }

            // otherwise use value from property set (if available)
            XPropertySet xPropSet = aPropChgHelper.GetPropSet();
            if (xPropSet != null)   // should always be the case
            {
                Object aObj = xPropSet.getPropertyValue( aPropName );
                if (AnyConverter.isBoolean( aObj ))
                    bRes = AnyConverter.toBoolean( aObj );
            }
        }
        catch (Exception e) {
            bRes = bDefaultVal;
        }
        
        return bRes;
    }
                    
    private short GetValueToUse( 
            String          aPropName,
            short           nDefaultVal,
            PropertyValue[] aProps )
    {
        short nRes = nDefaultVal;

        try
        {
            // use temporary value if supplied
            for (int i = 0;  i < aProps.length;  ++i)
            {
                if (aPropName.equals( aProps[i].Name ))
                {
                    Object aObj = aProps[i].Value;
                    if (AnyConverter.isShort( aObj ))
                    {
                        nRes = AnyConverter.toShort( aObj );
                        return nRes;
                    }
                }
            }

            // otherwise use value from property set (if available)
            XPropertySet xPropSet = aPropChgHelper.GetPropSet();
            if (xPropSet != null)   // should always be the case
            {
                    Object aObj = xPropSet.getPropertyValue( aPropName );
                    if (AnyConverter.isShort( aObj ))
                        nRes = AnyConverter.toShort( aObj );
            }
        }
        catch (Exception e) {
            nRes = nDefaultVal;
        }
        
        return nRes;
    }
    
    // __________ interface methods __________
    
    
    //*****************
    //XSupportedLocales
    //*****************
    public Locale[] getLocales()
        throws com.sun.star.uno.RuntimeException
    {
        Locale aLocales[] = 
        {
            new Locale( "tr", "TR", "" )
        };
        return aLocales;
    }
    
    public boolean hasLocale( Locale aLocale ) 
        throws com.sun.star.uno.RuntimeException
    {
        boolean bRes = false;
        if ( IsEqual( aLocale, turkishLocale)) bRes = true;
        return bRes;        
    }
    
    //***********
    //XHyphenator
    //***********
    public XHyphenatedWord hyphenate(
            String aWord, Locale aLocale,
            short nMaxLeading, PropertyValue[] aProperties ) 
        throws com.sun.star.uno.RuntimeException,
               IllegalArgumentException
    {
        if (IsEqual( aLocale, new Locale() ) || aWord.length() == 0)
            return null;

        // linguistic is currently not allowed to throw exceptions
        // thus we return null fwhich means 'word cannot be hyphenated'
        if (!hasLocale( aLocale ))
            return null;

        // get values of relevant properties that may be used.
        //! The values for 'IsIgnoreControlCharacters' and 'IsUseDictionaryList'
        //! are handled by the dispatcher! Thus there is no need to access
        //! them here.
        short   nHyphMinLeading             = GetValueToUse( "HyphMinLeading", (short)2, aProperties );
        short   nHyphMinTrailing            = GetValueToUse( "HyphMinTrailing", (short)2, aProperties );
        short   nHyphMinWordLen             = GetValueToUse( "HyphMinWordLength", (short)5, aProperties );

        XHyphenatedWord xRes = null;
        
        if (aWord.length() >= nHyphMinWordLen)
        {
            String  aHyphenatedWord = aWord;
            short   nHyphenationPos = -1;
            short   nHyphenPos = -1;
            //System.out.println("hyphenate called for : " + aWord + " Len: " + aWord.length());
            //System.out.println("MaxLeading: " + nMaxLeading + " Min Leading: " + nHyphMinLeading + " Trailing: " + nHyphMinTrailing);

            if (IsEqual( aLocale, turkishLocale) ){
                int[] heceIndeksleri = ZemberekLibrary.hecele(aWord);
                if(heceIndeksleri != null){
                    if(heceIndeksleri.length > 1){
                        int i = 0;
                        int boy = 0;
                        int eskiBoy = 0;
                        while(i<heceIndeksleri.length && heceIndeksleri[i] < nHyphMinLeading){
                            i++;
                        }
                        int maxLimit = nMaxLeading;
                        if(nMaxLeading > aWord.length() - nHyphMinTrailing ){
                            maxLimit =  aWord.length() - nHyphMinTrailing ;
                        }
                        if(i < heceIndeksleri.length && heceIndeksleri[i] < maxLimit){
                            while(i<heceIndeksleri.length &&  heceIndeksleri[i] < maxLimit ){
                                i++;
                            }
                            if(i < heceIndeksleri.length){
                                //System.out.println("HeceIndeks: " + i + " Boy: " + heceIndeksleri[i]);
                                nHyphenationPos = (short)(heceIndeksleri[i-1]-1);
                                nHyphenPos = nHyphenationPos;
                            }
                            else {
                                nHyphenationPos =  -1;
                                nHyphenPos = -1;
                            }
                        }
                    }
                }
            }
            
            // check if hyphenation pos is valid,
            // a value of -1 indicates that hyphenation is not possible
            if (  nHyphenationPos != -1 &&
                !(nHyphenationPos <  nHyphMinLeading) && 
                !(nHyphenationPos >= aWord.length() - nHyphMinTrailing))
            {
                xRes = new XHyphenatedWord_impl(aWord, aLocale, 
                                nHyphenationPos, aHyphenatedWord, nHyphenPos);
            }
        }
        return xRes;
    }
    
    public XHyphenatedWord queryAlternativeSpelling(
            String aWord, Locale aLocale,
            short nIndex, PropertyValue[] aProperties ) 
        throws com.sun.star.uno.RuntimeException,
               IllegalArgumentException
    {
        if (IsEqual( aLocale, emptyLocale) || aWord.length() == 0)
            return null;

        // linguistic is currently not allowed to throw exceptions
        // thus we return null which means 'word cannot be hyphenated'
        if (!hasLocale( aLocale ))
            return null;

        // get values of relevant properties that may be used.
        //! The values for 'IsIgnoreControlCharacters' and 'IsUseDictionaryList'
        //! are handled by the dispatcher! Thus there is no need to access
        //! them here.
        short   nHyphMinLeading             = GetValueToUse( "HyphMinLeading", (short)2, aProperties );
        short   nHyphMinTrailing            = GetValueToUse( "HyphMinTrailing", (short)2, aProperties );
        short   nHyphMinWordLen             = GetValueToUse( "HyphMinWordLength", (short)5, aProperties );
        XHyphenatedWord xRes = null;
        return xRes;
    }
    
    public XPossibleHyphens createPossibleHyphens(
            String aWord, Locale aLocale,
            PropertyValue[] aProperties ) 
        throws com.sun.star.uno.RuntimeException,
               IllegalArgumentException
    {
        if (IsEqual( aLocale, emptyLocale ) || aWord.length() == 0)
            return null;
        
        // linguistic is currently not allowed to throw exceptions
        // thus we return null which means 'word cannot be hyphenated'
        if (!hasLocale( aLocale ))
            return null;

        // get values of relevant properties that may be used.
        //! The values for 'IsIgnoreControlCharacters' and 'IsUseDictionaryList'
        //! are handled by the dispatcher! Thus there is no need to access
        //! them here.
        short   nHyphMinLeading             = GetValueToUse( "HyphMinLeading", (short)2, aProperties );
        short   nHyphMinTrailing            = GetValueToUse( "HyphMinTrailing", (short)2, aProperties );
        short   nHyphMinWordLen             = GetValueToUse( "HyphMinWordLength", (short)5, aProperties );
        
        XPossibleHyphens xRes = null;

        //System.out.println("createPossibleHyphens called for : " + aWord + " Len: " + aWord.length());
        
        if (IsEqual( aLocale, turkishLocale) ){
            int[] heceIndeksleri = ZemberekLibrary.hecele(aWord);
            if(heceIndeksleri != null){
                if(heceIndeksleri.length == 1){
                    short aPos[] = new short[]{(short) 3};
                    xRes = new XPossibleHyphens_impl(aWord, aLocale, aWord, aPos);                    
                }
                short aPos[] = new short[heceIndeksleri.length];
                for(int i=0; i<heceIndeksleri.length ; i++){
                    aPos[i] = (short)heceIndeksleri[i];
                }
                StringBuffer hypenatedStr = new StringBuffer(aWord.length() + aPos.length );
                for(int i=0; i < aPos.length-1; i++){
                    hypenatedStr.append(aWord.substring(aPos[i], aPos[i+1]));
                    if(i+1 < aPos.length-1)hypenatedStr.append("=");
                    //System.out.println("Hyphenated String : " + hypenatedStr );
                }
                hypenatedStr.append(aWord.substring(aPos[aPos.length-1], aWord.length()));
                short aPos2[] = new short[aPos.length - 1 ];
                for (int i = 1; i < aPos.length; i++) {
                    aPos2[i-1] = (short)(aPos[i]-1); 
                }
                xRes = new XPossibleHyphens_impl(aWord, aLocale,
                        new String(hypenatedStr), aPos2);
            }
        }     
            
        return xRes;
    }
    
    //*****************************
    //XLinguServiceEventBroadcaster
    //*****************************
    public boolean addLinguServiceEventListener (
            XLinguServiceEventListener xLstnr )
        throws com.sun.star.uno.RuntimeException
    {
        boolean bRes = false;   
        if (!bDisposing && xLstnr != null)
            bRes = aPropChgHelper.addLinguServiceEventListener( xLstnr );
        return bRes;
    }
    
    public boolean removeLinguServiceEventListener(
            XLinguServiceEventListener xLstnr ) 
        throws com.sun.star.uno.RuntimeException
    {
        boolean bRes = false;   
        if (!bDisposing && xLstnr != null)
            bRes = aPropChgHelper.removeLinguServiceEventListener( xLstnr );
        return bRes;
    }            

    //********************
    // XServiceDisplayName
    //********************
    public String getServiceDisplayName( Locale aLocale ) 
        throws com.sun.star.uno.RuntimeException
    {
        return "Zemberek Heceleyici (Hyphenator)";                                                    
    }

    //****************
    // XInitialization
    //****************
    public void initialize( Object[] aArguments ) 
        throws com.sun.star.uno.Exception,
               com.sun.star.uno.RuntimeException
    {
        int nLen = aArguments.length;
        if (2 == nLen)
        {
            XPropertySet xPropSet = (XPropertySet)UnoRuntime.queryInterface(
                                         XPropertySet.class, aArguments[0]);
            // start listening to property changes
            aPropChgHelper.AddAsListenerTo( xPropSet );
        }
    }

    
    //*************
    // XServiceInfo
    //*************
    public boolean supportsService( String aServiceName )
        throws com.sun.star.uno.RuntimeException
    {
        String[] aServices = getSupportedServiceNames_Static();
        int i, nLength = aServices.length;
        boolean bResult = false;

        for( i = 0; !bResult && i < nLength; ++i )
            bResult = aServiceName.equals( aServices[ i ] );

        return bResult;
    }

    public String getImplementationName()
        throws com.sun.star.uno.RuntimeException
    {
        return _aSvcImplName;
    }
        
    public String[] getSupportedServiceNames()
        throws com.sun.star.uno.RuntimeException
    {
        return getSupportedServiceNames_Static();
    }
    
    // __________ static things __________

    public static String _aSvcImplName = "net.zemberek.ooo.hyphenator.TurkishHyphenator";
    
    public static String[] getSupportedServiceNames_Static()
    {
        String[] aResult = { "com.sun.star.linguistic2.Hyphenator" };
        return aResult;
    }


    /**
     * Returns a factory for creating the service.
     * This method is called by the <code>JavaLoader</code>
     * <p>
     * @return  returns a <code>XSingleServiceFactory</code> for creating the component
     * @param   implName     the name of the implementation for which a service is desired
     * @param   multiFactory the service manager to be used if needed
     * @param   regKey       the registryKey
     * @see                  com.sun.star.comp.loader.JavaLoader
     */
    public static XSingleServiceFactory __getServiceFactory(
        String aImplName,
        XMultiServiceFactory xMultiFactory,
        com.sun.star.registry.XRegistryKey xRegKey )
    {
        XSingleServiceFactory xSingleServiceFactory = null;
        if( aImplName.equals( _aSvcImplName ) )
        {
            xSingleServiceFactory = new OneInstanceFactory(
                    TurkishHyphenator.class, _aSvcImplName,
                    getSupportedServiceNames_Static(),
                    xMultiFactory );
        }
        return xSingleServiceFactory;
    }

    /**
     * Writes the service information into the given registry key.
     * This method is called by the <code>JavaLoader</code>
     * <p>
     * @return  returns true if the operation succeeded
     * @param   xRegKey       the registryKey
     * @see                  com.sun.star.comp.loader.JavaLoader
     */
    public static boolean __writeRegistryServiceInfo( 
            com.sun.star.registry.XRegistryKey xRegKey )
    {
        boolean bResult = true;
        String[] aServices = getSupportedServiceNames_Static();
        int i, nLength = aServices.length;
        for( i = 0; i < nLength; ++i )
        {
            bResult = bResult && com.sun.star.comp.loader.FactoryHelper.writeRegistryServiceInfo(
                _aSvcImplName, aServices[i], xRegKey );
        }
        return bResult;
    }
}

