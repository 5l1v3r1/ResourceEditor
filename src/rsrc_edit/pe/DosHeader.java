/*

Copyright (C) 2013-2016, Securifera, Inc 

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

The copyright on this package is held by Securifera, Inc

*/
package rsrc_edit.pe;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author user
 */
public class DosHeader {
    
    public static final short IMAGE_DOS_SIGNATURE  =  0x5A4D;      /* MZ */
    public static final int DOS_HEADER_SIZE = 68;
    
    public short   e_magic;                     // Magic number
    public short   e_cblp;                      // Bytes on last page of file
    public short   e_cp;                        // Pages in file
    public short   e_crlc;                      // Relocations
    public short   e_cparhdr;                   // Size of header in paragraphs
    public short   e_minalloc;                  // Minimum extra paragraphs needed
    public short   e_maxalloc;                  // Maximum extra paragraphs needed
    public short   e_ss;                        // Initial (relative) SS value
    public short   e_sp;                        // Initial SP value
    public short   e_csum;                      // Checksum
    public short   e_ip;                        // Initial IP value
    public short   e_cs;                        // Initial (relative) CS value
    public short   e_lfarlc;                    // File address of relocation table
    public short   e_ovno;                      // Overlay number
    public short[] e_res = new short[4];        // Reserved words
    public short   e_oemid;                     // OEM identifier (for e_oeminfo)
    public short   e_oeminfo;                   // OEM information; e_oemid specific
    public short[] e_res2 = new short[10];      // Reserved words
    public int     e_lfanew;                    // File address of new exe header
    
    public DosHeader( ByteBuffer passedBB ) throws IOException{
        
        //Read all values from header
        e_magic = passedBB.getShort();
        
        //Not a valid file header
        if( e_magic != IMAGE_DOS_SIGNATURE )
            throw new IOException("File does not have a valid DOS header.");
        
        e_cblp  = passedBB.getShort();
        e_cp  = passedBB.getShort();
        e_crlc = passedBB.getShort();
        e_cparhdr = passedBB.getShort();
        e_minalloc = passedBB.getShort();
        e_maxalloc = passedBB.getShort();
        e_ss = passedBB.getShort();
        e_sp = passedBB.getShort();
        e_csum = passedBB.getShort();
        e_ip = passedBB.getShort();
        e_cs = passedBB.getShort();
        e_lfarlc = passedBB.getShort();
        e_ovno = passedBB.getShort();
        
        for( int i =0; i< 4; i++){
            e_res[i] = passedBB.getShort();
        }
        e_oemid = passedBB.getShort();
        e_oeminfo = passedBB.getShort();
        
        for( int i =0; i< 10; i++){
            e_res2[i] = passedBB.getShort();
        }
        e_lfanew = passedBB.getShort();       
        
    }
}
