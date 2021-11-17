import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PImage;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.PJOGL;
import processing.opengl.PGL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;
import processing.core.PFont;
public class Minecraft extends PApplet {
PJOGL pgl;
public static PImage clouds, crosshair;
public static Camera camera;
//public static Chunk[][] chunks = new Chunk[16][16];
public static PImage textureAtlas, sunImage, moonImage;
public static int size = 50;
RayCaster caster;
MemoryProfiler profiler;
public static TextureManager manager;
public static ConcurrentHashMap<PVector, Chunk> chunks = new ConcurrentHashMap<PVector, Chunk>();
public static ArrayList<PVector> toSend = new ArrayList<PVector>();
Sky sky;
public static int wid = 16;
public static int len = 16;
public static int viewDist = 4;
public static Noise gen, biomeNoise;
public PFont font;
public static ThreadImpl th = new ThreadImpl();
public static MeshGenThread thr = new MeshGenThread();
public static int seed;
public boolean fixedSize = false;
chunkManagerThread t2 = new chunkManagerThread();
PVector curPos, pastPos;
public void settings() {  
	size(1280, 720, P3D); 
//	fullScreen(P3D);
	noSmooth();
	}
public static void main(String[] passedArgs) {
  String[] appletArgs = new String[] { "Minecraft" };
  if (passedArgs != null) {
    PApplet.main(concat(appletArgs, passedArgs));
  } else {
    PApplet.main(appletArgs);
  }
}
public static float round50(float x) {
    return (float) Math.round((x+49)/50) * 50;
}
public void setup() {
	profiler = new MemoryProfiler(this);
	frameRate(62);
//	seed = (long) random(Integer.MIN_VALUE, Integer.MAX_VALUE); // can generate up to 4,294,967,294 different worlds;
	seed = (int) random(Integer.MIN_VALUE, Integer.MAX_VALUE); // can generate up to 18,446,744,073,709,551,616 different worlds;
//	seed = 0;
	  gen = new Noise(seed, this);
	  biomeNoise = new Noise(seed, this);
	  biomeNoise.octaves = 5;
	  biomeNoise.heightOffset = 96;
	  biomeNoise.roughness = 0.65;
	clouds = loadImage("assets/clouds.png");
	crosshair = loadImage("assets/crosshair.png");
	textureAtlas = loadImage("assets/terrain3.png");
	font = createFont("assets/MCFont.otf", 20);
	hint(DISABLE_TEXTURE_MIPMAPS);
    ((PGraphicsOpenGL)g).textureSampling(2);
    textureMode(NORMAL);
	  sunImage = loadImage("assets/sun.png");
	  moonImage = loadImage("assets/moon.png");
    surface.setLocation(0, 0);
  manager = new TextureManager();
  th.start();
  t2.start();
  thr.start();
  if(fixedSize) {
	  for(int i = 0; i < wid; i++) {
		  for(int j = 0; j < len; j++) {
			  chunks.put(new PVector(i, 0, j), new Chunk(this, i, 0, j));
			  chunks.get(new PVector(i, 0, j)).generateTerrain();
		  }
	  }
	  for(int i = 0; i < wid; i++) {
		  for(int j = 0; j < len; j++) {
			  try {
			  chunks.get(new PVector(i, 0, j)).left = chunks.get(new PVector(i - 1, 0, j));
			  } catch (Exception e) {
				  
			  }
			  try {
				  chunks.get(new PVector(i, 0, j)).right = chunks.get(new PVector(i + 1, 0, j));
				  } catch (Exception e) {
					  
				  }
			  try {
				  chunks.get(new PVector(i, 0, j)).front = chunks.get(new PVector(i, 0, j - 1));
				  } catch (Exception e) {
					  
				  }
			  try {
				  chunks.get(new PVector(i, 0, j)).back = chunks.get(new PVector(i, 0, j + 1));
				  } catch (Exception e) {
					  
				  }
				  chunks.get(new PVector(i, 0, j)).generateMesh();
		  }
	  }
  }
  pgl = (PJOGL) beginPGL();
  caster = new RayCaster(this);
  camera = new Camera(this);
  sky = new Sky(this);
}
public static int to1d (int x, int y) {
	return x + wid * y;
}
public static float round2(float x) {
	return PApplet.round((x+ ((Chunk.w * size) - 1))/(Chunk.w * size)) * (Chunk.w * size);
}
public static float round3(float x) {
	return PApplet.round((x+(size - 1))/size) * size;
	}
public static float floor3(float x) {
	return PApplet.floor((x+(size - 1))/size) * size;
	}
public static float ceil3(float x) {
	return PApplet.ceil((x+(size - 1))/size) * size;
	}
public static PVector PRound2(PVector p) {
    PVector x = new PVector(round3(p.x), round3(p.y), round3(p.z));
    x.div(Minecraft.size);
    return x;
}
public static PVector roundPVector(PVector p) {
	  return new PVector(round3(p.x), round3(p.y), round3(p.z));
}
public static PVector floorPVector(PVector p) {
	  return new PVector(floor3(p.x), floor3(p.y), floor3(p.z));
}
public static PVector ceilPVector(PVector p) {
	  return new PVector(ceil3(p.x), ceil3(p.y), ceil3(p.z));
}
public void mousePressed() {
    	if(mouseButton == LEFT) {
    		Ray ray = caster.traceRay(camera.realNorm, camera.look, 75);
    		if(ray.hasTarget()) {
    		removeBlock((int) ray.hitPosition.x, (int) ray.hitPosition.y, (int) ray.hitPosition.z);
    		}
    	} else if(mouseButton == RIGHT) {
    		Ray ray = caster.traceRay(camera.realNorm, camera.look, 75);
    		if(ray.hasTarget()) {
    		placeBlock((int) ray.hitPosition.x + (int) ray.hitNormal.x, (int) ray.hitPosition.y + (int) ray.hitNormal.y, (int) ray.hitPosition.z + (int) ray.hitNormal.z);
    		}
    }
}
public int getBlockAt (int x, int y, int z) {
	int blockX = 0;
	int blockZ = 0;
    int chunkX = x / 16;
    if(x >= 0) {
    blockX = x % 16;
    } else {
    blockX = -x % 16;
    }
    int chunkZ = z / 16;
    if(z >= 0) {
    blockZ = z % 16;
    } else {
    blockZ = -z % 16;
    }
    return chunks.get(new PVector(chunkX, 0, chunkZ)).getBlock(blockX, y, blockZ);

}
public static void removeBlock (int x, int y, int z) {
	int blockX = 0;
	int blockZ = 0;
    int chunkX = x / 16;
    if(x >= 0) {
    blockX = x % 16;
    } else {
    blockX = -x % 16;
    }
    int chunkZ = z / 16;
    if(z >= 0) {
    blockZ = z % 16;
    } else {
    blockZ = -z % 16;
    }
    Chunk c = chunks.get(new PVector(chunkX, 0, chunkZ));
    c.removeBlock(blockX, y, blockZ);
    c.generateMesh();

}
public static void placeBlock (int x, int y, int z) {

	int blockX = 0;
	int blockZ = 0;
    int chunkX = x / 16;
    if(x >= 0) {
    blockX = x % 16;
    } else {
    blockX = -x % 16;
    }
    int chunkZ = z / 16;
    if(z >= 0) {
    blockZ = z % 16;
    } else {
    blockZ = -z % 16;
    }
    Chunk c = chunks.get(new PVector(chunkX, 0, chunkZ));
    c.placeBlock(blockX, y, blockZ);
    c.generateMesh();

}

public void crosshair(int x, int y) {
    fill(0, 255, 0);
    rect(x - 2, y - 15, 4, 30);
    rect(x - 15, y - 2, 30, 4);	
    stroke(0);
}
public static PVector getPVector(PVector p) {
	PVector x = new PVector(0, 0, 0);
	x.x = p.x;
	x.y = p.y;
	x.z = p.z;
	return x;
}
public void drawFocusedBlock() {
	PVector vector = camera.look;
	PVector pos = camera.normPos;
    Ray ray = caster.traceRay(pos, vector, 25);
    PVector position = ray.getHitPosition();
    if (ray.hasTarget()) {
        highlight(position.x, position.y, position.z);
    }


}
public void highlight(int x, int y, int z) {
	pushStyle();
    stroke(0);
    noFill();
    strokeWeight(3);
    translate(x * size, y * size, z * size);
    box(size + 1);
    popStyle();
}
public void highlight(float x, float y, float z) {
	pushStyle();
    stroke(0);
    noFill();
    strokeWeight(3);
    translate(x * size, y * size, z * size);
    box(size + 1);
    popStyle();
}
public void drawGUI() {
    pushStyle();
    noStroke();
    crosshair(width/2, height/2);
    textFont(font);
    textAlign(LEFT, TOP);
    fill(255);
    text("FPS: " + round(frameRate), 10, 10);
    text("Seed: " + gen.seed, 10, 50);
    text("Position: (" + (long) camera.normPos.x + ", " + (long) camera.normPos.y + ", " + (long) camera.normPos.z + ")", 10, 90);
    text("Number of Chunks: " + chunks.size(), 10, 130);
    profiler.draw(this, 10, 170);
    popStyle();
}
public static boolean aroundPlayer(PVector vec, PVector position) {
	if(vec.x >= (int) (-viewDist + position.x) - viewDist && vec.x <= (int) (viewDist + position.x) && vec.z >= (int) (-viewDist + position.z) - viewDist && vec.z <= (int) (viewDist + position.z)) {
		return true;
	}
	return false;
}
public static Chunk getChunk(float x, float z) {
	for(PVector p : chunks.keySet()) {
		if(p.x == x && p.z == z) {
			return chunks.get(p);
		}
	}
	return null;
}
public void loadChunksAroundPlayer() {
	PVector position = new PVector(round2(camera.position.x), 0, round2(camera.position.z));
	position.div(Chunk.w * size);
	if(pastPos == null) pastPos = position;
	curPos = position;
//	println((int) (-viewDist + position.x));
	  for(int i = (int) (-viewDist + position.x) - viewDist + 1; i <= (int) (viewDist + position.x); i++) {
		  for(int j = (int) (-viewDist + position.z) - viewDist + 1; j <= (int) (viewDist + position.z); j++) {
			  PVector viewedCoord = new PVector(i, 0, j);
			  if(chunks.containsKey(viewedCoord)) {
				  /*
				PVector directionVector = chunks.get(new PVector(i, 0, j)).getPixelPosition().sub(camera.position);
				float dotProduct = PApplet.cos(PVector.angleBetween(directionVector, camera.look));
				if(dotProduct >= 0) {
				chunks.get(viewedCoord).render(this);
				}
				*/
				chunks.get(viewedCoord).render(this);
			  } else {
				  chunks.put(viewedCoord, new Chunk(this, i, 0, j));
				  chunks.get(viewedCoord).left = new Chunk(this, i - 1, 0, j);
				  chunks.get(viewedCoord).right = new Chunk(this, i + 1, 0, j);
				  chunks.get(viewedCoord).front = new Chunk(this, i, 0, j - 1);
				  chunks.get(viewedCoord).back = new Chunk(this, i, 0, j + 1);
				  toSend.add(viewedCoord);  
			  }
		  }
	  }
	  for(int i = (int) (-viewDist + position.x) - viewDist; i <= (int) (viewDist + position.x); i++) {
		  for(int j = (int) (-viewDist + position.z) - viewDist; j <= (int) (viewDist + position.z); j++) {
			  PVector viewedCoord = new PVector(i, 0, j);
			  if(chunks.containsKey(viewedCoord)) {
				  /*
						PVector directionVector = chunks.get(new PVector(i, 0, j)).getPixelPosition().sub(camera.position);
						float dotProduct = PApplet.cos(PVector.angleBetween(directionVector, camera.look));
						if(dotProduct >= 0) {
						chunks.get(viewedCoord).render2(this);
						}
						*/
				chunks.get(viewedCoord).render2(this);
			  }
		  }
	  }
	  for(PVector p : toSend) {
		  th.sendChunk(p);
	  }
	  toSend.clear();
	  if(!curPos.equals(pastPos)) {
		  t2.destroy(position);
		  pastPos = curPos;
	  }
}
public static int getIndex(PVector p) {
	int index = 0;
	for(PVector x : chunks.keySet()) {
		if(p == x) {
		   	break;
		}
		index++;
	}
	return index;
}
public void draw() {
  noCursor();
  pgl = (PJOGL) beginPGL();
  pgl.frontFace(PGL.CCW);
  perspective(PApplet.radians(90), (float)width/(float)height, 1f, 1000000f);
  sky.draw();
  pgl.enable(PGL.CULL_FACE);
  if(!fixedSize) {
loadChunksAroundPlayer();
  } else {
	  for(int i = 0; i < wid; i++) {
		  for(int j = 0; j < len; j++) {
			  chunks.get(new PVector(i, 0, j)).render(this);
		  }
	  }
	  for(int i = 0; i < wid; i++) {
		  for(int j = 0; j < len; j++) {
			  chunks.get(new PVector(i, 0, j)).render2(this);
		  }
	  }
  }
  pgl.disable(PGL.CULL_FACE);
  sky.drawObjects();
//PVector directionVector = chunks.get(new PVector(i, 0, j)).getPixelPosition().sub(camera.position);
//float dotProduct = (float) Math.cos(PVector.angleBetween(directionVector, camera.look));
//if(dotProduct >= 0) {
//chunks.get(new PVector(i, 0, j)).render(this);
drawFocusedBlock();
endPGL();
pushMatrix();
resetMatrix();
ortho();
translate(-width / 2, -height / 2);
drawGUI();
popMatrix();
  }
}