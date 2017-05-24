package com.example.butterfly.aptimetable.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.butterfly.aptimetable.InternetConnection;
import com.example.butterfly.aptimetable.R;
import com.example.butterfly.aptimetable.TaskDelegate;
import com.example.butterfly.aptimetable.WorkWithFile;
import com.example.butterfly.aptimetable.adapters.TimetableAdapter;
import com.example.butterfly.aptimetable.database.WorkWithDataBase;
import com.example.butterfly.aptimetable.models.TimetableMark;
import com.example.butterfly.aptimetable.models.TimetableView;
import com.example.butterfly.aptimetable.restasynctask.HttpGetTimetableAsyncTask;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TaskDelegate<TimetableView> {

    private static final int NOTIFY_ID_TIMETABLE = 101;

    ListView listView;
    TimetableAdapter timetableAdapter;
    ArrayList<TimetableView> timetableViewList = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;

    Gson gson = new Gson();
    String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listViewOfTimetable);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        jsonString = WorkWithFile.getDate(getApplicationContext());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (jsonString != null) {
                    if (InternetConnection.checkInternetConnection(MainActivity.this)) {
                        TimetableMark timetableMark = gson.fromJson(jsonString, TimetableMark.class);
                        new HttpGetTimetableAsyncTask(MainActivity.this, timetableMark).execute();
                        ((NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancel(NOTIFY_ID_TIMETABLE);
                    } else {
                        Toast.makeText(MainActivity.this, "Интернет соединение отсутствует", Toast.LENGTH_SHORT).show();
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        assert fab1 != null;
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetConnection.checkInternetConnection(MainActivity.this)) {
                    if (timetableViewList != null) {
                        TimetableMark timetableMark = (TimetableMark) getIntent().getSerializableExtra("timetableMark");
                        if (timetableMark != null) {
                            WorkWithDataBase workWithDataBase = new WorkWithDataBase(getApplicationContext());
                            workWithDataBase.deleteTimetables();
                            workWithDataBase.saveTimetable(timetableViewList);
                            WorkWithFile.saveDate(getApplicationContext(), gson.toJson(timetableMark));
                            Toast.makeText(getApplicationContext(), "Расписание успешно сохранено", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Это расписание уже сохранено", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "Перед сохранением необходимо выбрать расписание", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Интернет соединение отсутствует", Toast.LENGTH_SHORT).show();
                }
                setTitle(new WorkWithDataBase(getApplicationContext()).selectInfoFromTimetable());
            }
        });
        assert fab2 != null;
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jsonString != null) {
                    if (InternetConnection.checkInternetConnection(MainActivity.this)) {
                        TimetableMark timetableMark = gson.fromJson(jsonString, TimetableMark.class);
                        new HttpGetTimetableAsyncTask(MainActivity.this, timetableMark).execute();
                        ((NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancel(NOTIFY_ID_TIMETABLE);
                    } else {
                        Toast.makeText(MainActivity.this, "Интернет соединение отсутствует", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        assert fab3 != null;
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetConnection.checkInternetConnection(MainActivity.this)) {
                    Intent intent = new Intent(MainActivity.this, SelectTimetableActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Интернет соединение отсутствует", Toast.LENGTH_SHORT).show();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        if (InternetConnection.checkInternetConnection(this)) {
            if (id == R.id.nav_faculties) {
                intent = new Intent(this, FacultyRestActivity.class);
            } else if (id == R.id.nav_faculties_soap) {
                intent = new Intent(this, FacultySoapActivity.class);
            }
        } else
            Toast.makeText(getApplicationContext(), "Интернет соединение отсутствует", Toast.LENGTH_LONG).show();

        if (intent != null)
            startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void Task(List<TimetableView> arrayList) {
        timetableAdapter.update(arrayList);
        listView.setAdapter(timetableAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        timetableViewList = (ArrayList<TimetableView>) getIntent().getSerializableExtra("listTimetable");
        if (timetableViewList != null) {
            setTitle("APTimetable");
            timetableAdapter = new TimetableAdapter(this, timetableViewList);
        } else {
            setTitle(new WorkWithDataBase(getApplicationContext()).selectInfoFromTimetable());
            timetableAdapter = new TimetableAdapter(this, new WorkWithDataBase(getApplicationContext()).selectTimetables());
        }
        listView.setAdapter(timetableAdapter);
        if (!InternetConnection.checkInternetConnection(this)) {
            Toast.makeText(this, "Интернет соединение отсутствует", Toast.LENGTH_SHORT).show();
        }
        ((NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancel(NOTIFY_ID_TIMETABLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
