package ru.daru_jo.service;

import java.util.HashSet;
import java.util.Set;

public class CodeService {

    private static Set<String>  specializations;
    private static Set<String>  sex;

    public static Set<String> getSpecializations() {
        if (specializations ==  null){
            specializations = new HashSet<>();
            specializations.add("Разработка ПО");
            specializations.add("Тестирование ПО");
        }
        return specializations;
    }


    public static Set<String> getSex() {
        if (sex ==  null){
            sex = new HashSet<>();
            sex.add("Мужской");
            sex.add("Женский");
        }
        return sex;
    }

    public static boolean isMan(String sex) {
        return sex.equalsIgnoreCase("Мужской");
    }
}
