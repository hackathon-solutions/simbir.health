import cs from './LoginPage.module.css';
import {useState} from "react";
import {authenticate} from '../network/account';
import {useNavigate} from "react-router-dom";

const LoginPage = () => {

    const nav = useNavigate();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    function btnSubmit() {
        authenticate(username, password).then(isAuthenticated => {
            if (!isAuthenticated) {
                alert('Not authenticated');
            } else {
                nav("/profile");
            }
        });
    }

    return (
        <div className={cs.container}>
            <div className={cs.screen}>
                <div className={cs.screen__content}>
                    <div className={cs.login}>
                        <div className={cs.login__field}>
                            <i className={cs.login__icon + ' fas fa-user'}></i>
                            <input type="text" className={cs.login__input} placeholder="User name / Email" value={username} onChange={(e) => setUsername(e.target.value)}/>
                        </div>
                        <div className={cs.login__field}>
                            <i className={cs.login__icon + ' fas fa-lock'}></i>
                            <input type="password" className={cs.login__input} placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                        </div>
                        <button className={'button ' + cs.login__submit} onClick={btnSubmit}>
                            <span className="button__text">Log In Now</span>
                            <i className={cs.button__icon + ' fas fa-chevron-right'}></i>
                        </button>
                    </div>
                    <div style={{display: 'none'}} className="social-login">
                        <h3>log in via</h3>
                        <div className="social-icons">
                            <a href="#" className="social-login__icon fab fa-instagram"></a>
                            <a href="#" className="social-login__icon fab fa-facebook"></a>
                            <a href="#" className="social-login__icon fab fa-twitter"></a>
                        </div>
                    </div>
                </div>
                <div className={cs.screen__background}>
                    <span className={cs.screen__background__shape + ' ' + cs.screen__background__shape4}></span>
                    <span className={cs.screen__background__shape + ' ' + cs.screen__background__shape3}></span>
                    <span className={cs.screen__background__shape + ' ' + cs.screen__background__shape2}></span>
                    <span className={cs.screen__background__shape + ' ' + cs.screen__background__shape1}></span>
                </div>
            </div>
        </div>
    );
}

export default LoginPage;