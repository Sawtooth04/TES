import React, {useEffect, useRef, useState} from 'react';
import PasswordInput from "../UI/PasswordInput/PasswordInput";
import {Link, useNavigate} from "react-router-dom";

const Registration = () => {
    const [results, setResults] = useState(null);
    const loginInput = useRef(null);
    const emailInput = useRef(null);
    const passwordInput = useRef(null);
    const verificationPasswordInput = useRef(null);
    const navigate = useNavigate();

    useEffect(() => {
        if (results && results.total)
            navigate(`/registration/verification/${loginInput.current.value}`);
    }, [results]);

    async function registration() {
        let response = await fetch("/registration/register", {
            method: "post",
            headers: {"Content-Type": "application/json", "Accept": "application/json"},
            body: JSON.stringify({
                "name": loginInput.current.value,
                "password": passwordInput.current.value,
                "email": emailInput.current.value,
            })
        })
        if (response.ok)
            setResults(await response.json());
    }

    return (
        <div className={"registration smooth-show-component"}>
            <img className={"registration__logo"} src={"/assets/images/logo/logo-396-400.png"} alt={"Logo"}/>
            <div className={"registration__header registration-header"}>
                <p className={"registration-header__name registration-header__name_primary"}> Thoth </p>
                <p className={"registration-header__name registration-header__name_secondary"}> Education </p>
                <p className={"registration-header__name registration-header__name_primary"}> System </p>
            </div>
            <div className={"registration__logo-underline"}/>
            <input className={"registration__email-input registration__input"} placeholder={"Почта"} maxLength={320}
               ref={emailInput}/>
            <input className={"registration__login-input registration__input"} placeholder={"Логин"} maxLength={12}
               ref={loginInput}/>
            <PasswordInput className={"registration__password-input"} maxLength={12} placeholder={"Пароль"}
               inputRef={passwordInput}/>
            <PasswordInput className={"registration__verification-password-input"} maxLength={12}
               placeholder={"Подтвердите пароль"} inputRef={verificationPasswordInput}/>
            <button className={"registration__submit-button"} onClick={registration}> Зарегистрироваться </button>
            {(results && !results.isNameFree) ? <p className={"registration__error"}> Этот логин уже используется </p> : null}
            {(results && !results.isNameValid) ? <p className={"registration__error"}> Имя должно состоять минимум из 8 символов и максимум из 30 </p> : null}
            {(results && !results.isPasswordValid) ? <p className={"registration__error"}> Пароль должен состоять минимум из 8 символов и максимум из 20 </p> : null}
            {(results && !results.isEmailValid) ? <p className={"registration__error"}> Неверный формат электронной почты </p> : null}
            {(results && !results.isEmailFree) ? <p className={"registration__error"}> Эта почта уже используется </p> : null}
            <Link className={"registration__link"} to={"/login"}> Уже есть аккаунт? Войти. </Link>
        </div>
    );
};

export default Registration;