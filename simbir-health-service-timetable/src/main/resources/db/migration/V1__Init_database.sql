CREATE TABLE doctor_timetables (
    timetable_id VARCHAR(55) PRIMARY KEY NOT NULL,
    hospital_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    room VARCHAR(500) NOT NULL,
    from_at TIMESTAMP NOT NULL,
    to_at TIMESTAMP NOT NULL,
    UNIQUE (doctor_id, hospital_id, from_at, to_at)
);

CREATE TABLE patient_appointments (
    appointment_id VARCHAR(55) PRIMARY KEY NOT NULL,
    doctor_timetable_id VARCHAR(55) NOT NULL,
    patient_id BIGINT NOT NULL,
    time TIMESTAMP NOT NULL,
    FOREIGN KEY (doctor_timetable_id) REFERENCES doctor_timetables (timetable_id)
);