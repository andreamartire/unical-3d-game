package game.graphics;

import java.util.Iterator;

import game.common.WeaponType;

import com.jme.input.InputHandler;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.input.util.SyntheticButton;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Sphere;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.contact.ContactInfo;
import com.jmex.physics.material.Material;

public class Bullet {
	/** the bullet physicsNode */
	DynamicPhysicsNode physicsBullet;
	
	/** an freeCamInput handler used to detect bullet's collisions */
	InputHandler contactDetect = new InputHandler();
	
	/** the graphical world witch contains the bullet */
	GraphicalWorld world;
	
	/** the initial position of the bullet */
	Vector3f position;
	
	/** the type of the weapon witch shoot the bullet */
	WeaponType weaponType;
	
	/** the id of the character who shoot this bullet */
	String shooterId;
	
	boolean enabled;
	
	/** PhysicsBullet constructor <br>
	 * 
	 * @param shooterId - (String) the id of the character who shoot this bullet
	 * @param world - (GraphicalWorld) the graphical world witch contains the bullet
	 * @param direction - (Vector3d) the direction of the shoot 
	 * @param weaponType - (WeaponType) the type of the weapon witch shoot the bullet
	 * @param position - (Vector3f) the position where to create the bullet
	 */
	public Bullet( String shooterId, GraphicalWorld world, WeaponType weaponType, Vector3f position ) {
		this.shooterId = shooterId;
		this.world = world;
		this.position = new Vector3f( position );
		this.weaponType = weaponType;
		this.enabled = true;
	}
	
	public void shoot( Vector3f direction ) {
		physicsBullet = world.getPhysicsSpace().createDynamicNode();
		physicsBullet.setMaterial( Material.GHOST );
		world.getRootNode().attachChild( physicsBullet );
		physicsBullet.getLocalTranslation().set( position );
		
		Sphere s = new Sphere( "bullet", 10, 10, 0.05f );
		physicsBullet.attachChild( s );
		physicsBullet.generatePhysicsGeometry();
		s.lockBounds();
		s.lockMeshes();
		s.lockShadows();
		physicsBullet.addForce( direction.mult( weaponType.getPower() ) );
		
		contactDetection();
	}
	
	/** Function used to detect a contact between the bullet and something
	 *  when a contact is detected, the bullet is detached from the rootnode
	 */
	void contactDetection() {
        SyntheticButton collisionHandler = physicsBullet.getCollisionEventHandler();
        
        InputAction collisionAction = new InputAction() {

			public void performAction( InputActionEvent evt ) {
        		ContactInfo contactInfo = (ContactInfo) evt.getTriggerData();
        		physicsBullet.removeFromParent();
        		physicsBullet.delete();
        		enabled = false;
        		/** Control if the bullet hit a character */
        		Iterator<GraphicalCharacter> it = world.characters.values().iterator();
        		while( it.hasNext() ) {
        			GraphicalCharacter character = it.next();
    				if( ( contactInfo.getNode1() == character.getCharacterBody() ||
    					  contactInfo.getNode2() == character.getCharacterBody() ) ||
    					( contactInfo.getNode1() == character.getCharacterFeet() ||
    					  contactInfo.getNode2() == character.getCharacterFeet() ) ){

    					world.getCore().shooted( character.id, shooterId, weaponType.getDamage() );
    				}
        		}
        	}
        };
        
        /** the action to do when a collision is detected */
        contactDetect.addAction( collisionAction, collisionHandler, false );
    }
	
	/** ContactDetect update
	 * @param time
	 */
	public void update( float time ) {
		contactDetect.update(time);
	}
}
