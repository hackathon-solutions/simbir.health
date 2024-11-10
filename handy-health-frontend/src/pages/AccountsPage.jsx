import {useEffect, useState} from "react";
import {getAccounts, ifAccessAllow, setAccount} from "../network/account";
import {useNavigate} from "react-router-dom";
import cs from "./AccountsPage.module.css";
import {roles} from "aria-query";

const AccountsPage = () => {

    const nav = useNavigate();
    const [accounts, setAccounts] = useState([]);
    const [pageN, setPageN] = useState(0);

    const [activeAccountEdit, setActiveAccountEdit] = useState(undefined);
    const [accountFirstNameEdit, setAccountFirstNameEdit] = useState("");
    const [accountLastNameEdit, setAccountLastNameEdit] = useState("");
    const [accountUsernameEdit, setAccountUsernameEdit] = useState("");
    const [accountRolesEdit, setAccountRolesEdit] = useState([""]);

    const [accountEditPopupShow, setAccountEditPopupShow] = useState(false);

    useEffect(() => {
        ifAccessAllow(
            () => {
                getAccounts(pageN, 20).then(accounts => setAccounts(accounts.content));
            },
            () => {
                nav("/login");
            }
        ).then();
    }, [pageN]);

    function showChangeAccount(e, account) {
        setAccountEditPopupShow(true);
        setActiveAccountEdit(account);
        setAccountFirstNameEdit(account.firstName);
        setAccountLastNameEdit(account.lastName);
        setAccountUsernameEdit(account.credentials.username);
        setAccountRolesEdit(account.accountRoles ? account.accountRoles : [""]);
    }

    function closeEditAccount() {
        setAccountEditPopupShow(false);
        setActiveAccountEdit(undefined);
        setAccountFirstNameEdit("");
        setAccountLastNameEdit("");
        setAccountUsernameEdit("");
        setAccountRolesEdit([""]);
    }

    function editAccount() {
        const account = {
            firstName: accountFirstNameEdit,
            lastName: accountLastNameEdit,
            credentials: {
                username: accountUsernameEdit
            },
            roles: accountRolesEdit
        };
        setAccount(activeAccountEdit.accountId.id, account).then();
        closeEditAccount();
    }

    return (
        <main>
            <span className={cs.title}>Зарегистрированные аккаунты:</span>
            { accounts
                ? accounts.map(account =>
                    <div className={cs.item}>
                        <span><b>ID:</b> {account.accountId.id}</span>
                        <span><b>Имя:</b> {account.firstName}</span>
                        <span><b>Фамилия:</b> {account.lastName}</span>
                        <span><b>Никнейм:</b> {account.credentials.username}</span>
                        <span><b>Роли:</b> {account.accountRoles}</span>
                        <button onClick={(e) => showChangeAccount(e, account)}>изменить</button>
                    </div>
                )
                : <span>Упс. Пусто :(</span>
            }

            { accountEditPopupShow
                ? <div className={cs.popup}>
                    <b className={cs.close} onClick={closeEditAccount}>X</b>
                    <input placeholder="имя" value={accountFirstNameEdit}
                           onChange={(e) => setAccountFirstNameEdit(e.target.value)}/>
                    <input placeholder="фамилия" value={accountLastNameEdit}
                           onChange={(e) => setAccountLastNameEdit(e.target.value)}/>
                    <input placeholder="никнейм" value={accountUsernameEdit}
                           onChange={(e) => setAccountUsernameEdit(e.target.value)}/>
                    <div>
                        {accountRolesEdit.map((role, idx) =>
                            <select value={role} onChange={(e) => {accountRolesEdit[idx] = e.target.value; setAccountRolesEdit([...accountRolesEdit]); e.target.focus();}}>
                                <option></option>
                                <option>USER</option>
                                <option>MANAGER</option>
                                <option>DOCTOR</option>
                                <option>ADMIN</option>
                            </select>
                        )}
                        <button onClick={() => setAccountRolesEdit([...accountRolesEdit, ""])}>еще</button>
                    </div>
                    <button onClick={editAccount}>изменить</button>
                </div>
                : undefined
            }
        </main>
    );
}

export default AccountsPage;