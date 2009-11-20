package slashWork.game.input.action;

import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.math.Vector3f;
import slashWork.game.input.PhysicsInputHandler;

/**
 *
 * @author joseph
 */
public class PhysicsStrafeLeftAction extends InputAction {
    PhysicsInputHandler handler;
    Vector3f direction;

    public PhysicsStrafeLeftAction( PhysicsInputHandler handler, float speed ) {
        this.handler = handler;
        this.handler.getTarget().setSpeed(speed);
        this.direction = new Vector3f();
    }

    public void performAction(InputActionEvent evt) {
    	if( evt.getTriggerPressed() ) {
    		direction.set( handler.getCamera().getDirection().crossLocal( Vector3f.UNIT_Y ).negateLocal() );
	        handler.getTarget().setRest(false);
	        handler.getTarget().setStrafingLeft(true);
	        handler.getTarget().move( direction );
    	} else {
    		System.out.println("FUCK");
        	direction.set( Vector3f.ZERO );
    	}
    }
}