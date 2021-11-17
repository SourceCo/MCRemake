import processing.core.PVector;
public class AABB {
     public PVector min = new PVector(0, 0, 0), max = new PVector(0, 0, 0);
     public AABB (PVector minPos, PVector maxPos) {
    	 min = minPos;
    	 max = maxPos;
     }
     public boolean intersects(PVector point) {
    	  return (point.x > min.x && point.x < max.x) &&
    	         (point.y > min.y && point.y < max.y) &&
    	         (point.z > min.z && point.z < max.z);
    	}
     public boolean intersects (AABB b) {
    	  return (min.x <= b.max.x && max.x >= b.min.x) &&
    	         (min.y <= b.max.y && max.y >= b.min.y) &&
    	         (min.z <= b.max.z && max.z >= b.min.z);
    	}
}
