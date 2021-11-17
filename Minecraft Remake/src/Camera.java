import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.GraphicsEnvironment;
import java.util.HashMap;
import processing.core.*;
import processing.event.KeyEvent;
import processing.opengl.PGraphicsOpenGL;
import com.jogamp.newt.opengl.GLWindow;
public class Camera {

	public boolean controllable;
	public float speed;
	public float sensitivity;
	public PVector position;
	public float pan = 1f;
	public float tilt = -0.5f;
	public PVector velocity;
	public float friction;
//    public float gravity = 4f;
	private PApplet applet;
	private Robot robot;
	public PVector center = new PVector(0, 0, 0);
	private PVector up;
	private PVector right;
	public PVector forward;
    public PVector target = new PVector(0, 0, 0);
	private Point mouse;
	private Point prevMouse;
	public float sSpeed = 0;
	PVector normPos = new PVector(0, 0, 0);
	PVector chunkPos = new PVector(0, 0, 0);
	public PVector look = new PVector(0, 0, 0);
	public PVector realNorm = new PVector(0, 0, 0);
	public PVector prevNorm = new PVector(0, 0, 0);
	private HashMap<Character, Boolean> keys;
	public Camera(PApplet applet){
		this.applet = applet;
		applet.registerMethod("draw", this);
		applet.registerMethod("keyEvent", this);
		
		try {
			robot = new Robot();
		} catch (Exception e){}

		controllable = true;
		speed = 1f;
		sSpeed = speed;
	    sensitivity = 1f;
		position = new PVector(0 * Minecraft.size, 160 * Minecraft.size, 0);
		up = new PVector(0f, 1f, 0f);
		right = new PVector(1f, 0f, 0f);
		forward = new PVector(0f, 0.5f, 1f);
		velocity = new PVector(0f, 0f, 0f);
		pan = 0f;
		tilt = 0.5f;
		friction = 0.75f;
		keys = new HashMap<Character, Boolean>();
		applet.perspective(PApplet.radians(90), (float)applet.width/(float)applet.height, 1f, 1000000f);
	}
    public void centerMouse() {
        int x = ((GLWindow) applet.getSurface().getNative()).getX();
        int y = ((GLWindow) applet.getSurface().getNative()).getY();
        int w = applet.displayWidth;
        int h = applet.displayHeight;

        robot.mouseMove(w / 2, h / 2);
    }
	public void draw() {
		if (!controllable) return;
		if (prevNorm == null) prevNorm = normPos;
		mouse = MouseInfo.getPointerInfo().getLocation();
		normPos = Minecraft.getPVector(position).div(Minecraft.size).add(new PVector(0.5f, 0.5f, 0.5f));
		realNorm = Minecraft.getPVector(center).div(Minecraft.size).add(new PVector(0.5f, 0.5f, 0.5f));
//		normPos = new PVector(PApplet.round(normPos.x), PApplet.round(normPos.z), PApplet.round(normPos.z));
		if (prevMouse == null) prevMouse = new Point(mouse.x, mouse.y);
        int x = ((GLWindow) applet.getSurface().getNative()).getX();
        int y = ((GLWindow) applet.getSurface().getNative()).getY();
        int w = applet.displayWidth;
        int h = applet.displayHeight;

        int middleX = w / 2;
        int middleY = h / 2;

        int deltaX = mouse.x - middleX;
        int deltaY = mouse.y - middleY;
        centerMouse();
		if (mouse.x < 1 && (mouse.x - prevMouse.x) < 0){
			robot.mouseMove(w-2, mouse.y);
			mouse.x = w-2;
			prevMouse.x = w-2;
		}
				
		if (mouse.x > w-2 && (mouse.x - prevMouse.x) > 0){
			robot.mouseMove(2, mouse.y);
			mouse.x = 2;
			prevMouse.x = 2;
		}
		
		if (mouse.y < 1 && (mouse.y - prevMouse.y) < 0){
			robot.mouseMove(mouse.x, h-2);
			mouse.y = h-2;
			prevMouse.y = h-2;
		}
		
		if (mouse.y > h-1 && (mouse.y - prevMouse.y) > 0){
			robot.mouseMove(mouse.x, 2);
			mouse.y = 2;
			prevMouse.y = 2;
		}
		pan += PApplet.map(deltaX, 0, w, 0, PConstants.TWO_PI) * sensitivity;
		tilt += PApplet.map(deltaY, 0, h, 0, PConstants.PI) * sensitivity;
		tilt = PApplet.constrain(tilt, -PConstants.PI/2.01f, PConstants.PI/2.01f);
		if (tilt == PConstants.PI/2) tilt += 0.001f;
        forward = new PVector(PApplet.cos(pan), 0, PApplet.sin(pan));
        forward.normalize();
        look = new PVector(PApplet.cos(pan), PApplet.tan(tilt), PApplet.sin(pan));
        look.normalize();
        right = new PVector(PApplet.cos(pan - PConstants.PI/2), 0, PApplet.sin(pan - PConstants.PI/2));
        right.normalize();
        target = PVector.add(position, look);
		
		prevMouse = new Point(mouse.x, mouse.y);
		prevNorm = new PVector(Minecraft.round2(position.x), 0, Minecraft.round2(position.z));
		if (keys.containsKey('a') && keys.get('a')) {
			velocity.add(PVector.mult(right, speed));
			speed = sSpeed;
		}
		if (keys.containsKey('d') && keys.get('d')) {
			velocity.sub(PVector.mult(right, speed));
			speed = sSpeed;
		}
		if (keys.containsKey('w') && keys.get('w')) {
			velocity.add(PVector.mult(forward, speed));
			speed = sSpeed;
		}
		if (keys.containsKey('s') && keys.get('s')) {
			velocity.sub(PVector.mult(forward, speed));
			speed = sSpeed;
		}
		if (keys.containsKey('q') && keys.get('q')) {
			velocity.add(PVector.mult(up, speed));
			speed = sSpeed;
		}
		if (keys.containsKey('e') && keys.get('e')) {
			velocity.sub(PVector.mult(up, speed));
			speed = sSpeed;
		}
		if (keys.containsKey(' ') && keys.get(' ')) {
			speed = sSpeed * 8;
		}
		chunkPos = new PVector(Minecraft.round2(position.x), 0, Minecraft.round2(position.z));
		chunkPos.div(Chunk.w * Minecraft.size);
//		velocity.y += gravity;
		velocity.mult(friction);
		position.add(velocity);
		center = PVector.add(position, look);
		applet.camera(position.x, position.y, position.z, center.x, center.y, center.z, up.x, up.y, up.z);
	}
	public float[][] getModelViewMatrix() {
		float[] m = new float[16];
		m = ((PGraphicsOpenGL) applet.g).modelview.get(m);
		float[][] result = {
				{m[0], m[1], m[2], m[3]},
				{m[4], m[5], m[6], m[7]},
				{m[8], m[9], m[10], m[11]},
				{m[12], m[13], m[14], m[15]}
};
		return result;
	}
	public void keyEvent(KeyEvent event){
		char key = event.getKey();
		
		switch (event.getAction()){
			case KeyEvent.PRESS: 
				keys.put(Character.toLowerCase(key), true);
				break;
			case KeyEvent.RELEASE:
				keys.put(Character.toLowerCase(key), false);
				break;
		}
	}
	
	private float clamp(float x, float min, float max){
		if (x > max) return max;
		if (x < min) return min;
		return x;
	}
	
	public PVector getForward(){
		return forward;
	}
	public PVector getCenter() {
		return center;
	}
	public PVector getUp(){
		return up;
	}
	
	public PVector getRight(){
		return right;
	}
	public PVector getPosition(){
		return position;
	}
    
    public PVector getTarget(){
        return target;
    }
    
}