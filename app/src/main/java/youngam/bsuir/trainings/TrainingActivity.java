package youngam.bsuir.trainings;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import youngam.bsuir.R;
import youngam.bsuir.trainings.pickers.DatePickerFragment;
import youngam.bsuir.trainings.pickers.TimePickerFragment;

/**
 * Created by Alex on 09.03.2015.
 */
public class TrainingActivity extends ActionBarActivity{
    private DialogFragment dialogFragment;
    private AddingWorkout addingWorkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_layout);
        addingWorkout = new AddingWorkout();
        switchFragment(addingWorkout);


    }




    public void showDatePickerDialog() {
        dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog() {
        dialogFragment = new TimePickerFragment();
        dialogFragment.show(getFragmentManager(), "timePicker");
    }
    public void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.pager, fragment).addToBackStack(null).commit();

    }

}
