import processing.core.PVector;
import java.util.ArrayList;
public class MeshGenThread extends Thread {
  public ArrayList<PVector> toGenerate = new ArrayList<PVector>();
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      for (int x = 0; x < toGenerate.size(); x++) {
        try {
          if (Minecraft.chunks.containsKey(toGenerate.get(x))) {
            Minecraft.chunks.get(toGenerate.get(x)).generateMesh();
          }
        } 
        catch (Exception e) {
          toGenerate.remove(x);
          break;
        }
        toGenerate.remove(x);
      }
      /*
      for(int x = 0; x < toGenerate.size(); x++) {
    	  int i = (int) toGenerate.get(x).x;
    	  int j = (int) toGenerate.get(x).z;
			  try {
			  Minecraft.chunks.get(new PVector(i, 0, j)).left = Minecraft.chunks.get(new PVector(i - 1, 0, j));
			  } catch (Exception e) {
				  
			  }
			  try {
				  Minecraft.chunks.get(new PVector(i, 0, j)).right = Minecraft.chunks.get(new PVector(i + 1, 0, j));
				  } catch (Exception e) {
					  
				  }
			  try {
				  Minecraft.chunks.get(new PVector(i, 0, j)).front = Minecraft.chunks.get(new PVector(i, 0, j - 1));
				  } catch (Exception e) {
					  
				  }
			  try {
				  Minecraft.chunks.get(new PVector(i, 0, j)).back = Minecraft.chunks.get(new PVector(i, 0, j + 1));
				  } catch (Exception e) {
					  
				  }
			  try {
				  Minecraft.chunks.get(new PVector(i, 0, j)).generateMesh();
				  toGenerate.remove(x);
			  } catch (Exception e) {
				  e.printStackTrace();
			  }
	  }
	  */
    }
  }
  public void sendChunk(PVector p) {
    toGenerate.add(p);
  }
}
