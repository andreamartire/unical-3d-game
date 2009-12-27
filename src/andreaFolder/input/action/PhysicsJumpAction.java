/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package andreaFolder.input.action;

import andreaFolder.input.PhysicsInputHandler;
import andreaFolder.test.ThreadController;

import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;

/**
 *
 * @author joseph
 */
public class PhysicsJumpAction extends InputAction {
    PhysicsInputHandler handler;
    
    public PhysicsJumpAction( PhysicsInputHandler handler ) {
        this.handler = handler;
    }

    public void performAction(InputActionEvent evt) {
        handler.getTarget().setRest(false);
        if ( handler.getTarget().getOnGround() == true ) {
            handler.getTarget().setJumping(true);
            handler.getTarget().setOnGround(false);
            handler.getTarget().getCharacterFeet().addForce( handler.getTarget().getJumpVector() );
        }
    }

}