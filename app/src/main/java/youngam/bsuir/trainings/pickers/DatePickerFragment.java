package youngam.bsuir.trainings.pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;

import youngam.bsuir.core.model.MyCalendar;
import youngam.bsuir.listeners.OnFinishedListener;

/**
 * Created by Alex on 09.04.2015.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private int[] date = new int[3];
    private OnFinishedListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current date as the default date in the picker

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.getDatePicker().setMinDate(MyCalendar.getDateNowMilliseconds());
        dialog.getDatePicker().setMaxDate(MyCalendar.getDateNowMilliseconds() + MyCalendar.DAYS_IN_YEAR * MyCalendar.COUNT_OF_YEARS *
                MyCalendar.MILLISECONDS_IN_DAY);
        return dialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        date[0] = year;
        date[1] = month;
        date[2] = day;

        mListener.onFinish();
    }

    public int[] getResult() {
        if(Arrays.equals(date, new int[]{0,0,0})) {
            Log.d("getResult", "null");
            return MyCalendar.getArrayOfDate();
        } else{
            Log.d("getResult", "not null");
            return date;
        }
    }

    public String getText() throws ParseException {

        return MyCalendar.toDate(date);
    }

    public long getDateMilliseconds() {

        return MyCalendar.toMilliseconds(date);
    }

    public void setOnFinishedListener(OnFinishedListener listener) {
        mListener = listener;
    }
}