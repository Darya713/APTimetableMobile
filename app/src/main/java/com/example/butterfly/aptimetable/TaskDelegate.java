package com.example.butterfly.aptimetable;

import java.util.List;

public interface TaskDelegate<T> {
    void Task(List<T> arrayList);
}
