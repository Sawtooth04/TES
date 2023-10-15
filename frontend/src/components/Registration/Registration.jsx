import React, {useRef} from 'react';
import PasswordInput from "../UI/PasswordInput/PasswordInput";
import {Link, useNavigate} from "react-router-dom";

const Registration = () => {
    const loginInput = useRef(null);
    const emailInput = useRef(null);
    const passwordInput = useRef(null);
    const verificationPasswordInput = useRef(null);
    const navigate = useNavigate();

    async function registration() {
        let response = await fetch("/registration/register", {
            method: "post",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                "name": loginInput.current.value,
                "password": passwordInput.current.value,
                "email": emailInput.current.value,
            })
        })
        if (response.ok)
            navigate("/login");
    }

    return (
        <div className={"registration smooth-show-component"}>
            <img className={"registration__logo"} src={"/assets/images/logo/logo-396-400.png"} alt={"Logo"}/>
            <div className={"registration__header header"}>
                <p className={"header__name header__name_primary"}> Thoth </p>
                <p className={"header__name header__name_secondary"}> Education </p>
                <p className={"header__name header__name_primary"}> System </p>
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
            <Link className={"registration__link"} to={"/login"}> Уже есть аккаунт? Войти. </Link>
        </div>
    );
};

export default Registration;