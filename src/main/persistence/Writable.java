package persistence;

import org.json.JSONObject;

//This Writable interface references code from this CPSC210/JsonSerializationDemo repo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
