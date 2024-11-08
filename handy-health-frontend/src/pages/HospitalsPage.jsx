import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {ifAccessAllow} from "../network/account";
import {changeHospital, createHospital, getHospitals} from "../network/hospital";
import cs from "./HospitalsPage.module.css";

const HospitalsPage = () => {

    const nav = useNavigate();
    const [hospitals, setHospitals] = useState(undefined);

    const [hospitalNameAdd, setHospitalNameAdd] = useState("");
    const [hospitalAddressAdd, setHospitalAddressAdd] = useState("");
    const [hospitalContactPhoneAdd, setHospitalContactPhoneAdd] = useState("");

    const [hospitalActiveEdit, setHospitalActiveEdit] = useState({});
    const [hospitalNameEdit, setHospitalNameEdit] = useState("");
    const [hospitalAddressEdit, setHospitalAddressEdit] = useState("");
    const [hospitalContactPhoneEdit, setHospitalContactPhoneEdit] = useState("");
    const [hospitalRoomsEdit, setHospitalRoomsEdit] = useState([""]);

    const [createPopupAddShow, setCreatePopupAddShow] = useState(false);
    const [hospitalEditPopupShow, setHospitalEditPopupAddShow] = useState(false);

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

    function showChangeHospitalPopup(e, hospital) {
        setHospitalEditPopupAddShow(true);
        e.stopPropagation();

        setHospitalActiveEdit(hospital);
        setHospitalRoomsEdit(hospital.rooms && hospital.rooms[0] ? hospital.rooms : [""]);
        setHospitalContactPhoneEdit(hospital.contactPhone);
        setHospitalAddressEdit(hospital.address);
        setHospitalNameEdit(hospital.name);
    }

    function editHospital() {
        changeHospital(
            hospitalActiveEdit.hospitalId.id,
            {
                name: hospitalNameEdit,
                address: hospitalAddressEdit,
                contactPhone: hospitalContactPhoneEdit,
                rooms: hospitalRoomsEdit
            }
        ).then();
        closeEditHospital();
    }

    function closeEditHospital() {
        setHospitalEditPopupAddShow(false);
        setHospitalActiveEdit({});
        setHospitalRoomsEdit([""]);
        setHospitalContactPhoneEdit("");
        setHospitalAddressEdit("");
        setHospitalNameEdit("");
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
                <div key={hospital.hospitalId.id} onClick={() => pickHospital(hospital)} className={cs.item}>
                    <span>Название: {hospital.name}</span>
                    <span>Адрес: {hospital.address}</span>
                    <span>Контактный телефон: {hospital.contactPhone}</span>
                    <button onClick={(e) => showChangeHospitalPopup(e, hospital)}>изменить</button>
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

            { hospitalEditPopupShow
                ? <div className={cs.popup}>
                    <b className={cs.close} onClick={closeEditHospital}>X</b>
                    <input placeholder="имя" value={hospitalNameEdit}
                           onChange={(e) => setHospitalNameEdit(e.target.value)}/>
                    <input placeholder="адрес" value={hospitalAddressEdit}
                           onChange={(e) => setHospitalAddressEdit(e.target.value)}/>
                    <input placeholder="контакт" value={hospitalContactPhoneEdit}
                           onChange={(e) => setHospitalContactPhoneEdit(e.target.value)}/>
                    <div>
                        {hospitalRoomsEdit.map((room, idx) => <input key={idx} placeholder="room name" value={room} onChange={(e) => {hospitalRoomsEdit[idx] = e.target.value; setHospitalRoomsEdit([...hospitalRoomsEdit]); e.target.focus();}} />)}
                        <button onClick={() => setHospitalRoomsEdit([...hospitalRoomsEdit, ""])}>еще</button>
                    </div>
                    <button onClick={editHospital}>изменить</button>
                </div>
                : undefined
            }
        </main>
    );
}

export default HospitalsPage;