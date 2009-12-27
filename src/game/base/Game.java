package game.base;

import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;

import game.base.PhysicsGame;
import game.menu.LoadingFrame;

public abstract class Game extends PhysicsGame {
	
	public InputHandler freeCamInput;
	public LoadingFrame loadingFrame;
	
    @Override
    protected void setupGame() {
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
        
        freeCamInput = new FirstPersonHandler( cam, 100, 1 );
        freeCamInput.setEnabled( false );
        loadingFrame.setVisible(false);
    }
    
    public abstract void setupInit();
    
    public abstract void setupEnvironment();
    
    public abstract void setupPlayer();
    
    public abstract void setupEnemies();
    
    public abstract void setupCamera();
    
    public abstract void setupInput();
}