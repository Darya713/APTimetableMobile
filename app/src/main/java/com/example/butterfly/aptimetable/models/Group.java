package com.example.butterfly.aptimetable.models;

import java.io.Serializable;

public class Group implements Serializable {
    private String codeOfSpecialty;
    private int group;
    private int semester;

    public Group(String codeOfSpecialty, int group, int semester) {
        setCodeOfSpecialty(codeOfSpecialty);
        setGroup(group);
        setSemester(semester);
    }

    public String getCodeOfSpecialty() {
        return codeOfSpecialty;
    }

    private void setCodeOfSpecialty(String codeOfSpecialty) {
        this.codeOfSpecialty = codeOfSpecialty;
    }

    public int getGroup() {
        return group;
    }

    private void setGroup(int group) {
        this.group = group;
    }

    public int getSemester() {
        return semester;
    }

    private void setSemester(int semester) {
        this.semester = semester;
    }
}
