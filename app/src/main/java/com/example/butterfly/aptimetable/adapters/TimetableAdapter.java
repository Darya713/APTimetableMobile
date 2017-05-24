package com.example.butterfly.aptimetable.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.butterfly.aptimetable.R;
import com.example.butterfly.aptimetable.models.TimetableView;

import java.util.ArrayList;
import java.util.List;

public class TimetableAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<TimetableView> timetableViews = new ArrayList<>();

    public TimetableAdapter(Context context, List<TimetableView> timetableViews) {
        this.timetableViews = timetableViews;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return timetableViews.size();
    }

    @Override
    public Object getItem(int position) {
        return timetableViews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TimetableView timetableView = (TimetableView) getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_item_timetable, null);
        }

        convertView.setBackgroundResource(R.drawable.border_list_view);

        TextView date = (TextView) convertView.findViewById(R.id.textView_date);
        date.setText(timetableView.getDate());
        TextView time = (TextView) convertView.findViewById(R.id.textView_time);
        time.setText(timetableView.getTime());
        TextView auditorium = (TextView) convertView.findViewById(R.id.textView_auditorium);
        auditorium.setText(timetableView.getNumberOfAuditorium());
        TextView typeOfDiscipline = (TextView) convertView.findViewById(R.id.textView_type_of_loading);
        typeOfDiscipline.setText(timetableView.getFullNameOfLoad());
        TextView numberOfWeek = (TextView) convertView.findViewById(R.id.number_of_week);
        if (timetableView.getNumberOfWeek() == 1) {
            numberOfWeek.setText("Нечетные");
        } else if (timetableView.getNumberOfWeek() == 2) {
            numberOfWeek.setText("Четные");
        } else {
            numberOfWeek.setText("Все");
        }
        TextView discipline = (TextView) convertView.findViewById(R.id.textView_discipline);
        discipline.setText(timetableView.getNameOfDiscipline());
        TextView teacher = (TextView) convertView.findViewById(R.id.textView_teacher);
        teacher.setText(timetableView.getLastName() + " " + timetableView.getFirstName() + " " + timetableView.getMiddleName());

        return convertView;
    }

    public void update(List<TimetableView> timetableViews) {
        this.timetableViews.clear();
        this.timetableViews.addAll(timetableViews);
        notifyDataSetChanged();
    }
}
