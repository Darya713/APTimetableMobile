package com.example.butterfly.aptimetable.activities;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.butterfly.aptimetable.InternetConnection;
import com.example.butterfly.aptimetable.R;
import com.example.butterfly.aptimetable.adapters.FacultyAdapter;
import com.example.butterfly.aptimetable.models.Faculty;
import com.example.butterfly.aptimetable.restasynctask.HttpDeleteAsyncTask;
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

public class FacultyRestActivity extends AppCompatActivity {

    public static final int ID_MENU_EDIT = 1;
    public static final int ID_MENU_DELETE = 2;

    public ListView listView;
    FloatingActionButton floatingActionButton;
    public FacultyAdapter facultyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculties);

        listView = (ListView) findViewById(R.id.listViewOfFaculty);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (InternetConnection.checkInternetConnection(FacultyRestActivity.this)) {
                    Intent intent = new Intent(FacultyRestActivity.this, SpecialtyRestActivity.class);
                    intent.putExtra("idOfFaculty", ((Faculty) facultyAdapter.getItem(i)).getId());
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(), "Интернет соединение отсутствует", Toast.LENGTH_LONG).show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FacultyRestActivity.this, FacultySaveActivity.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, ID_MENU_EDIT, 0, "Изменить");
        menu.add(0, ID_MENU_DELETE, 0, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = info.position;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Вы действительно хотите удалить эту запись?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(FacultyRestActivity.this, "Запись удалена", Toast.LENGTH_SHORT).show();
                        if (InternetConnection.checkInternetConnection(FacultyRestActivity.this)) {
                            new HttpDeleteAsyncTask(FacultyRestActivity.this, ((Faculty) facultyAdapter.getItem(position)).getId()).execute();
                        }
                        new HttpGetFacultyAsyncTask(FacultyRestActivity.this).execute();
                    }
                })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        switch (item.getItemId()) {
            case ID_MENU_EDIT: {
                Intent intent = new Intent(this, FacultyEditActivity.class);
                intent.putExtra("id", ((Faculty) facultyAdapter.getItem(position)).getId());
                intent.putExtra("shortName", ((Faculty) facultyAdapter.getItem(position)).getShortNameOfFaculty());
                intent.putExtra("fullName", ((Faculty) facultyAdapter.getItem(position)).getFullNameOfFaculty());
                startActivity(intent);

                break;
            }
            case ID_MENU_DELETE: {
                builder.create().show();

                break;
            }
        }

        return true;
    }

    public void Task() {
        new HttpGetFacultyAsyncTask(FacultyRestActivity.this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (InternetConnection.checkInternetConnection(this)) {
            new HttpGetFacultyAsyncTask(this).execute();
        }
        ((NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancel(101);
    }


    private class HttpGetFacultyAsyncTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private String jsonFaculties = "";
        private Context context;

        HttpGetFacultyAsyncTask(Context context) {
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
        protected String doInBackground(String... params) {
            String URL_faculties = "http://aptimetable.herokuapp.com/Faculties";
            jsonFaculties = getJson(URL_faculties);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(context, "Данные успешно получены", Toast.LENGTH_SHORT).show();

            Gson gson = new Gson();
            Type typeFaculties = new TypeToken<List<Faculty>>() {}.getType();
            ArrayList<Faculty> faculties = gson.fromJson(jsonFaculties, typeFaculties);
            facultyAdapter = new FacultyAdapter(context, faculties);
            listView.setAdapter(facultyAdapter);

            progressDialog.dismiss();
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
