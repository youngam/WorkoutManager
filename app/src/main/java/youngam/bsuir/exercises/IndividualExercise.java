package youngam.bsuir.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

import youngam.bsuir.R;
import youngam.bsuir.core.model.WorkoutCategory;
public class IndividualExercise extends Fragment implements View.OnClickListener    {

    private TextView mTextView;
    public static final String API_KEY = "AIzaSyAGCtZ0prTrcyOCA6ajGgMQfv9kghSSCTo";
    private TextView textView;
    private WorkoutCategory currCategory;
    private ImageView image;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise_layout, container, false);
        textView = (TextView) view.findViewById(R.id.textView);
        image = (ImageView) view.findViewById(R.id.imageView);
        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(this);


        String exerciseId = getArguments().getString("exerciseId");
        System.out.println(exerciseId);
        try {
            currCategory = ((WorkoutManActivity)getActivity()).getIndividualExercise(exerciseId);
        } catch (Exception e) {
            e.printStackTrace();
        }

            setText(currCategory.getName().trim());


        return view;
    }

    //FIXME CAN'T GET THE RIGHT CONTEXT :(
    public void watchVideo(String VIDEO_ID) {
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(getActivity(), IndividualExercise.API_KEY, VIDEO_ID,
                0, true, false);
        startActivity(intent);
    }

    public void setText(String text) {
        textView.setText(text);
    }


    @Override
    // функция trim() удаляет пробелы до и после Id
    public void onClick(View v) {
        watchVideo(currCategory.getId().trim());
    }
}

