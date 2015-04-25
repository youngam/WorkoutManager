package youngam.bsuir.core.model;

/**
 * Created by Alex on 25.04.2015.
 */
public class UserTrainings {
    private String date;
    private String dateId;
    private String time;
    private String exerciseName;
    private String exerciseId;

    public UserTrainings(String date, String time, String dateId, String exerciseName, String exerciseId){
        this.date = date;
        this.time = time;
        this.dateId = dateId;
        this.exerciseName = exerciseName;
        this.exerciseId = exerciseId;

    }
    public String getDateId() {
        return dateId;
    }

    public void setDateId(String dateId) {
        this.dateId = dateId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
