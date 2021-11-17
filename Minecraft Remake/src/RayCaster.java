import processing.core.PVector;
import processing.core.PApplet;

public class RayCaster
{
    Minecraft world;
    
    public RayCaster(Minecraft mc) {
        world = mc;
    }
    
    public float round3(float x) {
    	float a = x / 100;
    	float b = Math.round(a);
    	float c = b * 100;
    	return c;
    }
    
    public float round50(float x) {
        return (float)(Math.round((x + 9) / 10) * 10);
    }
    
    public PVector PRound(PVector p) {
        PVector x = new PVector(round50(p.x), round50(p.y), round50(p.z));
        x.div(Minecraft.size);
        return x;
    }
    public PVector PRound2(PVector p) {
        PVector x = new PVector(round3(p.x), round3(p.y), round3(p.z));
        x.div(Minecraft.size);
        return x;
    }
    public Ray fireRay(PVector position, PVector direction, int max_distance) {
        float x = position.x;
        float y = position.y;
        float z = position.z;
        double dx = Math.floor(direction.x * Minecraft.size) / 10;
        double dy = Math.floor(direction.y * Minecraft.size) / 10;
        double dz = Math.floor(direction.z * Minecraft.size) / 10;
        Ray ray = new Ray();
        ray.direction = new PVector((float) dx, (float) dy, (float) dz);
        for(int i = 0; i < max_distance; i++) {
            PVector p = PRound2(new PVector((float) x, (float) y, (float) z));
            PVector p2 = PRound(new PVector((float) x, (float) y, (float) z));
            /*
            Cube block = Minecraft.getBlock(p.x, p.y, p.z);
          if(block != null) {
            if(block.collisionBox.intersects(p)) {
            	ray.hitPosition = block.position;
                ray.hitTarget = true;
                return ray;
              }
            }
            */
            /*
            if (Minecraft.isBlockAt(p2.x, p2.y, p2.z)) {
                ray.hitPosition = new PVector(p2.x, p2.y, p2.z);
                ray.hitTarget = true;
                return ray;
                }
                x += dx;
                y += dy;
                z += dz;
                */
        }
        return ray;
    }
    public Ray traceRay(PVector position, PVector direction, int maxDistance) {

        // consider raycast vector to be parametrized by t
        //   vec = [px,py,pz] + t * [dx,dy,dz]

        // algo below is as described by this paper:
        // http://www.cse.chalmers.se/edu/year/2010/course/TDA361/grid.pdf

        float px = position.x;
        float py = position.y;
        float pz = position.z;
        float dx = direction.x;
        float dy = direction.y;
        float dz = direction.z;


        float t = 0.0f;
        int ix = (int) px;
        int iy = (int) py;
        int iz = (int) pz;
        float stepx = (dx > 0) ? 1 : -1;
        float stepy = (dy > 0) ? 1 : -1;
        float stepz = (dz > 0) ? 1 : -1;
        // dx,dy,dz are already normalized
        float txDelta = Math.abs(1f / dx);
        float tyDelta = Math.abs(1f / dy);
        float tzDelta = Math.abs(1f / dz);

        float xdist = (stepx > 0) ? (ix + 1f - px) : (px - ix);
        float ydist = (stepy > 0) ? (iy + 1f - py) : (py - iy);
        float zdist = (stepz > 0) ? (iz + 1f - pz) : (pz - iz);

        // location of nearest voxel boundary, in units of t
        float txMax = txDelta * xdist;
        float tyMax = tyDelta * ydist;
        float tzMax = tzDelta * zdist;

        float steppedIndex = -1;

        Ray ray = new Ray();

        // main loop along raycast vector
        while (t <= maxDistance) {

            // exit check

                int block = 0;
                try {
                	block = world.getBlockAt(ix, iy, iz);
                } catch (Exception e) {
                }
                if(block == 0 || block == 6) {

                } else {
                        ray.hitPosition.x = ix;
                        ray.hitPosition.y = iy;
                        ray.hitPosition.z = iz;
                        ray.hitTarget = true;
                        if (steppedIndex == 0) ray.hitNormal.x = -stepx;
                        if (steppedIndex == 1) ray.hitNormal.y = -stepy;
                        if (steppedIndex == 2) ray.hitNormal.z = -stepz;
                        return ray;
                }
                // advance t to next nearest voxel boundary
                if (txMax < tyMax) {
                    if (txMax < tzMax) {
                        ix += stepx;
                        t = txMax;
                        txMax += txDelta;
                        steppedIndex = 0;
                    } else {
                        iz += stepz;
                        t = tzMax;
                        tzMax += tzDelta;
                        steppedIndex = 2;
                    }
                } else {
                    if (tyMax < tzMax) {
                        iy += stepy;
                        t = tyMax;
                        tyMax += tyDelta;
                        steppedIndex = 1;
                    } else {
                        iz += stepz;
                        t = tzMax;
                        tzMax += tzDelta;
                        steppedIndex = 2;
                    }
                }
            }


        ray.hitTarget = false;
        return ray;

    }
}