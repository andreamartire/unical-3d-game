/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package andreaFolder.input.action;

import andreaFolder.input.PhysicsInputHandler;

import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.math.Vector3f;

/**
 *
 * @author joseph
 */
public class PhysicsForwardAction extends InputAction {
    PhysicsInputHandler handler;
    Vector3f direction;

    public PhysicsForwardAction( PhysicsInputHandler handler ) {
        this.handler = handler;
    }

    public void performAction(InputActionEvent evt) {
        handler.getTarget().setRest(false);
        handler.getTarget().setMovingForward(true);
        direction = handler.getCamera().getDirection();
        handler.getTarget().move( direction );
        
    }
}
