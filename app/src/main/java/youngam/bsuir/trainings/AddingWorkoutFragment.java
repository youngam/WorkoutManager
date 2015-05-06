package youngam.bsuir.trainings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;

import youngam.bsuir.R;
import youngam.bsuir.core.model.MyCalendar;
import youngam.bsuir.core.model.WorkoutCategory;
import youngam.bsuir.databases.MySQLiteDB;
import youngam.bsuir.listeners.OnFinishedListener;
import youngam.bsuir.trainings.pickers.DatePickerFragment;
import youngam.bsuir.trainings.pickers.TimePickerFragment;

/**
 * Created by Alex on 15.04.2015.
 */
public class AddingWorkoutFragment extends Fragment implements View.OnClickListener {
    private MySQLiteDB db;
    private MultiSelectionSpinner spinnerGroups, spinnerExercises;
    private ArrayList<WorkoutCategory> exercisesChose;
    private DatePickerFragment datePicker;
    private TimePickerFragment timePicker;
    private Button btnTime;
    private Button btnDate;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.adding_workout, container, false);

        // Component initialization
        Button addButton = (Button) view.findViewById(R.id.btnAdd);
        addButton.setOnClickListener(this);
        btnDate = (Button) view.findViewById(R.id.bttnDate);
        btnTime = (Button) view.findViewById(R.id.bttnTime);
        btnDate.setText(MyCalendar.getDateNow());
        btnTime.setText(MyCalendar.getTimeNow());

        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);

        //Initialization of the db
        db = new MySQLiteDB();
        db.initDb(getActivity().getApplicationContext());

        spinnerGroups = (MultiSelectionSpinner) view.findViewById(R.id.spinnerGroups);
        spinnerGroups.setItems(db.getMuscleGroups());
        spinnerExercises = (MultiSelectionSpinner) view.findViewById(R.id.spinnerExercises);
        spinnerExercises.setItems(db.getExercises("1"));

        // Need to fill second spinner, when the first clicked.
        spinnerGroups.setOnFinishedListener(new OnFinishedListener() {
            @Override
            public void onFinish() {
                ArrayList<WorkoutCategory> exercises = new ArrayList<WorkoutCategory>();


                if (spinnerGroups.getResult() == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Выберите группу мышц",
                            Toast.LENGTH_SHORT).show();
                } else {
                    for (WorkoutCategory category : spinnerGroups.getResult()) {
                        exercises.addAll(db.getExercises(category.getId()));
                        Log.d("AddingWorkout", category.getName());
                    }
                    spinnerExercises.setItems(exercises);
                }


            }
        });
        spinnerExercises.setOnFinishedListener(new OnFinishedListener() {
            @Override
            public void onFinish() {
                exercisesChose = new ArrayList<WorkoutCategory>();
                exercisesChose = spinnerExercises.getResult();

            }
        });
        datePicker = new DatePickerFragment();
        timePicker = new TimePickerFragment();

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:

                //TODO :  check if user don't click on data or time set button
                long currDate = MyCalendar.toMilliseconds(datePicker.getResult(), timePicker.getResult());
                db.addToDate(currDate);

                String dateId = db.getDateId(currDate);

                if (exercisesChose == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Выберите упражнения  "
                            , Toast.LENGTH_SHORT).show();
                } else {
                    for (WorkoutCategory category : exercisesChose) {

                        db.addToUserTrainings(category.getId(), dateId);
                        Log.d("AddingWorkout OnClick()", "exerciseId: " + category.getId() + "dateId: " + dateId);
                    }
                    Toast.makeText(getActivity().getApplicationContext(), "Тренировка добавлена"
                            , Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.bttnTime:
                showTimePickerDialog();

                break;
            case R.id.bttnDate:
                showDatePickerDialog();
                break;

        }
    }


    public void showDatePickerDialog() {
        datePicker.show(getActivity().getFragmentManager(), "datePicker");
        datePicker.setOnFinishedListener(new OnFinishedListener() {
            @Override
            public void onFinish() {
                // Here I can get information from DatePickerDialog
                try {
                    btnDate.setText(datePicker.getText());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void showTimePickerDialog() {
        timePicker.show(getActivity().getFragmentManager(), "timePicker");
        timePicker.setOnFinishedListener(new OnFinishedListener() {
            @Override
            public void onFinish() {
                try {
                    btnTime.setText(timePicker.getText());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }


}
