package ru.daru_jo.entity;

public class Room {
    private Long id;

    private Integer  sex;

    private String specialization;

    private Integer maxStudent;


    public Room(Long id, Integer sex, String specialization,Integer maxStudent) {
        this.id = id;
        this.sex = sex;
        this.maxStudent = maxStudent;
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

    public Integer getMaxStudent() {
        return maxStudent;
    }
}
