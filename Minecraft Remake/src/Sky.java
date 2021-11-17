import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.core.PShape;
import processing.core.PConstants;
public class Sky {
  PShape clouds;
  PApplet app;
  int r = 0, g = 0, b = 0;
  public PVector position = new PVector(0, 0, 0);
  public PVector pos2 = new PVector(0, 0, 0);
  public PVector pos3 = new PVector(0, 0, 0);
  public PVector pos4 = new PVector(0, 0, 0);
  public PVector pos5 = new PVector(0, 0, 0);
  PShape skyShape;
  boolean day;
  boolean night;
  int dayTime = 10000;
  int sunsetColor = 0xFFFD5E53;
  int dayColor = 0xFF82E6FF;
  int nightColor = 0xFF1E1E1E;
  int sunsetAnimTime = 2000;
  int seconds = sunsetAnimTime/1000;
  int frames = ((sunsetAnimTime/1000) * 60) * 2;
  int frames2 = ((sunsetAnimTime/1000) * 60);
  boolean startTimer2 = false;
  boolean startTimer = false;
  int inc = 3800;
  int inc2 = 3800;
  int curSkyColor = 0xFFFFFFFF;
  int curWorldColor = 0xFFFFFFFF;
  PShape c, c2, c3;
  PShape sun, moon;
  int timer = ((sunsetAnimTime/1000) * 60) * 2;
  int timer2 = ((sunsetAnimTime/1000) * 60);
  public boolean isDay, isSunrise, isNight, isSunset;
  public Sky(PApplet p) {
	  app = p;
	  position = new PVector(0 * Minecraft.size, 200 * Minecraft.size, 0 * Minecraft.size);
	  pos2 = new PVector(0 * Minecraft.size, 200 * Minecraft.size, -128 * Minecraft.size);
	  pos3 = new PVector(0 * Minecraft.size, 200 * Minecraft.size, -256 * Minecraft.size);
	  pos4 = new PVector(0 * Minecraft.size, 200 * Minecraft.size, -128 * Minecraft.size);
	  pos5 = new PVector(0 * Minecraft.size, 200 * Minecraft.size, -256 * Minecraft.size);
	  float x1 = -1000;
	  float y1 = -1000;
	  float z1 = -1000;
	  float x2 = 1000;
	  float y2 = 1000;
	  float z2 = 1000;
	  sun = app.createShape();
      sun.setTexture(Minecraft.sunImage);
      sun.beginShape(PConstants.QUADS);
      sun.noFill();
      sun.noStroke();
      sun.vertex(x1, y1, z1, 0f, 0f);
      sun.vertex(x2, y1, z1, 1f, 0f);
      sun.vertex(x2, y2, z1, 1f, 1f);
      sun.vertex(x1, y2, z1, 0f, 1f);
      sun.endShape();
      
      moon = app.createShape();
      moon.setTexture(Minecraft.moonImage);
      moon.beginShape(PConstants.QUADS);
      moon.noFill();
      moon.noStroke();
      moon.vertex(x1, y1, z1, 0f, 0f);
      moon.vertex(x2, y1, z1, 1f, 0f);
      moon.vertex(x2, y2, z1, 1f, 1f);
      moon.vertex(x1, y2, z1, 0f, 1f);
      moon.endShape();
	  x1 = -6400;
	  y1 = -6400;
	  z1 = -6400;
	  x2 = 6400;
	  y2 = 6400;
	  z2 = 6400;
	  clouds = app.createShape();
      clouds.setTexture(Minecraft.clouds);
	  clouds.beginShape(PConstants.QUADS);
	  clouds.noFill();
	  clouds.noStroke();
	  clouds.vertex(x1, y1, z2, 0, 0);
	  clouds.vertex(x2, y1, z2, 1, 0);
	  clouds.vertex(x2, y1, z1, 1, 1);
	  clouds.vertex(x1, y1, z1, 0, 1);
	  clouds.endShape();
	  z1 -= 128 * Minecraft.size;
	  z2 -= 128 * Minecraft.size;
	  c2 = app.createShape();
      c2.setTexture(Minecraft.clouds);
	  c2.beginShape(PConstants.QUADS);
	  c2.noFill();
	  c2.noStroke();
	  c2.vertex(x1, y1, z2, 0, 0);
	  c2.vertex(x2, y1, z2, 1, 0);
	  c2.vertex(x2, y1, z1, 1, 1);
	  c2.vertex(x1, y1, z1, 0, 1);
	  c2.endShape();
	  z1 -= 128 * Minecraft.size;
	  z2 -= 128 * Minecraft.size;
	  c3 = app.createShape();
      c3.setTexture(Minecraft.clouds);
	  c3.beginShape(PConstants.QUADS);
	  c3.noFill();
	  c3.noStroke();
	  c3.vertex(x1, y1, z2, 0, 0);
	  c3.vertex(x2, y1, z2, 1, 0);
	  c3.vertex(x2, y1, z1, 1, 1);
	  c3.vertex(x1, y1, z1, 0, 1);
	  c3.endShape();
  }
  public void setColor(int x, int y, int z) {
	  r = x;
	  g = y;
	  b = z;
  }
  public int round(int x) {
	  return Math.round((x+(dayTime - 1))/dayTime) * dayTime;
  }
  public void drawObjects() {
	  PVector p = Minecraft.camera.position;
	  if(!isNight) {
		  
	  /*
	  pos.z = chunkPos.z * (Chunk.w * Minecraft.size);
	  pos2.z = chunkPos.z * (Chunk.w * Minecraft.size);
	  pos3.z = chunkPos.z * (Chunk.w * Minecraft.size);
	  */
		  PVector pos = position;
		  /*
			  float x1 = -32000;
			  float y1 = -6400;
			  float z1 = -32000;
			  float x2 = 32000;
			  float y2 = 6400;
			  float z2 = 32000;
			  app.pushMatrix();
			  app.pushStyle();
			  app.translate(pos.x, pos.y - 50, pos.z);
			  app.beginShape(PConstants.QUADS);
			  app.noStroke();
			  app.fill(curSkyColor);
			  app.vertex(x1, y1, z2, 0, 0);
			  app.vertex(x2, y1, z2, 1, 0);
			  app.vertex(x2, y1, z1, 1, 1);
			  app.vertex(x1, y1, z1, 0, 1);
			  app.endShape();
			  app.popStyle();
			  app.popMatrix();
			  */
	  app.pushMatrix();
	  app.translate(pos.x, pos.y, pos.z);
	  app.shape(clouds);
	  app.popMatrix();
	  
	  app.pushMatrix();
	  app.translate(pos.x, pos.y, pos.z - 12800);
	  app.shape(clouds);
	  app.popMatrix();
	  app.pushMatrix();
	  app.translate(pos.x, pos.y, pos.z - 25600);
	  app.shape(clouds);
	  app.popMatrix();
	  
	  app.pushMatrix();
	  app.translate(pos.x, pos.y, pos.z + 12800);
	  app.shape(clouds);
	  app.popMatrix();
	  app.pushMatrix();
	  app.translate(pos.x, pos.y, pos.z + 25600);
	  app.shape(clouds);
	  app.popMatrix();
	  
	  app.pushMatrix();
	  app.translate(pos.x + 12800, pos.y, pos.z);
	  app.shape(clouds);
	  app.popMatrix();
	  app.pushMatrix();
	  app.translate(pos.x + 25600, pos.y, pos.z);
	  app.shape(clouds);
	  app.popMatrix();
	  
	  app.pushMatrix();
	  app.translate(pos.x - 12800, pos.y, pos.z);
	  app.shape(clouds);
	  app.popMatrix();
	  app.pushMatrix();
	  app.translate(pos.x - 25600, pos.y, pos.z);
	  app.shape(clouds);
	  app.popMatrix();
	  
	  app.pushMatrix();
	  app.translate(pos.x - 12800, pos.y, pos.z - 12800);
	  app.shape(clouds);
	  app.popMatrix();
	  app.pushMatrix();
	  app.translate(pos.x - 25600, pos.y, pos.z - 25600);
	  app.shape(clouds);
	  app.popMatrix();
	  
	  app.pushMatrix();
	  app.translate(pos.x + 12800, pos.y, pos.z + 12800);
	  app.shape(clouds);
	  app.popMatrix();
	  app.pushMatrix();
	  app.translate(pos.x + 25600, pos.y, pos.z + 25600);
	  app.shape(clouds);
	  app.popMatrix();
	  
	  app.pushMatrix();
	  app.translate(pos.x + 12800, pos.y, pos.z - 12800);
	  app.shape(clouds);
	  app.popMatrix();
	  app.pushMatrix();
	  app.translate(pos.x + 25600, pos.y, pos.z - 25600);
	  app.shape(clouds);
	  app.popMatrix();
	  
	  app.pushMatrix();
	  app.translate(pos.x - 12800, pos.y, pos.z + 12800);
	  app.shape(clouds);
	  app.popMatrix();
	  app.pushMatrix();
	  app.translate(pos.x - 25600, pos.y, pos.z + 25600);
	  app.shape(clouds);
	  app.popMatrix();
	  
	  app.pushMatrix();
	  app.translate(pos.x + 12800, pos.y, pos.z + 25600);
	  app.shape(clouds);
	  app.popMatrix();
	  app.pushMatrix();
	  app.translate(pos.x - 12800, pos.y, pos.z + 25600);
	  app.shape(clouds);
	  app.popMatrix();
	  
	  app.pushMatrix();
	  app.translate(pos.x + 25600, pos.y, pos.z + 12800);
	  app.shape(clouds);
	  app.popMatrix();
	  app.pushMatrix();
	  app.translate(pos.x + 25600, pos.y, pos.z - 12800);
	  app.shape(clouds);
	  app.popMatrix();
	  
	  app.pushMatrix();
	  app.translate(pos.x + 12800, pos.y, pos.z - 25600);
	  app.shape(clouds);
	  app.popMatrix();
	  app.pushMatrix();
	  app.translate(pos.x - 12800, pos.y, pos.z - 25600);
	  app.shape(clouds);
	  app.popMatrix();
	  
	  app.pushMatrix();
	  app.translate(pos.x - 25600, pos.y, pos.z + 12800);
	  app.shape(clouds);
	  app.popMatrix();
	  app.pushMatrix();
	  app.translate(pos.x - 25600, pos.y, pos.z - 12800);
	  app.shape(clouds);
	  app.popMatrix();
  }
	  
	  if(!isNight) {
	  app.pushMatrix();
	  app.translate(p.x + (12800*PApplet.sin((float) app.millis()/(float) (dayTime*2))), 8200 - inc, p.z + (12800*PApplet.cos((float) app.millis()/(float) (dayTime*2))));
	  app.shape(sun);
	  app.popMatrix();
	  }
	  if(!isDay) {
	  app.pushMatrix();
	  app.translate(p.x, 8200 - inc2, p.z + 12800);
	  app.shape(moon);
	  app.popMatrix();
	  }
  }
  public void draw() {
	  PVector chunkPos = Minecraft.camera.chunkPos;
	  position.x = Minecraft.camera.position.x;
	  pos2.x = Minecraft.camera.position.x;
	  pos3.x = Minecraft.camera.position.x;
	 if (((app.millis() / dayTime) & 1) == 0) {
		 if((((app.millis() + sunsetAnimTime) / dayTime) & 1) != 0) {
			float a = PApplet.constrain(timer2, 1, 100);
		    animateSky(timer, nightColor, dayColor);
		    animatePlane(a, sunsetColor, dayColor);
		    isSunset = true;
		    isNight = false;
		    isDay = false;
		    isSunrise = false;
		 } else if((((app.millis() - sunsetAnimTime) / dayTime) & 1) != 0) {
					float a = PApplet.constrain(timer2, 1, 100);
				    animateSky(timer, dayColor, nightColor);
				    animatePlane(a, dayColor, sunsetColor);
				    isSunrise = true;
				    isNight = false;
				    isDay = false;
				    isSunset = false;
				 } else {
					 isDay = true;
					    isNight = false;
					    isSunset = false;
					    isSunrise = false;
			 app.background(dayColor);
			 curSkyColor = dayColor;
		 }
	 } else {
		 if((((app.millis() + sunsetAnimTime) / dayTime) & 1) == 0) {
			float a = PApplet.constrain(timer2, 1, 100);
		    animateSky(timer, dayColor, nightColor);
		    animatePlane(a, sunsetColor, nightColor);
		    isSunrise = true;
		    isNight = false;
		    isDay = false;
		    isSunset = false;
		 } else if((((app.millis() - sunsetAnimTime) / dayTime) & 1) == 0) {
					float a = PApplet.constrain(timer2, 1, 100);
				    animateSky(timer, nightColor, dayColor);
				    animatePlane(a, nightColor, sunsetColor);
				    isSunset = true;
				    isNight = false;
				    isDay = false;
				    isSunrise = false;
				 } else {
			 app.background(nightColor);
			 curSkyColor = nightColor;
			 isNight = true;
			    isDay = false;
			    isSunrise = false;
			    isSunset = false;
		 }
	 }
	 if(startTimer) {
		 timer--;
	 }
	  if(timer < 1) {
		  startTimer = false;
		  timer = frames;
	  }
	  if(startTimer) {
			 timer2--;
		 }
		  if(timer2 < 1) {
			  startTimer2 = false;
			  timer2 = frames2;
		  }
	  if(isSunset) {
		  if(inc > -400) {
			  inc -= 4200 / (frames + 6.66f);
		  }
	  } else if(isSunrise) {
		  if(inc < 3800) {
			  inc += 3800 / (frames);
		  }
	  }
	  
	  if(isSunrise) {
		  if(inc2 > -360) {
			  inc2 -= 4160 / (frames + 10);
		  }
	  } else if(isSunset) {
		  if(inc2 < 3800) {
			  inc2 += 3800 / (frames);
		  }
	  }
	  
	  if(position.z >= (128 * Minecraft.size) + chunkPos.z * (Chunk.w * Minecraft.size)) {
		  position.z = chunkPos.z * (Chunk.w * Minecraft.size) - (640 * Minecraft.size);
	  }
	  if(pos2.z >= (128 * Minecraft.size) + chunkPos.z * (Chunk.w * Minecraft.size) + 6400) {
		  pos2.z = chunkPos.z * (Chunk.w * Minecraft.size) - (512 * Minecraft.size);
	  }
	  if(pos3.z >= (128 * Minecraft.size) + chunkPos.z * (Chunk.w * Minecraft.size) + 12800) {
		  pos3.z = chunkPos.z * (Chunk.w * Minecraft.size) - (384 * Minecraft.size);
	  }
	  if(pos4.z >= (128 * Minecraft.size) + chunkPos.z * (Chunk.w * Minecraft.size) + 6400) {
		  pos4.z = chunkPos.z * (Chunk.w * Minecraft.size) - (256 * Minecraft.size);
	  }
	  if(pos5.z >= (128 * Minecraft.size) + chunkPos.z * (Chunk.w * Minecraft.size) + 12800) {
		  pos5.z = chunkPos.z * (Chunk.w * Minecraft.size) - (128 * Minecraft.size);
	  }
	  PVector c = chunkPos.mult(Chunk.w * Minecraft.size);
	  position.z += 1f;
	  pos2.z += 3f;
	  pos3.z += 3f;
	  pos4.z += 3f;
	  pos5.z += 3f;
	  position.x += c.x - Minecraft.camera.prevNorm.x;
	  pos2.x += c.x - Minecraft.camera.prevNorm.x;
	  pos3.x += c.x - Minecraft.camera.prevNorm.x;
	  pos4.x += c.x - Minecraft.camera.prevNorm.x;
	  pos5.x += c.x - Minecraft.camera.prevNorm.x;
  }
  public void animateSky(float x, int col1, int col2) {
	  curSkyColor = lerpColor(col1, col2, PApplet.norm(x, 0, frames));
  }
  public void animatePlane(float x, int col1, int col2) {
	  startTimer = true;
	  startTimer2 = true;
	  app.background(lerpColor(col1, col2, PApplet.norm(x, 0, frames2)));
	  curWorldColor = lerpColor(col1, col2, PApplet.norm(x, 0, frames2));
  }
  public int lerpColor(int c1, int c2, float amt) {
	    if (amt < 0) amt = 0;
	    if (amt > 1) amt = 1;
	      float a1 = ((c1 >> 24) & 0xff);
	      float r1 = (c1 >> 16) & 0xff;
	      float g1 = (c1 >> 8) & 0xff;
	      float b1 = c1 & 0xff;
	      float a2 = (c2 >> 24) & 0xff;
	      float r2 = (c2 >> 16) & 0xff;
	      float g2 = (c2 >> 8) & 0xff;
	      float b2 = c2 & 0xff;

	      return ((PApplet.round(a1 + (a2-a1)*amt) << 24) |
	              (PApplet.round(r1 + (r2-r1)*amt) << 16) |
	              (PApplet.round(g1 + (g2-g1)*amt) << 8) |
	              (PApplet.round(b1 + (b2-b1)*amt)));
  }
}
