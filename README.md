# Mini project in JAVA

Ok first of all we want to say some words to share our knowledge from the course,
As a graduates of this course, the skills and knowledge
acquired enable effective handling of complex problem
areas through the production of abstract models and the
design of evolving solutions. Through extensive training in
design, modeling, and rendering of virtual 3D graphic
scenes, we have gained expertise in various physical
phenomena such as light source, rays, reflection,
refraction, color, shadow, etc. Additionally, we have honed our
ability to create a coherent and well-designed software
architecture, demonstrated through my successful
completion of a large project. As a result, We are confident in
our ability to contribute and excel in any software development role.
The project is programmed in JAVA.

## **Functional:**
### *Tests*
In our mini-project, we followed a testing approach that included unit testing,
integration testing, and acceptance testing to ensure the quality and correctness of our renderer implementation.
For unit testing, we tested individual components such as geometries, lighting, primitives, and rendering techniques.
This helped us verify their functionality in isolation and ensure they produced the expected results.

Integration testing focused on assessing the interaction and compatibility of different components within the renderer.
We verified that the modules worked together harmoniously, exchanging data and producing the desired outcomes.

Acceptance testing involved validating the renderer against the defined requirements.
We executed a set of test cases covering various rendering scenarios to confirm that the renderer met the specified criteria and produced the expected visual output.

Throughout the testing process, we documented test cases, including input data, expected outputs, and actual results.
We paid attention to edge cases and corner cases for comprehensive coverage and recorded any failures or issues encountered.

To maintain quality, we conducted code reviews, created comprehensive code documentation, and performed code refactoring.
These measures ensured adherence to standards, improved maintainability, and enhanced overall code quality.


By incorporating these testing and quality assurance measures, we ensured a robust and reliable renderer that met the defined requirements.

### *Performance Acceleration*
To enhance our renderer's performance and visual quality, we implemented various techniques and optimizations.
These aimed to reduce artifacts, increase realism, and optimize the rendering process.
Here are some of the techniques we used:

**Antialiasing:** Antialiasing smooths jagged edges and reduces aliasing artifacts in rendered images, improving visual appeal.
We implemented antialiasing techniques within the camera class, focusing on ray precision by increasing the number of rays per pixel.
This captures finer details, resulting in smoother edges with reduced aliasing.
The code snippet demonstrates constructing rays with random offsets and averaging their colors for improved coverage.
By incorporating antialiasing techniques, we achieved visually pleasing images with reduced artifacts and increased realism.

**Soft Shadows:** Simulates the partial blocking of light by objects, resulting in smoother and more natural-looking shadows.

**Glossy Surfaces:** Introduces reflections with varying levels of glossiness, creating specular highlights and realistic reflections.

**Diffuse (Blurry) Glass:** Mimics the scattering and blurring of light as it passes through frosted or textured glass, improving the rendering of glass materials.

**Depth of Field:** Simulates the optical effect of selectively focusing on a specific point while blurring objects in front of or behind it, adding realism and visual interest.

**Multi-threading:** Improves rendering performance by distributing the workload across multiple threads, taking advantage of multi-core processors.

**Antialiasing and Adaptive Supersampling:** Smooths out jagged edges and reduces aliasing artifacts while dynamically adjusting the level of sampling based on scene complexity.

These techniques collectively enhance the realism and visual quality of our rendered images.

## Rendered Images:
# Before improvement:
![NotImproved.jpg](NotImproved.jpg)
# After improvement:
![Improved.jpg](Improved.jpg)
## Creddits:
### Developers:
#### Ayala Houri
https://github.com/AyalaHouri
#### Shani Zegal
https://github.com/ShaniZegal
### Mentor/Lecturer:
- Eliezer Gensburger  [LinkedIn](https://www.linkedin.com/in/גנסבורגר-אליעזר-56b14411/) | [GitHub](https://github.com/eliezergensburger)

