import processing.core.PApplet;
import processing.core.PConstants;
public class Noise {
	int octaves, amplitude, smoothness, heightOffset;
	double roughness;
	int seed;
	PApplet applet;
	FastNoise n;
	public Noise(int seed, PApplet app) {
    octaves = 7;
    amplitude = 64;
    smoothness = 256;
    heightOffset = 104;
    roughness = 0.5;
    this.seed = seed;
    applet = app;
    n = new FastNoise();
    n.SetNoiseType(FastNoise.NoiseType.Perlin);
    n.SetSeed(seed);
	}
void setParameters(int octaves, int amplitude, int smoothness, int heightOffset, int roughness) {
   this.octaves = octaves;
   this.amplitude = amplitude;
   this.smoothness = smoothness;
   this.heightOffset = heightOffset;
   this.roughness = roughness;
}

double getNoise(double d) {
	int n = (int) d;
    n += seed;
    n = (n << 13) ^ n;
    double newN = (n * (n * n * 60493 + 19990303) + 1376312589) & 0x7fffffff;

    return 1 - (newN / 1073741824.0);
}
double getNoise2(double d) {
	int n = (int) d;
    n += seed;
    n = (n << 13) ^ n;
    int newN = (n * (n * n * 60493 + 19990303) + 1376312589) & 0x7fffffff;

    return 1 - (newN / 1073741824.0);
}

double getNoise(double x, double z) {
//    return getNoise(x + z * 57.0);
//	return applet.noise((float) x, (float) z);
	return n.GetPerlin((float) (x * 171.103f), (float) (z * 171.103f));
}
double getNoise2(double x, double z) {
  return getNoise(x * 171.103 + z);
//	return applet.noise((float) x, (float) z);
//	return n.GetPerlin((float) (x * 171.103f), (float) (z * 171.103f));
}
double get3DNoise(double x, double y, double z) {
	return n.GetPerlin((float) (x * 171.103f), (float) (y * 171.103f), (float) (z * 171.103f));
}

double lerp(double a, double b, double z) {
    double mu2 = (1 - Math.cos(z * PConstants.PI)) / 2;
    return (a * (1 - mu2) + b * mu2);
}

double noise(double x, double z) {
	/*
    var floorX = (double)((int)x);
    var floorZ = (double)((int)z);
    var v = getNoise(floorX + 1, floorZ + 1);
    var s = getNoise(floorX, floorZ);
    var t = getNoise(floorX + 1, floorZ);
    var u = getNoise(floorX, floorZ + 1);
    		
    double rec1 = PApplet.lerp((float) s, (float) t, (float) (x - floorX));
    var rec2 = PApplet.lerp((float) u, (float) v, (float) (x - floorX));
    var rec3 = PApplet.lerp((float) rec1, (float) rec2, (float) (z - floorZ));
    return rec3;
    */
	return getNoise((float) x, (float) z);
}
double noise2(double x, double z) {
    var floorX = (double)((int)x);
    var floorZ = (double)((int)z);
    var v = getNoise2(floorX + 1, floorZ + 1);
    var s = getNoise2(floorX, floorZ);
    var t = getNoise2(floorX + 1, floorZ);
    var u = getNoise2(floorX, floorZ + 1);
    		
    double rec1 = PApplet.lerp((float) s, (float) t, (float) (x - floorX));
    var rec2 = PApplet.lerp((float) u, (float) v, (float) (x - floorX));
    var rec3 = PApplet.lerp((float) rec1, (float) rec2, (float) (z - floorZ));
    return rec3;
}
double noise3D(double x, double y, double z) {
 return get3DNoise(x, y, z);   
}
double getHeight(int x, int z, float chunkX, float chunkZ) {
    var newX = (x + chunkX * Chunk.w);
    var newZ = (z + chunkZ * Chunk.d);



    var totalValue = 0.0;

    for (var a = 0; a < octaves - 1; a++) {
        var frequency = Math.pow(2.0, a);
        var amplitude = Math.pow(roughness, a);
        totalValue += Math.pow(noise(((double)newX) * frequency / smoothness, ((double)newZ) * frequency / smoothness), 1) * amplitude;
    }

    var val = (((totalValue / 2.1) + 1.2) * amplitude) + heightOffset;

    return val > 0 ? val : 1;
  }
double getHeight2(int x, int z, float chunkX, float chunkZ) {
    var newX = (x + chunkX * Chunk.w);
    var newZ = (z + chunkZ * Chunk.d);



    var totalValue = 0.0;

    for (var a = 0; a < octaves - 1; a++) {
        var frequency = Math.pow(2.0, a);
        var amplitude = Math.pow(roughness, a);
        totalValue += Math.pow(noise2(((double)newX) * frequency / smoothness, ((double)newZ) * frequency / smoothness), 1) * amplitude;
    }

    var val = (((totalValue / 2.1) + 1.2) * amplitude) + heightOffset;

    return val > 0 ? val : 1;
  }
double getHeight(int x, int y, int z, float chunkX, float chunkZ) {
    var newX = (x + chunkX * Chunk.w);
    var newZ = (z + chunkZ * Chunk.d);



    var totalValue = 0.0;

    for (var a = 0; a < octaves - 1; a++) {
        var frequency = Math.pow(2.0, a);
        var amplitude = Math.pow(roughness, a);
        totalValue += Math.pow(noise3D(((double)newX) * frequency / smoothness, ((double)y) * frequency / smoothness, ((double)newZ) * frequency / smoothness), 1) * amplitude;
    }

    var val = (((totalValue / 2.1) + 1.2) * amplitude) + heightOffset;

    return val > 0 ? val : 1;
  }
}