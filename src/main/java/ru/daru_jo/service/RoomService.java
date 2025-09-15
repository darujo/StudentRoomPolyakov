package ru.daru_jo.service;

import ru.daru_jo.entity.FilterUser;
import ru.daru_jo.entity.User;
import ru.daru_jo.exceptions.UsernameNotFoundException;
import ru.daru_jo.repository.RoomRepository;

import java.util.List;
import java.util.Optional;


public class RoomService {
    private static RoomService instance;
    public static RoomService getInstance(){
        if(instance == null){
            instance = new RoomService();
        }
        return instance;
    }
    public RoomService() {
        this.roomRepository = new RoomRepository();
    }

    private final RoomRepository roomRepository;

    public List<User> findAll(FilterUser filterStrait) {
        return roomRepository.findAll(filterStrait);
    }

    public Optional<User> findByFio(String fio) {
        return roomRepository.findByFio(fio);
    }

    public void checkNull(String filed, String text) {
        if (filed == null || filed.isEmpty()) {
            throw new UsernameNotFoundException("Не заполнено поле " + text);
        }
    }

    public User saveUser(User user) {

        checkNull(user.getFio(), "ФИО");

        if (user.getId() != null) {
            if (roomRepository.findByFioAndIdIsNot(user.getFio(), user.getId()).isPresent()) {
                throw new UsernameNotFoundException("Уже есть пользователь с таким  ФИО");
            }
            User saveUser = roomRepository.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("Пользователь с id " + user.getId() + " не найден"));
        } else {
            if (roomRepository.findByFioIgnoreCase(user.getFio()).isPresent()) {
                throw new UsernameNotFoundException("Уже есть пользователь с таким ФИО");
            }
        }
        return roomRepository.save(user);
    }

    public List<User> getUserList() {
        return findAll(new FilterUser());
    }


    public User addUser(String fio, Boolean sex, String specialization) {
        User user = new User(null,fio,sex? 1:0,specialization);
        return saveUser(user);
    }
}
