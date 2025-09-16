package ru.daru_jo.service;

import ru.daru_jo.entity.Room;
import ru.daru_jo.entity.RoomMax;
import ru.daru_jo.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserRoomService {
    private static UserRoomService instance;

    public static UserRoomService getInstance() {
        if (instance == null) {
            instance = new UserRoomService();
        }
        return instance;
    }

    RoomService roomService;
    UserService userService;

    public UserRoomService() {
        instance = this;
        roomService = RoomService.getInstance();
        userService = UserService.getInstance();
    }

    public User addRoomIdForUser(User user) {
        List<RoomMax> rooms = getRoomMaxList(user.getSex() == 1, user.getSpecialization());
        for (RoomMax room : rooms) {
            if (room.getMaxStudent() > room.getCountStudent()) {
                user.setRoomId(room.getId());
                return user;
            }
        }
        return user;
    }

    public List<RoomMax> getRoomMaxList(Boolean sex, String specialization) {
        return roomService.getRoomList(sex, specialization).stream().map(this::roomToRoomMax).collect(Collectors.toList());
    }

    public RoomMax roomToRoomMax(Room room) {
        return new RoomMax(room.getId(), room.getName(), room.getSex(), room.getSpecialization(), userService.countUser(room.getId()), room.getMaxStudent());
    }
}
