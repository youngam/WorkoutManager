package youngam.bsuir.ui.spinner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import youngam.bsuir.R;
import youngam.bsuir.core.model.WorkoutCategory;
import youngam.bsuir.core.listeners.OnFinishedListener;

/**
 * Created by Alex on 14.04.2015.
 */
public class MultiSelectionSpinner extends Spinner implements DialogInterface.OnMultiChoiceClickListener{
    private String[] items;
    private ArrayList<WorkoutCategory> categories;
    private ArrayAdapter<String> adapter;
    // массив для проверки, какие checkboxes нажаты
    private boolean[] selection = null;
    private OnFinishedListener mListener;

    public MultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        //Инициализируем адаптер и передаём спинеру
        adapter = new ArrayAdapter<>(context,
                R.layout.spinner_item);
        categories = new ArrayList<>();
        super.setAdapter(adapter);
    }
    //Что именно писать в этом методе?
    public void setOnFinishedListener(OnFinishedListener listener){
        mListener = listener;
    }



    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (selection != null && which < selection.length) {
            selection[which] = isChecked;
         //Меняю элемент, который стоит по умолчанию на те,
         //которые выбраны пользователем
           adapter.clear();
           adapter.add(buildSelectedItemString());
        } else {
            throw new IllegalArgumentException(
                    "Argument 'which' is out of bounds.");
        }

    }


    // Метод, показывающий выпадающий список
    // Оказыватся, сделан на основе AlertDialog :)
    @Override
    public boolean performClick() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(items, selection, this);
        //Кнопка для подтверждения
        builder.setNeutralButton("Finished", new DialogInterface.OnClickListener() {
            @Override
            //Вот нажатие, после которого должно идти заполнение второго спинера
            public void onClick(DialogInterface dialog, int which) {

                mListener.onFinish();
            }
        });
        builder.show();
        return true;
    }

    public void setItems(ArrayList<WorkoutCategory> items) {
        categories = items;
        this.items = new String[items.size()];
        int i = 0;
        for(WorkoutCategory category : items) {
           this.items[i] = category.getName();
            i++;
        }
        // размер массива boolean ставим равным размеру items
        selection = new boolean[items.size()];
        adapter.clear();
        // по умолчанию будет стоять первый элемент массива
        adapter.add(this.items[0]);
        // заполняем весь массив boolean значениями false
        // для удобства
        Arrays.fill(selection, false);
    }

    // Тестовый режим, здесь будет идти запрос в базу
    public ArrayList<WorkoutCategory> getResult() {
        ArrayList<WorkoutCategory> result = new ArrayList<WorkoutCategory>();
        boolean isAnythingSelected = false;
        for (int i = 0; i < items.length; ++i) {
            // проверяем, выбран ли элемент
            if (selection[i]) {
                isAnythingSelected = true;
                Log.d("Spinner", "selection complete");
                //Добавляем элемент
                result.add(categories.get(i));
            }
        }

        return isAnythingSelected ? result : null;
    }
    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < items.length; ++i) {
            if (selection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;

                sb.append(items[i]);
            }
        }
        return sb.toString();
    }
}
