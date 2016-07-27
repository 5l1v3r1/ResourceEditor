/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsrc_edit.codec;

/**
 *
 * @author user
 */
public class ByteSpacer extends Codec{

    //===================================================================
    /**
     * 
     */
    public ByteSpacer() {
        super("Byte Spacer");
    }  

    //===================================================================
    /**
     * 
     * @param passedBytes
     * @return 
     */
    @Override
    public byte[] encode(byte[] passedBytes) {
        byte[] retBytes = new byte[passedBytes.length * 2];
        
        byte lw_Byte;
        byte hi_Byte;
        
        for( int i = 0,j=0; i < retBytes.length; i+=2,j++ ){
            byte aByte = passedBytes[j];
            lw_Byte = (byte) (aByte & 0xf);
            hi_Byte = (byte) ((aByte & 0xf0)>> 4 );
            retBytes[i] = hi_Byte;
            retBytes[i+1] = lw_Byte;            
        }
        
        return retBytes;
    }

    @Override
    public byte[] decode(byte[] passedBytes) {
        
        byte[] retBytes = new byte[passedBytes.length / 2];
        
        byte lw_Byte;
        byte hi_Byte;
        
        //Make it even
        int inputBufLen = passedBytes.length;
        inputBufLen -= inputBufLen % 2;
        
        for( int i = 0,j=0; i < inputBufLen; i+=2,j++ ){
            
            hi_Byte = passedBytes[i];
            lw_Byte = passedBytes[i+1];
                    
            retBytes[j] = (byte)((hi_Byte << 4) + lw_Byte);           
        }
        
        return retBytes;
    }
    
}
