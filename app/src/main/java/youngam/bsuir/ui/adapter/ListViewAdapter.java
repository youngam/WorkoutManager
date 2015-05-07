package youngam.bsuir.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import youngam.bsuir.R;
import youngam.bsuir.core.model.WorkoutCategory;

/**
 * Created by Alex on 10.03.2015.
 */
public class ListViewAdapter extends BaseAdapter {
    private ArrayList<WorkoutCategory> mCategories;
    private LayoutInflater inflater;

    public ListViewAdapter(ArrayList<WorkoutCategory> array){
        mCategories = array;
    }
    @Override
    public int getCount() {
        return mCategories == null ? 0 : mCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        //if there aren't complete view
        if(convertView == null){
            inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.muscleGroup);
            view.setTag(holder);
        }
        // if we have complete view
        else{
            holder = (ViewHolder) view.getTag();
        }

        holder.textView.setText(mCategories.get(position).getName());
        return view;


    }
    public class ViewHolder{
        TextView textView;
    }
}
