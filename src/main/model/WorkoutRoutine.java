package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// Represents a list of exercises
public class WorkoutRoutine implements Writable {
    private LinkedList<Exercise> exercises;
    private String description;
    private int position;


    //EFFECTS: constructs an empty workout routine with a name
    public WorkoutRoutine(String description) {
        this.description = description;
        exercises = new LinkedList<>();
    }


    //EFFECTS: returns name of WorkoutRoutine
    public String getName() {
        return description;
    }

    //EFFECTS: returns an unmodifiable list of exercises in this workout routine
    public List<Exercise> getExercises() {
        return Collections.unmodifiableList(exercises);
    }


    //REQUIRES: spot > 0
    //MODIFIES: this
    //EFFECTS: inserts exercise into list of exercises in correct position
    //Note: User must enter 0 for first position, 1 for second position, etc.
    public void addExercise(int position, Exercise exercise) {
        exercises.add(position, exercise);
        EventLog.getInstance().logEvent(new Event("Added new exercise"));
    }

    public void addExerciseLoad(int position, Exercise exercise) {
        exercises.add(position, exercise);
    }



    //REQUIRES: List of exercises is not empty
    //MODIFIES: this
    //EFFECTS: removes an exercise of given description from
    // the collection of exercises to be completed and returns edited list of exercises
    //if not found, return original list of exercises
    public LinkedList<Exercise> removeExercise(String description) {
        LinkedList<Exercise> edited = exercises;
        for (int i = 0; i < exercises.size(); i++) {
            Exercise next = exercises.get(i);
            if (next.getDescription().equals(description)) {
                exercises.remove(next);
                edited = exercises;
            } else {
                edited = exercises;
            }
            EventLog.getInstance().logEvent(new Event("Removed an exercise"));
        }
        return edited;
    }

    //REQUIRES: Routine is not empty
    //EFFECTS: returns the next exercise and removes it from the list
    public Exercise getNextExercise() {
        return exercises.poll();
    }

    //EFFECTS: returns the exercise in given position
    // if there is no exercise in that position, return exercise with description "fail" and 0 minutes
    public Exercise getExerciseWithPosition(int position) {
        Exercise failExercise = new Exercise("fail", 0);
        if (exercises.size() < position) {
            return failExercise;
        } else {
            return exercises.get(position);
        }
    }

    //EFFECTS: returns number of exercises remaining
    public int length() {
        return exercises.size();
    }

    //EFFECTS: returns time left of exercises remaining
    public int timeRemaining() {
        int minutes = 0;
        for (int i = 0; i < exercises.size(); i++) {
            Exercise next = exercises.get(i);
            minutes = minutes + next.getMinutes();
        }
        return minutes;
    }

    //MODIFIES: this
    //EFFECTS: removes all exercises in current list
    public void removeAll() {
        exercises = new LinkedList<>();
        EventLog.getInstance().logEvent(new Event("Removed all exercises"));
    }

    //REQUIRES: minutes > 0
    //MODIFIES: this
    //EFFECTS: returns the list of names of exercises which will be
    //worked on during given number of minutes and
    //removes exercises that have been completed from list to be worked on
    public LinkedList<Exercise> workOnExercises(int minutes) {
        LinkedList toRemove = new LinkedList<>();
        int index = 0;

        for (int i = 0; i < exercises.size(); i++) {
            Exercise next = exercises.get(i);
            if (next.getMinutes() <= minutes) {
                minutes -= next.getMinutes();
                toRemove.add(next);
            } else {
                //next.applyMinutes(minutes);
                minutes = 0;
            }
        }
        exercises.removeAll(toRemove);
        return exercises;
    }


    public boolean isFound(String description) {
        Integer status = 0;
        for (int i = 0; i < exercises.size(); i++) {
            Exercise next = exercises.get(i);
            if (next.getDescription().equals(description)) {
                status = 1;
            }
        }
        if (status == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("description", description);
        json.put("exercises", exercisesToJson());
        return json;
    }

    // EFFECTS: returns things in this workout routine as a JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise e : exercises) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }



}





