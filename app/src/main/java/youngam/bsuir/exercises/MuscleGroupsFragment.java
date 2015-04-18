package youngam.bsuir.exercises;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import youngam.bsuir.R;
import youngam.bsuir.adapter.MyAdapter;
import youngam.bsuir.core.model.WorkoutCategory;
import youngam.bsuir.listener.SwitchFragmentListener;
import youngam.bsuir.exercises.parser.MySQLiteDB;


public class MuscleGroupsFragment extends ListFragment {
    private final String[] muscleGroups = {"Ноги", "Спина", "Грудь", "Дельты", "Бицепс", "Трицепс"};
    private ArrayList<WorkoutCategory> mCategories;
    private final static String LOG_TAG = MuscleGroupsFragment.class.getSimpleName();
    private MySQLiteDB db;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.list_view_fragment, container, false);
       // db.initDb(getActivity().getApplicationContext());
        //fill list
        try {
            mCategories = ((WorkoutManActivity)getActivity()).getMuscleGroup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyAdapter adapter = new MyAdapter(mCategories);
        setListAdapter(adapter);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        try {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Список упражнений");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d(LOG_TAG, "onCreateView()");
        ExercisesForMusclesFragment fragment = new ExercisesForMusclesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", mCategories.get(position).getId());
        fragment.setArguments(bundle);
        ((SwitchFragmentListener) getActivity()).switchFragment(fragment);
    }
}
