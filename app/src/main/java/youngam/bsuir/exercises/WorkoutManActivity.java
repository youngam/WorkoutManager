package youngam.bsuir.exercises;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.ArrayList;

import youngam.bsuir.R;
import youngam.bsuir.core.model.WorkoutCategory;
import youngam.bsuir.listeners.SwitchFragmentListener;
import youngam.bsuir.exercises.databases.MySQLiteDB;

/**
 * Created by Alex on 09.03.2015.
 */
public class WorkoutManActivity extends ActionBarActivity implements SwitchFragmentListener {
    private static final String LOG_TAG = WorkoutManActivity.class.getSimpleName();
    private MySQLiteDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        db = new MySQLiteDB();
        db.initDb(this);
        setContentView(R.layout.man_layout);
        getSupportActionBar().setTitle("Группы мышц");
        if (savedInstanceState == null) {
            MuscleGroupsFragment fragment = new MuscleGroupsFragment();
            switchFragment(fragment, false);

        }

    }


    @Override
    public void switchFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(addToBackStack) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }

    }
    public ArrayList<WorkoutCategory> getMuscleGroup() throws Exception {
        return db.getMuscleGroups();
    }
    public ArrayList<WorkoutCategory> getExercises(String id) throws Exception {
        return db.getExercises(id);
    }
    public WorkoutCategory getIndividualExercise(String id) throws Exception {
        return db.getIndividualExercise(id);
    }


}
