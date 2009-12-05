package andreaFolder.enemyAI;

import java.io.Serializable;

import com.jme.math.Vector3f;

public enum Direction implements Serializable{
	
	FORWARD ( Vector3f.UNIT_Z ),
	
	BACKWARD ( Vector3f.UNIT_Z.negate() ),
	
	RIGHT ( Vector3f.UNIT_X.negate() ),
	
	LEFT ( Vector3f.UNIT_X ), 
	
	REST (Vector3f.ZERO );
	
	Vector3f direction;
	
	Direction( Vector3f direction ){
		this.direction = direction;
	}
	
	public Vector3f toVector() {
		return direction;
	}
}