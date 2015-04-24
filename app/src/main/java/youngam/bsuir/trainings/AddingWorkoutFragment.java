package youngam.bsuir.trainings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import youngam.bsuir.R;
import youngam.bsuir.core.model.WorkoutCategory;
import youngam.bsuir.exercises.databases.MySQLiteDB;
import youngam.bsuir.listeners.OnFinishedListener;
import youngam.bsuir.trainings.pickers.DatePickerFragment;
import youngam.bsuir.trainings.pickers.TimePickerFragment;

/**
 * Created by Alex on 15.04.2015.
 */
public class AddingWorkoutFragment extends Fragment implements View.OnClickListener {
    private MySQLiteDB db;
    private MultiSelectionSpinner spinnerGroups, spinnerExercises;
    private ArrayList<String> exercisesChoosed;
    private DatePickerFragment datePicker;
    private TimePickerFragment timePicker;
    private TextView textTime;
    private TextView textDate;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adding_workout, container, false);

        // Component initialization
        Button addButton = (Button) view.findViewById(R.id.btnAdd);
        addButton.setOnClickListener(this);
         textDate = (TextView) view.findViewById(R.id.textDate);
         textTime = (TextView) view.findViewById(R.id.textTime);
        textDate.setOnClickListener(this);
        textTime.setOnClickListener(this);

        //Initialization of the db
        db = new MySQLiteDB();
        db.initDb(getActivity().getApplicationContext());

        spinnerGroups = (MultiSelectionSpinner) view.findViewById(R.id.spinnerGroups);
        spinnerGroups.setItems(db.getMuscleGroups());
        spinnerExercises = (MultiSelectionSpinner) view.findViewById(R.id.spinnerExercises);

        // Need to fill second spinner, when the first clicked.
        spinnerGroups.setOnFinishedListener(new OnFinishedListener() {
            @Override
            public void onFinish() {
                ArrayList<WorkoutCategory> exercises = new ArrayList<WorkoutCategory>();

                for (String str : spinnerGroups.getResult()) {
                    String muscleGroupId = db.getMuscleGroupId(str);
                    exercises.addAll(db.getExercises(muscleGroupId));
                }
                spinnerExercises.setItems(exercises);


            }
        });
        spinnerExercises.setOnFinishedListener(new OnFinishedListener() {
            @Override
            public void onFinish() {
                exercisesChoosed = new ArrayList<String>();
                for (String str : spinnerExercises.getResult()) {
                    exercisesChoosed.add(str);

                }
            }
        });

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                Toast.makeText(getActivity().getApplicationContext(), "Типа добавление записи в бд", Toast.LENGTH_SHORT).show();
                break;
            case R.id.textTime:
                showTimePickerDialog();

                break;
            case R.id.textDate:
                showDatePickerDialog();
                break;

        }
    }

    public void initDb() {
        db = new MySQLiteDB();
        db.initDb(getActivity().getApplicationContext());
    }

    public ArrayList<WorkoutCategory> getExercise(String muscleGroup) {
        String id = db.getMuscleGroupId(muscleGroup);
        ArrayList<WorkoutCategory> exercises = db.getExercises(id);
        return exercises;

    }

    public void showDatePickerDialog() {
        datePicker = new DatePickerFragment();
        datePicker.show(getActivity().getFragmentManager(), "datePicker");
        datePicker.setOnFinishedListener(new OnFinishedListener() {
            @Override
            public void onFinish() {
                // Here I can get information from DatePickerDialog
                textDate.setText(datePicker.getResult());

            }
        });
    }

    public void showTimePickerDialog() {
        timePicker = new TimePickerFragment();
        timePicker.show(getActivity().getFragmentManager(), "timePicker");
        timePicker.setOnFinishedListener(new OnFinishedListener() {
            @Override
            public void onFinish() {
                textTime.setText(timePicker.getResult());

            }
        });
    }


}
