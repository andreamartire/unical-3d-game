package lowDetailGame.base;

import lowDetailGame.base.PhysicsGame;
import lowDetailGame.menu.LoadingFrame;

import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;


public abstract class Game extends PhysicsGame {
	
	public InputHandler freeCamInput;
	public LoadingFrame loadingFrame;
	
    @Override
    protected void setupGame() {
    	if( loadingFrame != null ) {
	    	loadingFrame.setProgress(20);
	        setupInit();
	        loadingFrame.setProgress(40);
	        setupEnvironment();
	        loadingFrame.setProgress(60);
	        setupPlayer();
	        loadingFrame.setProgress(80);
	        setupEnemies();
	        loadingFrame.setProgress(100);
	        setupCamera();
	        setupInput();
	        setupCollisionHandler();
	        loadingFrame.setVisible(false);
    	} else {
	        setupInit();
	        setupEnvironment();
	        setupPlayer();
	        setupEnemies();
	        setupCamera();
	        setupInput();
	        setupCollisionHandler();
    	}
        
        freeCamInput = new FirstPersonHandler( cam, 100, 1 );
        freeCamInput.setEnabled( false );
        
    }

	public abstract void setupInit();
    
    public abstract void setupEnvironment();
    
    public abstract void setupPlayer();
    
    public abstract void setupEnemies();
    
    public abstract void setupCamera();
    
    public abstract void setupInput();
    
    public abstract void setupCollisionHandler();
}
