package ru.daru_jo.entity;

public class FilterRoom {
    private Boolean  sex;

    private String specialization;



    public FilterRoom(Boolean sex, String specialization) {
        this.sex = sex;
        this.specialization = specialization;
    }

    public Boolean getSex() {
        return sex;
    }

    public String getSpecialization() {
        return specialization;
    }

}
