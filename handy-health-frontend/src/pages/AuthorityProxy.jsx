import {isAuthenticated} from '../network/account';
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

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
        </div>
    );
}

export default AuthorityProxy;