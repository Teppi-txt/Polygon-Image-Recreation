import java.awt.*;
import java.awt.image.BufferedImage;

public class Polygon extends Shape implements Cloneable{
    int[] xCoords;
    int[] yCoords;
    Color color;

    public Polygon(int[] xCoords, int[] yCoords, float rotation, boolean filled, Color color) {
        super("Polygon", rotation, filled);
        this.xCoords = xCoords;
        this.yCoords = yCoords;
        this.color = color;
    }

    public Polygon(int[] xCoords, int[] yCoords, float rotation, boolean filled) {
        super("Polygon", rotation, filled);
        this.xCoords = xCoords;
        this.yCoords = yCoords;
    }

    public Shape clone() throws CloneNotSupportedException {
        Polygon tempShape = (Polygon) super.clone();
        tempShape.xCoords = new int[xCoords.length];
        tempShape.yCoords = new int[yCoords.length];
        for (int i = 0; i < xCoords.length; i++) {
            tempShape.xCoords[i] = xCoords[i];
            tempShape.yCoords[i] = yCoords[i];
        }
        return tempShape;
    }

    public float[] getCenter() {
        int minX = Integer.MAX_VALUE; int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE; int maxY = Integer.MIN_VALUE;
        for (int value : xCoords) {
            if (value > maxX) maxX = value;
            if (value < minX) minX = value;
        }
        for (int value : yCoords) {
            if (value > maxY) maxY = value;
            if (value < minY) minY = value;
        }
        return new float[]{(minX + maxX)/2F, (minY + maxY)/2F};
    }

    public void draw(BufferedImage image) {
        //pivot to rotate the polygon around
        float[] pivot = getCenter();

        //converts the image to a graphic2d component
        Graphics2D canvas = image.createGraphics();
        RenderingHints hints =new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvas.setRenderingHints(hints);
        canvas.setColor(color); //sets color of the drawn element to the shape color
        canvas.rotate(Math.toRadians(rotation), pivot[0], pivot[1]); //rots the canvas around the center of the shape
        if (filled) canvas.fillPolygon(xCoords, yCoords, xCoords.length); //draw filled if filled
        else canvas.drawPolygon(xCoords, yCoords, xCoords.length);; //else draw outline
    }

    public void setColor(Color c) {
        color = c;
    }

    public void mutate(int mutateValue, BufferedImage target) {
        for (int i = 0; i < xCoords.length; i++) {
            xCoords[i] += ((Math.random() * 2) - 1) * mutateValue;
            yCoords[i] += ((Math.random() * 2) - 1) * mutateValue;
        }
        rotation += ((Math.random() * 2) - 1) * mutateValue;
        color = ShapeGenerator.getAverageColorOfShape(this, target);
//        Color tempColor = ShapeGenerator.getAverageColorOfShape(this, target);
//        color = new Color(tempColor.getRed(), tempColor.getGreen(), tempColor.getBlue(), (int) Math.min(Math.max((color.getAlpha() + ((Math.random() * 2) - 1) * mutateValue), 0), 255));
    }
}
