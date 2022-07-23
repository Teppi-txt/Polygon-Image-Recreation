# Polygon-Image-Recreation
A java program used to recreate a target image using primitive polygons.
This project was made over the summer of 2022 to practice Java development.

1. Import any image as a PNG into the input folder.
    - Smaller images work better, with 600x800 being a good size).
    - If you don't want to edit the code, name the image "target.png".
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
<img align="center" width="600" padding="50" src="https://user-images.githubusercontent.com/64125245/180592173-9252b0a1-1ab4-4fba-84e0-e00d81b57c60.png">
<h4 align="center"> 2041 Shapes, around 12 hours of runtime.</h4>
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
