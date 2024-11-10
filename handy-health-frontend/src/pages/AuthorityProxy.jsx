import {isAuthenticated} from '../network/account';
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import cs from './AuthorityProxy.module.css';

import profileIcon from '../assets/profile-icon.gif';

const AuthorityProxy = ({componentIfAuth, loginPath}) => {

    const nav = useNavigate();

    useEffect(() => {
        if (!isAuthenticated()) {
            nav(loginPath);
        }
    });

    return (
        <div data-proxy-authenticated="">
            { componentIfAuth }

            <div className={cs.profile_container} onClick={() => nav("/profile")}>
                <div className={cs.circle}>
                    <img className={cs.icon} src={profileIcon} alt=""/>
                </div>
            </div>
        </div>
    );
}

export default AuthorityProxy;