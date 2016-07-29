/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsrc_edit.codec;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class Codec {

    protected String name = "None";
    private static List<Codec> codecList = new ArrayList<>();
    
    public static final String BYTE_SPACER = "Byte Spacer";
    public static final String XOR = "XOR";
   
    //========================================================================
    /*
    */
    public static List<Codec> getCodecs() {
        
        //Is the list empty
        if( codecList.isEmpty() ){
            codecList.add( new Codec() );
            codecList.add( new ByteSpacer() );
            codecList.add( new Xor() );
        }
        
        return codecList;
        
    }
    
    //========================================================================
    /*
    
    */
    public static Codec getCodec(String codecStr ) {
        Codec retCodec = null;
        
        for( Codec aCodec: codecList ){
            if( aCodec.name.equals(codecStr) ){
                retCodec = aCodec;
                break;
            }
        }
        return retCodec;
    }
    
    //========================================================================
    /**
     * 
     * @param codecName 
     */
    public Codec( String codecName ){
        name = codecName;
    }

    //========================================================================
    /**
     * 
     */
    public Codec() {
    
    }
    
    //=========================================================================
    /**
    * 
     * @param passedBytes
    * @return 
    */
    public byte[] encode( byte[] passedBytes ){
        return passedBytes;
    }
    
    //=========================================================================
    /**
    * 
     * @param passedBytes
    * @return 
    */
    public byte[] decode( byte[] passedBytes ){
        return passedBytes;
    }
    
    //========================================================================
    /*
    */
    @Override
    public String toString(){
        return name;
    }
}
