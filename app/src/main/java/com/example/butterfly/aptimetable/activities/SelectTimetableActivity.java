package com.example.butterfly.aptimetable.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.butterfly.aptimetable.R;
import com.example.butterfly.aptimetable.TaskDelegate;
import com.example.butterfly.aptimetable.models.Faculty;
import com.example.butterfly.aptimetable.models.Group;
import com.example.butterfly.aptimetable.models.Specialty;
import com.example.butterfly.aptimetable.models.TimetableMark;
import com.example.butterfly.aptimetable.models.TimetableView;
import com.example.butterfly.aptimetable.restasynctask.HttpGetTimetableAsyncTask;
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
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectTimetableActivity extends AppCompatActivity implements TaskDelegate<TimetableView> {

    Spinner faculty, specialty, course, semester, group, subgroup;
    Button search;

    List<String> listCourse = new ArrayList<>();
    List<String> listSemester = new ArrayList<>();
    List<String> listSubgroup = new ArrayList<>();

    List<Faculty> facultyList = new ArrayList<>();
    List<Specialty> specialtyList = new ArrayList<>();
    List<Group> groupList = new ArrayList<>();

    int idFaculty, numberCourse, numberGroup, numberSubgroup, numberSemester;
    String codeOfSpecialty, shortNameOfSpecialty, fullNameOfSpecialty;
    TimetableMark timetableMark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_timetable);

        faculty = (Spinner) findViewById(R.id.faculty);
        course = (Spinner) findViewById(R.id.course);
        semester = (Spinner) findViewById(R.id.semester);
        specialty = (Spinner) findViewById(R.id.specialty);
        group = (Spinner) findViewById(R.id.group);
        subgroup = (Spinner) findViewById(R.id.subgroup);
        search = (Button) findViewById(R.id.search);

        Collections.addAll(listCourse, "1", "2", "3", "4", "5");
        Collections.addAll(listSemester, "1", "2");
        Collections.addAll(listSubgroup, "1", "2");

        ArrayAdapter<String> adapterCourse = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listCourse);
        course.setAdapter(adapterCourse);
        ArrayAdapter<String> adapterSemester = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listSemester);
        semester.setAdapter(adapterSemester);
        ArrayAdapter<String> adapterSubgroup = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listSubgroup);
        subgroup.setAdapter(adapterSubgroup);

        faculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (facultyList.size() != 0) {
                    for (Faculty f : facultyList) {
                        if (adapterView.getItemAtPosition(i).toString().equals(f.getFullNameOfFaculty())) {
                            new SpecialtiesAsyncTask().execute(String.valueOf(f.getId()));
                            idFaculty = f.getId();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        specialty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (specialtyList.size() != 0) {
                    for (Specialty s : specialtyList) {
                        if (String.valueOf(specialty.getItemAtPosition(specialty.getSelectedItemPosition())).equals(s.getFullNameOfSpecialty())) {
                            numberSemester = (course.getSelectedItemPosition() + 1) * 2 - 2 + (semester.getSelectedItemPosition() + 1);
                            new GroupsAsyncTask().execute(s.getCodeOfSpecialty(), String.valueOf(numberSemester));
                            codeOfSpecialty = s.getCodeOfSpecialty();
                            shortNameOfSpecialty = s.getShortNameOfSpecialty();
                            fullNameOfSpecialty = s.getFullNameOfSpecialty();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numberSemester = Integer.valueOf(adapterView.getItemAtPosition(i).toString()) * 2 - 2 + (semester.getSelectedItemPosition() + 1);
                numberCourse = Integer.valueOf(adapterView.getItemAtPosition(i).toString());
                if (specialtyList.size() != 0) {
                    for (Specialty s : specialtyList) {
                        if (String.valueOf(specialty.getItemAtPosition(specialty.getSelectedItemPosition())).equals(s.getFullNameOfSpecialty())) {
                            new GroupsAsyncTask().execute(s.getCodeOfSpecialty(), String.valueOf(numberSemester));
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numberSemester = (course.getSelectedItemPosition() + 1) * 2 - 2 + Integer.valueOf(adapterView.getItemAtPosition(i).toString());
                if (specialtyList.size() != 0) {
                    for (Specialty s : specialtyList) {
                        if (String.valueOf(specialty.getItemAtPosition(specialty.getSelectedItemPosition())).equals(s.getFullNameOfSpecialty())) {
                            new GroupsAsyncTask().execute(s.getCodeOfSpecialty(), String.valueOf(numberSemester));
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for (Group g : groupList) {
                    if (adapterView.getItemAtPosition(i).equals(g.getGroup())) {
                        numberGroup = g.getGroup();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numberSubgroup = Integer.valueOf(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fullNameOfSpecialty != null && numberGroup != 0 && numberSubgroup != 0 && numberSemester != 0) {
                    timetableMark = new TimetableMark(codeOfSpecialty, shortNameOfSpecialty, fullNameOfSpecialty, numberGroup, numberSubgroup, numberSemester);
                    new HttpGetTimetableAsyncTask(SelectTimetableActivity.this, timetableMark).execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Ошибка!\nЗаполните, пожалуйста, все поля", Toast.LENGTH_LONG).show();
                }
            }
        });

        new MainAsyncTask().execute();
    }

    @Override
    public void Task(List<TimetableView> arrayList) {
        if (arrayList.size() != 0) {
            Intent intent = new Intent(SelectTimetableActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("listTimetable", (Serializable) arrayList);
            intent.putExtra("timetableMark", timetableMark);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Расписание не найдено", Toast.LENGTH_SHORT).show();
        }
    }

    private class MainAsyncTask extends AsyncTask<String, Void, String> {
        String jsonFaculties;
        ProgressDialog progressDialog = new ProgressDialog(SelectTimetableActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Пожалуйста подождите ...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String URL_faculties = "http://aptimetable.herokuapp.com/Faculties";
            jsonFaculties = getJson(URL_faculties);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson();
            Type typeFaculties = new TypeToken<List<Faculty>>() {}.getType();
            List<Faculty> faculties = gson.fromJson(jsonFaculties, typeFaculties);
            facultyList.addAll(faculties);
            List<String> listF = new ArrayList<>();
            for (Faculty temp : faculties) {
                listF.add(temp.getFullNameOfFaculty());
            }

            progressDialog.dismiss();

            ArrayAdapter<String> adapterF = new ArrayAdapter<>(SelectTimetableActivity.this, android.R.layout.simple_list_item_1, listF);
            faculty.setAdapter(adapterF);
        }
    }

    private class SpecialtiesAsyncTask extends AsyncTask<String, Void, String> {
        String jsonSpecialties;
        ProgressDialog progressDialog = new ProgressDialog(SelectTimetableActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Пожалуйста подождите ...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String URL_specialties = "http://aptimetable.herokuapp.com/Specialties?idFaculty=" + strings[0];
            jsonSpecialties = getJson(URL_specialties);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson();
            Type typeSpecialties = new TypeToken<List<Specialty>>() {
            }.getType();
            List<Specialty> specialties = gson.fromJson(jsonSpecialties, typeSpecialties);
            specialtyList.addAll(specialties);
            List<String> list = new ArrayList<>();
            for (Specialty temp : specialties) {
                list.add(temp.getFullNameOfSpecialty());
            }

            progressDialog.dismiss();

            ArrayAdapter<String> adapter = new ArrayAdapter<>(SelectTimetableActivity.this, android.R.layout.simple_list_item_1, list);
            specialty.setAdapter(adapter);
        }
    }

    private class GroupsAsyncTask extends AsyncTask<String, Void, String> {
        String jsonGroups;
        ProgressDialog progressDialog = new ProgressDialog(SelectTimetableActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Пожалуйста подождите ...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String URL_groups = "http://aptimetable.herokuapp.com/Group?codeSpec=" + strings[0].replace(" ", "%20") + "&semester=" + strings[1];
            jsonGroups = getJson(URL_groups);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson();
            Type typeGroups = new TypeToken<List<Group>>() {
            }.getType();
            List<Group> groups = gson.fromJson(jsonGroups, typeGroups);
            groupList.addAll(groups);
            List<Integer> list = new ArrayList<>();
            for (Group temp : groups) {
                list.add(temp.getGroup());
            }

            progressDialog.dismiss();

            ArrayAdapter<Integer> adapter = new ArrayAdapter<>(SelectTimetableActivity.this, android.R.layout.simple_list_item_1, list);
            group.setAdapter(adapter);
        }
    }

    private String getJson(String URL) {
        InputStream inputStream;
        String jsonString = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(URL);
            HttpResponse httpResponse = httpClient.execute(httpGet);
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
