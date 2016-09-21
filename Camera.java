package com.base.engine;

public class Camera {
	
	public static final Vector3f yAxis = new Vector3f(0,1,0); 
	
	private Vector3f pos;
	private Vector3f forward;
	private Vector3f up;
	
	public Camera(){
		
		this(new Vector3f(0,0,0), new Vector3f(0,0,1), new Vector3f(0,1,0));
	}
	
	public Camera(Vector3f pos, Vector3f forward, Vector3f up){
		
		this.pos = pos;
		this.forward = forward;
		this.up = up;
		
		up.normalize();
		forward.normalize();
	}

	public void move(Vector3f dir, float amt){
		
		pos = pos.add(dir.mul(amt));
	}
	
	boolean mouseLocked = false;
	Vector2f centerPosition = new Vector2f(Window.getWidth()/2, Window.getHeight()/2);
	
	public void input(){
		
		float movAmt = (float)(10* Time.getDelta());
		float sensitivity = 0.5f;
		
		if(Input.getKey(Input.KEY_ESCAPE)){
			
			Input.setCursor(true);
			mouseLocked = false;
		}
		
		if(Input.getMouseDown(0)){
			
			Input.setMousePosition(centerPosition);
			Input.setCursor(false);
			mouseLocked = true;
		}
		
		if(Input.getKey(Input.KEY_W))
			move(getForward(), movAmt);
		if(Input.getKey(Input.KEY_A))
			move(getLeft(), movAmt);
		if(Input.getKey(Input.KEY_S))
			move(getForward(),-movAmt);
		if(Input.getKey(Input.KEY_D))
			move(getRight(), movAmt);
		
		if(mouseLocked){
			
			Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);
			
			boolean rotX = deltaPos.getX() != 0;
			boolean rotY = deltaPos.getY() != 0;
			
			if(rotY)
				rotateY(deltaPos.getX() * sensitivity);
			if(rotX)
				rotateX(-deltaPos.getY() * sensitivity);
			
			if(rotY || rotX)
				Input.setMousePosition(centerPosition);
		}
//		if(Input.getKey(Input.KEY_UP))
//			rotateX(rotAmt);
//		if(Input.getKey(Input.KEY_DOWN))
//			rotateX(-rotAmt);
//		if(Input.getKey(Input.KEY_LEFT))
//			rotateY(rotAmt);
//		if(Input.getKey(Input.KEY_RIGHT))
//			rotateY(-rotAmt);
		
	}
	public void rotateY(float angle){
		
		Vector3f Haxis = yAxis.cross(forward).normalize();
						
		forward = forward.rotate(angle, yAxis).normalize();
		
		up = forward.cross(Haxis).normalize();		
	}
	
	public void rotateX(float angle){
		
		Vector3f Haxis = yAxis.cross(forward).normalize();
		
		forward = forward.rotate(angle, Haxis).normalize();
		
		up = forward.cross(Haxis).normalize();	
		
	}
	
	public Vector3f getLeft(){
		
		return forward.cross(up).normalize();
	}
	
	public Vector3f getRight(){
		
		return up.cross(forward).normalize();
	}
	
	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	public Vector3f getForward() {
		return forward;
	}

	public void setForward(Vector3f forward) {
		this.forward = forward;
	}

	public Vector3f getUp() {
		return up;
	}

	public void setUp(Vector3f up) {
		this.up = up;
	}
}
