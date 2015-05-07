package youngam.bsuir.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

import youngam.bsuir.R;
import youngam.bsuir.core.model.MyCalendar;
import youngam.bsuir.core.model.UserTrainings;

/**
 * Created by Alex on 25.04.2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private ArrayList<UserTrainings> groups;

    public ExpandableListAdapter(ArrayList<UserTrainings> groups){
        this.groups = groups;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getExercises().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_item, parent, false);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.txtMuscle);
            holder.date = (TextView) convertView.findViewById(R.id.txtDate);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text.setText(groups.get(groupPosition).getMuscleGroups());
        String text = null;

        //Convert time from milliseconds to humans letters
        try {
             text = MyCalendar.getTime(groups.get(groupPosition).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.date.setText(text);
    return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();

            holder.text = (TextView) convertView.findViewById(R.id.muscleGroup);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Getting the exercises for muscleGroups
        holder.text.setText(groups.get(groupPosition).getExercises().get(childPosition).getName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class ViewHolder{
        TextView text;
        TextView date;
    }
}
