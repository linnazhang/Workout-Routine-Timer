package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutRoutineTest {
    public WorkoutRoutine exercises;

    @BeforeEach
    public void runBefore() {
        String name = new String();
        exercises = new WorkoutRoutine(name);
    }

    @Test
    public void testWorkoutRoutine() {
        assertEquals(exercises.length(), 0);
    }

    @Test
    public void testAddExerciseToEmptyList() {
        Exercise exercise = new Exercise ("Plank",2);
        exercises.addExercise(0,exercise);
        assertEquals(exercises.length(),1);
    }

    @Test
    public void testAddExerciseToBeginningOfList(){
        Exercise e1 = new Exercise("Filler", 1);
        Exercise e2 = new Exercise("Filler", 1);
        Exercise e3 = new Exercise("Filler", 1);
        exercises.addExercise(0, e1);
        exercises.addExercise(1, e2);
        exercises.addExercise(0, e3);
        assertEquals(exercises.length(),3);
        assertEquals(exercises.getExerciseWithPosition(0),e3);
        assertEquals(exercises.getExerciseWithPosition(1),e1);
        assertEquals(exercises.getExerciseWithPosition(2),e2);

    }

    @Test
    public void testAddExerciseLoad(){
        Exercise e1 = new Exercise("Filler", 1);
        Exercise e2 = new Exercise("Filler", 1);
        Exercise e3 = new Exercise("Filler", 1);
        exercises.addExerciseLoad(0, e1);
        exercises.addExerciseLoad(1, e2);
        exercises.addExerciseLoad(0, e3);
        assertEquals(exercises.length(),3);
        assertEquals(exercises.getExerciseWithPosition(0),e3);
        assertEquals(exercises.getExerciseWithPosition(1),e1);
        assertEquals(exercises.getExerciseWithPosition(2),e2);
    }

    @Test
    public void testGetNextExerciseOnlyOne() {
        Exercise e1 = new Exercise("Filler", 1);
        exercises.addExercise(0, e1);
        assertEquals(exercises.getNextExercise(), e1);
        assertEquals(exercises.length(), 0);
    }

    @Test
    public void testGetNextExerciseLongerList() {
        Exercise e1 = new Exercise("Filler", 1);
        Exercise e2 = new Exercise("Filler", 1);
        Exercise e3 = new Exercise("Filler", 1);
        exercises.addExercise(0, e1);
        exercises.addExercise(1, e2);
        exercises.addExercise(2, e3);
        assertEquals(exercises.getNextExercise(), e1);
        assertEquals(exercises.length(), 2);
    }

    @Test
    public void testTimeRemainingEmptyList() {
        assertEquals(exercises.timeRemaining(), 0);
    }

    @Test
    public void testTimeRemainingLongerList() {
        Exercise e1 = new Exercise("Filler", 1);
        Exercise e2 = new Exercise("Filler", 1);
        Exercise e3 = new Exercise("Filler", 1);
        exercises.addExercise(0, e1);
        exercises.addExercise(1, e2);
        exercises.addExercise(2, e3);
        assertEquals(exercises.timeRemaining(), 3);
    }

    @Test
    public void testGetNumberOfExercisesLeftEmptyList() {
        assertEquals(exercises.length(), 0);
    }

    @Test
    public void testGetNumberOfExercisesLeftLongerList() {
        Exercise e1 = new Exercise("Filler", 1);
        Exercise e2 = new Exercise("Filler", 1);
        Exercise e3 = new Exercise("Filler", 1);
        exercises.addExercise(0, e1);
        exercises.addExercise(1, e2);
        exercises.addExercise(2, e3);
        assertEquals(exercises.length(), 3);
    }

    @Test
    public void testRemoveExerciseNotThere() {
        Exercise e1 = new Exercise("Filler", 1);
        Exercise e2 = new Exercise("Filler", 1);
        Exercise e3 = new Exercise("Filler", 1);
        exercises.addExercise(0, e1);
        exercises.addExercise(1, e2);
        exercises.addExercise(2, e3);
        LinkedList<Exercise> updatedExercises = new LinkedList<>();
        updatedExercises.add(e1);
        updatedExercises.add(e2);
        updatedExercises.add(e3);
        assertEquals(exercises.removeExercise("Push-ups"), updatedExercises);

    }

    @Test
    public void testRemoveExerciseFromFirst() {
        Exercise e1 = new Exercise("A", 1);
        Exercise e2 = new Exercise("B", 1);
        Exercise e3 = new Exercise("C", 1);
        exercises.addExercise(0, e1);
        exercises.addExercise(1, e2);
        exercises.addExercise(2, e3);
        exercises.removeExercise("A");
        assertEquals(exercises.length(), 2);
        assertEquals(exercises.getExerciseWithPosition(1), e3);
        assertEquals(exercises.getExerciseWithPosition(0), e2);

    }

    @Test
    public void testRemoveExerciseFromMiddle() {
        Exercise e1 = new Exercise("A", 1);
        Exercise e2 = new Exercise("B", 1);
        Exercise e3 = new Exercise("C", 1);
        exercises.addExercise(0, e1);
        exercises.addExercise(1, e2);
        exercises.addExercise(2, e3);
        exercises.removeExercise("B");
        assertEquals(exercises.length(), 2);
        assertEquals(exercises.getExerciseWithPosition(1), e3);
        assertEquals(exercises.getExerciseWithPosition(0), e1);

    }

    @Test
    public void testRemoveExerciseFromLast() {
        Exercise e1 = new Exercise("A", 1);
        Exercise e2 = new Exercise("B", 1);
        Exercise e3 = new Exercise("C", 1);
        exercises.addExercise(0, e1);
        exercises.addExercise(1, e2);
        exercises.addExercise(2, e3);
        exercises.removeExercise("C");
        assertEquals(exercises.length(), 2);
        assertEquals(exercises.getExerciseWithPosition(1), e2);
        assertEquals(exercises.getExerciseWithPosition(0), e1);

    }

    @Test
    public void testWorkOnExercisesTimeLongerThanExercises() {
        Exercise e1 = new Exercise("A", 1);
        Exercise e2 = new Exercise("B", 1);
        exercises.addExercise(0, e1);
        exercises.addExercise(1, e2);
        exercises.workOnExercises(5);
        assertEquals(exercises.length(), 0);
    }

    @Test
    public void testWorkOnExercisesTimeShorterThanExercises() {
        Exercise e1 = new Exercise("A", 1);
        Exercise e2 = new Exercise("B", 1);
        exercises.addExercise(0, e1);
        exercises.addExercise(1, e2);
        exercises.workOnExercises(1);
        assertEquals(exercises.length(), 1);
        assertEquals(exercises.getExerciseWithPosition(0), e2);
    }

    @Test
    public void testWorkOnExercisesTimeShorterThanManyExercises() {
        Exercise e1 = new Exercise("A", 2);
        Exercise e2 = new Exercise("B", 2);
        Exercise e3 = new Exercise("C", 7);
        exercises.addExercise(0, e1);
        exercises.addExercise(1, e2);
        exercises.addExercise(2, e3);
        exercises.workOnExercises(6);
        assertEquals(exercises.length(), 1);
        assertEquals(exercises.getExerciseWithPosition(0), e3);
    }

    @Test
    public void testGetExerciseWithPositionInEmptyRoutine(){
        Exercise exerciseFail = new Exercise("fail",0);
        Exercise fetch = exercises.getExerciseWithPosition(2);
        assertEquals(fetch.getMinutes(),0);
        assertEquals(fetch.getDescription(),"fail");

    }

    @Test
    public void testGetExerciseInRoutineWithManyExercises(){
        Exercise e1 = new Exercise("A", 2);
        Exercise e2 = new Exercise("B", 2);
        Exercise e3 = new Exercise("C", 7);
        exercises.addExercise(0, e1);
        exercises.addExercise(1, e2);
        exercises.addExercise(2, e3);
        assertEquals(exercises.getExerciseWithPosition(1),e2);
    }

    @Test
    public void testGetExerciseInEmptySpot(){
        Exercise e1 = new Exercise("A", 2);
        exercises.addExercise(0, e1);
        Exercise fetch = exercises.getExerciseWithPosition(2);
        assertEquals(fetch.getDescription(), "fail");
        assertEquals(fetch.getMinutes(),0);
    }

    @Test
    public void testLengthOfEmptyList(){
        assertEquals(exercises.length(),0);
    }

    @Test
    public void testLengthOfLongerList(){
        Exercise e1 = new Exercise("A", 2);
        Exercise e2 = new Exercise("B", 2);
        Exercise e3 = new Exercise("C", 7);
        exercises.addExercise(0, e1);
        exercises.addExercise(1, e2);
        exercises.addExercise(2, e3);
        assertEquals(exercises.length(),3);

    }

    @Test
    public void testIsFoundEmptyList(){
        assertFalse(exercises.isFound("Test"));
    }

    @Test
    public void testIsFoundTrue(){
        Exercise e1 = new Exercise("A", 2);
        Exercise e2 = new Exercise("B", 2);
        exercises.addExercise(0, e1);
        exercises.addExercise(1, e2);
        assertTrue(exercises.isFound("A"));
    }
    @Test
    public void testIsFoundFalse(){
        Exercise e1 = new Exercise("A", 2);
        Exercise e2 = new Exercise("B", 2);
        exercises.addExercise(0, e1);
        exercises.addExercise(1, e2);
        assertFalse(exercises.isFound("D"));
    }

    @Test
    public void testTickTockZeroMinutes(){
        Exercise exercise = new Exercise("Test",-1);
        Exercise exercise1 = exercise.tickTock(exercise);
        assertEquals(exercise1.getMinutes(),0);
    }

    @Test
    public void testTickTock(){
        Exercise exercise = new Exercise("Test",5);
        Exercise exercise2 = exercise.tickTock(exercise);
        assertEquals(exercise2.getMinutes(),4);
    }

    @Test
    public void testRemoveAll(){
        exercises.addExercise(0,new Exercise("Test",5));
        exercises.addExercise(1,new Exercise("Test1",2));
        assertEquals(exercises.length(),2);
        exercises.removeAll();
        assertEquals(exercises.length(),0);

    }

    @Test
    public void testGetMinutesString(){
        Exercise exercise = new Exercise("hi",3);
        assertEquals(exercise.toString(),"hi , 3 minute(s)");
    }

}
