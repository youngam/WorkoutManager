package youngam.bsuir.trainings.pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

import youngam.bsuir.listeners.OnFinishedListener;

/**
 * Created by Alex on 09.04.2015.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private HashMap<String, Integer> result;
    private OnFinishedListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        result = new HashMap<>();

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        mListener.onFinish();
        result.put("year", year);
        result.put("month", month);
        result.put("day", day);
        Toast.makeText(getActivity().getApplicationContext(), "You set "
        + year + "." + month + "." + day, Toast.LENGTH_SHORT).show();
    }
    public HashMap<String, Integer> getResult(){
        return result;
    }
    public void setOnFinishedListener(OnFinishedListener listener){
        mListener = listener;
    }
}