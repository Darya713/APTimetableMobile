package com.example.butterfly.aptimetable.models;

import java.io.Serializable;

public class TimetableView implements Serializable {
    private int id;
    private String date;
    private String time;
    private int numberOfWeek;
    private String numberOfAuditorium;
    private String nameOfDiscipline;
    private String fullNameOfLoad;
    private String fullNameOfSpecialty;
    private int groupNum;
    private int subgroup;
    private int semester;
    private String lastName;
    private String firstName;
    private String middleName;

    public TimetableView(int id, String date, String time, int numberOfWeek, String numberOfAuditorium, String nameOfDiscipline, String fullNameOfLoad,
                         String fullNameOfSpecialty, int groupNum, int subgroup, int semester, String lastName, String firstName, String middleName) {
        setId(id);
        setDate(date);
        setTime(time);
        setNumberOfWeek(numberOfWeek);
        setNumberOfAuditorium(numberOfAuditorium);
        setNameOfDiscipline(nameOfDiscipline);
        setFullNameOfLoad(fullNameOfLoad);
        setFullNameOfSpecialty(fullNameOfSpecialty);
        setGroupNum(groupNum);
        setSubgroup(subgroup);
        setSemester(semester);
        setLastName(lastName);
        setFirstName(firstName);
        setMiddleName(middleName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimetableView timetableView = (TimetableView) o;

        return id == timetableView.id &&
                numberOfWeek == timetableView.numberOfWeek &&
                groupNum == timetableView.groupNum &&
                subgroup == timetableView.subgroup &&
                semester == timetableView.semester &&
                date.equals(timetableView.date) &&
                time.equals(timetableView.time) &&
                numberOfAuditorium.equals(timetableView.numberOfAuditorium) &&
                nameOfDiscipline.equals(timetableView.nameOfDiscipline) &&
                fullNameOfLoad.equals(timetableView.fullNameOfLoad) &&
                fullNameOfSpecialty.equals(timetableView.fullNameOfSpecialty) &&
                lastName.equals(timetableView.lastName) &&
                firstName.equals(timetableView.firstName) &&
                middleName.equals(timetableView.middleName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    private void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    private void setTime(String time) {
        this.time = time;
    }

    public int getNumberOfWeek() {
        return numberOfWeek;
    }

    private void setNumberOfWeek(int numberOfWeek) {
        this.numberOfWeek = numberOfWeek;
    }

    public String getNumberOfAuditorium() {
        return numberOfAuditorium;
    }

    private void setNumberOfAuditorium(String numberOfAuditorium) {
        this.numberOfAuditorium = numberOfAuditorium;
    }

    public String getNameOfDiscipline() {
        return nameOfDiscipline;
    }

    private void setNameOfDiscipline(String nameOfDiscipline) {
        this.nameOfDiscipline = nameOfDiscipline;
    }

    public String getFullNameOfLoad() {
        return fullNameOfLoad;
    }

    private void setFullNameOfLoad(String fullNameOfLoad) {
        this.fullNameOfLoad = fullNameOfLoad;
    }

    public String getFullNameOfSpecialty() {
        return fullNameOfSpecialty;
    }

    private void setFullNameOfSpecialty(String fullNameOfSpecialty) {
        this.fullNameOfSpecialty = fullNameOfSpecialty;
    }

    public int getGroupNum() {
        return groupNum;
    }

    private void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public int getSubgroup() {
        return subgroup;
    }

    private void setSubgroup(int subgroup) {
        this.subgroup = subgroup;
    }

    public int getSemester() {
        return semester;
    }

    private void setSemester(int semester) {
        this.semester = semester;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    private void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
