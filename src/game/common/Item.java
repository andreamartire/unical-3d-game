package game.common;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;

public class Item {
	String fileName;
	Vector3f position;
	Quaternion rotation;
	Vector3f scale;
	Node model;
	
	public Item() {
		this.fileName = "";
		this.position = new Vector3f();
		this.rotation = new Quaternion();
		this.scale = new Vector3f();
	}
	
	public Item( String fileName, Vector3f position, Quaternion rotation, Vector3f scale ) {
		this.fileName = fileName;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public String getFileName() {
		return fileName;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Quaternion getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		return scale;
	}
	
	public Node getModel() {
		return model;
	}
	
	public void setModel( Node model ) {
		this.model = model;
	}
}