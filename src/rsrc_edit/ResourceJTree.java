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

/*
 * ResourceJTree.java
 *
 */

package rsrc_edit;

import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
/**
 *
 *  
 */
public class ResourceJTree extends SearchableJTree {
    
   
    // =======================================================================
    /**
    * Adds {@code theObjectToAdd} as a child node under {@code parentNode} at the specified {@code index}.
    *
    * @param theObjectToAdd  the {@link XmlBase} object to be added to the tree
    * @param parentNode      the node under which this object will be added ({@code null}
    *                        to set {@code theObjectToAdd} as the root of the tree)
    * @param index           the child index of {@code parentNode} at which to add {@code theNodeToAdd},
    *                        if {@code null}, it will be added as the last child of {@code parentNode}
     * @return 
    *
    */
    public DefaultMutableTreeNode addObjectToTree( Object theObjectToAdd, DefaultMutableTreeNode parentNode, Integer index ) {

        DefaultMutableTreeNode theNodeToAdd = new DefaultMutableTreeNode( theObjectToAdd );

        if( theObjectToAdd instanceof ResourceController ){
            theNodeToAdd = new IconNode( theObjectToAdd, theObjectToAdd instanceof Ancestor );
        }

        if ( index == -1 ) {
            //Add the node at the end of the children of the parentNode
            index = parentNode.getChildCount();
        }

        ((DefaultTreeModel)getModel()).insertNodeInto( theNodeToAdd, parentNode, index );
       
        //Add the children of the node
        if( theObjectToAdd instanceof Ancestor ){
            
            Ancestor anAncestor = (Ancestor)theObjectToAdd;
            List<ResourceController> aList = anAncestor.getChildren();
            
            //Loop through the children
            for( ResourceController aChild : aList){
                addObjectToTree( aChild, theNodeToAdd, -1);
            }
        }
        
        
        return theNodeToAdd;

    }
    
     /**
     * Returns the <code>TreeModel</code> that is providing the data.
     *
     * @return the <code>TreeModel</code> that is providing the data
     */
    @Override
    public ResourceJTreeModel getModel() {
        return (ResourceJTreeModel)treeModel;
    }

}
