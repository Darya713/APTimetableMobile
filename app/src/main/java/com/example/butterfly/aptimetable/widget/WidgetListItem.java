package com.example.butterfly.aptimetable.widget;

class WidgetListItem {
    private String time;
    private String numberOfAuditorium;
    private String nameOfDiscipline;
    private String fullNameOfLoad;

    String getTime() {
        return time;
    }

    void setTime(String time) {
        this.time = time;
    }

    String getNumberOfAuditorium() {
        return numberOfAuditorium;
    }

    void setNumberOfAuditorium(String numberOfAuditorium) {
        this.numberOfAuditorium = numberOfAuditorium;
    }

    String getNameOfDiscipline() {
        return nameOfDiscipline;
    }

    void setNameOfDiscipline(String nameOfDiscipline) {
        this.nameOfDiscipline = nameOfDiscipline;
    }

    String getFullNameOfLoad() {
        return fullNameOfLoad;
    }

    void setFullNameOfLoad(String fullNameOfLoad) {
        this.fullNameOfLoad = fullNameOfLoad;
    }
}
