import cs from './ProfilePage.module.css';
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {getAccountMe, ifAccessAllow} from "../network/account";

const ProfilePage = () => {

    const [user, setUser] = useState({});
    const nav = useNavigate();

    useEffect(() => {
        ifAccessAllow(
            () => {
                getAccountMe().then(acc => {
                    setUser(acc);
                    console.log(acc);
                });
            },
            () => {
                nav("/login");
             }
        ).then();
    }, []);

    return (
        <div className={cs.wrapper}>
            <div className={cs.img_area}>
                <div className={cs.inner_area}>
                    <img src="https://cdn4.iconfinder.com/data/icons/interface-glyph-34/32/account-512.png" alt=""/>
                </div>
            </div>
            <div className={cs.icon + ' ' + cs.arrow}><i className="fas fa-arrow-left"></i></div>
            <div className={cs.icon + ' ' + cs.dots}><i className="fas fa-ellipsis-v"></i></div>
            <div className={cs.name}>{user.firstName} {user.lastName}</div>
            <div className={cs.about}>{user.accountRoles}</div>
            <div className={cs.social_icons}>
                <a href="#" className={cs.fb}><i className="fab fa-facebook-f"></i></a>
                <a href="#" className={cs.twitter}><i className="fab fa-twitter"></i></a>
                <a href="#" className={cs.insta}><i className="fab fa-instagram"></i></a>
                <a href="#" className={cs.yt}><i className="fab fa-youtube"></i></a>
            </div>
            <div className={cs.buttons}>
                <button onClick={() => nav("/appointments/available")}>Записаться</button>
                <button onClick={() => nav("/my/appointments")}>Записи</button>
            </div>
            <div className={cs.social_share}>
                <div className={cs.row}>
                    <i className="far fa-heart"></i>
                    <i className={cs.icon2 + ' fas fa-heart'}></i>
                    <span>&ensp;</span>
                </div>
                <div className={cs.row}>
                    <i className="far fa-comment"></i>
                    <i className={cs.icon2 + ' fas fa-comment'}></i>
                    <span>&ensp;</span>
                </div>
                <div className={cs.row}>
                    <i className="fas fa-share"></i>
                    <span>&ensp;</span>
                </div>
            </div>
        </div>
    );
}

export default ProfilePage;