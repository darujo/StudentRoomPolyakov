package ru.daru_jo.entity;

public class FilterUser {
    private final String fio;
    private final Boolean sex;
    private final String specialization;
    private final Long roomId;

    public FilterUser(String fio, Boolean sex, String specialization, Long roomId) {
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

    public Long getRoomId() {
        return roomId;
    }
}
