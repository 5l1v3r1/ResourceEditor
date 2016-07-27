/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
