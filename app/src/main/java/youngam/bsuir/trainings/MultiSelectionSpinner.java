package youngam.bsuir.trainings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import youngam.bsuir.core.model.WorkoutCategory;
import youngam.bsuir.listener.OnFinishedListener;

/**
 * Created by Alex on 14.04.2015.
 */
public class MultiSelectionSpinner extends Spinner implements DialogInterface.OnMultiChoiceClickListener{
    private String[] items;
    private ArrayAdapter<String> adapter;
    // массив для проверки, какие checkboxes нажаты
    private boolean[] selection = null;
    private OnFinishedListener mListener;

    public MultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        //Инициализируем адаптер и передаём спинеру
        adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item);
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
                StringBuilder sb = new StringBuilder();
                //Достаём информацию из List

              /*  for (String str : getResult()) {
                    sb.append(str);
                    sb.append("\n");
                }

                Toast.makeText(getContext(), "The result is" + sb.toString(), Toast.LENGTH_SHORT).show();*/
                mListener.onFinish();
            }
        });
        builder.show();
        return true;
    }

    public void setItems(ArrayList<WorkoutCategory> items) {
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
    public ArrayList<String> getResult() {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < items.length; ++i) {
            // проверяем, выбран ли элемент
            if (selection[i]) {
                //Добавляем элемент
                result.add(items[i]);
            }
        }
        return result;
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
