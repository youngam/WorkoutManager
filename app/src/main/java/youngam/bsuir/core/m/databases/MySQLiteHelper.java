package youngam.bsuir.core.m.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alex on 02.04.2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FitnessManager.db";
    private static final int DATABASE_VERSION = 1;


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Tables.CREATE_TABLE_MUSCLE_GROUPS);
        db.execSQL(Tables.CREATE_TABLE_EXERCISES_FOR_GROUPS);
        db.execSQL(Tables.CREATE_TABLE_INDIVIDUAL_EXERCISE);
        db.execSQL(Tables.CREATE_TABLE_DATE_TIME);
        db.execSQL(Tables.CREATE_TABLE_USER_TRAININGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
