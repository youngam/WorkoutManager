package youngam.bsuir.core.model;

/**
 * Created by Alex on 13.03.2015.
 */
public class WorkoutCategory {
    String name;
    String exercises;

    public String getExercises() {
        return exercises;
    }

    public void setExercises(String exercises) {
        this.exercises = exercises;
    }

    public WorkoutCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
