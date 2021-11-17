import processing.core.PApplet;
import processing.core.PVector;

public class Ray {
    public PVector hitPosition;
    public PVector hitNormal;
    public PVector direction;
    boolean hitTarget = false;

    public Ray(){
        this.hitPosition = new PVector(0, 0, 0);
        this.hitNormal = new PVector(0, 0, 0);
        this.direction = new PVector(0, 0, 0);
    }
    void step(PVector position, PVector direction, float scale) {
    	PVector p = position;
        float yaw = PApplet.radians(direction.y + 90);
        float pitch = PApplet.radians(direction.x);
        
        p.x -= Math.cos(yaw) * scale;
        p.z -= Math.sin(yaw) * scale;
        p.y -= Math.tan(pitch) * scale;
    }
    public PVector getDirection() {
    	return direction;
    }

    public boolean hasTarget(){
        return hitTarget;
    }

    public PVector getHitPosition() {
        return hitPosition;
    }

    public PVector getHitNormal() {
        return hitNormal;
    }
}