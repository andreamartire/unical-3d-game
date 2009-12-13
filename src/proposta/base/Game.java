package proposta.base;

import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;

import proposta.base.PhysicsGame;

public abstract class Game extends PhysicsGame {
	
	public InputHandler freeCamInput;
	
    @Override
    protected void setupGame() {
        setupInit();
        setupEnvironment();
        setupPlayer();
        setupEnemies();
        setupCamera();
        setupInput();
        
        freeCamInput = new FirstPersonHandler( cam, 200, 1 );
        freeCamInput.setEnabled( false );
    }
    
    public abstract void setupInit();
    
    public abstract void setupEnvironment();
    
    public abstract void setupPlayer();
    
    public abstract void setupEnemies();
    
    public abstract void setupCamera();
    
    public abstract void setupInput();
}
