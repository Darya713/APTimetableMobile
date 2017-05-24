package com.example.butterfly.aptimetable.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.butterfly.aptimetable.R;
import com.example.butterfly.aptimetable.models.Faculty;

import java.util.ArrayList;
import java.util.List;

public class FacultyAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Faculty> faculties = new ArrayList<>();

    public FacultyAdapter(Context context, List<Faculty> faculties) {
        this.faculties = faculties;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return faculties.size();
    }

    @Override
    public Object getItem(int position) {
        return faculties.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Faculty faculty = (Faculty) getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_item, null);
        }

        TextView fullNameOfFaculty = (TextView) convertView.findViewById(R.id.textView);
        fullNameOfFaculty.setText(faculty.getFullNameOfFaculty());
        return convertView;
    }
}
