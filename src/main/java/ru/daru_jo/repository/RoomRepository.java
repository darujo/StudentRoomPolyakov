package ru.daru_jo.repository;

import ru.daru_jo.entity.FilterUser;
import ru.daru_jo.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomRepository {

    public Optional<User> findByFio(String fio) {
        return Optional.empty();
    }

    public Optional<User> findByFioAndIdIsNot(String fio, Long id) {
        return Optional.empty();
    }

    public  Optional<User> findById(Long id) {
        return Optional.empty();
    }

    public Optional<User> findByFioIgnoreCase(String fio) {
        return Optional.empty();

    }

    public User save(User user) {
        return user;
    }

    public List<User> findAll() {
        return new ArrayList<>();
    }

    public List<User> findAll(FilterUser filterStrait) {
        return new ArrayList<>();
    }
}
