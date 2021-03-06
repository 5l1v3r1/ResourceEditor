/*

Copyright (C) 2013-2014, Securifera, Inc 

All rights reserved. 

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
	this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright notice,
	this list of conditions and the following disclaimer in the documentation 
	and/or other materials provided with the distribution.

    * Neither the name of Securifera, Inc nor the names of its contributors may be 
	used to endorse or promote products derived from this software without specific
	prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS 
OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY 
AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER 
OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

================================================================================

Pwnbrew is provided under the 3-clause BSD license above.

The copyright on this package is held by Securifera, Inc

*/

package rsrc_edit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 **
 */
public class StandardValidation {

    //Hex or decimal validation
    private static final String REGEX_Hex_Decimal = "^([\\d]|0x)\\p{XDigit}*$";
   
    //StandardValidation keywords...
    public static final String KEYWORD_DECIMAL_OR_HEX = "hex";
    //
    private static final List<String> KEYWORD_LIST;
    static {
        List<String> tempList = new ArrayList<>();
        tempList.add( KEYWORD_DECIMAL_OR_HEX );
        KEYWORD_LIST = Collections.unmodifiableList( tempList );
    }

    //Linkage of validation keywords to methods...
    private static final HashMap<String, String> theKeywordToMethodNameMap = new HashMap<>();
    static {
        theKeywordToMethodNameMap.put( KEYWORD_DECIMAL_OR_HEX, "validateHexStr" );
    }

    // ==========================================================================
    /**
     * Translates the given String to a validation keyword.
     * <p>
     * The process for looking up the appropriate validation method to invoke is
     * sensitive to the cases (upper or lower) of the characters in the validation
     * keywords. This method will translate the given String (assuming it is a variant
     * of one of the validation keywords) into the equivalent keyword that can be
     * used in the method look-up process.
     * <p>
     * If the argument is null this method returns null.
     *
     * @param string the validation keyword variation to translate
     *
     * @return the validation keyword equal to (ignoring case) the given String;
     * null if there is no such keyword
     */
    public static String translateToKeyword( String string ) {

        String rtnStr = null;

        for( String keyword : KEYWORD_LIST ) 
            if( keyword.equalsIgnoreCase( string ) ) { 
                rtnStr = keyword; 
                break; 
            }

        return rtnStr;

    }/* END translateToKeyword( String ) */


    // ==========================================================================
    /**
     * Determines if the given value is valid according to the validation identified
     * by the given keyword.
     * <p>
     * If the {@code value} argument is null this method returns false. Null is
     * never valid, even for parameters that have no validation.
     * 
     * @param keyword the keyword identifying the validation to use
     * @param value the value to validate
     *
     * @return {@code true} if the value is valid according to the specified validation;
     * {@code false} otherwise
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    public static boolean validate( String keyword, String value ) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        if( value == null ) //If the "value" String is null...
            return false; //Do nothing / Null is never valid

        boolean rtnBool = false;

        String methodName = theKeywordToMethodNameMap.get( translateToKeyword( keyword ) ); //Get the method name mapped to the keyword
        if( methodName != null ) { //If a method name was obtained...

            //Create a list of the parameter types
            Class[] parameterTypes = new Class[]{ String.class };

            //Get the Method with the name and the list of parameter types...
            Method validationMethod = null;
            validationMethod = StandardValidation.class.getMethod( methodName, parameterTypes );
            

            if( validationMethod != null )
                rtnBool = (Boolean)validationMethod.invoke( null, value );              

        } 

        return rtnBool;

    }/* END validate( String, String ) */
        
    // ==========================================================================
    /**
     * Determines if the given String is a valid IPv4 address in the dotted-decimal
     * notation. (ie. "172.16.254.1:80:80")
     * <p>
     * If the argument is null this method returns false.
     *
     * @param address the String to test
     *
     * @return {@code true} if the given String is a valid IPv4 address in the dotted-decimal
     * notation; {@code false} otherwise
     */
    public static boolean validateHexStr( String address ) {

        if( address == null ) 
            return false; 

        return address.matches( REGEX_Hex_Decimal ); 

    }


}/* END CLASS StandardValidation */
