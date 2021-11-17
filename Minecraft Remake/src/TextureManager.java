public class TextureManager {

    public float imageSize;

    public TextureManager() {
    	imageSize = 256;
    }

    public TextureAtlasPosition getTextureIndex(int[] pos){
        float texturesPerRow = 16;
        float indvTexSize = 1.0f / texturesPerRow;
        float pixelSize = 1.0f / imageSize;

        float xMin = (pos[0] * indvTexSize) + 0.0f * pixelSize;
        float yMin = (pos[1] * indvTexSize) + 0.0f * pixelSize;

        float xMax = (xMin + indvTexSize) - 0.0f * pixelSize;
        float yMax = (yMin + indvTexSize) - 0.0f * pixelSize;
        return new TextureAtlasPosition(xMin, yMin, xMax, yMax);
    }
}