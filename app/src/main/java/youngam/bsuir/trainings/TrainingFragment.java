package youngam.bsuir.trainings;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;

import youngam.bsuir.R;
import youngam.bsuir.core.model.MyCalendar;

/**
 * Created by Alex on 09.03.2015.
 */
public class TrainingFragment extends Fragment{
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private ActionBar mAction;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.training_fragment_layout, container, false);
        mAction = ((TrainingActivity)getActivity()).getSupportActionBar();
        try {
            mAction.setTitle(MyCalendar.getDate(MyCalendar.getDateNowMilliseconds()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private final int DAY_IN_YEAR = 364;
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
            //TODO: choose an interval of a time to show exercises
            return DAY_IN_YEAR;
        }
    }
}
