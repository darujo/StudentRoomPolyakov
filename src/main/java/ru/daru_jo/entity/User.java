package ru.daru_jo.entity;

import ru.daru_jo.service.CodeService;

public class User {
    private Integer id;

    private String fio;

    private Integer  sex;

    private String specialization;

    private Integer roomId;

    public User(Integer id, String fio, Integer sex, String specialization, Integer roomId) {
        this.id = id;
        this.fio = fio;
        this.sex = sex;
        this.specialization = specialization;
        this.roomId = roomId;
    }

    public User(Integer id, String fio, Integer sex, String specialization) {
        this.id = id;
        this.fio = fio;
        this.sex = sex;
        this.specialization = specialization;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public Integer getSex() {
        return sex;
    }
    public String getSexStr() {
        return CodeService.getSex(sex == 1);
    }

    public String getSpecialization() {
        return specialization;
    }

    public Integer getRoomId() {
        return roomId;
    }
}

