CREATE TABLE usersStorage (
	id INTEGER,
	fio TEXT,
	sex INTEGER,
	specialization TEXT,
	room_id INTEGER
);
CREATE UNIQUE INDEX usersStorage_id_IDX ON usersStorage (id);
CREATE INDEX usersStorage_room_id_IDX ON usersStorage (room_id);
