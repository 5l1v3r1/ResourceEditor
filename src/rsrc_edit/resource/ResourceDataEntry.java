/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsrc_edit.resource;

import java.nio.ByteBuffer;
import java.util.Stack;
import rsrc_edit.MainJFrame;

/**
 *
 * @author user
 */
public class ResourceDataEntry {

    public static final String NAMED = "Named";
    
    public long DataVirtualAddr;
    public long Size; 
    public int CodePage; 
    public int Reserved; 
    public String Type = "Integer";      
    public Stack<Object> ID_Stack = new Stack<>();
    
    public byte[] data = null;
      
    //=========================================================================
    /**
     * 
     * @param passedBB 
     * @param delta 
     */
    public ResourceDataEntry( ByteBuffer passedBB, long delta ) {   
        
        DataVirtualAddr = passedBB.getInt() + delta;
        Size = passedBB.getInt();
        CodePage = passedBB.getInt();
        Reserved = passedBB.getInt();
                        
    }
    
     //=========================================================================
    /**
     * 
     * @param addr
     * @param size
     * @param stackId
     * @param passed_data
     */
    public ResourceDataEntry( long addr, long size, Object stackId, byte[] passed_data ) {   
        
        DataVirtualAddr = addr;
        Size = size;     
        ID_Stack.push(stackId);
        data = passed_data;
    }
    
    //=========================================================================
    /**
     * 
     * @param passedObj 
     */
    public void addIdToStack( Object passedObj ){
        ID_Stack.push(passedObj);
    }
    
    //=========================================================================
    /**
     * 
     * @return 
     */
    public Stack<Object> getResourceIds() {
        Stack<Object> stackCopy = new Stack<>();
        stackCopy.addAll(ID_Stack);
        return stackCopy;
    }
    
    //=========================================================================
    /**
     * 
     * @return 
     */
    @Override
    public String toString(){
        String id = ID_Stack.peek().toString();
        String retVal = MainJFrame.theResourceMap.get(id);
        if( retVal == null )
            retVal = id;
        return retVal;
    }

    //=========================================================================
    /**
     * 
     * @param passedType 
     */
    public void setType(String passedType) {
        Type = passedType;
    }
    
    //=========================================================================
    /**
     * 
     * @return 
     */
    public String getType() {
        return Type;
    }
    
    
}
