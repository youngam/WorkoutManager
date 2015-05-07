package youngam.bsuir.core.databases;

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
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import youngam.bsuir.core.model.Date;
import youngam.bsuir.core.model.MyCalendar;
import youngam.bsuir.core.model.UserTrainings;
import youngam.bsuir.core.model.WorkoutCategory;


/**
 * Created by Alex on 01.04.2015.
 */
public class MySQLiteDB {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;;

    //инициализируем компоненты бд
    public void initDb(Context context) {
        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    //Проверяем, если бд не пустая, то её не надо заполнять
    public boolean isEmpty() {

        return !database.query(Tables.TABLE_MUSCLE_GROUPS, null, null, null, null, null, null).moveToFirst();
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

    public String getMuscleGroupName(String groupId){

        Cursor cursor = database.query(Tables.TABLE_MUSCLE_GROUPS, null, Tables.COLUMN_ID + "= ?",
                new String[]{groupId}, null, null, null);

        cursor.moveToFirst();


        return  cursor.getString(cursor.getColumnIndex(Tables.COLUMN_NAME));

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

    public ArrayList<UserTrainings> getUserTrainings(long date) {

        //FIXME : exercises are adding with repetition

        // Adding exercises, which user should do at this date
        ArrayList<UserTrainings> mExercises =  new ArrayList<>();

         for(Date listOfDates : getDates(date)){

             //I'm using  HashSet because I need to avoid repetition of names
             Set<String> groupName = new HashSet<>();
              ArrayList<WorkoutCategory> categories = getExercisesUserTrainings(listOfDates.getId());

             for(WorkoutCategory category : categories) {
                 groupName.add(category.getParentName());
             }



             mExercises.add(new UserTrainings(listOfDates.getDate(),listOfDates.getId(), groupName, categories));
             categories = null;

        }

        return mExercises;
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

    public WorkoutCategory getExerciseFromGroupExercises(String exerciseId){
        Cursor cursor = database.query(Tables.TABLE_GROUP_EXERCISES, null,
                Tables.COLUMN_ID + "= ?",
                new String[]{exerciseId}, null, null, null);
        cursor.moveToFirst();
        Log.d("GetExercise", cursor.getString(cursor.getColumnIndex(Tables.COLUMN_NAME)));

        return new WorkoutCategory(cursor.getString(cursor.getColumnIndex(Tables.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Tables.COLUMN_ID)),
                getMuscleGroupName(cursor.getString(cursor.getColumnIndex(Tables.COLUMN_MUSCLE_ID)))) ;

    }

    public ArrayList<WorkoutCategory> getExercisesUserTrainings(String dateId) {
        Cursor cursor = database.query(Tables.TABLE_USER_TRAININGS, null,
                Tables.COLUMN_DATE_ID + "= ?",
                new String[]{dateId}, null, null, null);
        cursor.moveToFirst();
        ArrayList<WorkoutCategory> categories = new ArrayList<>();

        while(!cursor.isAfterLast()){
            categories.add(getExerciseFromGroupExercises(cursor.getString(cursor.getColumnIndex(Tables.COLUMN_TRAINING_ID))));
            cursor.moveToNext();
        }
        return categories;

    }




    public void addToDate(long milliseconds) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Tables.COLUMN_DATE, milliseconds);
        database.insert(Tables.TABLE_DATE_TIME, null, contentValues);
    }
    public ArrayList<Date> getDates(long date) {
        // end of the day in milliseconds
        ArrayList<Date> dates = new ArrayList<>();
        long nextDay = date + MyCalendar.MILLISECONDS_IN_DAY;

        // Choose date which are in interval of this day
        Cursor cursor = database.query(Tables.TABLE_DATE_TIME, null, Tables.COLUMN_DATE + " < ?"+ " AND " + Tables.COLUMN_DATE + " > ?",
                new String[] {String.valueOf(nextDay), String.valueOf(date)}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            dates.add(new Date(Long.parseLong(cursor.getString(cursor.getColumnIndex(Tables.COLUMN_DATE))),
                    cursor.getString(cursor.getColumnIndex(Tables.COLUMN_DATE_ID))));
            cursor.moveToNext();
        }

        return dates;


    }

    public String getDateId(long date){
        Cursor cursor = database.query(Tables.TABLE_DATE_TIME, null, Tables.COLUMN_DATE + " = ?",
                new String[] {String.valueOf(date)}, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(Tables.COLUMN_DATE_ID));

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

