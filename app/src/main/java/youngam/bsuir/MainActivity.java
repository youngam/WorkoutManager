package youngam.bsuir;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import youngam.bsuir.databases.MySQLiteDB;
import youngam.bsuir.exercises.WorkoutManActivity;
import youngam.bsuir.trainings.TrainingActivity;


public class MainActivity extends Activity implements View.OnClickListener {
    private Button mBtnMan;
    private Button mBtnWoman;
    private MySQLiteDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnMan = (Button) findViewById(R.id.manButton);
        mBtnMan.setOnClickListener(this);
        mBtnWoman = (Button) findViewById(R.id.buttonTrainings);
        mBtnWoman.setOnClickListener(this);
        // заполняю бд
        fillDb();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.manButton:

                Intent manIntent = new Intent(this, WorkoutManActivity.class);
                startActivity(manIntent);
                break;
            case R.id.buttonTrainings:

                Intent trainingIntent = new Intent(this, TrainingActivity.class);
                startActivity(trainingIntent);
                break;
        }

    }
    public void fillDb(){
        //инициализация базы
        db = new MySQLiteDB();
        db.initDb(getApplicationContext());
        //Если база пустая, то наполняем её
        if (db.isEmpty()) {
            new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    try {
                        db.parseFile(getResources().openRawResource(R.raw.fitness_exercise));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute(null, null, null);
        }
    }
}
