import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import {signout} from "../network/account";

const LogoutPage = () => {

    const nav = useNavigate();

    useEffect(() => {
        signout();
        nav("/login");
    });

    return (
        <div></div>
    );
}

export default LogoutPage;