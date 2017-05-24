package com.example.butterfly.aptimetable.database;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.butterfly.aptimetable.R;
import com.example.butterfly.aptimetable.WorkWithFile;
import com.example.butterfly.aptimetable.activities.MainActivity;
import com.example.butterfly.aptimetable.models.TimetableMark;
import com.example.butterfly.aptimetable.models.TimetableView;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WorkWithDataBase {

    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private boolean notificationTimetables;

    private static final int NOTIFY_ID_TIMETABLE = 101;

    public WorkWithDataBase(Context context) {
        this.context = context;
        DataBase dataBase = new DataBase(context);
        sqLiteDatabase = dataBase.getWritableDatabase();
    }

    public void saveTimetable(ArrayList<TimetableView> timetableViews) {
        for (TimetableView timetableView : timetableViews) {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM timetables WHERE id = " + timetableView.getId(), null);
            if (cursor.moveToFirst()) {
                TimetableView temp = new TimetableView(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9),
                            cursor.getInt(10), cursor.getString(11), cursor.getString(12), cursor.getString(13));
                if (!temp.equals(timetableView)) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("date", timetableView.getDate());
                    contentValues.put("time", timetableView.getTime());
                    contentValues.put("numberOfWeek", timetableView.getNumberOfWeek());
                    contentValues.put("numberOfAuditorium", timetableView.getNumberOfAuditorium());
                    contentValues.put("nameOfDiscipline", timetableView.getNameOfDiscipline());
                    contentValues.put("fullNameOfLoad", timetableView.getFullNameOfLoad());
                    contentValues.put("fullNameOfSpecialty", timetableView.getFullNameOfSpecialty());
                    contentValues.put("groupNum", timetableView.getGroupNum());
                    contentValues.put("subgroup", timetableView.getSubgroup());
                    contentValues.put("semester", timetableView.getSemester());
                    contentValues.put("lastName", timetableView.getLastName());
                    contentValues.put("firstName", timetableView.getFirstName());
                    contentValues.put("middleName", timetableView.getMiddleName());
                    sqLiteDatabase.update("timetables", contentValues, "id = ?", new String[]{String.valueOf(cursor.getInt(0))});
                    notificationTimetables = true;
                }
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", timetableView.getId());
                contentValues.put("date", timetableView.getDate());
                contentValues.put("time", timetableView.getTime());
                contentValues.put("numberOfWeek", timetableView.getNumberOfWeek());
                contentValues.put("numberOfAuditorium", timetableView.getNumberOfAuditorium());
                contentValues.put("nameOfDiscipline", timetableView.getNameOfDiscipline());
                contentValues.put("fullNameOfLoad", timetableView.getFullNameOfLoad());
                contentValues.put("fullNameOfSpecialty", timetableView.getFullNameOfSpecialty());
                contentValues.put("groupNum", timetableView.getGroupNum());
                contentValues.put("subgroup", timetableView.getSubgroup());
                contentValues.put("semester", timetableView.getSemester());
                contentValues.put("lastName", timetableView.getLastName());
                contentValues.put("firstName", timetableView.getFirstName());
                contentValues.put("middleName", timetableView.getMiddleName());
                sqLiteDatabase.insert("timetables", null, contentValues);
                notificationTimetables = true;
            }
            cursor.close();
        }

        if (notificationTimetables) {
            Uri ringURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_bell)
                    .setContentTitle("Уведомление")
                    .setContentText("Расписание было обновлено")
                    .setSound(ringURI);
            Intent intent = new Intent(context, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(intent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFY_ID_TIMETABLE, builder.build());
        }
    }

    public List<TimetableView> selectTimetables() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM timetables", null);
        ArrayList<TimetableView> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                TimetableView temp = null;
                try {
                    temp = new TimetableView(cursor.getInt(0), new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(cursor.getString(1))), cursor.getString(2), cursor.getInt(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9),
                            cursor.getInt(10), cursor.getString(11), cursor.getString(12), cursor.getString(13));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                list.add(temp);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    public List<TimetableView> selectTimetables(String date) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM timetables where date='" + date + "'", null);
        ArrayList<TimetableView> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                TimetableView temp = null;
                try {
                    temp = new TimetableView(cursor.getInt(0), new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(cursor.getString(1))), cursor.getString(2), cursor.getInt(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9),
                            cursor.getInt(10), cursor.getString(11), cursor.getString(12), cursor.getString(13));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                list.add(temp);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    public String selectInfoFromTimetable () {
        String result;
        Gson gson = new Gson();
        String jsonString = WorkWithFile.getDate(context);
        if (jsonString != null) {
            TimetableMark timetableMark = gson.fromJson(jsonString, TimetableMark.class);
                int course = (int) Math.round((double) timetableMark.getNumberSemester() / 2);
                int semester = timetableMark.getNumberSemester() - course - 1;
                result = timetableMark.getShortNameOfSpecialty() + ", " + course + " курс " + timetableMark.getNumberGroup() + "/" + timetableMark.getNumberSubgroup() + ", " + semester + " сем";
        } else {
            result = "APTimetable";
        }

        return result;
    }

    public boolean deleteTimetables() {

        return sqLiteDatabase.delete("timetables", null, null) > 0;
    }
}
