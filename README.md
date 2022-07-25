# Polygon-Image-Recreation
A java program used to recreate a target image using simple polygons.
This project was made over the summer of 2022 to practice Java development.

1. Import any image as a PNG into the input folder.
    - Smaller images work better, with 600x800 being a good size.
    - If you don't want to edit the code, name the image "target.png", otherwise, edit the first filepath in the main.java file.
2. Run the program, with an expected runtime of around 5-8 hours.
    - Run the .jar file found in out/artifacts.
    - Terminate the program at any time once the correct level of detail is achieved.
3. The renders will output in the animation_output folder, with a set of numbered frames as well as a main.png image which contains the most recent render.
4. The animation frames can be stitched together using third party software like Blender.

<h2>Some images generated using the algorithm:</h2>
<p align="center">
<img align="center" width="600" padding="50" src="https://user-images.githubusercontent.com/64125245/180592168-cb216279-de3d-4f53-8449-3b1d348d6bf9.png">
<h4 align="center">1920 Shapes, around 8 hours of runtime.</h4>
</p>

<p align="center">
<img align="center" width="600" padding="50" src="https://user-images.githubusercontent.com/64125245/180592172-6ad13874-8da1-49f9-9bc8-32bf4ef2ff2f.png">
<h4 align="center">1271 Shapes, around 5 hours of runtime.</h4>
</p>

<p align="center">
<img align="center" width="600" padding="50" src="https://user-images.githubusercontent.com/64125245/180630897-9ab33a6a-6105-4fb6-9a7f-339865606f41.png">
<h4 align="center"> 3300 Shapes, around 12 hours of runtime.</h4>
</p>



<p align="center">
<img align="center" width="600" padding="50" src="https://user-images.githubusercontent.com/64125245/180592176-b18c432e-9e74-4d98-939a-faa052a62609.png">
<h4 align="center"> 1500 Shapes, around 7 hours of runtime.</h4>
</p>

<h2>Animated GIFs using Blender Image Sequence Tool</h2>
<p align="center">
<img align="center" width="600" padding="50" src="https://user-images.githubusercontent.com/64125245/180593016-4fddb837-8f24-4798-a4f3-32bd172c00bd.gif">
<h4 align="center">60 fps gif, with each 2nd frame removed to speed up runtime.</h4>
</p>

<h2>Code Breakdown:</h2>

1. Reads the target image from the input/target.png filepath and saves it as a BUfferedImage.
    - It also creates a new, blank BufferedImage to use as the program's recreation.
2. Generates a large list of shapes (Ellipses, Rects, or Polygons), each containing a set of random parameters.  
    - Each shape contains a rotation, whether to draw as filled or outlined.
    - A color is also assigned by averaging the color of the pixels in the target image behind the shape.
    - This is done by drawing the shape on a completely blank image, then looping through it's pixels to isolate ones without a null value, and then getting the corresponding pixels in the target image.
3. The program iterates through the HashMap, drawing each shape on a copy of the current image and comparing it to the target (using mean-squared difference) to store the "fitness" value in <Shape, double> pairs.
    - The program also sorts the list based on the fitness value in descending order.
    - The list is trimmed to contain a few best scoring shapes.
4. For every remaining shape in the list, a new list is created containing the original shape, as well as 5-10 mutated copies of itself.
    - Mutations are when properties of a shape are shifted by a value between 0 and a mutateValue variable, which can happen positively or negatively.
5. Repeat steps 3-5 for a set number of generations, before selecting the best scoring shape and drawing it onto the current image.
    - After a shape is drawn, the program jumps back to step 2. Outputting the current image as a frame also happens during this time.
    
<h2>Code Breakdown:</h2> 
