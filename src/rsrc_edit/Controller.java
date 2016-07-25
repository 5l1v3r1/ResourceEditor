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
    
    protected boolean headless = true;
    
    //========================================================================
    /**
     * 
     * @param passedBool 
     */
    public Controller( boolean passedBool ){
        headless = passedBool;
    }
    
     //=========================================================================
    /**
     * 
     * @return 
     */
    public boolean isHeadless(){
        return headless;
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
