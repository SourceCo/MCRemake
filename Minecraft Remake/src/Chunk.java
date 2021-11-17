import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PShape;
import processing.core.PConstants;
import java.util.ArrayList;
import java.util.Random;
class Chunk {
  public static int w = 16, h = 256, d = 16;
  public int size;
  PVector position = new PVector(0, 0, 0);
  PShape mesh, transparentMesh;
  ArrayList<PVector> trees = new ArrayList<PVector>();
  Chunk left, right, front, back;
  PApplet app;
  int maxValue;
  int minValue = 155, maxValue2 = 0;
  boolean show = true;
  boolean generated = false;
  public byte[][][] chunkBinary;
  int[] biomeMap;
  public Chunk(PApplet p, float x, float y, float z) {
	  app = p;
	  position = new PVector(x, y, z);
	  chunkBinary = new byte[w][h][d];
	  size = Minecraft.size;
	  biomeMap = generateBiomeMap();
  }
  public void setPosition(int x, int y, int z) {
	position = new PVector(x, y, z);
  }
  public float noise(int x, int y) {
//	  return Minecraft.generator.GetNoise(x, y);
	  return 0;
  }
  public int[] generateHeightMap() {
	int[] map = new int[w * d];
	for(int i = 0; i < w; i++) {
		for(int j = 0; j < d; j++) {
//			float e = noise(x, y) + 0.5f * noise(2 * x, 2 * y) + 0.25f * noise(4 * x, 4 * y);
//			e = e / (1f + 0.5f + 0.25f);
//			biome b = biome(biomeMap[i * w + j]);
//			BiomeParams params = new BiomeParams(b);
//			Minecraft.gen.smoothness = params.smoothness;
//			Minecraft.gen.roughness = params.roughness;
		    double e = Minecraft.gen.getHeight(i, 0, j, position.x, position.z);
		    int a = (int) e;
			map[i * w + j] = a;
			maxValue = (int) Math.max(maxValue, a);
			minValue = (int) Math.min(minValue, a);
		}
	}
	return map;
  }
  public int[] generate3DHeightMap(int y) {
	int[] map = new int[w * d];
	for(int i = 0; i < w; i++) {
		for(int j = 0; j < d; j++) {
//			float e = noise(x, y) + 0.5f * noise(2 * x, 2 * y) + 0.25f * noise(4 * x, 4 * y);
//			e = e / (1f + 0.5f + 0.25f);
//			Minecraft.gen.smoothness = params.smoothness;
//			Minecraft.gen.roughness = params.roughness;
		    double e = Minecraft.gen.getHeight(i, j, position.x, position.z);
		    int a = (int) e;
			map[i * w + j] = a;
			maxValue = (int) Math.max(maxValue, a);
			minValue = (int) Math.min(minValue, a);
		}
	}
	return map;
  }
  biome getBiome(int x, int z) {
	  int value = biomeMap[x * w + z];
	  if(value > 95) {
		  return biome.DESERT;
	  } else if(value > 88) {
		  return biome.FOREST;
	  } else {
		  return biome.TUNDRA;
	  }
	}
  public int[] generateBiomeMap() {
    int[] map = new int[w * d];
	for(int i = 0; i < w; i++) {
		for(int j = 0; j < d; j++) {
//			float e = noise(x, y) + 0.5f * noise(2 * x, 2 * y) + 0.25f * noise(4 * x, 4 * y);
//			e = e / (1f + 0.5f + 0.25f);
		    double e = Minecraft.biomeNoise.getHeight2(i, j, position.x, position.z);
			map[i * w + j] = (int) e;
		}
	}
	return map;
  }

  public biome biome(int a) {
	  if (a > 186) return biome.BEACH;
	  else if (a > 176) return biome.FOREST;
	  else if (a > 166) return biome.JUNGLE;
	  else if (a > 156) return biome.SAVANNA;
	  else if (a > 150) return biome.TUNDRA;
	  else return biome.TUNDRA;
  }
  public String getName(int x) {
	  String name = "";
	  switch(x) {
		 case 0:
		 break;
		 
		 case 1:
		name = "DirtBlock";
		 break;
		 
		 case 2:
		name = "GrassBlock";
	     break;
	     
		 case 3:
		name = "SandBlock";
		 break;
		 
		 case 4:
		name = "StoneBlock";
	     break;
	     
		 case 5:
		name = "OakBlock";
		 break;
		 
		 case 6:
		name = "WaterBlock";
		 break;
		 
		 case 7:
		name = "Leaves";
		 break;
		 
		 case 8:
		name = "TallGrass";
	     break;
		 
		 case 9:
		name = "Bedrock";
		break;
		 case 10:
			 name = "SnowBlock";
			 break;
		 case 11:
			 name = "Cactus";
			 break;
		 case 12:
			 name = "SandStone";
			 break;
		 case 13:
			 name = "IceBlock";
			 break;
		 case 14:
			 name = "RedFlower";
			 break;
		 case 15:
			 name = "YellowFlower";
			 break;
		 }
	  return name;
  }
  public void generateTerrain() {
	int[] map = generateHeightMap();
	Random rand = new Random((long) (position.x * w * position.z));
	int waterLevel = h - 64;
	if(!generated) {
	  for (int i = 0; i < w; i++) {
		for (int j = 0; j < d; j++) {
			int a = map[i * w + j];
			biome curBiome = biome(biomeMap[i * w + j]);
			BiomeParams params = new BiomeParams(curBiome);
			for(int k = 0; k < h; k++) {
				if(k < h) {
				if(k == waterLevel && chunkBinary[i][k][j] == 0 && k < a) {
					chunkBinary[i][k][j] = params.waterBlock;
					if(k == waterLevel) {
					}
				  }
				if(k < a) {
					continue;
				} else if(k == a && k + 3 <= waterLevel) {
					chunkBinary[i][k][j] = params.surfaceBlock;
				} else if (k == a) {
					chunkBinary[i][k][j] = params.sandBlock;
				} else {
					if(a < k - 4) {
					chunkBinary[i][k][j] = params.stoneBlock;
					} else {
						chunkBinary[i][k][j] = params.soilBlock;
					}
				}
				if(k >= h - 1) {
					chunkBinary[i][k][j] = 9;
				}
				if((float) rand.nextDouble() <= params.treeFrequency && a < waterLevel && chunkBinary[i][a][j] == 2) {
					   generateTree(PApplet.constrain(i, 2, 13), a, PApplet.constrain(j, 2, 13));	
				}
				if((float) rand.nextDouble() <= params.treeFrequency && a < waterLevel && chunkBinary[i][a][j] == 3 && curBiome == biome.DESERT) {
					   generateCactus(PApplet.constrain(i, 2, 13), a, PApplet.constrain(j, 2, 13));	
				}
				if((float) rand.nextDouble() <= 0.0001 && a < waterLevel && chunkBinary[i][a][j] == 2 && chunkBinary[i][a - 1][j] == 0) {
					chunkBinary[i][a - 1][j] = params.plantBlock;
				    }
				  }
				if((float) rand.nextDouble() <= params.treeFrequency && a < waterLevel && chunkBinary[i][a][j] == 2) {
					if((float) rand.nextDouble() <= 0.5f) {
					   chunkBinary[i][a - 1][j] = 14;
					} else {
					   chunkBinary[i][a - 1][j] = 15;
					}
				}
				if(k >= waterLevel && chunkBinary[i][k][j] == 1) {
					chunkBinary[i][k][j] = params.sandBlock;
				}
	  }
	}
	  }
	}
	generated = true;
  }
  public void generate3DTerrain() {
	  int[] map = generate3DHeightMap(0);
		int waterLevel = h - 64;
		if(!generated) {
		  for (int i = 0; i < w; i++) {
			for (int j = 0; j < d; j++) {
				for(int k = 0; k < h; k++) {
					int a = map[i * w + j];
					if(k < h) {
					if(k < a) {
						continue;
					} else if(k == a && k + 3 <= waterLevel) {
						chunkBinary[i][k][j] = 4;
					} else if (k == a) {
						chunkBinary[i][k][j] = 4;
					} else {
						if(a < k - 4) {
						chunkBinary[i][k][j] = 4;
						} else {
							chunkBinary[i][k][j] = 4;
						}
					}
					if(k >= h - 1) {
						chunkBinary[i][k][j] = 9;
					}
		  }
		}
		  }
		}
		}
		generated = true;
	  }
  public boolean intersectsMesh(int x, int y, int z) {
	  return true;
  }
  public void generateCactus(int x, int h, int z) {
	  Random rand = new Random((long) (position.x * w + position.z));
	  int height = (int) Math.round(rand.nextDouble() * 6);
	  for(int i = 0; i < height; i++) {
		  chunkBinary[x][h - i][z] = 11;
	  }
   }
  public boolean generate(int x, int h, int z) {
  	  boolean gen = true;
  	  for(int i = 0; i < trees.size(); i++) {
  		  if(trees.get(i).equals(new PVector(x, trees.get(i).y, z))) {
  			  gen = false;
  		  } else if(trees.get(i).equals(new PVector(x - 1, trees.get(i).y, z))) {
  			  gen = false;
  		  } else if(trees.get(i).equals(new PVector(x + 1, trees.get(i).y, z))) {
  			  gen = false;
  		  } else if(trees.get(i).equals(new PVector(x, trees.get(i).y, z - 1))) {
  			  gen = false;
  		  } else if(trees.get(i).equals(new PVector(x, trees.get(i).y, z + 1))) {
  			  gen = false;
  		  } else if(trees.get(i).equals(new PVector(x - 1, trees.get(i).y, z - 1))) {
  			  gen = false;
  		  } else if(trees.get(i).equals(new PVector(x - 1, trees.get(i).y, z + 1))) {
  			  gen = false;
  		  } else if(trees.get(i).equals(new PVector(x + 1, trees.get(i).y, z - 1))) {
  			  gen = false;
  		  } else if(trees.get(i).equals(new PVector(x + 1, trees.get(i).y, z + 1))) {
  			  gen = false;
  		  }
  	  }
  	  return gen;
  }
  public void generateTree(int x, int h, int z) {
	  Random rand = new Random((long) (x * w + z));
  	  boolean gen = generate(x, h, z);
  	  if(gen) {
  		  trees.add(new PVector(x, h, z));
  	  int height = (int) (Math.round(Math.abs(rand.nextDouble()) * 11));
  	  for(int i = 1; i < height; i++) {
  		  chunkBinary[x][h - i][z] = 5;
  		if(i == height - 1 || i == height - 2) {
  		  if(inBounds(z, h - i, x + 1)) {
  		  chunkBinary[x][h - i][z + 1] = 7;
  		    }
  		  if(inBounds(z - 1, h - i, x + 1)) {
  			  chunkBinary[x - 1][h - i][z + 1] = 7;
  			    }
  		  if(inBounds(z - 1, h - i, x - 1)) {
  			  chunkBinary[x - 1][h - i][z - 1] = 7;
  			    }
  		  if(inBounds(z, h - i, x - 1)) {
  			  chunkBinary[x][h - i][z - 1] = 7;
  			    }
  		  if(inBounds(z + 1, h - i, x)) {
  			  chunkBinary[x + 1][h - i][z] = 7;
  			    }
  		  if(inBounds(z - 1, h - i, x)) {
  			  chunkBinary[x - 1][h - i][z] = 7;
  			    }
  		  if(inBounds(z + 1, h - i, x + 1)) {
  			  chunkBinary[x + 1][h - i][z + 1] = 7;
  			    }
  		  if(inBounds(z + 1, h - i, x - 1)) {
  			  chunkBinary[x + 1][h - i][z - 1] = 7;
  			    }
  		  if(i == height - 1) {
  		  if(inBounds(z, h - i - 1, x)) {
  			  chunkBinary[x][h - i - 1][z] = 7;
  			    }
  		    }
  		  }
  	    }
  	  }
    }
  public PVector getPixelPosition() {
	  PVector p = new PVector(0, 0, 0);
		            p.x = position.x * (w*size);
		            p.y = position.y * (h*size);
		            p.z = position.z * (d*size);
		            return p;
		          }
  public PVector getPos() {
	  PVector p = new PVector(0, 0, 0);
		            p.x = position.x * (w);
		            p.y = position.y * (h);
		            p.z = position.z * (d);
		            return p;
		          }
  public float round3(float x) {
	  float a = Math.round(x);
	  float b = a / size;
	  float c = Math.round(b);
	  float d = c * size;
	  return d;
	}
  public PVector round2(PVector p) {
	  return new PVector(round3(p.x), round3(p.y), round3(p.z));
  }
  boolean inBounds(double x, double y, double z) {
	  return x < w && y < h && z < d && x > 0 && y > 0 && z > 0;
  }
  public boolean isFaceAt(int x, int y, int z, PVector p, int a) {
	  int q = Chunk.w - 1;
	  int w = Chunk.d - 1;
		try {
	  if(chunkBinary[x][y][z] != 0) {
        if (isOpaque(chunkBinary[x][y][z])) {
	            return true;
	            }
	          if(!isOpaque(a) && !isOpaque(chunkBinary[x][y][z])) {
	        	  return true;
	          }
	       }
		} catch (Exception e) {
			if(x == p.x - 1) {
				if(left != null) {
					if(left.chunkBinary[q][y][z] != 0) {
				          if (left.isOpaque(left.chunkBinary[q][y][z])) {
					            return true;
					            }
					          if(!isOpaque(a) && !left.isOpaque(left.chunkBinary[q][y][z])) {
					        	  return true;
					          }
					       }
					}
				} else if(x == p.x + 1) {
			    if(right != null) {
					if(right.chunkBinary[0][y][z] != 0) {
				          if (right.isOpaque(right.chunkBinary[0][y][z])) {
					            return true;
					            }
					          if(!isOpaque(a) && !right.isOpaque(right.chunkBinary[0][y][z])) {
					        	  return true;
					          }
					       }
					}
				} else if(z == p.z - 1) {
					if(front != null) {
						if(front.chunkBinary[x][y][w] != 0) {
					          if (front.isOpaque(front.chunkBinary[x][y][w])) {
						            return true;
						            }
						          if(!isOpaque(a) && !front.isOpaque(front.chunkBinary[x][y][w])) {
						        	  return true;
						          }
						       }
						}
					} else if(z == p.z + 1) {
				    if(back != null) {
						if(back.chunkBinary[x][y][0] != 0) {
					          if (back.isOpaque(back.chunkBinary[x][y][0])) {
						            return true;
						            }
						          if(!isOpaque(a) && !back.isOpaque(back.chunkBinary[x][y][0])) {
						        	  return true;
						          }
						       }
						}
					}
		}
	  return false;
	  }
  public PVector getPosition(PVector p) {
	  return p.add(position);
  }
  public int getBlock(int x, int y, int z) {
	  try {
		  return chunkBinary[x][y][z];
	  } catch (Exception e) {
		  e.printStackTrace();
		  return 0;
	  }
  }
  public void removeBlock(int x, int y, int z) {
	  try {
		  if(chunkBinary[x][y][z] != 9) {
		  chunkBinary[x][y][z] = 0;
		  }
	  } catch (Exception e) {
		  
	  }
	  System.gc();
  }
  public void placeBlock(int x, int y, int z) {
	  try {
		  chunkBinary[x][y][z] = 4;
	  } catch (Exception e) {
		  
	  }
	  System.gc();
  }
  public MultiTexture getTexture(int x) {
	 MultiTexture textures = new MultiTexture();
	 switch(x) {
	 case 0:
	 break;
	 
	 case 1:
			textures.setTOP(2, 0);
			textures.setSIDES(2, 0);
			textures.setBOTTOM(2, 0);
	 break;
	 
	 case 2:
			textures.setTOP(0, 0);
	        textures.setSIDES(3, 0);
	        textures.setBOTTOM(2, 0);
     break;
     
	 case 3:
			textures.setTOP(2, 1);
			textures.setSIDES(2, 1);
			textures.setBOTTOM(2, 1);
	 break;
	 
	 case 4:
			textures.setTOP(1, 0);
			textures.setSIDES(1, 0);
			textures.setBOTTOM(1, 0);
     break;
     
	 case 5:
			textures.setTOP(5, 1);
	        textures.setBOTTOM(5, 1);
	        textures.setSIDES(4, 1);
	 break;
	 
	 case 6:
			textures.setTOP(13, 12);
			textures.setSIDES(13, 12);
			textures.setBOTTOM(13, 12);
	 break;
	 
	 case 7:
			textures.setTOP(4, 8);
	        textures.setSIDES(4, 8);
	        textures.setBOTTOM(4, 8);
	 break;
	 
	 case 8:
			textures.setTOP(7, 2);
	        textures.setSIDES(7, 2);
	        textures.setBOTTOM(7, 2);
     break;
     
	 case 9:
			textures.setTOP(1,1);
	        textures.setSIDES(1,1);
	        textures.setBOTTOM(1,1);
	        break;
	 case 10:
		   textures.setTOP(2, 4);
		   textures.setSIDES(4, 4);
		   textures.setBOTTOM(2, 0);
		   break;
	 case 11:
		 textures.setTOP(5, 4);
		 textures.setSIDES(6, 4);
		 textures.setBOTTOM(5, 4);
		 break;
	 case 12:
		 textures.setTOP(0, 11);
		 textures.setSIDES(0, 12);
		 textures.setBOTTOM(0, 13);
		 break;
	 case 13:
		 textures.setTOP(3, 4);
		 textures.setSIDES(3, 4);
		 textures.setBOTTOM(3, 4);
		 break;
	 case 14:
		 textures.setTOP(12, 0);
		 textures.setSIDES(12, 0);
		 textures.setBOTTOM(12, 0);
		 break;
	 case 15:
		 textures.setTOP(13, 0);
		 textures.setSIDES(13, 0);
		 textures.setBOTTOM(13, 0);
		 break;
	 }
	 return textures;
  }
  public renderType getType(int x) {
	  renderType type = null;
	  switch(x) {
		 case 0:
		 break;
		 
		 case 1:
		type = renderType.BLOCK;
		 break;
		 
		 case 2:
			 type = renderType.BLOCK;
	     break;
	     
		 case 3:
			 type = renderType.BLOCK;
		 break;
		 
		 case 4:
			 type = renderType.BLOCK;
	     break;
	     
		 case 5:
			 type = renderType.BLOCK;
		 break;
		 
		 case 6:
			 type = renderType.BLOCK;
		 break;
		 
		 case 7:
			 type = renderType.BLOCK;
		 break;
		 
		 case 8:
			 type = renderType.SPRITE;
	     break;
	     
		 case 9:
			 type = renderType.BLOCK;
			 break;
		 case 10:
			 type = renderType.BLOCK;
			 break;
		 case 11:
			 type = renderType.BLOCK;
			 break;
		 case 12:
			 type = renderType.BLOCK;
			 break;
		 case 13:
			 type = renderType.BLOCK;
			 break;
		 case 14:
			 type = renderType.SPRITE;
			 break;
		 case 15:
			 type = renderType.SPRITE;
			 break;
		 }
	  return type;  
  }
  public boolean belowGround(int x, int y, int z) {
	  if(y > getGround(x, z)) {
		  return true;
	  }
	  return false;
  }
  public boolean isEdge(int x, int z) {
	  if(x == 0) {
		  return true;
	  }
	  if(x >= w - 1) {
		  return true;
	  }
	  if(z == 0) {
		  return true;
	  }
	  if(z >= d - 1) {
		  return true;
	  }
	  return false;
  }
  public int getDir(int x, int z) {
	  if(x == 0) {
		  return 1;
	  }
	  if(x == w - 1) {
		  return 2;
	  }
	  if(z == 0) {
		  return 3;
	  }
	  if(z >= d - 1) {
		  return 4;
	  }
	  return 0;
  }
  public boolean isOpaque(int x) {
	  boolean isTransparent = false;
	  switch(x) {
		 case 0:
		isTransparent = true;
		 break;
		 
		 case 1:
		isTransparent = false;
		 break;
		 
		 case 2:
			 isTransparent = false;
	     break;
	     
		 case 3:
			 isTransparent = false;
		 break;
		 
		 case 4:
			 isTransparent = false;
	     break;
	     
		 case 5:
			 isTransparent = false;
		 break;
		 
		 case 6:
			 isTransparent = true;
		 break;
		 
		 case 7:
			 isTransparent = true;
		 break;
		 
		 case 8:
			 isTransparent = true;
	     break;
	     
		 case 9:
			 isTransparent = false;
			 break;
		 case 10:
			 isTransparent = false;
			 break;
		 case 11:
			 isTransparent = true;
			 break;
		 case 12:
			 isTransparent = false;
			 break;
		 case 13:
			 isTransparent = true;
		 case 14:
			 isTransparent = true;
		 case 15:
			 isTransparent = true;
		 }
	  return !isTransparent;
  }
  public boolean isAllSolid(int y) {
	  if(y < 0) {
		  throw new IllegalArgumentException("y cannot be less than 0!");
	  }
	  for(int i = 0; i < w; i++) {
		  for(int j = 0; j < d; j++) {
			  if(!isOpaque(chunkBinary[i][y][j])) {
				  return false;
			  }
		  }
	  }
	  return true;
  }
  public boolean isNoneSolid(int y) {
	  if(y < 0) {
		  throw new IllegalArgumentException("y cannot be less than 0!");
	  }
	  for(int i = 0; i < w; i++) {
		  for(int j = 0; j < d; j++) {
			  if(isOpaque(chunkBinary[i][y][j])) {
				  return false;
			  }
		  }
	  }
	  return true;
  }
  public int getGround(int x, int z) {
	  for(int y = 0; y < h; y++) {
		  if(chunkBinary[x][y][z] != 0) {
			  return y;
		  }
	  }
	  return 0;
  }
  public void generateMesh() {
	  mesh = app.createShape();
	  transparentMesh = app.createShape();
	  mesh.setTexture(Minecraft.textureAtlas);
	  transparentMesh.setTexture(Minecraft.textureAtlas);
	  mesh.beginShape(PConstants.QUADS);
	  transparentMesh.beginShape(PConstants.QUADS);
	  mesh.noStroke();
	  mesh.noFill();
	  mesh.tint(255);
	  transparentMesh.noStroke();
	  transparentMesh.noFill();
	  transparentMesh.tint(255);
	  for (int k = 0; k < h; k++) {
		  boolean skipLayer = false;
		  if(isAllSolid(k) && isAllSolid(k - 1)) {
			  skipLayer = true;
		  }
		  skipLayer = false;
		  if(!skipLayer) {
		    for (int i = 0; i < w; i++) {
		      for (int j = 0; j < d; j++) {
//		    	  int ground = getGround(i, j);
		    	  if(chunkBinary[i][k][j] != 0) {
		    		  boolean front, right, back, left, top, bottom;
		    		  front = right = back = left = top = bottom = false;
		    		  PVector pos = new PVector(i, k, j);
		    		  try {
		    		  front = !isFaceAt(i, k, j - 1, pos, chunkBinary[i][k][j]);
		    		  } catch (Exception e) {
		    			  front = true;
		    		  }
		    		  try {
		    		  right = !isFaceAt(i + 1, k, j, pos, chunkBinary[i][k][j]);
		    		  } catch (Exception e) {
		    			  right = true;
		    		  }
		    		  try {
		    		  back = !isFaceAt(i, k, j + 1, pos, chunkBinary[i][k][j]);
		    		  } catch (Exception e) {
		    			  back = true;
		    		  }
		    		  try {
		    		  left = !isFaceAt(i - 1, k, j, pos, chunkBinary[i][k][j]);
		    		  } catch (Exception e) {
		    			  left = true;
		    		  }
		    		  try {
		    		  top = !isFaceAt(i, k - 1, j, pos, chunkBinary[i][k][j]);
		    		  } catch (Exception e) {
		    			  top = true;
		    		  }
		    		  try {
		    		  bottom = !isFaceAt(i, k + 1, j, pos, chunkBinary[i][k][j]);
		    		  } catch (Exception e) {
		    			  bottom = true;
		    		  }
		    		  if(chunkBinary[i][k][j] == 6 || chunkBinary[i][k][j] == 13) {
		    			  bottom = false;
		    			  front = false;
		    			  right = false;
		    			  back = false;
		    			  left = false;
		    		  }
		    		  /*
		    		  boolean edge = isEdge(i, j);
		    		  int dir = getDir(i, j);
		    		  // if implementing caves fails because of this, come back here
		    		  if(k > ground && edge) {
		    			 if(dir != 0) {
		    			   if(dir == 1) {
		    				 left = false;  
		    			     } 
		    			   if(dir == 2) {
		    			    	 right = false;
		    			     } 
		    			   if(dir == 3) {
		    			    	 front = false;
		    			     } 
		    			   if(dir == 4) {
		    			    	 back = false;
		    			     }
		    			   }
		    			  front = false;
		    			  left = false;
		    			  right = false;
		    			  back = false;
		    			 }
		    			 */
		    		  biome curBiome = biome(biomeMap[i * w + j]);
		    		  if(isOpaque(chunkBinary[i][k][j])) {
		    			  Geometry.constructBlock(mesh, size, getName(chunkBinary[i][k][j]), pos, Minecraft.manager, getTexture(chunkBinary[i][k][j]), front, right, back, left, bottom, top, getType(chunkBinary[i][k][j]), curBiome);
		    		  } else {
		    			  Geometry.constructBlock(transparentMesh, size, getName(chunkBinary[i][k][j]), pos, Minecraft.manager, getTexture(chunkBinary[i][k][j]), front, right, back, left, bottom, top, getType(chunkBinary[i][k][j]), curBiome); 
		    		  }
		    	  }
	    }
	  }
	  }
	}
	  transparentMesh.endShape();
	  mesh.endShape();
	  generated = true;
  }

  public void render(PApplet app) {
	  if(mesh != null && show) {
	app.pushMatrix();
    app.translate(position.x * (w*size), position.y * (h*size), position.z * (d*size));
    app.shape(mesh);
    app.popMatrix();
	  }
  }
  public void render2(PApplet app) {
	  if(transparentMesh != null && show) {
	app.pushMatrix();
    app.translate(position.x * (w*size), position.y * (h*size), position.z * (d*size));
    app.shape(transparentMesh);
    app.popMatrix();
	  }
  }
}