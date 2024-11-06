import {getMyAppointments} from "../network/timetable";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {ifAccessAllow} from "../network/account";
import cs from "./AppointmentsPage.module.css";

const AppointmentsPage = () => {

    const [appointments, setAppointments] = useState(undefined);
    const nav = useNavigate();

    useEffect(() => {
        ifAccessAllow(
            () => {
                getMyAppointments().then(appointments => setAppointments(appointments.content));
            },
            () => {
                nav("/login");
            }
        ).then();
    }, []);

    return (
        <main>
            <span className={cs.title}>Ваши записи к врачам:</span>
            {appointments ? appointments.map(appointment =>
                <div className={cs.item}>
                    <span>Время: {appointment.time}</span>
                    <span>Кабинет: {appointment.room}</span>
                </div>
            ) : undefined}
        </main>
    );
}

export default AppointmentsPage;