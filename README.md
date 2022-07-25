# Image Polygonizer
Image Polygonizer is a program which recreates existing artworks or photographs using basic polygons.
This project was made over the summer of 2022 to practice Java development.


## Installation/Running
1. Import any image as a PNG into the input folder.
    - Smaller images work better, with 600x800 being a good size.
    - If you don't want to edit the code, name the image "target.png", otherwise, edit the first filepath in the main.java file.
2. Run the program, with an expected runtime of around 5-8 hours.
    - Run the .jar file found in out/artifacts.
    - Terminate the program once the correct level of detail is achieved.
3. The renders will output in the animation_output folder, with a set of numbered frames as well as a main.png image which contains the most recent render.
4. The animation frames can be stitched together using third party software like Blender.

## Example renders
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

## Creating animated GIFs using Blender's image sequence tool
<p align="center">
<img align="center" width="600" padding="50" src="https://user-images.githubusercontent.com/64125245/180593016-4fddb837-8f24-4798-a4f3-32bd172c00bd.gif">
<h4 align="center">60 fps gif, with each 2nd frame removed to speed up runtime.</h4>
</p>

## Code Breakdown

1. Reads the target image from the input/target.png filepath and saves it as a `BufferedImage`.
    - Also creates a blank `BufferedImage` to use as the program's recreation.
<img align="right" width="230" margin="5" src="https://user-images.githubusercontent.com/64125245/180714378-922a39a2-df11-4657-b9d0-20f355ff5e6c.png"></p>
2. Generates a large list of shapes (Ellipses, Rectangles, or Polygons) each containing a set of random parameters.  
    - Each shape contains geometry data, rotation, and whether to draw as filled or outlined.
    - A color is also assigned by averaging the pixels in the target image behind the shape.
        - Done by drawing the shape on a completely blank image, then looping through its pixels to isolate ones without a null value and getting the corresponding pixels in the target image.
    
3. The program iterates through the `HashMap`, drawing each shape on a copy of the current image and comparing it to the target using mean squared difference to store the "fitness" value in `<Shape, Double>` pairs.
    - Sorts the list based on the fitness value in descending order, and removes all low-scoring shapes.
    
4. For every remaining shape in the list, a new list is created containing the original shape and 10 `mutated` copies of itself.
    - `Mutations` shift properties of shapes by a value between 0 and a `mutateValue` variable, which can happen positively or negatively.
    
5. Repeat steps 3-5 for a set number of generations, before selecting the best scoring shape and drawing it onto the current image.
    - After a shape is drawn, the program jumps back to step 2. Outputting the current image as a frame also happens during this time.
    
## Optimizations

### Bounding Box Detection
In **Step 2**, when assigning shapes a color:
>  ...drawing the shape on a completely blank image, then loop through its pixels to isolate ones without a null value and getting the corresponding pixels in the target image.

This process wastes a large amount of runtime, as looping through all the pixels in the image can waste unneccesary resources if the shape is significantly smaller than the dimensions of the image. 
-   *This is seen much more prominently deep into a render, as when the current image resembles the target image more, the better shapes will be ones with small sizes, as they can provide more detail without messing up the progress already made.*

To ensure the program only check the pixels it absolutely needs to, we can find the bounding box of each type of shape using basic trigonometry.
<p align="center" margin="0" padding="0" >
<img width="600"  margin="0" padding="0" alt="Screen Shot 2022-07-24 at 13 56 36" src="https://user-images.githubusercontent.com/64125245/180719180-a5cc90f0-de19-4561-9fd5-2b10b6ea8445.png">
</p>

#### Bounding Box Algorithm
1. **Since shape coordinates are stored before a rotation is applied, the program must find the coordinate's location after a rotation:**
    1. Store a list of vectors from the pivot (center) to each coordinate.
    2. Calculate the length of this vector, as well it's angle from 0° by normalising it and using the unit circle.
    3. Add the shapes rotation to the vector's angle, and convert it back into a normalised vector.
    4. Multiply the normalised vector by the original length to scale it properly, the final position of which would be the coordinate after rotating.
2. **To get the bounding box, all that needs to be done is to find the minimum and maximum x and y values within the coordinate list.**
3. **A similar method is used for ellipses but instead of coordinates, it uses their radii.**

### Benchmarking

|Processing Time | No Optimisation | Bounding Box Optimisation |
|---------| ------------- | ------------- |
| 1 Shape (ms) | 15777 | 10465  |

<h4 align="center">Approximately 150% runtime improvement</h4>
