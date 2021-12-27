package persistence;

import model.Exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;

//This JsonTest class references code from this CPSC210/JsonSerializationDemo repo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//Checks that the description and minutes match up with exercise
public class JsonTest {
    protected void checkExercise(String description, int minutes, Exercise exercise) {
        assertEquals(description, exercise.getDescription());
        assertEquals(minutes,exercise.getMinutes());
    }
}
