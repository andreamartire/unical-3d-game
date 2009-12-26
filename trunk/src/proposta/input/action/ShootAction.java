package proposta.input.action;

import proposta.input.ThirdPersonHandler;

import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;

public class ShootAction extends MouseInputAction {
	
	ThirdPersonHandler handler;
    
    public ShootAction( ThirdPersonHandler handler ) {
        this.handler = handler;
    }

    public void performAction(InputActionEvent evt) {
    	if ( handler.isFirstPerson() && evt.getTriggerPressed() ) {
    		handler.setShooting( true );
    	} else {
    		handler.setShooting( false );
    	}
    }
}