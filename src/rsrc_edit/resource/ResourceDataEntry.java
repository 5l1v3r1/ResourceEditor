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
    public long Embedded_Write_Delta;
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
     * @param write_delta 
     */
    public ResourceDataEntry( ByteBuffer passedBB, long delta, long write_delta ) {   
        
        DataVirtualAddr = passedBB.getInt() + delta;
        Size = passedBB.getInt();
        CodePage = passedBB.getInt();
        Reserved = passedBB.getInt();
        Embedded_Write_Delta = write_delta;
                        
    }
    
     //=========================================================================
    /**
     * 
     * @param addr
     * @param size
     * @param stackId
     * @param passed_data
     * @param write_delta
     */
    public ResourceDataEntry( long addr, long write_delta, long size, Object stackId, byte[] passed_data ) {   
        
        DataVirtualAddr = addr;
        Size = size;     
        ID_Stack.push(stackId);
        data = passed_data;
        Embedded_Write_Delta = write_delta;
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
