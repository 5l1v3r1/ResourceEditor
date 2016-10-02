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
package rsrc_edit.resource;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class ResourceDirectoryEntry {
    
    public int Name_Int_Id;
    public int Data_Entry_Or_SubDirectory; 
    
    public String ResourceName = "";    
    public ResourceDataEntry aRD = null;
    public ResourceDirectoryTable aRDT = null;
      
    //=========================================================================
    /**
     * 
     * @param passedBB 
     */
    public ResourceDirectoryEntry( ByteBuffer passedBB ) {   
        
        Name_Int_Id = passedBB.getInt();
        Data_Entry_Or_SubDirectory = passedBB.getInt();
                
    }

    //========================================================================
    /**
     * 
     * @param passedBB 
     */
    public void populateLeafNodes(ByteBuffer passedBB, long delta, long write_delta) {
        
        if( Name_Int_Id < 0 ){
            passedBB.position( Name_Int_Id & 0x7fffffff );
            int str_size = passedBB.getShort();
            
            //Get the string
            byte[] name_bytes = new byte[str_size * 2 ];
            passedBB.get(name_bytes);
            
            try {
                ResourceName = new String(name_bytes, "UTF-16LE");
            } catch (UnsupportedEncodingException ex) {
            }
            
        } 
        
        if( Data_Entry_Or_SubDirectory < 0 ){
            
            //Set position
            passedBB.position( Data_Entry_Or_SubDirectory & 0x7fffffff );
            aRDT = new ResourceDirectoryTable(passedBB, delta, write_delta);
            
            
        } else {
            
            //Create Resource data entry
            passedBB.position( Data_Entry_Or_SubDirectory & 0x7fffffff );
            aRD = new ResourceDataEntry(passedBB, delta, write_delta);
            
        }
    }

    //====================================================================
    /**
     * 
     * @return 
     */
    public List<ResourceDataEntry> getResources() {
        List<ResourceDataEntry> aRDE_List = new ArrayList<>();
        ResourceDataEntry retEntry = aRD;
        if( retEntry == null ){
            
            //Add list
            aRDE_List.addAll( aRDT.getResources() );
            
        } else {
                       
            //Add to the list
            aRDE_List.add( retEntry );
        }
        
        //Add an id to the entry
        for( ResourceDataEntry aRD : aRDE_List ){ 
            
            if( !ResourceName.isEmpty() ){
                aRD.addIdToStack(ResourceName);
                aRD.setType( ResourceDataEntry.NAMED );
            } else 
                aRD.addIdToStack(Name_Int_Id);
            
        }
        
        return aRDE_List;
    }
}
