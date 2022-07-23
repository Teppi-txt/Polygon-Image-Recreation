import java.awt.*;
import java.awt.image.BufferedImage;

public class Ellipse extends Shape implements Cloneable{
    int x;
    int y;
    int width;
    int height;
    Color color;

    //two constructors for with color (mutations) and without (generation)
    public Ellipse(int x, int y, int width, int height, float rotation, boolean filled, Color color) {
        super("Ellipse", rotation, filled);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public Ellipse(int x, int y, int width, int height, float rotation, boolean filled) {
        super("Ellipse", rotation, filled);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Shape clone() throws CloneNotSupportedException {
        Ellipse tempShape = (Ellipse) super.clone();
        tempShape.color = new Color(color.getRed(), color.getBlue(), color.getGreen());
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
        if (filled) canvas.fillOval(x, y, width, height); //draw filled if filled
        else canvas.drawOval(x, y, width, height); //else draw outline
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
        //color = new Color(tempColor.getRed(), tempColor.getGreen(), tempColor.getBlue(), (int) Math.min(Math.max((color.getAlpha() + ((Math.random() * 2) - 1) * mutateValue), 0), 255));
    }
}
