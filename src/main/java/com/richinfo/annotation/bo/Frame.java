package com.richinfo.annotation.bo;

import java.util.List;

public class Frame {
   private String filename;
   private List<Car> car_list;
   private List<People> people_list;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<Car> getCar_list() {
        return car_list;
    }

    public void setCar_list(List<Car> car_list) {
        this.car_list = car_list;
    }

    public List<People> getPeople_list() {
        return people_list;
    }

    public void setPeople_list(List<People> people_list) {
        this.people_list = people_list;
    }
}
