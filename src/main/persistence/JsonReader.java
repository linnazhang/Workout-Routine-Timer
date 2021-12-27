package persistence;

import model.Exercise;
import model.WorkoutRoutine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//This JsonReader class references code from this CPSC210/JsonSerializationDemo repo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads workout routine from JSON data stored in file
public class JsonReader {
    private String source;
    private int position;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workout routine from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WorkoutRoutine read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkoutRoutine(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workout routine from JSON object and returns it
    private WorkoutRoutine parseWorkoutRoutine(JSONObject jsonObject) {
        String name = jsonObject.getString("description");
        WorkoutRoutine wr = new WorkoutRoutine(name);
        addExercises(wr, jsonObject);
        return wr;
    }

    // MODIFIES: wr
    // EFFECTS: parses exercises from JSON object and adds them to workout routine
    private void addExercises(WorkoutRoutine wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("exercises");
        for (Object json : jsonArray) {
            JSONObject nextExercise = (JSONObject) json;
            addExercise(wr, nextExercise);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses exercise from JSON object and adds it to workout routine
    private void addExercise(WorkoutRoutine wr, JSONObject jsonObject) {
        String name = jsonObject.getString("description");
        int minutes = jsonObject.getInt("minutes");
        Exercise exercise = new Exercise(name, minutes);
        wr.addExercise(position, exercise);
    }
}
