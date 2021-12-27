package model;

import java.util.Calendar;
import java.util.Date;


//Represents a workout routine event
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    //EFFECTS: creates an event with the given description and the current date/time stamp
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    //EFFECTS: gets the date and time of this event
    public Date getDate() {
        return dateLogged;
    }

    //EFFECTS: gets the description of this event
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    //EFFECTS: creates hashcode for date and description of this event
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }


    //EFFECTS: makes date and time of event into a string
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
