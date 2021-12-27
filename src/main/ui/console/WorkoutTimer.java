package ui.console;


import model.Exercise;
import model.WorkoutRoutine;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Workout Routine application
public class WorkoutTimer {
    private static final String JSON_STORE = "./data/workroom.json";
    private WorkoutRoutine routine;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the workout timer application
    public WorkoutTimer() throws FileNotFoundException {
        input = new Scanner(System.in);
        routine = new WorkoutRoutine("Workout Routine");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runWorkoutRoutine();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runWorkoutRoutine() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                try {
                    processCommand(command);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("\nBye Bye :)");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) throws InterruptedException {
        if (command.equals("a")) {
            doAddWorkout();
        } else if (command.equals("r")) {
            doRemoveWorkout();
        } else if (command.equals("p")) {
            printRoutine();
        } else if (command.equals("s")) {
            saveRoutine();
        } else if (command.equals("l")) {
            loadRoutine();
        } else if (command.equals("b")) {
            try {
                startRoutine();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
// EFFECTS: initializes exercises
    private void init() {
        String name = new String();
        routine = new WorkoutRoutine(name);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add exercise");
        System.out.println("\tr -> remove exercise");
        System.out.println("\tp -> print routine");
        System.out.println("\ts -> save routine to file");
        System.out.println("\tl -> load routine from file");
        System.out.println("\tb -> begin routine");
        System.out.println("\tq -> quit");
    }

    //MODIFIES: this
//EFFECTS: adds a new exercise to the workout routine given user inputs of description, minutes, and position
    private void doAddWorkout() {
        System.out.println("Enter exercise description:");
        String description = input.next();
        System.out.println("Enter exercise minutes:");
        int minutes = input.nextInt();
        if (minutes <= 0) {
            System.out.println("Invalid entry\n");
        }
        Exercise exercise = new Exercise(description, minutes);
        System.out.println("Enter position in list:");
        int position = input.nextInt();
        if (position < 0) {
            System.out.println("Invalid entry!\n");
        } else {
            if (position > routine.length()) {
                System.out.println("Invalid entry. Enter a number no greater than number of exercises in current list");
            } else {
                System.out.println("Added to list:" + description + ", " + minutes + " minute(s)");
                routine.addExercise(position, exercise);
            }
        }
    }

    //MODIFIES: this
//EFFECTS: removes an exercise from the workout routine
    private void doRemoveWorkout() {
        System.out.println("Enter exercise description:");
        String description = input.next();
        Boolean find = routine.isFound(description);
        if (find = true) {
            System.out.println("Exercise " + description + " removed");
            routine.removeExercise(description);
        } else {
            System.out.println("Exercise not found!!!");
        }
    }

    //EFFECTS: prints a list of all the exercises in the routine to the screen
    private void printRoutine() {
        if (routine.length() == 0) {
            System.out.println("No exercises in workout!");
        } else {
            for (int i = 0; i < routine.length(); i++) {
                Exercise fetch = routine.getExerciseWithPosition(i);
                System.out.println("- " + fetch.getDescription() + " ," + fetch.getMinutes() + " minute(s)");
            }
        }
    }

    //EFFECTS: starts the workout routine
// Each exercise completed will show up with 0 minutes and removed from routine
    private void startRoutine() throws InterruptedException {
        printRoutine();
        while (routine.length() > 0) {
            for (int i = 0; i < routine.length(); i++) {  // for every item in the list
                Exercise current = routine.getExerciseWithPosition(i);

                Exercise newE = new Exercise("Empty", 1);

                Thread.sleep(60000 * current.getMinutes()); //java sleep for its length of time
                newE = current.tickTock(current); //modifies the current exercise to decrease in time
                System.out.print(">> " + newE.getDescription() + " completed ," + newE.getMinutes()
                        + " minutes left" + " ✓ \n");
                routine.removeExercise(current.getDescription());
            }
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveRoutine() {
        try {
            jsonWriter.open();
            jsonWriter.write(routine);
            jsonWriter.close();
            System.out.println("Saved " + routine.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadRoutine() {
        try {
            routine = jsonReader.read();
            System.out.println("Loaded " + routine.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}






