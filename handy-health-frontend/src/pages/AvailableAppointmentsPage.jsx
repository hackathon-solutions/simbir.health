import {useEffect, useState} from "react";
import {getDoctors, ifAccessAllow} from "../network/account";
import {useNavigate} from "react-router-dom";
import cs from "./AvailableAppointmentsPage.module.css";
import {createTimetableAppointment, getDoctorTimetables, getTimetableAppointments} from "../network/timetable";

const AvailableAppointmentsPage = () => {

    const [doctors, setDoctors] = useState(undefined);
    const [pickedDoctorTimetable, setPickedDoctorTimetable] = useState(undefined);
    const [timetableAppointments, setTimetableAppointments] = useState(undefined);
    const nav = useNavigate();

    useEffect(() => {
        ifAccessAllow(
            () => {
                getDoctors().then(doctors => setDoctors(doctors));
            },
            () => {
                nav("/login");
            }
        ).then();
    }, []);

    function btnClick(acc) {
        getDoctorTimetables(acc.accountId.id).then(doctorTimetables => {
            setPickedDoctorTimetable(doctorTimetables.content);

            if (!doctorTimetables.content || !doctorTimetables.content[0]) {
                return;
            }

            getTimetableAppointments(doctorTimetables.content[0].timetableId.id, doctorTimetables.content[0].from, doctorTimetables.content[0].to).then(appointments => setTimetableAppointments(appointments));
        });
    }

    function btnClose() {
        setPickedDoctorTimetable(undefined);
        setTimetableAppointments(undefined);
    }

    function createAppointment(time) {
        const timetableId = pickedDoctorTimetable[0].timetableId.id;
        createTimetableAppointment(timetableId, time).then(() => btnClose());
    }

    return (
        <main>
            {doctors ? doctors.content.map(acc =>
                <div className={cs.item}>
                    <span>{acc.firstName} {acc.lastName}</span>
                    <button onClick={() => btnClick(acc)}>Выбрать доктора</button>
                </div>
            ) : undefined}

            { pickedDoctorTimetable && pickedDoctorTimetable[0]
                ? <div className={cs.timetable}>
                    <b className={cs.close} onClick={btnClose}>X</b>
                    <span>Сегодня работает с: {pickedDoctorTimetable[0].from}</span>
                    <span>Работает до: {pickedDoctorTimetable[0].to}</span>
                    <span>В кабинете: {pickedDoctorTimetable[0].room}</span>
                    <div className={cs.times}>
                        { timetableAppointments
                            ? timetableAppointments.map(appointment => <button onClick={() => createAppointment(appointment.time)} className={cs.time}>{appointment.time}</button>)
                            : undefined
                        }
                    </div>
                  </div>
                : undefined
            }
        </main>
    );
}

export default AvailableAppointmentsPage;