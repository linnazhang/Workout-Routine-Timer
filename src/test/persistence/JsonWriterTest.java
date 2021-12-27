package persistence;

import model.Exercise;
import model.WorkoutRoutine;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//This JsonWriterTest class references code from this CPSC210/JsonSerializationDemo repo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            WorkoutRoutine wr = new WorkoutRoutine("My workout routine");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkoutRoutine() {
        try {
            WorkoutRoutine wr = new WorkoutRoutine("My workout routine");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkoutRoutine.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkoutRoutine.json");
            wr = reader.read();
            assertEquals("My workout routine", wr.getName());
            assertEquals(0, wr.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkoutRoutine() {
        try {
            WorkoutRoutine wr = new WorkoutRoutine("My workout routine");
            wr.addExercise(0,(new Exercise("swim",2)));
            wr.addExercise(1,(new Exercise("situps",1)));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkoutRoutine.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkoutRoutine.json");
            wr = reader.read();
            assertEquals("My workout routine", wr.getName());
            List<Exercise> routine = wr.getExercises();
            assertEquals(2, routine.size());
            checkExercise("swim", 2, routine.get(1));
            checkExercise("situps",1, routine.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}