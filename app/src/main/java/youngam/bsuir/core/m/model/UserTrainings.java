package youngam.bsuir.core.m.model;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Alex on 25.04.2015.
 */
public class UserTrainings {
    private long date;
    private String dateId;
    private Set <String> muscleGroups;
    private ArrayList<WorkoutCategory> exercises;


    public UserTrainings(long date, String dateId, Set <String> muscleGroups, ArrayList<WorkoutCategory> exercises){
        this.date = date;
        this.dateId = dateId;
        this.muscleGroups = muscleGroups;
        this.exercises = exercises;

    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDateId() {
        return dateId;
    }

    public void setDateId(String dateId) {
        this.dateId = dateId;
    }

    public String getMuscleGroups() {
        StringBuilder result = new StringBuilder();
        for(String s : muscleGroups ){
            result.append(s);
            result.append(", ");
        }

        // delete the last comma
        result.delete(result.length() - 2, result.length() - 1);
        return result.toString();
    }

    public void setMuscleGroups(Set<String> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    public ArrayList<WorkoutCategory> getExercises() {

        return exercises;
    }

    public void setExercises(ArrayList<WorkoutCategory> exercises) {
        this.exercises = exercises;
    }
}
