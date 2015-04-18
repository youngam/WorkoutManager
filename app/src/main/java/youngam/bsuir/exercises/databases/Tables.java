package youngam.bsuir.exercises.databases;

/**
 * Created by Alex on 02.04.2015.
 */
public class Tables {

    //Первая таблица для групп мышц
    public static final String TABLE_MUSCLE_GROUPS = "muscleGroups";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ID = "id";
    public static final String CREATE_TABLE_MUSCLE_GROUPS =
            "CREATE TABLE " + TABLE_MUSCLE_GROUPS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT NOT NULL);";

    //Вторая таблица для списка упражнений для каждой группы мышц
    public static final String TABLE_GROUP_EXERCISES = "exercisesForGroups";
    public static final String COLUMN_MUSCLE_ID = "muscleId";
    public static final String CREATE_TABLE_EXERCISES_FOR_GROUPS =
            "CREATE TABLE " + TABLE_GROUP_EXERCISES + " (" + COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_MUSCLE_ID + " TEXT, " + COLUMN_NAME + " TEXT NOT NULL);";

    //Третья для хранения информации для каждого упражнения
    public static final String TABLE_INDIVIDUAL_EXERCISE = "individualExercise";
    public static final String COLUMN_EXERCISE_ID = "groupID";
    public static final String COLUMN_VIDEO_ID = "youtubeID";
    public static final String CREATE_TABLE_INDIVIDUAl_EXERCISE=
            "CREATE TABLE " + TABLE_INDIVIDUAL_EXERCISE + " (" + COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_EXERCISE_ID + " TEXT, "
                    + COLUMN_VIDEO_ID + " TEXT NOT NULL, "
                    + COLUMN_NAME + " TEXT NOT NULL);";
    //Четвёртая для хранения информации о пользовательских тренировках
    public static final String TABLE_USER_TRAININGS = "userTrainings";
    public static final String COLUMN_MUSCLE_GROUP = "muscleGroup";
    public static final String CREATE_TABLE_USER_TRAININGS =
            "CREATE TABLE " + TABLE_INDIVIDUAL_EXERCISE + " (" + COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_EXERCISE_ID + " TEXT, "
                    + COLUMN_VIDEO_ID + " TEXT NOT NULL, "
                    + COLUMN_NAME + " TEXT NOT NULL);";


}
