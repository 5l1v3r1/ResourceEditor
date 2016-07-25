/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsrc_edit.pe;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author user
 */
public class NtHeader {
    
    public static final int IMAGE_NT_SIGNATURE = 0x00004550;  /* PE00 */
    
    public int Signature;
    public ImageFileHeader FileHeader;
    public ImageOptionalHeader OptionalHeader;
    
    public NtHeader( ByteBuffer passedBB ) throws IOException{
        
        //Read all values from header
        Signature = passedBB.getInt();
        
        //Not a valid file header
        if( Signature != IMAGE_NT_SIGNATURE )
            throw new IOException("File does not have a valid NT header.");
        
        //Read all values from header
        FileHeader = new ImageFileHeader(passedBB);
        
        //Read optional header
        switch (FileHeader.Machine) {            
            case ImageFileHeader.IMAGE_FILE_MACHINE_I386:
                OptionalHeader = new ImageOptionalHeader32(passedBB);
                break;
            case ImageFileHeader.IMAGE_FILE_MACHINE_AMD64:
                OptionalHeader = new ImageOptionalHeader64(passedBB);
                break;
            default:
                throw new IOException("Unsupported image file header.");
        }
    }
    
    public boolean isX86(){
        return OptionalHeader instanceof ImageOptionalHeader32;
    }
    
    public boolean isX64(){
        return OptionalHeader instanceof ImageOptionalHeader64;
    }

    //=========================================================================
    /**
     * 
     * @return 
     */
    public long size() {
        return 4 + ImageFileHeader.IMAGE_FILE_HEADER_SIZE + OptionalHeader.size();
    }
    
}
