CREATE TABLE roomsStorage (
	id INTEGER,
	name TEXT,
	sex INTEGER,
	specialization TEXT,
	max_student INTEGER
);
CREATE INDEX roomsStorage_id_IDX ON roomsStorage (id);
CREATE INDEX roomsStorage_specialization_IDX ON roomsStorage (specialization,sex);
