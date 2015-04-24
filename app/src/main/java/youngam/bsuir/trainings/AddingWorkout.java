package youngam.bsuir.trainings;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import youngam.bsuir.R;
import youngam.bsuir.core.model.WorkoutCategory;
import youngam.bsuir.exercises.parser.MySQLiteDB;
import youngam.bsuir.listener.OnFinishedListener;

/**
 * Created by Alex on 15.04.2015.
 */
public class AddingWorkout extends Fragment implements View.OnClickListener {
    private MySQLiteDB db;
    private DialogFragment dialogFragment;
    private MultiSelectionSpinner spinnerGroups, spinnerExercises;

        @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.adding_workout, container, false);

        // Button initialization
        Button addButton = (Button) view.findViewById(R.id.btnAdd);
        addButton.setOnClickListener(this);
        Button dateButton = (Button) view.findViewById(R.id.btnDate);
        dateButton.setOnClickListener(this);
        Button timeButton = (Button) view.findViewById(R.id.btnTime);
        timeButton.setOnClickListener(this);

        //Initialization of the db
        db = new MySQLiteDB();
        db.initDb(getActivity().getApplicationContext());

        spinnerGroups = (MultiSelectionSpinner) view.findViewById(R.id.spinnerGroups);
        spinnerGroups.setItems(db.getMuscleGroups());
            spinnerExercises = (MultiSelectionSpinner) view.findViewById(R.id.spinnerExercises);
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

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                Toast.makeText(getActivity().getApplicationContext(), "Типа добавление записи в бд", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnTime:
                ((TrainingActivity)getActivity()).showTimePickerDialog();
                break;
            case R.id.btnDate:
                ((TrainingActivity)getActivity()).showDatePickerDialog();
                break;

        }
    }
    public void initDb(){
        db = new MySQLiteDB();
        db.initDb(getActivity().getApplicationContext());
    }
    public ArrayList<WorkoutCategory> getExercise(String muscleGroup){
        String id = db.getMuscleGroupId(muscleGroup);
        ArrayList<WorkoutCategory> exercises = db.getExercises(id);
        return exercises;

    }



}
