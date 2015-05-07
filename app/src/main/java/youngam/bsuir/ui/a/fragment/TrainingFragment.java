package youngam.bsuir.ui.a.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.software.shell.fab.ActionButton;

import java.text.ParseException;

import youngam.bsuir.R;
import youngam.bsuir.core.m.model.MyCalendar;
import youngam.bsuir.core.m.listeners.OnFinishedListener;
import youngam.bsuir.core.m.listeners.SwitchFragmentListener;
import youngam.bsuir.ui.a.dialog.DatePickerFragment;
import youngam.bsuir.ui.a.activity.TrainingActivity;

/**
 * Created by Alex on 09.03.2015.
 */
public class TrainingFragment extends Fragment{
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private ActionButton actionButton;
    private ActionBar mAction;
    private DatePickerFragment datePickerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.training_fragment_layout, container, false);
        mAction = ((TrainingActivity)getActivity()).getSupportActionBar();
        try {
            mAction.setTitle(MyCalendar.getDate(MyCalendar.getDateNowMilliseconds()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        actionButton = (ActionButton) view.findViewById(R.id.action_button);
        actionButton.setButtonColor(getResources().getColor(R.color.fab_material_blue_900));
        actionButton.setButtonColorPressed(getResources().getColor(R.color.fab_material_blue_500));
        actionButton.setImageDrawable(getResources().getDrawable(R.drawable.fab_plus_icon));
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddingWorkoutFragment addingWorkoutFragment = new AddingWorkoutFragment();
                ((SwitchFragmentListener)getActivity()).switchFragment(addingWorkoutFragment, true);
            }
        });

        mAction.setDisplayShowTitleEnabled(true);
        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {
                    Log.d("DEBUG", "position is : " + String.valueOf(position));
                    long date = MyCalendar.getDateNowMilliseconds() + position * MyCalendar.MILLISECONDS_IN_DAY;
                    mAction.setTitle(MyCalendar.getDate(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_calendar:
                datePickerFragment = new DatePickerFragment();
                datePickerFragment.setOnFinishedListener(new OnFinishedListener() {
                    @Override
                    public void onFinish() {
                        mPager.setCurrentItem(MyCalendar.getDifference(datePickerFragment.getDateMilliseconds()));
                    }
                });
                datePickerFragment.show(getActivity().getFragmentManager(), "datePicker");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int day) {
            Log.d("TAG", "GET ITEM:" + day);
            long date = MyCalendar.getDateNowMilliseconds() + day * MyCalendar.MILLISECONDS_IN_DAY;
            return ListOfTrainingsFragment.newInstance(date);
        }

        @Override
        public int getCount() {
            return MyCalendar.DAYS_IN_YEAR * MyCalendar.COUNT_OF_YEARS;
    }
}
}
