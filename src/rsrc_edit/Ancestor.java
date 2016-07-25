/*
 * Ancestor.java
 *
 */

package rsrc_edit;

import java.util.List;

/**
 *
 *  
 */
public interface Ancestor {
    
    public List<ResourceController> getChildren();    
      
    public void addChild( ResourceController libraryItem );
    
    public void insertChild( ResourceController libraryItem, int index );
    
    public boolean removeChild( ResourceController libraryItem );

    public boolean hasChild( ResourceController libraryItem );

    

}
