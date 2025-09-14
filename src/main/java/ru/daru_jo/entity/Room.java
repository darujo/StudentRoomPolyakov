package ru.daru_jo.entity;

public class Room {
    private Long id;

    private Integer  sex;

    private String specialization;

    public Room(Long id, Integer sex, String specialization) {
        this.id = id;
        this.sex = sex;
        this.specialization = specialization;
    }

    public Long getId() {
        return id;
    }

    public Integer getSex() {
        return sex;
    }

    public String getSpecialization() {
        return specialization;
    }
}
