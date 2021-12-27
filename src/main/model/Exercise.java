package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an exercise having a description and length of time (in minutes)
public class Exercise implements Writable {
    private String description;
    private int minutes;


    //REQUIRES: time > 0
    //EFFECTS: constructs an exercise with description and minutes to complete
    public Exercise(String description, int minutes) {
        this.description = description;
        this.minutes = minutes;
    }


    //EFFECTS: returns name of exercise
    public String getDescription() {
        return description;
    }

    //EFFECTS: returns number of minutes required to complete this exercise
    public int getMinutes() {
        return minutes;
    }

    //MODIFIES: this
    //EFFECTS: subtracts 1 from the exercise minutes
    public Exercise tickTock(Exercise exercise) {
        Exercise replace = new Exercise("Blank", 1);
        if (exercise.getMinutes() > 0) {
            replace = new Exercise(exercise.getDescription(), exercise.getMinutes() - 1);
        } else {
            replace = new Exercise(exercise.getDescription(), 0);
        }
        return replace;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("description", description);
        json.put("minutes", minutes);
        return json;
    }

    @Override
    public String toString() {
        return description + " , " + minutes + " minute(s)";
    }
}


