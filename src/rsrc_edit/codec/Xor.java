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
public class Xor extends Codec{
    
    public byte[] key = null;

    //===================================================================
    /**
     * 
     */
    public Xor() {
        super(Codec.XOR);
    }  

    //===================================================================
    /**
     * 
     * @return 
     */
    public byte[] getKey() {
        return key;
    }

    //===================================================================
    /**
     * 
     * @param passedHexStr  
     */
    public void setKey(String passedHexStr ) {
        
        String s = passedHexStr.replace("0x", "");
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }        
        
        key = data;
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
        
       
        
        return retBytes;
    }

    @Override
    public byte[] decode(byte[] passedBytes) {
        
        byte[] retBytes = new byte[passedBytes.length / 2];
        
       
        
        return retBytes;
    }
    
}
