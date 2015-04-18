package youngam.bsuir.exercises;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import youngam.bsuir.R;
import youngam.bsuir.adapter.MyAdapter;
import youngam.bsuir.core.model.WorkoutCategory;
import youngam.bsuir.listener.SwitchFragmentListener;
import youngam.bsuir.exercises.parser.MySQLiteDB;

/**
 * Created by Alex on 11.03.2015.
 */
public class ExercisesForMusclesFragment extends ListFragment {
    private final static String LOG_TAG = ExercisesForMusclesFragment.class.getSimpleName();
    private ArrayList<WorkoutCategory> exercises;
    MySQLiteDB db;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView");
        View view = inflater.inflate(R.layout.biceps_layout, container, false);

        //Получаем id нажатой группы мышц
        String muscleId = getArguments().getString("category");
        //Вызываем метод из activity
        //для выборки упражнений одной группы мышц
        try {
            exercises = ((WorkoutManActivity)getActivity()).getExercises(muscleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyAdapter adapter = new MyAdapter(exercises);
        setListAdapter(adapter);
        return view;
    }

    //On click for button

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        IndividualExercise fragment = new IndividualExercise();
        Bundle bundle = new Bundle();
        bundle.putString("exerciseId", exercises.get(position).getId());
        fragment.setArguments(bundle);
        //execute activity switchFragment method
        //which move this fragment to the main UI
        ((SwitchFragmentListener) getActivity()).switchFragment(fragment, true);
    }


}
