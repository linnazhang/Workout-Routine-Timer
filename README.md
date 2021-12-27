# My Personal Project

## *Workout Timer*

This application is a **circuit training timer** which will allow the user to customize their own workout
routine. The user will be able to add exercises to their workout, and set how long they want each exercise 
to last. Once started, the application will work down the list and the user will be notified each time the exercise 
changes. I wanted to do this for my project because I personally need one, and all the ones
available either cost money or aren't user-friendly. 

##*User Stories*
- As a user, I want to be able to add an exercise to my workout routine
- As a user, I want to be able to view the list of exercises in my workout routine
- As a user, I want to be able to remove an exercise from my workout routine
- As a user, I want to be able to set a time length for each exercise
- As a user, I want to be able to preview my next exercise
- As a user, I want to be able to see how much time is left in the current exercise
- As a user, I want to be able to know what exercise I am currently supposed to do
- As a user, I want to be able to reorganize my workout routine
- As a user, I want to be notified (visual/audio cue) when the exercise changes
- As a user, I want to be able to save my workout routine to file
- As a user, I want to be able to load my workout routine from file

##*Phase 4: Task 2*
![](/Users/linnazhang/Desktop/Screen Shot 2021-11-24 at 1.13.47 PM.png)


##*Phase 4: Task 3*
To improve the design of my code if I had more time, I would:
- Separate GUI class into several classes to ensure each class only has one function 
(single responsibility design principle) and to improve readability.
- Example: create separate classes for panels, buttons, and labels
- My current code has lots of labels, buttons, and panels, with lots of repetition , so I would
apply the composite pattern to avoid duplication and enforce a tree-like structure so all my components 
are added to one parent
- Example: create a new class Component with each button, label, and panel class extending it. Anything added to the 
panels would be the leaves, and the panel class would be the composite. 
