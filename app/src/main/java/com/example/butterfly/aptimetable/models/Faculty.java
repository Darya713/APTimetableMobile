package com.example.butterfly.aptimetable.models;

import java.io.Serializable;

public class Faculty implements Serializable {
    private int id;
    private String shortNameOfFaculty;
    private String fullNameOfFaculty;

    public Faculty(String shortNameOfFaculty, String fullNameOfFaculty) {
        setShortNameOfFaculty(shortNameOfFaculty);
        setFullNameOfFaculty(fullNameOfFaculty);
    }

    public Faculty(int id, String short_Name_Of_Faculty, String full_Name_Of_Faculty){
        setId(id);
        setShortNameOfFaculty(short_Name_Of_Faculty);
        setFullNameOfFaculty(full_Name_Of_Faculty);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faculty faculty = (Faculty) o;

        return id == faculty.id &&
                shortNameOfFaculty.equals(faculty.shortNameOfFaculty) &&
                fullNameOfFaculty.equals(faculty.fullNameOfFaculty);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortNameOfFaculty() {
        return shortNameOfFaculty;
    }

    private void setShortNameOfFaculty(String short_Name_Of_Faculty) {
        this.shortNameOfFaculty = short_Name_Of_Faculty;
    }

    public String getFullNameOfFaculty() {
        return fullNameOfFaculty;
    }

    private void setFullNameOfFaculty(String full_Name_Of_Faculty) {
        this.fullNameOfFaculty = full_Name_Of_Faculty;
    }
}
