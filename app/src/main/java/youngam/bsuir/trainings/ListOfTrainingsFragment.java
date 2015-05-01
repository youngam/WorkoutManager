package youngam.bsuir.trainings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import youngam.bsuir.R;
import youngam.bsuir.adapter.ExpandableListAdapter;
import youngam.bsuir.core.model.UserTrainings;
import youngam.bsuir.databases.MySQLiteDB;
import youngam.bsuir.listeners.SwitchFragmentListener;

/**
 * Created by Alex on 25.04.2015.
 */
public class ListOfTrainingsFragment extends Fragment {
    private ExpandableListView listView;
    private ArrayList<UserTrainings> trainings;
    private String[][] children;
    private ActionBar mActionBar;
    private MySQLiteDB db;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mActionBar = ((TrainingActivity)getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(true);
        View view = inflater.inflate(R.layout.list_of_trainings, container, false);
        long currentDate = getArguments().getLong("date");

        db = new MySQLiteDB();
        db.initDb(getActivity().getApplicationContext());
        trainings = new ArrayList<>();
        trainings = db.getUserTrainings(currentDate);
        for(UserTrainings tr: trainings){
            Log.d("DEBUG", "groups" + tr.getMuscleGroups() + "exercises : "  + tr.getExercises());
        }


        Button button = (Button) view.findViewById(R.id.bttnAddTraining);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddingWorkoutFragment addingWorkoutFragment = new AddingWorkoutFragment();
                ((SwitchFragmentListener)getActivity()).switchFragment(addingWorkoutFragment, true);
            }
        });
        return view;
    }

    public static ListOfTrainingsFragment newInstance(long date){
        Bundle arg = new Bundle();
        arg.putLong("date", date);
        ListOfTrainingsFragment fragment = new ListOfTrainingsFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        ExpandableListAdapter adapter = new ExpandableListAdapter(trainings);
        listView.setAdapter(adapter);
    }
}
