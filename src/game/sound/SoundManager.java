/**
 * Copyright (C) 2010 Salvatore Loria, Andrea Martire, Giuseppe Leone
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package game.sound;

import java.net.URL;

import utils.Util;

import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jmex.audio.AudioSystem;
import com.jmex.audio.AudioTrack;
import com.jmex.audio.AudioTrack.TrackType;
import com.jmex.audio.MusicTrackQueue.RepeatType;

/**
 * Class SoundManager
 * <br>
 * The Main class that handles all sound in the game
 * 
 * @author Andrea Martire, Salvatore Loria, Giuseppe Leone
 */
public class SoundManager {
	
	/**
	 * Various sound types 
	 */
	public static enum SoundType {
		DEATH,
		GAME,
		SHOOT,
		EXPLOTION,
		NOAMMO,
		WALK,
		RUN,
		VICTORY,
		ALERTMUSIC,
		ATTACKMUSIC;
	}

	/** audio controller */
	public AudioSystem audio;
	
	/** Audio tracks */
	private static AudioTrack backgroundMusic;
	private static AudioTrack alertMusic;
	private static AudioTrack attackMusic;
	
	private static AudioTrack shoot;
	private static AudioTrack explosion;
	private static AudioTrack death;
	private static AudioTrack noAmmo;
	private static AudioTrack walk;
	private static AudioTrack run;
	private static AudioTrack victory;
	
	/**
	 * Point to camera
	 */
	Camera cam;
	
	public SoundManager( Camera cam ) {
		this.cam = cam;
		
		initSound();
	}
	
	/**
	 * Initialize all the sound in the game
	 */
	public void initSound() {
		audio = AudioSystem.getSystem();

		audio.getEar().trackOrientation(cam);
		audio.getEar().trackPosition(cam);

		backgroundMusic = getMusic( Util.load("game/data/sound/game.ogg"));
		backgroundMusic.setVolume(0.25f);
		audio.getMusicQueue().setRepeatType(RepeatType.ONE);
		audio.getMusicQueue().setCrossfadeinTime(2.5f);
		audio.getMusicQueue().setCrossfadeoutTime(2.5f);
		audio.getMusicQueue().addTrack(backgroundMusic);
		audio.getMusicQueue().play();
		
		shoot = audio.createAudioTrack("/game/data/sound/mp5.ogg", false);
		shoot.setType(TrackType.POSITIONAL);
		shoot.setRelative(true);
		shoot.setMaxAudibleDistance(100000);
		shoot.setVolume(.7f);

		explosion = audio.createAudioTrack("/game/data/sound/explosion.ogg", false);
		explosion.setType(TrackType.POSITIONAL);
		explosion.setRelative(true);
		explosion.setMaxAudibleDistance(100000);
		explosion.setVolume(4.0f);

		death = audio.createAudioTrack("/game/data/sound/death.ogg", false);
		death.setType(TrackType.POSITIONAL);
		death.setRelative(true);
		death.setMaxAudibleDistance(100000);
		death.setVolume(4.0f);
		
		noAmmo = audio.createAudioTrack("/game/data/sound/trigger.ogg", false);
		noAmmo.setType(TrackType.POSITIONAL);
		noAmmo.setRelative(true);
		noAmmo.setMaxAudibleDistance(100000);
		noAmmo.setVolume(2.0f);
		
		walk = audio.createAudioTrack("/game/data/sound/walk.ogg", false);
		walk.setType(TrackType.POSITIONAL);
		walk.setRelative(true);
		walk.setMaxAudibleDistance(100000);
		walk.setVolume(6.0f);
		
		run = audio.createAudioTrack("/game/data/sound/run.ogg", false);
		run.setType(TrackType.POSITIONAL);
		run.setRelative(true);
		run.setMaxAudibleDistance(100000);
		run.setVolume(2.0f);
		
		victory = audio.createAudioTrack("/game/data/sound/victory.ogg", false);
		victory.setType(TrackType.POSITIONAL);
		victory.setRelative(true);
		victory.setMaxAudibleDistance(100000);
		victory.setVolume(2.0f);
		
		alertMusic = audio.createAudioTrack("/game/data/sound/alert.ogg", false);
		alertMusic.setType(TrackType.MUSIC);
		alertMusic.setRelative(true);
		alertMusic.setMaxAudibleDistance(100000);
		alertMusic.setVolume(2.0f);
		
		attackMusic = audio.createAudioTrack("/game/data/sound/attack.ogg", false);
		attackMusic.setType(TrackType.MUSIC);
		attackMusic.setRelative(true);
		attackMusic.setMaxAudibleDistance(100000);
		attackMusic.setVolume(2.0f);

	}
	
	private static AudioTrack getMusic( URL resource ) {
		// Create a non-streaming, non-looping, relative sound clip.
		AudioTrack sound = AudioSystem.getSystem().createAudioTrack( resource, true );
		sound.setType( TrackType.MUSIC );
		sound.setRelative(true);
		sound.setTargetVolume(0.7f);
		sound.setLooping(false);
		return sound;
	}
	
	public static void playSound( SoundType soundType, Vector3f position ) {
		switch (soundType) {
			case DEATH:
				death.setWorldPosition( position );
				death.play();
				break;
			case SHOOT:
				shoot.setWorldPosition( position );
				shoot.play();
				break;
			case EXPLOTION:
				explosion.setWorldPosition( position );
				explosion.play();
				break;
			case NOAMMO:
				noAmmo.setWorldPosition( position );
				noAmmo.play();
				break;
			case WALK:
				if(!walk.isPlaying()){
					walk.setWorldPosition( position );
					walk.play();
				}
				break;
			case RUN:
				if(!run.isPlaying()){
					run.setWorldPosition( position );
					run.play();
				}
				break;
			case VICTORY:
				if(attackMusic.isPlaying())
					attackMusic.stop();
				victory.setWorldPosition( position );
				victory.play();
				break;
			case ALERTMUSIC:
				if(!alertMusic.isPlaying()){
					if(backgroundMusic.isPlaying())
						backgroundMusic.stop();
					if(attackMusic.isPlaying())
						attackMusic.stop();
					alertMusic.play();
				}
				break;
			case ATTACKMUSIC:
				if(!attackMusic.isPlaying()){
					if(backgroundMusic.isPlaying())
						backgroundMusic.stop();
					if(alertMusic.isPlaying())
						alertMusic.stop();
					attackMusic.play();
				}
				break;
		}
	}
	
	public void update() {
		audio.update();
	}

	public void cleanup() {
		shoot.clearTrackStateListeners();
		explosion.clearTrackStateListeners();
		death.clearTrackStateListeners();
		noAmmo.clearTrackStateListeners();
		walk.clearTrackStateListeners();
		run.clearTrackStateListeners();
		victory.clearTrackStateListeners();
		alertMusic.clearTrackStateListeners();
		attackMusic.clearTrackStateListeners();
		audio.cleanup();
	}
}
