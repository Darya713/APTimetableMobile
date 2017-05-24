package com.example.butterfly.aptimetable.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DataBase extends SQLiteOpenHelper {
    DataBase(Context context) {
        super(context, "TimetableView", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE timetables (" +
                "id INTEGER NOT NULL, " +
                "date TEXT NOT NULL, " +
                "time TEXT NOT NULL, " +
                "numberOfWeek INTEGER NOT NULL, " +
                "numberOfAuditorium TEXT NOT NULL, " +
                "nameOfDiscipline TEXT NOT NULL, " +
                "fullNameOfLoad TEXT NOT NULL, " +
                "fullNameOfSpecialty TEXT NOT NULL, " +
                "groupNum INTEGER NOT NULL, " +
                "subgroup INTEGER NOT NULL, " +
                "semester INTEGER NOT NULL, " +
                "lastName TEXT NOT NULL, " +
                "firstName TEXT NOT NULL, " +
                "middleName TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }
}
