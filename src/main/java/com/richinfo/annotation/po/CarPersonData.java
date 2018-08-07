package com.richinfo.annotation.po;

import java.util.List;

public class CarPersonData {
    private String taskno;
    private String taskbatchno;
    private PersonsData personsData;
    private CarsData carsData;
    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTaskno() {
        return taskno;
    }

    public void setTaskno(String taskno) {
        this.taskno = taskno;
    }

    public String getTaskbatchno() {
        return taskbatchno;
    }

    public void setTaskbatchno(String taskbatchno) {
        this.taskbatchno = taskbatchno;
    }

    public CarsData getCarsData() {
        return carsData;
    }

    public void setCarsData(CarsData carsData) {
        this.carsData = carsData;
    }

    public PersonsData getPersonsData() {

        return personsData;
    }

    public void setPersonsData(PersonsData personsData) {
        this.personsData = personsData;
    }
}
