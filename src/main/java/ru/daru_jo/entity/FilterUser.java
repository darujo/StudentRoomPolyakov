package ru.daru_jo.entity;

public class FilterUser {
    private String fio = null;
    private Boolean sex = null;
    private String specialization =null;
    private Integer roomId= null;

    public FilterUser(String fio, Boolean sex, String specialization, Integer roomId) {
        this.fio = fio;
        this.sex = sex;
        this.specialization = specialization;
        this.roomId = roomId;
    }

    public FilterUser() {
    }

    public String getFio() {
        return fio;
    }

    public Boolean getSex() {
        return sex;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Integer getRoomId() {
        return roomId;
    }
}
