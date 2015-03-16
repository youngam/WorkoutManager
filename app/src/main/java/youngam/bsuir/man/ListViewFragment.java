package youngam.bsuir.man;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import youngam.bsuir.R;
import youngam.bsuir.adapter.MyAdapter;
import youngam.bsuir.core.model.WorkoutCategory;
import youngam.bsuir.listener.SwitchFragmentListener;


public class ListViewFragment extends ListFragment {
    private final String[] muscleGroups = {"Ноги", "Спина", "Грудь", "Дельты", "Бицепс", "Трицепс", "Предплечья"};
    private ArrayList<WorkoutCategory> mCategories;
    private final static String LOG_TAG = ListViewFragment.class.getSimpleName();



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.list_view_fragment, container, false);
            mCategories = generateCategories();
            MyAdapter adapter = new MyAdapter(mCategories);
            setListAdapter(adapter);
            return view;
    }

    private ArrayList<WorkoutCategory> generateCategories() {
        ArrayList<WorkoutCategory> categories = new ArrayList<>();
        for (String str : muscleGroups) {
            categories.add(new WorkoutCategory(str));
        }
        return categories;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d(LOG_TAG, "onCreateView()");
        MyFragment fragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", mCategories.get(position).getName());
        fragment.setArguments(bundle);
        //check interface
        ((SwitchFragmentListener) getActivity()).switchFragment(fragment);
    }
}
