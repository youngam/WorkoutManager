package youngam.bsuir.trainings.pickers;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;

import youngam.bsuir.core.model.MyCalendar;
import youngam.bsuir.listeners.OnFinishedListener;

/**
 * Created by Alex on 09.04.2015.
 */
public  class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    private int[] result = new int[3];
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
        result[0] = hourOfDay;
        result[1] = minute;
        mListener.onFinish();
    }

    public void setOnFinishedListener(OnFinishedListener listener){
        mListener = listener;

    }

    public int[] getResult(){
        if(Arrays.equals(result, new int[]{0, 0, 0})) {
            Log.d("getResult", "null");
            return MyCalendar.getArrayOfTime();
        } else{
            Log.d("getResult", "not null");
            return result;
        }
    }

    public String getText() throws ParseException {
        return MyCalendar.toTime(result);
    }

}
