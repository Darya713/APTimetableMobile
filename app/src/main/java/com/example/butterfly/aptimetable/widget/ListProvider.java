package com.example.butterfly.aptimetable.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.example.butterfly.aptimetable.R;
import com.example.butterfly.aptimetable.database.WorkWithDataBase;
import com.example.butterfly.aptimetable.models.TimetableView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

class ListProvider implements RemoteViewsFactory {

    private List<WidgetListItem> widgetListItems;
    private Context context;

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    ListProvider(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        widgetListItems = new ArrayList<>();
    }

    @Override
    public void onDataSetChanged() {
        widgetListItems.clear();
        List<TimetableView> viewList = new WorkWithDataBase(context).selectTimetables(dateFormat.format(calendar.getTime()));
        for (int i = 0; i < viewList.size(); i++) {
            WidgetListItem listItem = new WidgetListItem();
            listItem.setTime(viewList.get(i).getTime());
            listItem.setFullNameOfLoad(viewList.get(i).getFullNameOfLoad());
            listItem.setNumberOfAuditorium(viewList.get(i).getNumberOfAuditorium());
            listItem.setNameOfDiscipline(viewList.get(i).getNameOfDiscipline());
            widgetListItems.add(listItem);
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return widgetListItems.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.row_item_widget_timetable);
        if (widgetListItems.size() != 0) {
            remoteView.setTextViewText(R.id.textView_time_widget, widgetListItems.get(i).getTime());
            remoteView.setTextViewText(R.id.textView_type_of_loading_widget, widgetListItems.get(i).getFullNameOfLoad());
            remoteView.setTextViewText(R.id.textView_auditorium_widget, widgetListItems.get(i).getNumberOfAuditorium());
            remoteView.setTextViewText(R.id.textView_discipline_widget, widgetListItems.get(i).getNameOfDiscipline());
        }

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
