import processing.core.PVector;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class chunkManagerThread extends Thread {
	public PVector position = new PVector(0, 0, 0);
	public boolean start = false;
	public ArrayList<PVector> toDestroy = new ArrayList<PVector>();
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			if(start) {
        	  for(PVector p : Minecraft.chunks.keySet()) {
        		try {
        		  if(!Minecraft.aroundPlayer(p, position)) {
        			  Minecraft.chunks.remove(p);
        		    }
        		  } catch (Exception e) {
        			  e.printStackTrace();
        		  }
        	  }
        	  System.gc();
        	  start = false;
			}
         }
    }
	public void destroy(PVector p) {
		position = p;
		start = true;
	}
}
