import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.*;

public class EvolutionManager {
    int maxX; int maxY;

    int initialListSize = 400;
    int trimmedListSize = 30;
    int amountOfChildren = 10;

    public EvolutionManager(int maxX, int maxY) {
        this.maxX = maxX; this.maxY = maxY;
    }

    public static double getSimilarityIndex(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            System.out.println("Unable to compare images with different dimensions.");
            return Double.MAX_VALUE;
        }
        double error = 0;
        for (int x = 0; x < img1.getWidth(); x++) {
            for (int y = 0; y < img1.getHeight(); y++) {
                int pixelOneColor = img1.getRGB(x, y);
                int pixelTwoColor = img2.getRGB(x, y);
                error += Math.pow(((pixelOneColor & 0xff0000) >> 16) - ((pixelTwoColor & 0xff0000) >> 16), 2);
                error += Math.pow(((pixelOneColor & 0xff00) >> 8) - ((pixelTwoColor & 0xff00) >> 8), 2);
                error += Math.pow((pixelOneColor & 0xff) - (pixelTwoColor & 0xff), 2);
            }
        }
        return error/(img1.getWidth() * img1.getHeight());
    }

    public static Map<Shape, Double> sortShapeList(Map<Shape, Double> currentList) {
        LinkedList<Map.Entry<Shape, Double>> list = new LinkedList<Map.Entry<Shape, Double>>(currentList.entrySet());
        list.sort(new Comparator<Map.Entry<Shape, Double>>() {
            @Override
            public int compare(Map.Entry<Shape, Double> o1, Map.Entry<Shape, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        HashMap<Shape, Double> temp = new LinkedHashMap<Shape, Double>();
        for (Map.Entry<Shape, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static Map<Shape, Double> Darwinism(Map<Shape, Double> currentList, BufferedImage target, BufferedImage current, int trimmedListSize, int amountOfChildren) throws CloneNotSupportedException {
        Map<Shape, Double> returnMap = new LinkedHashMap<>();
        Set<Shape> keys = currentList.keySet();
        int temp = 0;

        for (Shape key : keys) {
            //System.out.println(key);
            if (temp > trimmedListSize) break;
            returnMap.put(key, currentList.get(key));
            for (Shape mutated : ShapeGenerator.mutateShape(key, target, amountOfChildren, 20)) {
                BufferedImage tempImage = ShapeGenerator.deepCopy(current);
                mutated.draw(tempImage);
                returnMap.put(mutated, getSimilarityIndex(target, tempImage));
            }
            temp++;
        }
        return returnMap;
    }

    public static Color mutateColor(Color color, int mutateValue) {
        int r = (int) Math.min(Math.max(color.getRed() + (Math.random() * 2 - 1) * mutateValue, 0), 255);
        int g = (int) Math.min(Math.max(color.getGreen() + (Math.random() * 2 - 1) * mutateValue, 0), 255);
        int b = (int) Math.min(Math.max(color.getBlue() + (Math.random() * 2 - 1) * mutateValue, 0), 255);
        int a = (int) Math.min(Math.max(color.getAlpha() + (Math.random() * 2 - 1) * mutateValue, 0), 255);
        return new Color(r, g, b, a);
    }
}
