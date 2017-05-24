package com.example.butterfly.aptimetable.models;

import java.io.Serializable;

public class TimetableMark implements Serializable {
    private String codeOfSpecialty;
    private String shortNameOfSpecialty;
    private String fullNameOfSpecialty;
    private int numberGroup;
    private int numberSubgroup;
    private int numberSemester;

    public TimetableMark(String codeOfSpecialty, String shortNameOfSpecialty, String fullNameOfSpecialty, int numberGroup, int numberSubgroup, int numberSemester) {
        setCodeOfSpecialty(codeOfSpecialty);
        setShortNameOfSpecialty(shortNameOfSpecialty);
        setFullNameOfSpecialty(fullNameOfSpecialty);
        setNumberGroup(numberGroup);
        setNumberSubgroup(numberSubgroup);
        setNumberSemester(numberSemester);
    }

    public String getCodeOfSpecialty() {
        return codeOfSpecialty;
    }

    private void setCodeOfSpecialty(String codeOfSpecialty) {
        this.codeOfSpecialty = codeOfSpecialty;
    }

    public String getShortNameOfSpecialty() {
        return shortNameOfSpecialty;
    }

    private void setShortNameOfSpecialty(String shortNameOfSpecialty) {
        this.shortNameOfSpecialty = shortNameOfSpecialty;
    }

    public String getFullNameOfSpecialty() {
        return fullNameOfSpecialty;
    }

    private void setFullNameOfSpecialty(String fullNameOfSpecialty) {
        this.fullNameOfSpecialty = fullNameOfSpecialty;
    }

    public int getNumberGroup() {
        return numberGroup;
    }

    private void setNumberGroup(int numberGroup) {
        this.numberGroup = numberGroup;
    }

    public int getNumberSubgroup() {
        return numberSubgroup;
    }

    private void setNumberSubgroup(int numberSubgroup) {
        this.numberSubgroup = numberSubgroup;
    }

    public int getNumberSemester() {
        return numberSemester;
    }

    private void setNumberSemester(int numberSemester) {
        this.numberSemester = numberSemester;
    }
}
