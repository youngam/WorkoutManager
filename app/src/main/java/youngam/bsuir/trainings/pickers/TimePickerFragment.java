package youngam.bsuir.trainings.pickers;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import youngam.bsuir.listeners.OnFinishedListener;

/**
 * Created by Alex on 09.04.2015.
 */
public  class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    private String result;
    private OnFinishedListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        result = hourOfDay + " :" + minute;
        mListener.onFinish();
    }

    public void setOnFinishedListener(OnFinishedListener listener){
        mListener = listener;

    }

    public String getResult(){
        return result;
    }

}
