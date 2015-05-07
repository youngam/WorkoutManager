package youngam.bsuir.core.m.databases;

/**
 * Created by Alex on 02.04.2015.
 */
public class Tables {

    //Table for muscle groups
    public static final String TABLE_MUSCLE_GROUPS = "muscleGroups";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ID = "id";
    public static final String CREATE_TABLE_MUSCLE_GROUPS =
            "CREATE TABLE " + TABLE_MUSCLE_GROUPS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT NOT NULL);";

    //Table for exercises for each group of muscles
    public static final String TABLE_GROUP_EXERCISES = "exercisesForGroups";
    public static final String COLUMN_MUSCLE_ID = "muscleId";
    public static final String CREATE_TABLE_EXERCISES_FOR_GROUPS =
            "CREATE TABLE " + TABLE_GROUP_EXERCISES + " (" + COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_MUSCLE_ID + " TEXT, " + COLUMN_NAME + " TEXT NOT NULL);";

    //Table for individual exercise
    public static final String TABLE_INDIVIDUAL_EXERCISE = "individualExercise";
    public static final String COLUMN_EXERCISE_ID = "groupID";
    public static final String COLUMN_VIDEO_ID = "youtubeID";
    public static final String CREATE_TABLE_INDIVIDUAL_EXERCISE =
            "CREATE TABLE " + TABLE_INDIVIDUAL_EXERCISE + " (" + COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_EXERCISE_ID + " TEXT, "
                    + COLUMN_VIDEO_ID + " TEXT NOT NULL, "
                    + COLUMN_NAME + " TEXT NOT NULL);";

    //Table for userTrainings(join table)
    // which compare date,time and the exercise that should be execute
    public static final String TABLE_USER_TRAININGS = "userTrainings";
    public static final String COLUMN_TRAINING_ID = "exerciseId";
    public static final String COLUMN_DATE_ID = "dateId";
    public static final String CREATE_TABLE_USER_TRAININGS =
            "CREATE TABLE " + TABLE_USER_TRAININGS + " (" + COLUMN_TRAINING_ID + " INTEGER NOT NULL, "
            +COLUMN_DATE_ID + " TEXT NOT NULL);";

    //Table for date and time

    public static final String TABLE_DATE_TIME = "dateTime";
    public static final String COLUMN_DATE = "date";
    public static final String CREATE_TABLE_DATE_TIME =
            "CREATE TABLE " + TABLE_DATE_TIME  + " ("+ COLUMN_DATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DATE + " INTEGER NOT NULL);";




}
