package com.example.butterfly.aptimetable.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.butterfly.aptimetable.R;
import com.example.butterfly.aptimetable.models.Specialty;

import java.util.ArrayList;
import java.util.List;

public class SpecialtyAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Specialty> specialties = new ArrayList<>();

    public SpecialtyAdapter(Context context, List<Specialty> specialties) {
        this.specialties = specialties;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return specialties.size();
    }

    @Override
    public Object getItem(int position) {
        return specialties.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Specialty specialty = (Specialty) getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_item, null);
        }

        TextView fullNameOfSpecialty = (TextView) convertView.findViewById(R.id.textView);
        fullNameOfSpecialty.setText(specialty.getFullNameOfSpecialty());
        return convertView;
    }
}
