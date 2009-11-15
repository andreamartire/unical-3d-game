package game.graphics;

import com.jme.input.InputHandler;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.input.util.SyntheticButton;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jmex.model.animation.JointController;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.RotationalJointAxis;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.contact.ContactInfo;
import com.jmex.physics.contact.MutableContactInfo;
import com.jmex.physics.geometry.PhysicsCapsule;
import com.jmex.physics.geometry.PhysicsSphere;
import com.jmex.physics.material.Material;

public class PhysicsCharacter {

	/** the character identifier */
	String id;
	
	/** the physics character material */
	Material characterMaterial;
  
    /** the main node of the character */
    Node characterNode;
    
    /** The feet of the character: a Sphere that permit the movements by rotating 
     * (rotation controlled by a RotationalJointAxis) */
	DynamicPhysicsNode feet;

	/** The body of the character: a Capsule, placed upon the feet, that contains the model */
    DynamicPhysicsNode body; 
    
    /** 3d model applied to the character */
    Node model;
    
	/** animation controller */
	JointController animationController;
	
	/** the graphical world in whitch the character live */
    GraphicalWorld world;
    
    /** the ground on whitch the character moves */
    StaticPhysicsNode ground;
    
    /** the joint that connect the feet to the body */
    Joint feetToBodyJoint;
    
    /** the joint that permit the rotation of the feet sphere */
    RotationalJointAxis rotationalAxis;

    /** movements speed */
    float speed;
    
    /** physics character mass */
    float mass;

    /** the current movement's direction */
    Vector3f moveDirection;
    
    /** the force applied to the character to jump */
    Vector3f jumpVector;
    
    /** an util handler that detect the contact between the character and the ground */
    InputHandler contactDetect = new InputHandler();
    
    /** utilitly quaternion */
    Quaternion quaternion;
    
    
    /** PhysicsCharacter constructor <br>
     * Create a new character affected by physics. 
     * 
     * @param id - (String) the character's identifier
     * @param world - (GraphicalWorld) the graphical world in whitch the character live
     * @param speed - (float) character's movements speed
     * @param mass - (float) the mass of the character
     * @param model - (Node) the model to apply to the character
     */
    public PhysicsCharacter( String id, GraphicalWorld world, float speed, float mass, Node model ) {
        
    	this.id = id;
    	
    	this.world = world;
    	this.ground = world.getGround();
        
        characterNode = new Node("character node");
        body = world.getPhysicsSpace().createDynamicNode();
        feet = world.getPhysicsSpace().createDynamicNode();
        feetToBodyJoint = world.getPhysicsSpace().createJoint();
        rotationalAxis = feetToBodyJoint.createRotationalAxis();

        this.moveDirection = new Vector3f( 0, 1, 0 );
        this.speed = speed;
        this.mass = mass;
        this.model = model;
        
        characterMaterial = new Material("Character Material");
        characterMaterial.setDensity(100f);
        MutableContactInfo contactDetails = new MutableContactInfo();
        contactDetails.setBounce(0);
        contactDetails.setMu( 100 );
        contactDetails.setMuOrthogonal( 10 );
        contactDetails.setDampingCoefficient(0);
        characterMaterial.putContactHandlingDetails( Material.DEFAULT, contactDetails );

        quaternion = new Quaternion();
        
        this.rest();

        createPhysics();
        contactDetection();
    }

    /** Function <code>createPhysics</code> <p>
	 * 
	 * Create the physics geometry of a character: <br>
	 *  - <i>feet</i> node, a Sphere used for the movements (by rotation) <br>
	 *  - <i>body</i> node, a Capsule placed upon the feet that contains the character model <br>
	 *  - <i>rotationalAxis</i>, the axis that permit the movement (by rotating the sphere) <br>
	 *  <p>
	 *  Also initialize animation controller and set the <i>"idle"</i> animation
	 */
	void createPhysics() {
	
	    // Create the feet
	    PhysicsSphere feetGeometry = feet.createSphere("feet geometry");
	    feetGeometry.setLocalScale(2);
	    feet.setMaterial(characterMaterial);
	    feet.computeMass();
	
	    // Append feet to main Character Node
	    characterNode.attachChild(feet);
	
	    // Create the body
	    PhysicsCapsule bodyGeometry = body.createCapsule("body geometry");
	    bodyGeometry.setLocalScale(2);
	    bodyGeometry.setLocalTranslation(0,5,0);
	    // Set UP the orientation of the Body
	    quaternion.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_X);
	    bodyGeometry.setLocalRotation(quaternion);
	    // Setting up the body
	    body.setAffectedByGravity(false);
	    body.computeMass();
	    body.attachChild( model );
	
	    // Append body to main Character Node
	    characterNode.attachChild(body);
	
	    // Create the joint
	    rotationalAxis.setDirection(moveDirection);
	    feetToBodyJoint.attach(body, feet);
	    rotationalAxis.setRelativeToSecondObject(true);
	    rotationalAxis.setAvailableAcceleration(0f);
	    rotationalAxis.setDesiredVelocity(0f);
	
	    // Set default mass
	    feet.setMass(mass);
	
	    // Set the jump direction
	    jumpVector = new Vector3f(0, mass*400, 0);
	    
	    /** initialize the animation */ 
		animationController = (JointController) model.getController(0);
		// set the start and end frame to execute
		// the initial animation is "idle"
		animationController.setTimes( 327, 360 );
		// set the repeate type of the animation
		animationController.setRepeatType( JointController.RT_CYCLE );
		// activate the animation 
		animationController.setActive(true);
		// set the speed of the animation
		animationController.setSpeed( 0.25f );
	}

	/** Function <code>move</code> <br>
	 * Move the character in this direction (only if the character is on the ground)
	 * 
	 * @param direction - (Vector3f) the direction of the character's movement
	 */
	public void move( Vector3f direction ) {
	    moveDirection.set( direction );
	    
	    if( world.getCore().getCharacterOnGround( id ) ) {
	        if( moveDirection != Vector3f.ZERO ){
	            try{
	            	// the rotational axis is orthogonal to the direction and
	            	// to the Y axis. It's calculated using cross product
	                rotationalAxis.setDirection( moveDirection.cross( Vector3f.UNIT_Y ) );
	                rotationalAxis.setAvailableAcceleration( 15*speed );
	                rotationalAxis.setDesiredVelocity( speed );
	            } catch( Exception e ) {
	                rotationalAxis.setDesiredVelocity(0f);
	            }
	        }
	    }
	}

	/** Function <code>update</code> <br>
	 * Update the physics character 
	 * @param time
	 */
	public void update( float time ) {
	    this.rest();
		
		preventFall();
	
	    contactDetect.update(time);
	    body.rest();
	    
	    // update core
	    world.getCore().setCharacterPosition( id, feet.getWorldTranslation() );
	}

	/** Function <code>preventFall</code> <br>
	 *  prevent the falling of the character body due to physics
	 */
	public void preventFall() {
	    quaternion = body.getLocalRotation();
	    Vector3f[] axes = new Vector3f[3];
	    quaternion.toAxes(axes);
	
	    quaternion.fromAxes(axes[0], Vector3f.UNIT_Y, axes[2]);
	    body.setLocalRotation(quaternion);
	    body.setAngularVelocity(Vector3f.ZERO);
	    body.updateWorldVectors();
	}

	/** Function <code>contactDetection</code> <p>
     * Detect when the player collide to the ground, and set the control variable onGround to true
     */
	void contactDetection() {
        SyntheticButton playerCollisionEventHandler = feet.getCollisionEventHandler();
        contactDetect.addAction( new InputAction() {
            public void performAction( InputActionEvent evt ) {
                ContactInfo contactInfo = (ContactInfo) evt.getTriggerData();
                if ( contactInfo.getNode1() == ground || contactInfo.getNode2() == ground ) {
                    world.getCore().setCharacterOnGround( id, true );
                }
            }
        }, playerCollisionEventHandler, false );
    }

    /** Function <code>rest</code> <p>
	 * Set the character to the rest status
	 */
	private void rest() {
		world.getCore().characterRest(id);
	}

	/** Function <code>clearDynamics</code> <br>
     * Reset all dynamics of the physics character
     * and set him to rest (with the rest animation) 
     */
    public void clearDynamics() {
        feet.clearDynamics();
        rotationalAxis.setDesiredVelocity(0);
        rotationalAxis.setAvailableAcceleration(0);
        
        body.clearDynamics();
        
    	if( animationController.getMaxTime() != (360 / animationController.FPS) ) {
    		// activate the animation "idle"
    		animationController.setTimes( 327, 360 );
    	}
    }

    /** Function <code>getCharacterNode</code> <br>
     *  Return the main Node of the physics character
     * 
     * @return the main Node of the physics character
     */
    public Node getCharacterNode() {
        return characterNode;
    }

    /** Function <code>getCharacterBody</code> <br>
     *  Return the body Node of the physics character
     * 
     * @return the body Node of the physics character (a capsule)
     */
    public DynamicPhysicsNode getCharacterBody() {
        return body;
    }

    /** Function <code>getCharacterFeet</code> <br>
     *  Return the feet Node of the physics character 
     * 
     * @return the feet Node of the physics character (a sphere)
     */
    public DynamicPhysicsNode getCharacterFeet() {
        return feet;
    }

	/** Function <code>getModel</code> <p>
	 * 
	 * @return (float) the character's movements speed
	 */
    public float getSpeed() {
        return speed;
    }
    
	/** Function <code>setSpeed</code> <br>
	 * Set the character's movements speed
	 * @param speed - (Float) the movement speed
	 */
    public void setSpeed( float speed ) {
        this.speed = speed;
    }

    /** Function <code>getJumpVector</code> <br>
     * 
     * @return (Vector3f) the jump vector
     */
    public Vector3f getJumpVector() {
        return jumpVector;
    }

	/** Function <code>getModel</code> <br>
	 * 
	 * @return (Node) the character 3d model
	 */
    public Node getModel() {
        return model;
    }

	/** Function <code>setModel</code> <br>
	 * Apply this model to the character
	 * 
	 * @param model - (Node) the model to apply to the character
	 */
    public void setModel( Node model ) {
        this.model = model;
    }

	/** Function <code>getOnGround</code> <p>
	 * 
	 * @return <b>true</b> if the character is in rest status
	 */
	public boolean getRest() {
		return world.getCore().getCharacterRest(id);
	}

	/** Function <code>getOnGround</code> <br>
	 * 
	 * @return <b>true</b> if the character is on the ground
	 */
	public boolean getOnGround() {
		return world.getCore().getCharacterOnGround(id);
	}
	
	/** Function <code>getAnimationController</code>
	 * 
	 * @return the animation controller
	 */
    public JointController getAnimationController() {
		return animationController;
	}
	
	/** Function <code>setOnGround</code> <p>
	 * If the boolean parameter is true, set the character's status to onGround
	 * @param onGround - (boolean)
	 */
	public void setOnGround( boolean onGround ) {
		world.getCore().setCharacterOnGround( id, onGround );
	}
	
	/** Function <code>setRest</code> <p>
	 * If the boolean parameter is true, set the character's status to rest
	 * @param rest - (boolean)
	 */
	public void setRest( boolean rest ) {		
		world.getCore().setCharacterRest( id, rest );
	}

	/** Function <code>setMovingBackward</code> <p>
	 * If the boolean parameter is true, set the character's status to moving backward and activate the right animation
	 * @param movingBackward - (boolean)
	 */
	public void setMovingBackward( boolean movingBackward ) {
		world.getCore().setCharacterMovingBackward( id, movingBackward );
	}

	/** Function <code>setMovingForward</code> <p>
	 * If the boolean parameter is true, set the character's status to moving forward and activate the right animation
	 * @param movingForward - (boolean)
	 */
	public void setMovingForward( boolean movingForward ) {
    	// activate the animation only if the previous animation was different and if the character is on the ground
    	if( animationController.getMaxTime() != (26 / animationController.FPS) && movingForward == true && getOnGround() == true ) {
    		// activate the animation "run" 
    		animationController.setTimes( 16, 26 );
    	}
		
		world.getCore().setCharacterMovingForward( id, movingForward );
	}

	/** Function <code>setJumping</code> <p>
	 * If the boolean parameter is true, set the character's status to jumping and activate the right animation
	 * @param jumping - (boolean)
	 */
	public void setJumping( boolean jumping ) {	
		// if the character is jumping
    	if( animationController.getMaxTime() != (40 / animationController.FPS) && jumping == true ) {
    		// activate the animation "jump"
    		animationController.setTimes( 42, 54 );
    	}
		world.getCore().setCharacterJumping( id, jumping );
	}

	/** Function <code>setStrafingLeft</code> <p>
	 * If the boolean parameter is true, set the character's status to strafing left
	 * @param strafingLeft - (boolean)
	 */
	public void setStrafingLeft( boolean strafingLeft ) {
		world.getCore().setCharacterStrafingLeft( id, strafingLeft );
	}

	/** Function <code>setStrafingRight</code> <p>
	 * If the boolean parameter is true, set the character's status to strafing right
	 * @param strafingRight - (boolean)
	 */
	public void setStrafingRight( boolean strafinRight ) {
		world.getCore().setCharacterStrafingRight( id, strafinRight );
	}
}