package com.example.butterfly.aptimetable.restasynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.butterfly.aptimetable.activities.MainActivity;
import com.example.butterfly.aptimetable.activities.SelectTimetableActivity;
import com.example.butterfly.aptimetable.database.WorkWithDataBase;
import com.example.butterfly.aptimetable.models.TimetableMark;
import com.example.butterfly.aptimetable.models.TimetableView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HttpGetTimetableAsyncTask extends AsyncTask<String, Void, String> {

    private ProgressDialog progressDialog;
    private String jsonTimetable = "";
    private Context context;

    private TimetableMark timetableMark;

    public HttpGetTimetableAsyncTask(Context context, TimetableMark timetableMark) {
        this.context = context;
        this.timetableMark = timetableMark;
        progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setTitle("Пожалуйста подождите ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String URL_timetable = "http://aptimetable.herokuapp.com/ViewTimetable";
        jsonTimetable = getJson(URL_timetable);

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Gson gson = new Gson();
        Type typeTimetable = new TypeToken<List<TimetableView>>(){}.getType();
        ArrayList<TimetableView> timetableViews = gson.fromJson(jsonTimetable, typeTimetable);

        WorkWithDataBase workWithDataBase = new WorkWithDataBase(context);

        ArrayList<TimetableView> temp = new ArrayList<>();
        if (timetableMark.getFullNameOfSpecialty() != null) {
            for (TimetableView t : timetableViews) {
                if (t.getFullNameOfSpecialty().equals(timetableMark.getFullNameOfSpecialty()) &&
                        t.getSemester() == timetableMark.getNumberSemester() &&
                        t.getGroupNum() == timetableMark.getNumberGroup() &&
                        t.getSubgroup() == timetableMark.getNumberSubgroup()) {
                    temp.add(t);
                }
//                else if (t.getFullNameOfSpecialty().equals(timetableMark.getFullNameOfSpecialty()) &&
//                        t.getGroupNum() == 0 &&
//                        t.getSubgroup() == 0 &&
//                        t.getSemester() == timetableMark.getNumberSemester()) {
//                    temp.add(t);
//                } else if (t.getFullNameOfSpecialty().equals(timetableMark.getFullNameOfSpecialty()) &&
//                        t.getGroupNum() == timetableMark.getNumberGroup() &&
//                        t.getSubgroup() == 0 &&
//                        t.getSemester() == timetableMark.getNumberSemester()) {
//                    temp.add(t);
//                }
            }
        }
        else {
            workWithDataBase.saveTimetable(timetableViews);
        }

        Collections.sort(temp, new Comparator<TimetableView>() {
            @Override
            public int compare(TimetableView t1, TimetableView t2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date date1 = new Date(), date2 = new Date();
                try {
                    date1 = dateFormat.parse(t1.getDate());
                    date2 = dateFormat.parse(t2.getDate());
                } catch (ParseException e) {
                    Log.d("ERROR", e.getMessage());
                }
                if (date1.compareTo(date2) < 0)

                    return -1;
                else if (date1.compareTo(date2) > 0)

                    return 1;
                else if (Integer.parseInt(t1.getTime().substring(0, t1.getTime().indexOf("."))) < Integer.parseInt(t2.getTime().substring(0, t2.getTime().indexOf("."))))

                    return -1;
                else
                    return 1;
            }
        });

        if (temp.size() != 0) {
            Toast.makeText(context, "Данные успешно получены", Toast.LENGTH_SHORT).show();
        }
        workWithDataBase.saveTimetable(temp);

        if (context instanceof MainActivity) {
            ((MainActivity) context).Task(workWithDataBase.selectTimetables());
        }

        progressDialog.dismiss();

        if (context instanceof SelectTimetableActivity) {
            ((SelectTimetableActivity) context).Task(temp);
        }
    }

    private String getJson(String URL) {
        InputStream inputStream;
        String jsonString = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(new HttpGet(URL));
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    jsonString += line;
                }
                bufferedReader.close();
            } else {
                jsonString = "Didn't work!";
            }

            assert inputStream != null;
            inputStream.close();

            return jsonString;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
