import React from 'react';
import PasswordInput from "../UI/PasswordInput/PasswordInput";
import {Link} from "react-router-dom";

const Registration = () => {
    return (
        <div className={"registration smooth-show-component"}>
            <img className={"registration__logo"} src={"/assets/images/logo/logo-396-400.png"} alt={"Logo"}/>
            <div className={"registration__header header"}>
                <p className={"header__name header__name_primary"}> Thoth </p>
                <p className={"header__name header__name_secondary"}> Education </p>
                <p className={"header__name header__name_primary"}> System </p>
            </div>
            <div className={"registration__logo-underline"}/>
            <input className={"registration__email-input registration__input"} placeholder={"Почта"} maxLength={320}/>
            <input className={"registration__login-input registration__input"} placeholder={"Логин"} maxLength={12}/>
            <PasswordInput className={"registration__password-input"} maxLength={12} placeholder={"Пароль"}/>
            <PasswordInput className={"registration__verification-password-input"} maxLength={12} placeholder={"Подтвердите пароль"}/>
            <button className={"registration__submit-button"}> Зарегистрироваться </button>
            <Link className={"registration__link"} to={"/login"}> Уже есть аккаунт? Войти. </Link>
        </div>
    );
};

export default Registration;