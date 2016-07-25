/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsrc_edit.pe;

import java.nio.ByteBuffer;

/**
 *
 * @author user
 */
public class ImageDataDirectory {
    
    //
    public long VirtualAddress;
    public long Size;
    
    public ImageDataDirectory( ByteBuffer passedBB ) {        
        VirtualAddress = passedBB.getInt() & 0xffffffff;
        Size = passedBB.getInt() & 0xffffffff;
    }
    
}
