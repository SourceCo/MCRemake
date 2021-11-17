import processing.core.PApplet;
public class MemoryProfiler {

    Runtime rt;

    long usedMB;
    
    public MemoryProfiler(PApplet app){
        rt = Runtime.getRuntime();
    }

    public void draw(PApplet applet, int x, int y) {
        usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
        applet.fill(255, 255, 255);
        applet.stroke(255, 255, 255);
        applet.text("Memory Used: " + usedMB + "MB", x, y);
    }
    public void printMemoryUsage() {
    	usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
    	System.out.println("Memory Used: " + usedMB + "MB");
    }

}