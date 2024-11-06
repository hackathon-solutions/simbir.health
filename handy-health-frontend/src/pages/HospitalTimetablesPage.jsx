import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {ifAccessAllow} from "../network/account";
import cs from "./HospitalTimetablesPage.module.css";
import {createTimetable, getHospitalTimetables} from "../network/timetable";
import {getHospitalRooms} from "../network/hospital";

const HospitalTimetablesPage = () => {

    const nav = useNavigate();
    const { id } = useParams();

    const [timetables, setTimetables] = useState(undefined);
    const [doctors, setDoctors] = useState(undefined);
    const [rooms, setRooms] = useState(undefined);

    const [hospital, setHospital] = useState("");
    const [doctor, setDoctor] = useState("");
    const [room, setRoom] = useState("");
    const [from, setFrom] = useState("");
    const [to, setTo] = useState("");

    const [createPopupAddShow, setCreatePopupAddShow] = useState(false);

    useEffect(() => {
        ifAccessAllow(
            () => {
                getHospitalTimetables(id).then(timetables => setTimetables(timetables.content));
            },
            () => {
                nav("/login");
            }
        ).then();

        ifAccessAllow(
            () => {
                getHospitalRooms(id).then(rooms => setRooms(rooms));
            },
            () => {
                nav("/login");
            }
        ).then();
    }, []);

    function saveTimetable() {
        createTimetable(id, doctor, room, from, to).then();
        setHospital("");
        setDoctor("");
        setRoom("");
        setFrom("");
        setTo("");
        setCreatePopupAddShow(false);
    }

    return (
        <main>
            <span className={cs.title}>Расписание поликлиники:</span>
            <button onClick={() => setCreatePopupAddShow(true)}>добавить</button>
            {timetables ? timetables.map(timetable =>
                <div className={cs.item}>
                    <span>Поликлиника ID: {timetable.hospitalId.id}</span>
                    <span>Доктор ID: {timetable.doctorId.id}</span>
                    <span>Кабинет: {timetable.room}</span>
                    <span>Работает с: {timetable.from}</span>
                    <span>Работает до: {timetable.to}</span>
                </div>
            ) : undefined}

            { createPopupAddShow
                ? <div className={cs.popup}>
                    <b className={cs.close} onClick={() => setCreatePopupAddShow(false)}>X</b>
                    {/*<div>*/}
                    {/*    <span>Доктор: </span>*/}
                    {/*    <select value={doctor} onChange={(e) => setDoctor(e.target.value)}>*/}
                    {/*        <option>0</option>*/}
                    {/*        { doctors*/}
                    {/*            ? doctors.map(doctor => <option>{doctor}</option>)*/}
                    {/*            : undefined*/}
                    {/*        }*/}
                    {/*    </select>*/}
                    {/*</div>*/}
                    <input placeholder="acc id" value={doctor} onChange={(e) => setDoctor(e.target.value)}/>
                    <div>
                        <span>Кабинет: </span>
                        <select value={room} onChange={(e) => setRoom(e.target.value)}>
                            <option>0</option>
                            {rooms
                                ? rooms.map(room => <option>{room}</option>)
                                : undefined
                            }
                        </select>
                    </div>
                    <input type="datetime-local" placeholder="Работает с" value={from}
                           onChange={(e) => setFrom(e.target.value)}/>
                    <input type="datetime-local" placeholder="Работает до" value={to}
                           onChange={(e) => setTo(e.target.value)}/>
                    <button onClick={saveTimetable}>сохранить</button>
                </div>
                : undefined
            }
        </main>
    );
}

export default HospitalTimetablesPage;