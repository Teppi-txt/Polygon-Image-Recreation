import java.awt.*;
import java.awt.image.BufferedImage;

public class Rect extends Shape implements Cloneable{
    int x;
    int y;
    int width;
    int height;
    Color color;

    //two constructors for with color (mutations) and without (generation)
    public Rect(int x, int y, int width, int height, float rotation, boolean filled, Color color) {
        super("Rect", rotation, filled);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public Rect(int x, int y, int width, int height, float rotation, boolean filled) {
        super("Rect", rotation, filled);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Shape clone() throws CloneNotSupportedException {
        Rect tempShape = (Rect) super.clone();
        //tempShape.color = new Color(0, 0, 0);
        return tempShape;
    }

    public void draw(BufferedImage image) {
        //converts the image to a graphic2d component
        Graphics2D canvas = image.createGraphics();
        RenderingHints hints =new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvas.setRenderingHints(hints);
        canvas.setColor(color); //sets color of the drawn element to the shape color
        canvas.rotate(Math.toRadians(rotation), x + width/2.0, y + height/2.0); //rots the canvas around the center of the shape
        if (filled) canvas.fillRect(x, y, width, height); //draw filled if filled
        else canvas.drawRect(x, y, width, height); //else draw outline
    }

    public void setColor(Color c) {
        color = c;
    }

    public void mutate(int mutateValue, BufferedImage target) {
        x += ((Math.random() * 2) - 1) * mutateValue;
        y += ((Math.random() * 2) - 1) * mutateValue;
        width += ((Math.random() * 2) - 1) * mutateValue;
        height += ((Math.random() * 2) - 1) * mutateValue;
        rotation += ((Math.random() * 2) - 1) * mutateValue;
        color = ShapeGenerator.getAverageColorOfShape(this, target);
//        Color tempColor = ShapeGenerator.getAverageColorOfShape(this, target);
//        color = new Color(tempColor.getRed(), tempColor.getGreen(), tempColor.getBlue(), (int) Math.min(Math.max((color.getAlpha() + ((Math.random() * 2) - 1) * mutateValue), 0), 255));
    }
}
