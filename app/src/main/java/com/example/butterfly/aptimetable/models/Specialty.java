package com.example.butterfly.aptimetable.models;

import java.io.Serializable;

public class Specialty implements Serializable {
    private String codeOfSpecialty;
    private String shortNameOfSpecialty;
    private String fullNameOfSpecialty;
    private int idOfFaculty;

    public Specialty(String codeOfSpecialty, String shortNameOfSpecialty, String fullNameOfSpecialty, int idOfFaculty) {
        setCodeOfSpecialty(codeOfSpecialty);
        setShortNameOfSpecialty(shortNameOfSpecialty);
        setFullNameOfSpecialty(fullNameOfSpecialty);
        setIdOfFaculty(idOfFaculty);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Specialty specialty = (Specialty) o;

        return idOfFaculty == specialty.idOfFaculty &&
                codeOfSpecialty.equals(specialty.codeOfSpecialty) &&
                shortNameOfSpecialty.equals(specialty.shortNameOfSpecialty) &&
                fullNameOfSpecialty.equals(specialty.fullNameOfSpecialty);
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

    public int getIdOfFaculty() {
        return idOfFaculty;
    }

    private void setIdOfFaculty(int idOfFaculty) {
        this.idOfFaculty = idOfFaculty;
    }
}
