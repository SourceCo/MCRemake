import processing.core.PShape;
import processing.core.PVector;
public class Geometry {
  public static void constructBlock(PShape shape, int size, String name, PVector position, TextureManager manager, MultiTexture textures, boolean front, boolean right, boolean back, boolean left, boolean bottom, boolean top, renderType type, biome b) {
	if(type == renderType.BLOCK) {
			  shape.tint(155);
		    	shape.normal(0, 0, 1);
			  if(name == "Leaves") {
			    	if(b == biome.FOREST) {
			    	    shape.tint(90, 170, 50);
			    	    	} else if(b == biome.SAVANNA) {
			    	    shape.tint(170, 160, 40);
			    	    	} else if(b == biome.JUNGLE) {
			          shape.tint(50, 190, 10);
			    	    	} else if(b == biome.GRASSLAND || b == biome.WOODLAND || b == biome.PLAINS) {
			    	    		shape.tint(120, 170, 50);
			    	    	} else if(b == biome.TAIGA) {
			    	    		shape.tint(100, 160, 100);
			    	    	} else if(b == biome.MOUNTAIN) {
			    	    		shape.tint(110, 160, 110);
			    	    	}
			    }
	    if (front) {
	    	shape.vertex(-size/2 + (position.x * size), -size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).minY);
	        shape.vertex(size/2 + (position.x * size), -size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).minY);
	        shape.vertex(size/2 + (position.x * size), size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).maxY);
	        shape.vertex(-size/2 + (position.x * size), size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).maxY);
	    }
	    shape.tint(205);
    	shape.normal(1, 0, 0);
	    if(name == "Leaves") {
	    	if(b == biome.FOREST) {
	    	    shape.tint(90, 170, 50);
	    	    	} else if(b == biome.SAVANNA) {
	    	    shape.tint(170, 160, 40);
	    	    	} else if(b == biome.JUNGLE) {
	          shape.tint(50, 190, 10);
	    	    	} else if(b == biome.GRASSLAND || b == biome.WOODLAND || b == biome.PLAINS) {
	    	    		shape.tint(120, 170, 50);
	    	    	} else if(b == biome.TAIGA) {
	    	    		shape.tint(100, 160, 100);
	    	    	} else if(b == biome.MOUNTAIN) {
	    	    		shape.tint(110, 160, 110);
	    	    	}
	    }
	    if (right) {
	    	shape.vertex(size/2 + (position.x * size), -size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).minY);
	    	shape.vertex(size/2 + (position.x * size), -size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).minY);
	    	shape.vertex(size/2 + (position.x * size), size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).maxY);
	    	shape.vertex(size/2 + (position.x * size), size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).maxY);
	    }
	    shape.tint(155);
    	shape.normal(0, 0, -1);
	    if(name == "Leaves") {
	    	if(b == biome.FOREST) {
	    	    shape.tint(90, 170, 50);
	    	    	} else if(b == biome.SAVANNA) {
	    	    shape.tint(170, 160, 40);
	    	    	} else if(b == biome.JUNGLE) {
	          shape.tint(50, 190, 10);
	    	    	} else if(b == biome.GRASSLAND || b == biome.WOODLAND || b == biome.PLAINS) {
	    	    		shape.tint(120, 170, 50);
	    	    	} else if(b == biome.TAIGA) {
	    	    		shape.tint(100, 160, 100);
	    	    	} else if(b == biome.MOUNTAIN) {
	    	    		shape.tint(110, 160, 110);
	    	    	}
	    }
	    if (back) {
	    	shape.vertex(size/2 + (position.x * size), -size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).minY);
	    	shape.vertex(-size/2 + (position.x * size), -size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).minY);
	    	shape.vertex(-size/2 + (position.x * size), size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).maxY);
	    	shape.vertex(size/2 + (position.x * size), size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).maxY);
	    }
	    shape.tint(205);
    	shape.normal(-1, 0, 0);
	    if(name == "Leaves") {
	    	if(b == biome.FOREST) {
	    	    shape.tint(90, 170, 50);
	    	    	} else if(b == biome.SAVANNA) {
	    	    shape.tint(170, 160, 40);
	    	    	} else if(b == biome.JUNGLE) {
	          shape.tint(50, 190, 10);
	    	    	} else if(b == biome.GRASSLAND || b == biome.WOODLAND || b == biome.PLAINS) {
	    	    		shape.tint(120, 170, 50);
	    	    	} else if(b == biome.TAIGA) {
	    	    		shape.tint(100, 160, 100);
	    	    	} else if(b == biome.MOUNTAIN) {
	    	    		shape.tint(110, 160, 110);
	    	    	}
	    }
	    if (left) {
	    	shape.vertex(-size/2 + (position.x * size), -size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).minY);
	    	shape.vertex(-size/2 + (position.x * size), -size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).minY);
	    	shape.vertex(-size/2 + (position.x * size), size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).maxY);
	    	shape.vertex(-size/2 + (position.x * size), size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).maxY);
	    }
	    shape.tint(255);
    	shape.normal(0, 1, 0);
	    if(name == "GrassBlock") {
	    	if(b == biome.FOREST) {
	    shape.tint(120, 190, 90);
	    	} else if(b == biome.SAVANNA) {
	    shape.tint(190, 190, 90);
	    	} else if(b == biome.JUNGLE) {
      shape.tint(90, 200, 60);
	    	} else if(b == biome.GRASSLAND || b == biome.WOODLAND || b == biome.PLAINS) {
	    		shape.tint(150, 190, 90);
	    	} else if(b == biome.TAIGA) {
	    		shape.tint(130, 180, 130);
	    	} else if(b == biome.MOUNTAIN) {
	    		shape.tint(140, 180, 140);
	    	}
	    } else if(name == "Leaves") {
	    	if(b == biome.FOREST) {
	    	    shape.tint(90, 170, 50);
	    	    	} else if(b == biome.SAVANNA) {
	    	    shape.tint(170, 160, 40);
	    	    	} else if(b == biome.JUNGLE) {
	          shape.tint(50, 190, 10);
	    	    	} else if(b == biome.GRASSLAND || b == biome.WOODLAND || b == biome.PLAINS) {
	    	    		shape.tint(120, 170, 50);
	    	    	} else if(b == biome.TAIGA) {
	    	    		shape.tint(100, 160, 100);
	    	    	} else if(b == biome.MOUNTAIN) {
	    	    		shape.tint(110, 160, 110);
	    	    	}
	    }
	    if (top) {
	    	shape.vertex(-size/2 + (position.x * size), -size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.TOP).minX, manager.getTextureIndex(textures.TOP).minY);
	    	shape.vertex(size/2 + (position.x * size), -size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.TOP).maxX, manager.getTextureIndex(textures.TOP).minY);
	    	shape.vertex(size/2 + (position.x * size), -size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.TOP).maxX, manager.getTextureIndex(textures.TOP).maxY);
	    	shape.vertex(-size/2 + (position.x * size), -size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.TOP).minX, manager.getTextureIndex(textures.TOP).maxY);
	    }
	    shape.tint(100);
		   shape.normal(0, -1, 0);
	    if(name == "Leaves") {
	    	if(b == biome.FOREST) {
	    	    shape.tint(90, 170, 50);
	    	    	} else if(b == biome.SAVANNA) {
	    	    shape.tint(170, 160, 40);
	    	    	} else if(b == biome.JUNGLE) {
	          shape.tint(50, 190, 10);
	    	    	} else if(b == biome.GRASSLAND || b == biome.WOODLAND || b == biome.PLAINS) {
	    	    		shape.tint(120, 170, 50);
	    	    	}
	    	    	 else if(b == biome.TAIGA) {
		    	    		shape.tint(100, 160, 100);
		    	    	} else if(b == biome.MOUNTAIN) {
		    	    		shape.tint(110, 160, 110);
		    	    	}
	    }
		   if (bottom) {
			   shape.vertex(-size/2 + (position.x * size), size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.BOTTOM).minX, manager.getTextureIndex(textures.BOTTOM).minY);
			   shape.vertex(size/2 + (position.x * size), size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.BOTTOM).maxX, manager.getTextureIndex(textures.BOTTOM).minY);
			   shape.vertex(size/2 + (position.x * size), size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.BOTTOM).maxX, manager.getTextureIndex(textures.BOTTOM).maxY);
			   shape.vertex(-size/2 + (position.x * size), size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.BOTTOM).minX, manager.getTextureIndex(textures.BOTTOM).maxY);
		   }
			  shape.tint(255);
	} else if(type == renderType.SPRITE) {
		  if(name == "TallGrass") {
		    	if(b == biome.FOREST) {
		    	    shape.tint(120, 190, 90);
		    	    	} else if(b == biome.SAVANNA) {
		    	    shape.tint(190, 190, 90);
		    	    	} else if(b == biome.JUNGLE) {
		          shape.tint(90, 200, 60);
		    	    	} else if(b == biome.GRASSLAND || b == biome.WOODLAND || b == biome.PLAINS) {
		    	    		shape.tint(150, 190, 90);
		    	    	} else if(b == biome.TAIGA) {
		    	    		shape.tint(130, 180, 130);
		    	    	} else if(b == biome.MOUNTAIN) {
		    	    		shape.tint(140, 180, 140);
		    	    	}
		  } else {
			  shape.tint(255);
		  }
		  shape.vertex(-size/2 + (position.x * size), -size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).minY);
	    	shape.vertex(size/2 + (position.x * size), -size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).minY);
	    	shape.vertex(size/2 + (position.x * size), size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).maxY);
	    	shape.vertex(-size/2 + (position.x * size), size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).maxY);
	    	
	    	shape.vertex(-size/2 + (position.x * size), -size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).minY);
	    	shape.vertex(size/2 + (position.x * size), -size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).minY);
	    	shape.vertex(size/2 + (position.x * size), size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).maxY);
	    	shape.vertex(-size/2 + (position.x * size), size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).maxY);
	    	
	    	shape.vertex(size/2 + (position.x * size), -size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).minY);
	    	shape.vertex(-size/2 + (position.x * size), -size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).minY);
	    	shape.vertex(-size/2 + (position.x * size), size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).maxY);
	    	shape.vertex(size/2 + (position.x * size), size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).maxY);
	    	
	    	shape.vertex(size/2 + (position.x * size), -size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).minY);
	    	shape.vertex(-size/2 + (position.x * size), -size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).minY);
	    	shape.vertex(-size/2 + (position.x * size), size/2 + (position.y * size), size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).maxX, manager.getTextureIndex(textures.SIDES).maxY);
	    	shape.vertex(size/2 + (position.x * size), size/2 + (position.y * size), -size/2 + (position.z * size), manager.getTextureIndex(textures.SIDES).minX, manager.getTextureIndex(textures.SIDES).maxY);
	}
  }
}
