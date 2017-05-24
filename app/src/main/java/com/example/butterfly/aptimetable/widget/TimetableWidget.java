package com.example.butterfly.aptimetable.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.butterfly.aptimetable.R;
import com.example.butterfly.aptimetable.activities.MainActivity;
import com.example.butterfly.aptimetable.database.WorkWithDataBase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimetableWidget extends AppWidgetProvider {

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.timetable_widget);
        setUpdate(views, context, appWidgetId);
        setList(views, context, appWidgetId);
        setClick(views, context);
        ComponentName componentName = new ComponentName(context, TimetableWidget.class);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widgetListView);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widgetTextView);
        appWidgetManager.updateAppWidget(componentName, views);
    }

    void setUpdate(RemoteViews views, Context context, int appWidgetId) {
        if (new WorkWithDataBase(context).selectTimetables(new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Calendar.getInstance().getTime())).size() == 0) {
            views.setTextViewText(R.id.widgetTextView, "Сегодня нет занятий");
//            views.setTextViewText(R.id.widgetTextView, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Calendar.getInstance().getTime()));
        }
        Intent intent = new Intent(context, TimetableWidget.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[] {appWidgetId});
    }

    void setList(RemoteViews views, Context context, int appWidgetId) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        Uri data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME));
        intent.setData(data);
        views.setRemoteAdapter(R.id.widgetListView, intent);
    }

    void setClick(RemoteViews views, Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.relativeLayout, pendingIntent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Intent intent = new Intent(context, TimetableWidget.class);
        intent.setAction("update_widget");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 60000, pendingIntent);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Intent intent = new Intent(context, TimetableWidget.class);
        intent.setAction("update_widget");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equalsIgnoreCase("update_widget")) {
            ComponentName componentName = new ComponentName(context.getPackageName(), getClass().getName());
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] ids = appWidgetManager.getAppWidgetIds(componentName);
            for (int id : ids) {
                updateAppWidget(context, appWidgetManager, id);
            }
        }
    }


}

