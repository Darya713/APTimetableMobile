package com.example.butterfly.aptimetable.activities;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.butterfly.aptimetable.R;
import com.example.butterfly.aptimetable.adapters.SpecialtyAdapter;
import com.example.butterfly.aptimetable.models.Specialty;
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
import java.util.ArrayList;
import java.util.List;

public class SpecialtyRestActivity extends AppCompatActivity {
    ListView listView;
    SpecialtyAdapter specialtyAdapter;
    int idOfFaculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialties);

        listView = (ListView) findViewById(R.id.listViewOfSpecialty);

        idOfFaculty = getIntent().getIntExtra("idOfFaculty", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new HttpGetSpecialtyAsyncTask(this).execute();
        ((NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancel(102);
    }

    private class HttpGetSpecialtyAsyncTask extends AsyncTask<String, Void, List<Specialty>> {

        private ProgressDialog progressDialog;
        private String jsonSpecialties = "";
        private Context context;

        HttpGetSpecialtyAsyncTask(Context context) {
            this.context = context;
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
        protected List<Specialty> doInBackground(String... params) {
            String URL_specialties = "http://aptimetable.herokuapp.com/Specialties?idFaculty=" + idOfFaculty;
            jsonSpecialties = getJson(URL_specialties);

            return null;
        }

        @Override
        protected void onPostExecute(List<Specialty> result) {
            Toast.makeText(context, "Данные успешно получены", Toast.LENGTH_SHORT).show();

            Gson gson = new Gson();
            Type typeSpecialties = new TypeToken<List<Specialty>>(){}.getType();
            ArrayList<Specialty> specialties = gson.fromJson(jsonSpecialties, typeSpecialties);

            progressDialog.dismiss();

            specialtyAdapter = new SpecialtyAdapter(context, specialties);
            listView.setAdapter(specialtyAdapter);
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
}
