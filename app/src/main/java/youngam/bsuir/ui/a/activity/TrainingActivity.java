package youngam.bsuir.ui.a.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import youngam.bsuir.R;
import youngam.bsuir.core.m.listeners.SwitchFragmentListener;
import youngam.bsuir.ui.a.fragment.TrainingFragment;

/**
 * Created by Alex on 29.04.2015.
 */
public class TrainingActivity extends ActionBarActivity implements SwitchFragmentListener {
    private TrainingFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_layout);
        fragment = new TrainingFragment();
        switchFragment(fragment, false);


    }


    @Override
    //@param addToBackStack need to prevent the bag when back key pressed
    // and you didn't go back to mainActivity
    public void switchFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (addToBackStack) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    }
}

