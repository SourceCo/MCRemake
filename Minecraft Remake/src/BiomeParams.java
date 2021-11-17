public class BiomeParams {
byte surfaceBlock, soilBlock, plantBlock, waterBlock, sandBlock, stoneBlock, snowBlock;
float treeFrequency;
double roughness = 0.65;
int smoothness = 256;
public BiomeParams(biome b) {
  switch(b) {
    case FOREST: 
  surfaceBlock = 2;
  soilBlock = 1;
  plantBlock = 8;
  waterBlock = 6;
  sandBlock = 3;
  stoneBlock = 4;
  snowBlock = 10;
  treeFrequency = 0.0001f;
  break;
    case JUNGLE:
    	  surfaceBlock = 2;
    	  soilBlock = 1;
    	  plantBlock = 8;
    	  waterBlock = 6;
    	  sandBlock = 3;
    	  stoneBlock = 4;
    	  snowBlock = 10;
    	  treeFrequency = 0.00025f;
    	  roughness = 0.5;
    	break;
    case GRASSLAND:
  	  surfaceBlock = 2;
  	  soilBlock = 1;
  	  plantBlock = 8;
  	  waterBlock = 6;
  	  sandBlock = 3;
  	  stoneBlock = 4;
  	  snowBlock = 10;
  	  treeFrequency = 0.00001f;
  	break;
    case PLAINS:
    	  surfaceBlock = 2;
    	  soilBlock = 1;
    	  plantBlock = 8;
    	  waterBlock = 6;
    	  sandBlock = 2;
    	  stoneBlock = 4;
    	  snowBlock = 10;
    	  treeFrequency = 0.000005f;
    	break;
    case WOODLAND:
    	  surfaceBlock = 2;
    	  soilBlock = 1;
    	  plantBlock = 8;
    	  waterBlock = 6;
    	  sandBlock = 3;
    	  stoneBlock = 4;
    	  snowBlock = 10;
    	  treeFrequency = 0.000015f;
    	break;
    case TAIGA:
      surfaceBlock = 2;
  	  soilBlock = 1;
  	  plantBlock = 8;
  	  waterBlock = 6;
  	  sandBlock = 3;
  	  stoneBlock = 4;
  	  snowBlock = 10;
  	  treeFrequency = 0.0000125f;
    	break;
    case DESERT:
    	  surfaceBlock = 3;
    	  soilBlock = 3;
    	  plantBlock = 8;
    	  waterBlock = 6;
    	  sandBlock = 3;
    	  stoneBlock = 12;
    	  snowBlock = 10;
    	  treeFrequency = 0.00025f;
    	  break;
    case OCEAN:
      surfaceBlock = 6;
  	  soilBlock = 6;
  	  plantBlock = 0;
  	  waterBlock = 6;
  	  sandBlock = 3;
  	  stoneBlock = 4;
  	  snowBlock = 10;
  	  treeFrequency = 0.0f;
    	break;
    case TUNDRA:
  	  surfaceBlock = 10;
  	  soilBlock = 1;
  	  plantBlock = 0;
  	  waterBlock = 13;
  	  sandBlock = 10;
  	  stoneBlock = 4;
  	  snowBlock = 10;
  	  treeFrequency = 0.0f;
  	  break;
    case MOUNTAIN:
    	surfaceBlock = 4;
    	  soilBlock = 1;
    	  plantBlock = 8;
    	  waterBlock = 6;
    	  sandBlock = 4;
    	  stoneBlock = 4;
    	  snowBlock = 10;
    	  treeFrequency = 0.000005f;
    	  roughness = 0.7;
    	break;
    case SAVANNA:
      surfaceBlock = 2;
  	  soilBlock = 1;
  	  plantBlock = 8;
  	  waterBlock = 6;
  	  sandBlock = 3;
  	  stoneBlock = 4;
  	  snowBlock = 10;
  	  treeFrequency = 0.0000625f;
    	break;
    case ICE:
    	  surfaceBlock = 13;
    	  soilBlock = 4;
    	  plantBlock = 0;
    	  waterBlock = 13;
    	  sandBlock = 10;
    	  stoneBlock = 4;
    	  snowBlock = 10;
    	  treeFrequency = 0.0f;
    	break;
    case BEACH:
      surfaceBlock = 3;
  	  soilBlock = 3;
  	  plantBlock = 0;
  	  waterBlock = 6;
  	  sandBlock = 3;
  	  stoneBlock = 12;
  	  snowBlock = 10;
  	  treeFrequency = 0.0f;
  	break;
	}
}
}
