package youngam.bsuir.man;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import youngam.bsuir.R;

/**
 * Created by Alex on 11.03.2015.
 */
public class MyFragment extends Fragment {
    private final static String LOG_TAG = MyFragment.class.getSimpleName();
    TextView mTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView");
        View view = inflater.inflate(R.layout.biceps_layout, container, false);
        mTextView = (TextView) view.findViewById(R.id.tv_bom);
        mTextView.setText(getArguments().getString("category"));
        return view;
    }

}
