CREATE TABLE hospitals (
    hospital_id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    contact_phone VARCHAR(50) NOT NULL
);

CREATE TABLE hospital_rooms (
    hospital_id BIGINT NOT NULL,
    room VARCHAR(255) NOT NULL,
    FOREIGN KEY (hospital_id) REFERENCES hospitals (hospital_id)
);