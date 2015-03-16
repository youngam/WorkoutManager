package youngam.bsuir.man;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import youngam.bsuir.R;
import youngam.bsuir.adapter.MyAdapter;
import youngam.bsuir.listener.SwitchFragmentListener;

/**
 * Created by Alex on 09.03.2015.
 */
public class WorkoutManActivity extends ActionBarActivity implements SwitchFragmentListener {
    private MyAdapter adapter;
    private ListView listView;
    private static final String LOG_TAG = WorkoutManActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.man_layout);
        if (savedInstanceState == null) {
            ListViewFragment fragment = new ListViewFragment();
            switchFragment(fragment);
        }


    }

    @Override
    public void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

    }

}
