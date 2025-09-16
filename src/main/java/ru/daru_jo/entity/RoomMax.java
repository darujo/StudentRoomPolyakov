package ru.daru_jo.entity;

import ru.daru_jo.service.CodeService;

public class RoomMax {
    private Integer id;
    private String name;

    private Integer sex;

    private String specialization;

    private Integer maxStudent;
    private Integer countStudent;

    public RoomMax(Integer id, String name, Integer sex, String specialization, Integer countStudent, Integer maxStudent) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.maxStudent = maxStudent;
        this.countStudent = countStudent;
        this.specialization = specialization;
    }

    public Integer getId() {
        return id;
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

    public Integer getMaxStudent() {
        return maxStudent;
    }

    public String getName() {
        return name;
    }

    public Integer getCountStudent() {
        return countStudent;
    }
}
