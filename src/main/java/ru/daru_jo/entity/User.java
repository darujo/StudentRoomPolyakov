package ru.daru_jo.entity;

public class User {
    private Long id;

    private String fio;

    private Integer  sex;

    private String specialization;

    private Long roomId;

    public User(Long id, String fio, Integer sex, String specialization) {
        this.id = id;
        this.fio = fio;
        this.sex = sex;
        this.specialization = specialization;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public Integer getSex() {
        return sex;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Long getRoomId() {
        return roomId;
    }
}

