package youngam.bsuir.ui.a.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import youngam.bsuir.R;
import youngam.bsuir.ui.a.adapter.ListViewAdapter;
import youngam.bsuir.core.m.model.WorkoutCategory;
import youngam.bsuir.core.m.databases.MySQLiteDB;
import youngam.bsuir.core.m.listeners.SwitchFragmentListener;
import youngam.bsuir.ui.a.activity.WorkoutManActivity;


public class MuscleGroupsFragment extends ListFragment {
    private ArrayList<WorkoutCategory> mCategories;
    private final static String LOG_TAG = MuscleGroupsFragment.class.getSimpleName();
    private MySQLiteDB db;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Группы мышц:");
        }catch (Exception e){
            e.printStackTrace();
        }
        View view = inflater.inflate(R.layout.list_view_fragment, container, false);
       // db.initDb(getActivity().getApplicationContext());
        //fill list
        try {
            mCategories = ((WorkoutManActivity)getActivity()).getMuscleGroup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListViewAdapter adapter = new ListViewAdapter(mCategories);
        setListAdapter(adapter);
        return view;
    }




    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d(LOG_TAG, "onCreateView()");
        ExercisesForMusclesFragment fragment = new ExercisesForMusclesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", mCategories.get(position).getId());
        fragment.setArguments(bundle);
        ((SwitchFragmentListener) getActivity()).switchFragment(fragment,true);
    }
}
