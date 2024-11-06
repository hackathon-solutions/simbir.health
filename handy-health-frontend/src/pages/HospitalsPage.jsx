import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {ifAccessAllow} from "../network/account";
import {createHospital, getHospitals} from "../network/hospital";
import cs from "./HospitalsPage.module.css";

const HospitalsPage = () => {

    const nav = useNavigate();
    const [hospitals, setHospitals] = useState(undefined);

    const [createPopupAddShow, setCreatePopupAddShow] = useState(false);
    const [hospitalNameAdd, setHospitalNameAdd] = useState("");
    const [hospitalAddressAdd, setHospitalAddressAdd] = useState("");
    const [hospitalContactPhoneAdd, setHospitalContactPhoneAdd] = useState("");

    useEffect(() => {
        ifAccessAllow(
            () => {
                getHospitals().then(hospitals => setHospitals(hospitals.content));
            },
            () => {
                nav("/login");
            }
        ).then();
    }, []);

    function saveHospital() {
        createHospital(hospitalNameAdd, hospitalAddressAdd, hospitalContactPhoneAdd).then();
        setCreatePopupAddShow(false);
        setHospitalNameAdd("");
        setHospitalAddressAdd("");
        setHospitalContactPhoneAdd("");
    }

    function pickHospital(hospital) {
        const hospitalId = hospital.hospitalId.id;
        nav(`/timetables/hospitals/${hospitalId}`);
    }

    return (
        <main>
            <span className={cs.title}>Зарегистрированные поликлиники:</span>
            <button onClick={() => setCreatePopupAddShow(true)}>добавить</button>
            {hospitals ? hospitals.map(hospital =>
                <div onClick={() => pickHospital(hospital)} className={cs.item}>
                    <span>Название: {hospital.name}</span>
                    <span>Адрес: {hospital.address}</span>
                    <span>Контактный телефон: {hospital.contactPhone}</span>
                </div>
            ) : undefined}

            { createPopupAddShow
                ? <div className={cs.popup}>
                    <b className={cs.close} onClick={() => setCreatePopupAddShow(false)}>X</b>
                    <input placeholder="имя" value={hospitalNameAdd}
                           onChange={(e) => setHospitalNameAdd(e.target.value)}/>
                    <input placeholder="адрес" value={hospitalAddressAdd}
                           onChange={(e) => setHospitalAddressAdd(e.target.value)}/>
                    <input placeholder="контакт" value={hospitalContactPhoneAdd}
                           onChange={(e) => setHospitalContactPhoneAdd(e.target.value)}/>
                    <button onClick={saveHospital}>сохранить</button>
                </div>
                : undefined
            }
        </main>
    );
}

export default HospitalsPage;