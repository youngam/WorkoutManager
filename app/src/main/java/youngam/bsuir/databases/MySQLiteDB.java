package youngam.bsuir.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import youngam.bsuir.core.model.DateTime;
import youngam.bsuir.core.model.UserTrainings;
import youngam.bsuir.core.model.WorkoutCategory;


/**
 * Created by Alex on 01.04.2015.
 */
public class MySQLiteDB {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    //инициализируем компоненты бд
    public void initDb(Context context) {
        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    //Проверяем, если бд не пустая, то её не надо заполнять
    public boolean isEmpty() {

        return !database.query(Tables.TABLE_MUSCLE_GROUPS, null, null, null, null, null, null).moveToFirst() ;
    }


    // / метод для добавления мышечной группы

    public void addToMuscleGroupDb(String name) {
        ContentValues values = new ContentValues();
        values.put(Tables.COLUMN_NAME, name);
        database.insert(Tables.TABLE_MUSCLE_GROUPS, null, values);
    }

    //Достаём данные из бд и кладём их в ArrayList
    public ArrayList<WorkoutCategory> getMuscleGroups() {
        Cursor cursor = database.query(Tables.TABLE_MUSCLE_GROUPS, null, null,
                null, null, null, null);

        cursor.moveToFirst();
        ArrayList<WorkoutCategory> categories = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            categories.add(new WorkoutCategory(cursor.getString(cursor.getColumnIndex(Tables.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(Tables.COLUMN_ID))));
            cursor.moveToNext();
        }
        cursor.close();
        return categories;
    }

    //метод для добавления упражнений для мышечной группы
    //@param groupId - указывает, к какой группе мышц относится список упражнений

    public void addToExercisesForGroupDb(String muscleGroupId, String name) {
        //FIXME повторное добавление данных в бд
        ContentValues values = new ContentValues();
        values.put(Tables.COLUMN_NAME, name);
        values.put(Tables.COLUMN_MUSCLE_ID, muscleGroupId);
        database.insert(Tables.TABLE_GROUP_EXERCISES, null, values);

    }

    public ArrayList<WorkoutCategory> getExercises(String groupId) {

        //Достаём только те записи, где muscleId == id.

        Cursor cursor = database.query(Tables.TABLE_GROUP_EXERCISES, null,
                Tables.COLUMN_MUSCLE_ID + "= ?",
                new String[]{groupId}, null, null, null);
        cursor.moveToFirst();

        ArrayList<WorkoutCategory> exercises = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            exercises.add(new WorkoutCategory(cursor.getString(cursor.getColumnIndex(Tables.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(Tables.COLUMN_ID))));
            cursor.moveToNext();
        }
        cursor.close();
        return exercises;

    }

    public WorkoutCategory getExercise(String exerciseId) {
        Cursor cursor = database.query(Tables.TABLE_GROUP_EXERCISES, null,
                Tables.COLUMN_ID + "= ?",
                new String[]{exerciseId}, null, null, null);
        cursor.moveToFirst();

        return new WorkoutCategory(cursor.getString(cursor.getColumnIndex(Tables.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Tables.COLUMN_ID)));

    }

    //метод для добавления упражнений для мышечной группы
    //@param exerciseId - указывает, к какой группе мышц относится список упражнений
    public void addToIndividualExerciseDb(String name, String exerciseGroupId, String videoUrl) {
        //FIXME повторное добавление данных в бд
        ContentValues values = new ContentValues();
        values.put(Tables.COLUMN_NAME, name);
        values.put(Tables.COLUMN_EXERCISE_ID, exerciseGroupId);
        values.put(Tables.COLUMN_VIDEO_ID, videoUrl);
        database.insert(Tables.TABLE_INDIVIDUAL_EXERCISE, null, values);

    }

    public WorkoutCategory getIndividualExercise(String exerciseId) {

        //Достаём только ту запись, где exerciseId == id

        Cursor cursor = database.query(Tables.TABLE_INDIVIDUAL_EXERCISE, null,
                Tables.COLUMN_EXERCISE_ID + "= ?",
                new String[]{exerciseId}, null, null, null);
        cursor.moveToFirst();

        //Добавляем два поля: name, videoId
        String videoId = cursor.getString(cursor.getColumnIndex(Tables.COLUMN_VIDEO_ID));
        String name = cursor.getString(cursor.getColumnIndex(Tables.COLUMN_NAME));
        WorkoutCategory exercise = new WorkoutCategory(name, videoId);

        cursor.close();
        return exercise;

    }

    public void addToUserTrainings(String exerciseId, String dateId) {
        ContentValues values = new ContentValues();
        values.put(Tables.COLUMN_TRAINING_ID, exerciseId);
        values.put(Tables.COLUMN_DATE_ID, dateId);
        database.insert(Tables.TABLE_USER_TRAININGS, null, values);

    }

    public ArrayList<UserTrainings> getUserTrainings(String date) {

        ArrayList<UserTrainings> trainings = new ArrayList<>();
        DateTime currentDate = getDate(date);
        Cursor cursor = database.query(Tables.TABLE_USER_TRAININGS, null, Tables.COLUMN_DATE_ID + "= ?",
                new String[] {currentDate.getId()}, null, null, null);

        cursor.moveToFirst();
        Log.d("DEBUG", "exerciseId: " +cursor.getString(cursor.getColumnIndex(Tables.COLUMN_DATE_ID)));


        while (!cursor.isAfterLast()) {

            //Get exercise from ExerciseTable

             WorkoutCategory exercise = getExercise(cursor.getString(cursor.getColumnIndex(Tables.COLUMN_TRAINING_ID)));

             trainings.add(new UserTrainings(currentDate.getDate(), currentDate.getTime(),currentDate.getId(), exercise.getName(), exercise.getId()));

            cursor.moveToNext();
        }


        cursor.close();
        return trainings;
    }

    public void addToDate(DateTime dateTime) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Tables.COLUMN_DATE, dateTime.getDate());
        contentValues.put(Tables.COLUMN_TIME, dateTime.getTime());
        database.insert(Tables.TABLE_DATE_TIME, null, contentValues);
    }

    public DateTime getDate(String date) {
        Cursor cursor = database.query(Tables.TABLE_DATE_TIME, null, Tables.COLUMN_DATE + "= ?",
                new String[]{date}, null, null, null);
        cursor.moveToFirst();

        return new DateTime(cursor.getString(cursor.getColumnIndex(Tables.COLUMN_DATE_ID)),
                cursor.getString(cursor.getColumnIndex(Tables.COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndex(Tables.COLUMN_TIME)));

    }


    //Достаём все данные из xml файла и записываем их в соотв. таблицы бд
    public void parseFile(InputStream file) throws Exception {
        // Стандартное объявление для dom parser
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        //добавление записей в бд
        NodeList group = doc.getElementsByTagName("group");
        for (int temp = 0; temp < group.getLength(); temp++) {
            System.out.println("----------------------------");
            Node groupNode = group.item(temp);
            Element groupElement = (Element) groupNode;
            //Добавляем запись в таблицу мышечных групп
            addToMuscleGroupDb(groupElement.getAttribute("name"));
            String muscleGroupId = groupElement.getAttribute("id");


            NodeList exercises = groupElement.getElementsByTagName("exercises");
            for (int count = 0; count < exercises.getLength(); count++) {
                Node exerciseNode = exercises.item(count);
                Element eElement = (Element) exerciseNode;

                //Добавляем запись в таблицу упражнений на группу мышц;
                addToExercisesForGroupDb(muscleGroupId, eElement.getAttribute("name"));

                //Добавляем запись в таблицу индивидуальных упражнений
                String implementation = eElement.getElementsByTagName("implementation").item(0).getTextContent();
                String videoUrl = eElement.getElementsByTagName("videoUrl").item(0).getTextContent();
                String exerciseId = eElement.getAttribute("id");
                addToIndividualExerciseDb(implementation, exerciseId, videoUrl);


            }
        }
    }
}

