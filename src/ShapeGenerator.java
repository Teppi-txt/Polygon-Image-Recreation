import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShapeGenerator {
    int maxX; int maxY; BufferedImage target;
    public ShapeGenerator (int maxX, int maxY, BufferedImage target) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.target = target;
    }

    public static Shape[] mutateShape(Shape shape, BufferedImage target, int amountOfChildren, int mutateValue) throws CloneNotSupportedException {
        Shape[] returnArray = new Shape[amountOfChildren];
        for (int child = 0; child < amountOfChildren; child++) {
            Shape clone = shape.clone();
            clone.mutate(mutateValue, target);
            clone.setColor(getAverageColorOfShape(clone, target));
            returnArray[child] = clone;
        }
        return returnArray;
    }

    public Shape generateShape() {
        int id = (int) (Math.random() * 3);
        float rotation = (float) (Math.random() * 360);
        boolean filled = Math.random() * 2 > 0.3; //code for generating a 50/50 boolean
        //generates random dimensions for the shape, making sure it doesnt surpass the bounds of the image
        int width = (int) (Math.random() * maxX);
        int height = (int) (Math.random() * maxY);

        //this set of operations ensures the shape can be anywhere on the screen
        int x = (int) (Math.random() * (maxX + (2 * width)) - width);
        int y = (int) (Math.random() * (maxY + (2 * height)) - height);

        Shape returnShape = null;
        switch (id) {
            case 0 -> returnShape = new Ellipse(x, y, width, height, rotation, filled);
            case 1 -> returnShape = new Rect(x, y, width, height, rotation, filled);
            case 2 -> {
                int polygonSize = (int) (Math.random() * 2) + 3;
                int[] xCoords = new int[polygonSize];
                int[] yCoords = new int[polygonSize];
                for (int point = 0; point < polygonSize; point++) {
                    xCoords[point] = (int) (Math.random() * width) + x;
                    yCoords[point] = (int) (Math.random() * height) + y;
                }
                returnShape = new Polygon(xCoords, yCoords, rotation, filled);
            }
        }
        ;
        assert returnShape != null;
        returnShape.setColor(getAverageColorOfShape(returnShape, target));
        return returnShape;
    }

    public Map<Shape, Double> generateShapeList(int size, BufferedImage current) {
        //makes a hashmap
        Map<Shape, Double> returnList = new LinkedHashMap<Shape, Double>();
        //loops up to the value of size
        for (int shape = 0; shape < size; shape++) {
            Shape randomShape = generateShape(); //gens a rando shape
            BufferedImage tempImage = deepCopy(current); //deepCopies the current image to use as a canvas
            randomShape.draw(tempImage); //draws on that temp
            returnList.put(randomShape, EvolutionManager.getSimilarityIndex(tempImage, target)); //evaluates it
        }
        return returnList; //returns the list
    }

    public static Color getAverageColorOfShape(Shape shape, BufferedImage target) {
        //sets up a blank image with the same dimensions as the target
        BufferedImage tempImage = new BufferedImage(target.getWidth(), target.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int redSum = 0; int blueSum = 0; int greenSum = 0; int count = 0; //counts the sums of colors and n
        shape.setColor(Color.BLACK); //sets a temp color for the shape
        shape.draw(tempImage); //draws the shape onto the tempImage

        for (int x = 0; x < target.getWidth(); x++) {
            for (int y = 0; y < target.getHeight(); y++) {
                //checks if the pixel color of the temp image isnt equal to 0, the default value
                //means the shape that was drawn includes that pixel
                if (tempImage.getRGB(x, y) != 0) {
                    int pixelColor = target.getRGB(x, y);
                    count += 1;
                    redSum += (pixelColor & 0xff0000) >> 16;
                    greenSum += (pixelColor & 0xff00) >> 8;
                    blueSum += (pixelColor & 0xff);
                }
            }
        }
        if (count > 0) return new Color(redSum/count, greenSum/count, blueSum/count, 255);
        return Color.BLACK;
    }


    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
