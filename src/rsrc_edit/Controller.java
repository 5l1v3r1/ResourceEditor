/*
* Controller.java
*
*/

package rsrc_edit;

import javax.swing.JPanel;

/**
 *
 * 
 */
public abstract class Controller {
    
    
    //========================================================================
    /**
     * 
     * @param passedBool 
     */
    public Controller(){
    }
    
    //=========================================================================
    /**
    * 
    * @return 
    */
    abstract public Object getObject();

    //=========================================================================
    /**
    * 
    * @param passedObj 
    */
    abstract public void setObject( Object passedObj);

    //=========================================================================
    /**
    * 
    * @return 
    */
    abstract public JPanel getRootPanel();

    //=========================================================================
    /**
    * 
    */
    abstract public void updateComponents();

}
