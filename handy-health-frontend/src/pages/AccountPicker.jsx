import cs from './AccountPicker.module.css';
import {useEffect, useState} from "react";
import {getAccountsByRole, getDoctorAccounts, ifAccessAllow} from "../network/account";
import {useNavigate} from "react-router-dom";

let safeTimeout = null;

const AccountPicker = ({setStatePickedItem, searchRole, onDismiss, onPreparePickedItem = (a) => a}) => {

    const nav = useNavigate();
    const [searchName, setSearchName] = useState("");
    const [pageN, setPageN] = useState(0);

    const [accounts, setAccounts] = useState(undefined);

    useEffect(() => {
        ifAccessAllow(
            () => {
                if (searchName) {
                    getDoctorAccounts(searchName, pageN, 20).then(accounts => setAccounts(accounts.content))
                } else {
                    getAccountsByRole(searchRole, pageN, 20).then(accounts => setAccounts(accounts.content));
                }
            },
            () => {
                nav("/login");
            }
        ).then();
    }, [searchName, pageN]);

    function safeTimeoutFunc(e) {
        clearTimeout(safeTimeout);
        safeTimeout = setTimeout(() => setSearchName(e.target.value), 700);
    }

    return (
        <div className={cs.main}>
            <b className={cs.close} onClick={onDismiss}>X</b>
            <input placeholder="search" type="search" onChange={safeTimeoutFunc}/>
            {accounts
                ? accounts.map(account =>
                    <div className={cs.item}>
                        <span>{account.firstName} {account.lastName}</span>
                        <button onClick={() => {
                            setStatePickedItem(onPreparePickedItem(account));
                            onDismiss();
                        }}>
                            выбрать
                        </button>
                    </div>
                )
                : undefined
            }
            <div className={cs.page_control}>
                <button onClick={() => setPageN(pageN - 1)}>меньше</button>
                <button onClick={() => setPageN(pageN + 1)}>больше</button>
            </div>
        </div>
    );
}

export default AccountPicker;