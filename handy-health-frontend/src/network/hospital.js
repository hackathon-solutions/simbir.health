import network from './network_config.json';
import {getAccessToken} from "./account";

const HOSPITAL_SERVICE_ORIGIN = network.network.hospital.origin;

export async function getHospitals() {
    const api = HOSPITAL_SERVICE_ORIGIN + network.network.hospital.methods.hospitalsGet;
    const response = await fetch(api, {headers: {Authorization: 'Bearer ' + getAccessToken()}});
    return response.ok ? (await response.json()) : {};
}

export async function createHospital(name, address, contactPhone) {
    const api = HOSPITAL_SERVICE_ORIGIN + network.network.hospital.methods.createHospital;
    const response = await fetch(api, {method: "POST", headers: {Authorization: 'Bearer ' + getAccessToken(), "Content-Type": "application/json"}, body: JSON.stringify({name, address, contactPhone})});
    return response.ok ? (await response.json()) : {};
}

export async function getHospitalRooms(hospitalId) {
    const api = HOSPITAL_SERVICE_ORIGIN + network.network.hospital.methods.hospitalRoomsGet.replace(":hid", hospitalId);
    const response = await fetch(api, {headers: {Authorization: 'Bearer ' + getAccessToken()}});
    return response.ok ? (await response.json()) : {};
}

export async function changeHospital(hospitalId, hospital) {
    const api = HOSPITAL_SERVICE_ORIGIN + network.network.hospital.methods.changeHospital.replace(":hid", hospitalId);
    const response = await fetch(api, {method: "PUT", headers: {Authorization: 'Bearer ' + getAccessToken(), "Content-Type": "application/json"}, body: JSON.stringify(hospital)});
    return response.ok ? (await response.json()) : {};
}