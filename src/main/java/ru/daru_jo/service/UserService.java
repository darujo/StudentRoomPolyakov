package ru.daru_jo.service;

import ru.daru_jo.entity.FilterUser;
import ru.daru_jo.entity.User;
import ru.daru_jo.exceptions.UsernameNotFoundException;
import ru.daru_jo.repository.UserRepository;

import java.util.*;


public class UserService {
    private static UserService instance;
    public static UserService getInstance(){
        if(instance == null){
            instance = new UserService();
        }
        return instance;
    }
    public UserService() {
        this.userRepository = new UserRepository();
    }

    private UserRepository userRepository;

    public static User findAll(FilterUser filterStrait) {
        return userRepository.findAll(filterStrait);
    }

    public Optional<User> findByFio(String fio) {
        return userRepository.findByFio(fio);
    }

    public void checkNull(String filed, String text) {
        if (filed == null || filed.isEmpty()) {
            throw new UsernameNotFoundException("Не заполнено поле " + text);
        }
    }

    public User saveUser(User user) {

        checkNull(user.getFio(), "ФИО");

        if (user.getId() != null) {
            if (userRepository.findByFioAndIdIsNot(user.getFio(), user.getId()).isPresent()) {
                throw new UsernameNotFoundException("Уже есть пользователь с таким  ФИО");
            }
            User saveUser = userRepository.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("Пользователь с id " + user.getId() + " не найден"));
        } else {
            if (userRepository.findByFioIgnoreCase(user.getFio()).isPresent()) {
                throw new UsernameNotFoundException("Уже есть пользователь с таким ФИО");
            }
        }
        return userRepository.save(user);
    }

    public List<User> getUserList() {
        findAll(new FilterUser());
    }



}
