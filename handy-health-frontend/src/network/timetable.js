import network from './network_config.json';
import {getAccessToken} from "./account";

const TIMETABLE_SERVICE_ORIGIN = network.network.timetable.origin;

export async function getDoctorTimetables(doctorId) {
    const now = new Date();
    const startOfDay = new Date(now.getFullYear(), now.getMonth(), now.getDate());
    const startOfDayISO = startOfDay.toISOString();
    const endOfDay = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 23, 59, 59, 999);
    const endOfDayISO = endOfDay.toISOString();

    const api = TIMETABLE_SERVICE_ORIGIN + network.network.timetable.methods.doctorTimetables;
    const response = await fetch(`${api}/${doctorId}?page=0&size=20&from=${startOfDayISO}&to=${endOfDayISO}`, {headers: {Authorization: `Bearer ${getAccessToken()}`}});
    return response.ok ? (await response.json()) : {};
}

export async function getHospitalTimetables(hospitalId) {
    const now = new Date();
    const startOfDay = new Date(now.getFullYear(), now.getMonth(), now.getDate());
    const startOfDayISO = startOfDay.toISOString();
    const endOfDay = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 23, 59, 59, 999);
    const endOfDayISO = endOfDay.toISOString();

    const api = TIMETABLE_SERVICE_ORIGIN + network.network.timetable.methods.hospitalTimetables;
    const response = await fetch(`${api}/${hospitalId}?page=0&size=20&from=${startOfDayISO}&to=${endOfDayISO}`, {headers: {Authorization: `Bearer ${getAccessToken()}`}});
    return response.ok ? (await response.json()) : {};
}

export async function getTimetableAppointments(timetableId, from, to) {
    const api = TIMETABLE_SERVICE_ORIGIN + network.network.timetable.methods.doctorTimetableAppointments.replace(":tid", timetableId);
    const response = await fetch(`${api}?from=${from}&to=${to}`, {headers: {Authorization: 'Bearer ' + getAccessToken()}});
    return response.ok ? (await response.json()) : {};
}

export async function createTimetableAppointment(timetableId, time) {
    const api = TIMETABLE_SERVICE_ORIGIN + network.network.timetable.methods.appointmentCreate.replace(":tid", timetableId);
    const response = await fetch(`${api}?time=${time}`, {method: "POST", headers: {Authorization: 'Bearer ' + getAccessToken()}});
    return response.ok ? (await response.json()) : {};
}

export async function getMyAppointments() {
    const api = TIMETABLE_SERVICE_ORIGIN + network.network.timetable.methods.myAppointments;
    const response = await fetch(api, {headers: {Authorization: 'Bearer ' + getAccessToken()}});
    return response.ok ? (await response.json()) : {};
}

export async function createTimetable(hospitalId, doctorId, room, from, to) {
    const req = {
        hospitalId: {
            id: hospitalId
        },
        doctorId: {
            id: doctorId
        },
        room,
        from,
        to
    };
    const api = TIMETABLE_SERVICE_ORIGIN + network.network.timetable.methods.createTimetable;
    const response = await fetch(api, {method: "POST", headers: {Authorization: 'Bearer ' + getAccessToken(), "Content-Type": "application/json"}, body: JSON.stringify(req)});
    return response.ok ? (await response.json()) : {};
}