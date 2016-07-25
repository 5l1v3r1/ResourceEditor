
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
