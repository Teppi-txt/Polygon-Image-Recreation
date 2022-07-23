import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        //instantiates the target and current viarbales for future use
        BufferedImage target = null;
        BufferedImage current = null;
        int maxX = 0; int maxY = 0;
        ShapeGenerator gen = null;

        //tries updating target to a file "target.png", exits program if failed
        try {
            target = ImageIO.read(new File("input/yoi.png"));
            maxX = target.getWidth(); maxY = target.getHeight();
            gen = new ShapeGenerator(maxX, maxY, target);
        } catch (IOException e) {
            System.out.println("Unable to find image in filepath input/image.png, terminating program.");
            System.exit(1);
        }

        //checks if there is already an image named output.png, otherwise create one
        try {current = ImageIO.read(new File("ouadsadtput/image.png"));} catch (IOException e) {
            current = new BufferedImage(target.getWidth(), target.getHeight(), BufferedImage.TYPE_INT_ARGB);
            System.out.println(9);
        }

        for (int i = 0; i < 10000; i++) {
            long startTime = System.currentTimeMillis();
            Map<Shape, Double> shapeList = gen.generateShapeList(300, current);
            for (int generation = 0; generation < 5; generation++) {
                shapeList = EvolutionManager.sortShapeList(shapeList);
                shapeList = EvolutionManager.Darwinism(shapeList, target, current, 10, 5);
            }
            //after repeating the generations, the final list has to be sorted 1 more time
            shapeList = EvolutionManager.sortShapeList(shapeList);


            Shape bestShape = shapeList.keySet().iterator().next();
            //checks if the shape is an improvement
            System.out.println(EvolutionManager.getSimilarityIndex(target, current));
            if (EvolutionManager.getSimilarityIndex(target, current) > shapeList.get(bestShape)) {
                bestShape.draw(current);
                ImageIO.write(current, "png", new File("animation_output/main.png"));
                ImageIO.write(current, "png", new File("animation_output/image" + String.valueOf(i) + ".png"));
                System.out.println("Finished generating shape " + String.valueOf(i) + " in " + String.valueOf(System.currentTimeMillis() - startTime) + "ms with a score of " + String.valueOf(shapeList.get(bestShape)));
            } else {
                System.out.println("not improvb");
            }
        }
        //System.out.println("Hello world!");
    }
}