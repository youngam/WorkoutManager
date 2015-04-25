package youngam.bsuir.trainings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import youngam.bsuir.R;
import youngam.bsuir.adapter.ExpandableListAdapter;
import youngam.bsuir.core.model.UserTrainings;

/**
 * Created by Alex on 25.04.2015.
 */
public class ListOfTrainingsFragment extends Fragment {
    private ExpandableListView listView;
    private ArrayList<UserTrainings> trainings;
    private String[][] children;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_of_trainings, container, false);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        ExpandableListAdapter adapter = new ExpandableListAdapter(trainings, children);
        listView.setAdapter(adapter);
    }
}
