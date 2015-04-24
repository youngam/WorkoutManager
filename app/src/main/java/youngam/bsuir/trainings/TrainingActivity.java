package youngam.bsuir.trainings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import youngam.bsuir.R;

/**
 * Created by Alex on 09.03.2015.
 */
public class TrainingActivity extends ActionBarActivity{
    private AddingWorkoutFragment addingWorkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_layout);
        addingWorkout = new AddingWorkoutFragment();
        switchFragment(addingWorkout);


    }





    public void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.pager, fragment).commit();

    }

}
