package persistence;

import org.junit.jupiter.api.Test;

import model.Exercise;
import model.WorkoutRoutine;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//This JsonReaderTest class references code from this CPSC210/JsonSerializationDemo repo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WorkoutRoutine wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkoutRoutine() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkoutRoutine.json");
        try {
            WorkoutRoutine wr = reader.read();
            assertEquals("My workout routine", wr.getName());
            assertEquals(0, wr.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkoutRoutine() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkoutRoutine.json");
        try {
            WorkoutRoutine wr = reader.read();
            assertEquals("My workout routine", wr.getName());
            List<Exercise> exercises = wr.getExercises();
            assertEquals(2, exercises.size());
            checkExercise("plank",1,exercises.get(0));
            checkExercise("swim", 1, exercises.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}