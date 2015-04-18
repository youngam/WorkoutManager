package youngam.bsuir.trainings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 14.04.2015.
 */
public class MultiSelectionSpinner extends Spinner implements DialogInterface.OnMultiChoiceClickListener{
    private String[] items = null;
    private ArrayAdapter<String> adapter;
    // массив для проверки, какие checkboxes нажаты
    boolean[] selection = null;
    // FIXME костыль для проверки, нажата ли кнопка
    private boolean isClicked = false;

    public MultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        //Инициализируем адаптер и передаём спинеру
        adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(adapter);
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
    //Вроде как опять кастыль для проверки, нажата ли кнопка
    public boolean isClicked(){
        return isClicked;
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
            public void onClick(DialogInterface dialog, int which) {
                isClicked = true;
                StringBuilder sb = new StringBuilder();
                //Достаём информацию из List

                for (String str : getResult()) {
                    sb.append(str);
                    sb.append("\n");
                }

                Toast.makeText(getContext(), "The result is" + sb.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
        return true;
    }

    public void setItems(String[] items) {
        this.items = items;
        // размер массива boolean ставим равным размеру items
        selection = new boolean[items.length];
        adapter.clear();
        // по умолчанию будет стоять первый элемент массива
        adapter.add(items[0]);
        // заполняем весь массив boolean значениями false
        // для удобства
        Arrays.fill(selection, false);
    }

    // Тестовый режим, здесь будет идти запрос в базу
    public List<String> getResult() {
        List<String> result = new ArrayList<String>();
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
