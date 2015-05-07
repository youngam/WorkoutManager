package youngam.bsuir.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import youngam.bsuir.R;
import youngam.bsuir.core.databases.MySQLiteDB;
import youngam.bsuir.core.model.UserTrainings;
import youngam.bsuir.ui.activity.TrainingActivity;
import youngam.bsuir.ui.adapter.ExpandableListAdapter;

/**
 * Created by Alex on 25.04.2015.
 */
public class ListOfTrainingsFragment extends Fragment {
    private ExpandableListView listView;
    private ArrayList<UserTrainings> trainings;
    private ActionBar mActionBar;
    private MySQLiteDB db;
    private TextView txtView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_of_trainings, container, false);

        mActionBar = ((TrainingActivity)getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(true);
        txtView = (TextView) view.findViewById(R.id.txtHoliday);
        long currentDate = getArguments().getLong("date");

        db = new MySQLiteDB();
        db.initDb(getActivity().getApplicationContext());
        trainings = new ArrayList<>();
        trainings = db.getUserTrainings(currentDate);
        for(UserTrainings training : trainings){
            Log.d("ListOfTrainings", training.getMuscleGroups());
        }
        if(trainings.isEmpty()){
            txtView.setVisibility(View.VISIBLE);
        }

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
