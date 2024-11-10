import cs from './LoginRegPage.module.css';
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import {authenticate, signup} from "../network/account";

const LoginRegPage = () => {

    const nav = useNavigate();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const [firstNameReg, setFirstNameReg] = useState('');
    const [lastNameReg, setLastNameReg] = useState('');
    const [usernameReg, setUsernameReg] = useState('');
    const [passwordReg, setPasswordReg] = useState('');

    function loginBtnSubmit() {
        authenticate(username, password).then(isAuthenticated => {
            if (!isAuthenticated) {
                alert('Not authenticated');
            } else {
                nav("/profile");
            }
        });
    }

    function regBtnSubmit() {
        signup(firstNameReg, lastNameReg, usernameReg, passwordReg).then();
        alert('Данные были отправлены');
    }

    return (
        <div className={cs.body}>
            <div className={cs.main}>
                <input type="checkbox" id="chk" className={cs.chk} aria-hidden="true"/>

                <div className={cs.signup}>
                    <div>
                        <label htmlFor="chk" aria-hidden="true">Регистрация</label>
                        <input value={firstNameReg} onChange={(e) => setFirstNameReg(e.target.value)} type="text"
                               placeholder="Имя" required=""/>
                        <input value={lastNameReg} onChange={(e) => setLastNameReg(e.target.value)} type="text"
                               placeholder="Фамилия" required=""/>
                        <input value={usernameReg} onChange={(e) => setUsernameReg(e.target.value)} type="text"
                               placeholder="Никнейм" required=""/>
                        <input value={passwordReg} onChange={(e) => setPasswordReg(e.target.value)} type="password"
                               placeholder="Пароль" required=""/>
                        <button onClick={regBtnSubmit}>Зарегистрироваться</button>
                    </div>
                </div>

                <div className={cs.login}>
                    <div>
                        <label htmlFor="chk" aria-hidden="true">Вход</label>
                        <input value={username} onChange={(e) => setUsername(e.target.value)} type="text"
                               placeholder="Никнейм" required=""/>
                        <input value={password} onChange={(e) => setPassword(e.target.value)} type="password"
                               placeholder="Пароль" required=""/>
                        <button onClick={loginBtnSubmit}>Войти</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default LoginRegPage;