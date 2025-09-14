package ru.daru_jo.entity;

import java.time.LocalDate;

public class FilterUser {
    private final String fio;
    private final Boolean sex;
    private final String specialization;
    private final Long room;

    public FilterUser(String fio, Boolean sex, String specialization, Long room) {
        this.fio = fio;
        this.sex = sex;
        this.specialization = specialization;
        this.room = room;
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

    public Long getRoom() {
        return room;
    }
}
