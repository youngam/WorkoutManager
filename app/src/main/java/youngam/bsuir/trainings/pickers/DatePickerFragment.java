package youngam.bsuir.trainings.pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.text.ParseException;
import java.util.Calendar;

import youngam.bsuir.core.model.MyCalendar;
import youngam.bsuir.listeners.OnFinishedListener;

/**
 * Created by Alex on 09.04.2015.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private int[] date;
    private OnFinishedListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        date = new int[3];
        date[0] = year;
        date[1] = month;
        date[2] = day;

        mListener.onFinish();
    }

    public int[] getResult() {
        return date;
    }
    public String getText() throws ParseException {

        return MyCalendar.toDate(date);
    }

    public void setOnFinishedListener(OnFinishedListener listener) {
        mListener = listener;
    }
}