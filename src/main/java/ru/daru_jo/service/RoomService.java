package ru.daru_jo.service;

import ru.daru_jo.entity.FilterRoom;
import ru.daru_jo.entity.Room;
import ru.daru_jo.entity.RoomMax;
import ru.daru_jo.exceptions.UsernameNotFoundException;
import ru.daru_jo.repository.RoomRepository;

import java.util.List;


public class RoomService {
    private static RoomService instance;

    public static RoomService getInstance() {
        if (instance == null) {
            instance = new RoomService();
        }
        return instance;
    }
    private final RoomRepository roomRepository;
    private final UserRoomService userRoomService;

    public RoomService() {
        instance = this;
        this.roomRepository = new RoomRepository();
        this.userRoomService = UserRoomService.getInstance();
    }


    public List<Room> findAll(FilterRoom filterRoom) {
        return roomRepository.findAll(filterRoom);
    }

    public void checkNull(String filed, String text) {
        if (filed == null || filed.isEmpty()) {
            throw new UsernameNotFoundException("Не заполнено поле " + text);
        }
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> getRoomList(Boolean sex, String specialization) {
        return findAll(new FilterRoom(sex, specialization));
    }


    public Room addRoom(String name, Boolean sex, String specialization, Integer maxStudent) {
        Room room = new Room(null, name, sex ? 1 : 0, specialization, maxStudent);
        return saveRoom(room);
    }

    public RoomMax roomToRoomMax(Room room) {
        return userRoomService.roomToRoomMax (room);
    }
    public List<RoomMax> getRoomMaxList(Boolean sex, String specialization) {
        return userRoomService.getRoomMaxList(sex,specialization);
    }
}
