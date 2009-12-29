package game.main;

import java.util.logging.Level;

import game.base.PhysicsGame;
import game.core.LogicWorld;
import game.graphics.GraphicalWorld;
import game.menu.LoadingFrame;

public class GameThread implements Runnable {

	public LogicWorld logicGame;
	public GraphicalWorld game;
	public LoadingFrame loadingFrame;
	
	public GameThread( LoadingFrame loadingFrame ) {
		this.loadingFrame = loadingFrame;
		logicGame = new LogicWorld();
    	logicGame.createPlayer( 100, -1000, -1000 );
//    	logicGame.createEnemy( -900, -900, MovementType.REST );
    	logicGame.createEnemiesGroup( 10, -700, -700 );
    	logicGame.createEnemiesGroup( 10, 100, 100 );
//    	logicGame.createEnemiesGroup( 20, 500, 500 );
//    	logicGame.createEnemiesGroup( 10, 400, 400 );
//    	logicGame.createEnemiesGroup( 10, 300, 300 );
//    	logicGame.createEnemiesGroup( 100, 0, 0 );
    	
//    	logicGame.createEnergyPackages( 20, 129*20, 129*20 );
    	
    	logicGame.initScoreManager();
	}
	
	public GameThread( LogicWorld logicGame, LoadingFrame loadingFrame ){
		this.loadingFrame = loadingFrame;
		this.logicGame = logicGame;
	}

	public void run() {
		game = new GraphicalWorld( logicGame, loadingFrame );
		PhysicsGame.logger.setLevel( Level.SEVERE );
        game.start();
    }
	
	public void quit() {
		game.finish();
	}
}
