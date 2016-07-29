/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsrc_edit;

import rsrc_edit.codec.Codec;

/**
 *
 * @author user
 */
public interface Codecable {
    
    //========================================================================
    /**
     * 
     * @param passedCodec 
     */
    public void encodingChanged( Codec passedCodec );
}
