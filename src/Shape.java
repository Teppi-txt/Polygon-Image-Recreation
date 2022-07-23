import java.awt.*;
import java.awt.image.BufferedImage;

public class Shape {
    //all shapes should have a rotation, color, and type
    float rotation;
    Color color;
    boolean filled;
    String type;

    public Shape(String type, float rotation, boolean filled) {
        this.type = type;
        this.rotation = rotation;
        this.filled = filled;
    }

    public Shape clone() throws CloneNotSupportedException {
        Shape tempShape = (Shape) super.clone();
        //tempShape.color = new Color(color.getRed(), color.getBlue(), color.getGreen());
        return tempShape;
    }
    public void draw(BufferedImage image) {
        System.out.println("ERROR: cannot draw null shape");
    }

    public void setColor(Color red) {
        System.out.println("ERROR: cannot set color of null shape");
    }

    public void mutate(int mutateValue, BufferedImage target) {
        System.out.println("ERROR: cannot mutate null shape");
    }
}
